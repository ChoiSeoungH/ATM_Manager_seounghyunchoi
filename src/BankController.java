package src;
//[1]관리자 [2]사용자 [0]종료

//관리자
//[1]회원목록 [2]회원 수정 [3]회원 삭제 [4]데이터 저장 [5]데이터 불러오기

//회원 수정 : 회원 아이디 검색,비밀번호, 이름 수정가능
//회원 삭제 : 회원아이디 검색
//데이터 저장 : account.txt , client.txt


//사용자 메뉴
//[1]회원가입 [2]로그인 [0]뒤로가기
// 회원가입 : 회원 아이디 중복 확인

// 로그인 메뉴
//[1]계좌추가 [2]계좌삭제 [3]입금 [4]출금 [5]이체 [6]탈퇴 [7]마이페이지 [0]로그아웃

//계좌 추가 1111-1111-1111 일치할때 추가 가능 : 중복확인
//계좌 삭제 : 본인 회원 계좌만 가능

// 입금 : accList에 계좌가 있을때만 입금 가능 : 100원 이상 입금/이체/출금 : 계죄 잔고만큼만
// 이체 : 이체할 계좌랑 이체받을 계좌 일치 불가

// 탈퇴 : 패스워드 다시 입력 -> 탈퇴 가능

// 마이페이지 : 내계좌(+잔고) 목록 확인
public class BankController {

  private final String bankName = "우리은행";
  private AccountDAO accountDAO;
  private ClientDAO clientDAO;
  private String log;

  public BankController() {
    accountDAO = new AccountDAO();
    clientDAO = new ClientDAO();
    Util.tempData();
    Util.loadFromFile(accountDAO, clientDAO);

  }

  public void run() {

    while (true) {
      System.out.println("============" + bankName + "============");
      ShowMenu("[1]관리자 [2]사용자 [0]종료");
      int menu = Util.getValue("메뉴 >> ", 0, 2);
      switch (menu) {
        case 0:
          System.out.println("종료");
          return;
        case 1:
          outer1:
          while (true) {
            ShowMenu("[1]회원목록 [2]회원 수정 [3]회원 삭제 [4]데이터 저장 [5]데이터 불러오기 [6]전체계좌 [0]뒤로가기");
            menu = Util.getValue("메뉴 >> ", 0, 6);
            switch (menu) {
              case 0:
                System.out.println("뒤로가기");
                break outer1;
              case 1: //회원목록
                clientDAO.printAllClient(accountDAO);
                break;
              case 2: //회원수정
                clientDAO.updateClient();
                break;
              case 3: //회원삭제
                clientDAO.deleteClient(accountDAO);
                break;
              case 4: //데이터 저장
                Util.saveDataToFile(accountDAO, clientDAO);
                break;
              case 5: //데이터 불러오기
                Util.printAllData();
                break;
              case 6: //전체
                accountDAO.printAllAccount(accountDAO.getAccList());
                break;
            }

          }//eow
          break;//eoc1
        case 2:
          outer2:
          while (true) {
            ShowMenu("[1]회원가입 [2]로그인 [0]뒤로가기");
            menu = Util.getValue("메뉴 >> ", 0, 2);
            switch (menu) {
              case 0:
                System.out.println("뒤로가기");
                break outer2;
              case 1: //회원가입
                clientDAO.AddClient();
                break;
              case 2: //로그인
                log = clientDAO.login();
                if (log != null) {
                  System.out.printf("%s님 로그인 되었습니다.%n", log);
                }
                outer3:
                while (log != null) {
                  ShowMenu("[1]계좌추가 [2]계좌삭제 [3]입금 [4]출금 [5]이체 [6]탈퇴 [7]마이페이지 [0]로그아웃");
                  menu = Util.getValue("메뉴 >> ", 0, 7);
                  switch (menu) {
                    case 0:
                      log = null;
                      System.out.println("로그아웃");
                      break outer3;
                    case 1: //계좌추가
                      accountDAO.addAccount(log);
                      break;
                    case 2: //계좌삭제
                      accountDAO.deleteAccount(log);
                      break;
                    case 3: //입금
                      accountDAO.deposit(log);
                      break;
                    case 4: //출금
                      accountDAO.withdraw(log);
                      break;
                    case 5: //이체
                      accountDAO.transfer(log);
                      break;
                    case 6: //탈퇴
                      log = clientDAO.deleteClient(log, accountDAO);
                      break;
                    case 7: //마이페이지
                      accountDAO.printMyAccount(log);
                      break;
                  }
                }
                break;//eoc2
            }

          }
      }//eoOuterS

    }//eow


  }//eom


  private void ShowMenu(String msg) {
    System.out.println(msg);
  }


}
