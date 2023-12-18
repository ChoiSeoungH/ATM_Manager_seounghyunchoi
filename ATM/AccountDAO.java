package ATM;

import School_정답.Student;

public class AccountDAO {
  Account[] accList;
  int cnt;
  Util u;

  public AccountDAO() {
    u = new Util();
  }

  void addAccountFromData(String accountData) {
    String[] temp = accountData.split("\n");
    cnt = temp.length;
    accList = new Account[cnt];
    int idx = 0;
    for (String t : temp) {
      String[] info = t.split("/");
      accList[idx++] = new Account(info[0], info[1], Integer.parseInt(info[2]));
    }
  }

  String saveAsFileData() {
    if (cnt == 0) return "";
    String data = "";
    for (Account a : accList) {
      data += a.saveToData();
    }
    return data;
  }

  public void addAccount(String log) {
    int cnt = 0;
    System.out.println("내 계좌 목록 : ");
    for (Account a : accList) {
      if (a.clientId.equals(log)) {
        cnt += 1;
        System.out.println(a.accNumber + " " + a.money + "원");
      }
    }
    if (cnt == 3) {
      System.out.println("더이상 계좌를 만들수 없습니다.");
    }

    String accNo = u.getValue("가입할 계좌번호 >> ");
    if (!checkAccountNo(accNo)) {
      System.out.println(accList[0].accNumber + "형태로 입력해주세요");
      return;
    }
    int delIdx = findAccountNo(accNo);
    if (delIdx != -1) {
      System.out.println("계좌번호 중복");
      return;
    }

    Account[] temp = accList;
    accList = new Account[this.cnt + 1];
    for (int i = 0; i < this.cnt; i++) {
      accList[i] = temp[i];
    }
    accList[this.cnt++] = new Account(log, accNo, 1000);
  }

  boolean checkAccountNo(String accNo) {
    if (accNo.length() != accList[0].accNumber.length()) {
      return false;
    }
    int[] correctNum = {0, 1, 2, 3, 5, 6, 7, 8, 10, 11, 12, 13};
    int[] correctDeLim = {4, 9};
    for (int i : correctNum) {
      if (accNo.charAt(i) < '0' || accNo.charAt(i) > '9') {
        return false;
      }
    }
    for (int i : correctDeLim) {
      if (accNo.charAt(i) != '-') {
        return false;
      }
    }
    return true;
  }

  public void deleteAccount(String log) {
    printMyAccount(log);
    String accNo = u.getValue("삭제할 계좌번호 >> ");
    int delIdx = findAccountNo(accNo,log);
    if (delIdx == -1) {
      System.out.println("일치하는 계좌가 없습니다");
      return;
    }
    Account[] temp = accList;
    accList = new Account[cnt - 1];
    int idx = 0;
    for (int i = 0; i < cnt; i++) {
      if (i != delIdx) {
        accList[idx++] = temp[i];
      }
    }
    cnt -= 1;

  }

  public void deleteAllAccount(String log) {
    for (int j = 0; j < accList.length; j++) {
      if (accList[j].clientId.equals(log)) {
        Account[] temp = accList;
        accList = new Account[cnt - 1];
        int idx = 0;
        for (int i = 0; i < cnt; i++) {
          if (i != j) {
            accList[idx++] = temp[i];
          }
        }
        cnt -= 1;
      }
    }
  }


  boolean printMyAccount(String log) {
    int cnt = 0;
    System.out.println("내 계좌 목록 : ");
    for (Account a : accList) {
      if (a.clientId.equals(log)) {
        cnt += 1;
        System.out.println(a.accNumber + " " + a.money + "원");
      }
    }
    return cnt == 0 ? false : true;
  }

  int findAccountNo(String accNo) {
    for (int i = 0; i < accList.length; i++) {
      if (accList[i].accNumber.equals(accNo)) {
        return i;
      }
    }
    return -1;
  }

  int findAccountNo(String accNo,String log) {
    for (int i = 0; i < accList.length; i++) {
      if (accList[i].accNumber.equals(accNo) && accList[i].clientId.equals(log)) {
        return i;
      }
    }
    return -1;
  }

  public void deposit(String log) {
    if (!printMyAccount(log)) {
      System.out.println("계좌가 없습니다");
      return;
    }

    String accNo = u.getValue("입금할 계좌번호 >> ");
    int idx = findAccountNo(accNo,log);
    if (idx == -1) {
      System.out.println("계좌번호가 맞지않습니다.");
      return;
    }


    int money = u.getValue("100 ~ 10000000원 입금가능 >> ", 100, 10000000);
    accList[idx].money += money;
    System.out.println("입금완료");
    System.out.println(accList[idx]);
  }

  public void withdraw(String log) {
    if (!printMyAccount(log)) {
      System.out.println("계좌가 없습니다");
      return;
    }

    String accNo = u.getValue("출금할 계좌번호 >> ");
    int idx = findAccountNo(accNo,log);
    if (idx == -1) {
      System.out.println("계좌번호가 맞지않습니다.");
      return;
    }

    int money = u.getValue("100 ~ %d원 출금가능 >> ".formatted(accList[idx].money), 100, accList[idx].money);
    accList[idx].money -= money;
    System.out.println(accList[idx]);
    System.out.println("출금완료");
  }

  public void transfer(String log) {
    if (!printMyAccount(log)) {
      System.out.println("계좌가 없습니다");
      return;
    }
    String fromNo = u.getValue("이체할 계좌번호 >> ");
    int fromIdx = findAccountNo(fromNo,log);
    if (fromIdx == -1) {
      System.out.println("계좌번호가 맞지않습니다.");
      return;
    }

    for (Account a : accList) {
      System.out.println(a);
    }
    String toNo = u.getValue("이체받을 계좌번호 >> ");
    int toIdx = findAccountNo(toNo);
    if (toIdx == -1) {
      System.out.println("계좌번호가 맞지않습니다.");
      return;
    }
    if (fromIdx == toIdx) {
      System.out.println("이체할 계좌번호와 이체받을 계좌번호가 같습니다.");
      return;
    }
    int money = u.getValue("100 ~ %d원 출금가능 >> ".formatted(accList[fromIdx].money), 100, accList[fromIdx].money);
    accList[toIdx].money += money;
    accList[fromIdx].money -= money;
    System.out.println(accList[fromIdx]);
    System.out.println(accList[toIdx]);
    System.out.println("이체완료");
  }

  void printAllAccount(Account[] accList) {
    for (Account a : accList) {
      System.out.println(a);
    }
  }
}
