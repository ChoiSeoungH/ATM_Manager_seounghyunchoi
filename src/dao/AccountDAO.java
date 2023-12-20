package src.dao;

import src.Utils.Util;
import src.vo.Account;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccountDAO {
  private Account[] accList;
  private int cnt;

  public AccountDAO() {

  }

  public Account[] getAccList() {
    return accList;
  }

  public void setAccList(Account[] accList) {
    this.accList = accList;
  }

  public void addAccountFromData(String accountData) {
    String[] temp = accountData.split("\n");
    cnt = temp.length;
    accList = new Account[cnt];
    int idx = 0;
    for (String t : temp) {
      String[] info = t.split("/");
      accList[idx++] = new Account(info[0], info[1], Integer.parseInt(info[2]));
    }
  }

  public String saveAsFileData() {
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
      if (a.getClientId().equals(log)) {
        cnt += 1;
        System.out.println(a.getAccNumber() + " " + a.getMoney() + "원");
      }
    }
    if (cnt == 3) {
      System.out.println("더이상 계좌를 만들수 없습니다.");
      return;
    }

    String accNo = Util.getValue("가입할 계좌번호 >> ");
    if (!checkAccountNo(accNo)) {
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

  private boolean checkAccountNo(String accNo) {
    if (accNo.length() != accList[0].getAccNumber().length()) {
      System.out.println("틀린 계좌번호");
      return false;
    }
    String accPattern = "^\\d{4}-\\d{4}-\\d{4}$";
    Pattern p = Pattern.compile(accPattern);
    Matcher m = p.matcher(accNo);
    if (!m.matches()) {
      System.out.println("틀린 계좌번호");
      return false;
    }
    System.out.println("올바른 계좌번호");
    return true;

  }

  public void deleteAccount(String log) {
    printMyAccount(log);
    String accNo = Util.getValue("삭제할 계좌번호 >> ");
    int delIdx = findAccountNo(accNo, log);
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
      if (accList[j].getClientId().equals(log)) {
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


  public boolean printMyAccount(String log) {
    int cnt = 0;
    System.out.println("내 계좌 목록 : ");
    for (Account a : accList) {
      if (a.getClientId().equals(log)) {
        cnt += 1;
        System.out.println(a.getAccNumber() + " " + a.getMoney() + "원");
      }
    }
    return cnt == 0 ? false : true;
  }

  private int findAccountNo(String accNo) {
    for (int i = 0; i < accList.length; i++) {
      if (accList[i].getAccNumber().equals(accNo)) {
        return i;
      }
    }
    return -1;
  }

  private int findAccountNo(String accNo, String log) {
    for (int i = 0; i < accList.length; i++) {
      if (accList[i].getAccNumber().equals(accNo) && accList[i].getClientId().equals(log)) {
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

    String accNo = Util.getValue("입금할 계좌번호 >> ");
    int idx = findAccountNo(accNo, log);
    if (idx == -1) {
      System.out.println("계좌번호가 맞지않습니다.");
      return;
    }


    int money = Util.getValue("100 ~ 10000000원 입금가능 >> ", 100, 10000000);
    accList[idx].setMoney(accList[idx].getMoney() + money);
    System.out.println("입금완료");
    System.out.println(accList[idx]);
  }

  public void withdraw(String log) {
    if (!printMyAccount(log)) {
      System.out.println("계좌가 없습니다");
      return;
    }

    String accNo = Util.getValue("출금할 계좌번호 >> ");
    int idx = findAccountNo(accNo, log);
    if (idx == -1) {
      System.out.println("계좌번호가 맞지않습니다.");
      return;
    }

    int money = Util.getValue("100 ~ %d원 출금가능 >> ".formatted(accList[idx].getMoney()), 100, accList[idx].getMoney());
    accList[idx].setMoney(accList[idx].getMoney() + money);
    System.out.println(accList[idx]);
    System.out.println("출금완료");
  }

  public void transfer(String log) {
    if (!printMyAccount(log)) {
      System.out.println("계좌가 없습니다");
      return;
    }
    String fromNo = Util.getValue("이체할 계좌번호 >> ");
    int fromIdx = findAccountNo(fromNo, log);
    if (fromIdx == -1) {
      System.out.println("계좌번호가 맞지않습니다.");
      return;
    }

    for (Account a : accList) {
      System.out.println(a);
    }
    String toNo = Util.getValue("이체받을 계좌번호 >> ");
    int toIdx = findAccountNo(toNo);
    if (toIdx == -1) {
      System.out.println("계좌번호가 맞지않습니다.");
      return;
    }
    if (fromIdx == toIdx) {
      System.out.println("이체할 계좌번호와 이체받을 계좌번호가 같습니다.");
      return;
    }
    int money = Util.getValue("100 ~ %d원 출금가능 >> ".formatted(accList[fromIdx].getMoney()), 100, accList[fromIdx].getMoney());
    accList[toIdx].setMoney(accList[toIdx].getMoney() + money);
    accList[fromIdx].setMoney(accList[fromIdx].getMoney() - money);
    System.out.println(accList[fromIdx]);
    System.out.println(accList[toIdx]);
    System.out.println("이체완료");
  }

  public void printAllAccount(Account[] accList) {
    for (Account a : accList) {
      System.out.println(a);
    }
  }
}
