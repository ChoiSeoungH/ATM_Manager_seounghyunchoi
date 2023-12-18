package src.dao;

import src.Utils.Util;
import src.vo.Account;
import src.vo.Client;

public class ClientDAO {
  private Client[] clientList;
  private int cnt;
  private int maxNo;

  public ClientDAO() {
  }

  public String login() {
    String id = Util.getValue("id >> ");
    int idx = findClientId(id);
    if (idx==-1) {
      System.out.println("id가 존재하지 않습니다");
      return null;
    }
    return checkPw(idx)? id:null;
  }
  private boolean checkPw(int idx){
    String pw = Util.getValue("pw >> ");
    if (!clientList[idx].getPw().equals(pw)) {
      System.out.println("pw가 일치하지 않습니다.");
      return false;
    }
    return true;
  }

  public void addClientFromData(String clientData) {
    String[] temp = clientData.split("\n");
    cnt = temp.length;
    clientList = new Client[cnt];
    int idx=0;
    for (String t : temp) {
      String[] info = t.split("/");
      clientList[idx++] = new Client(Integer.parseInt(info[0]), info[1], info[2], info[3]);
    }
  }

  public void updateMaxClientNo() {
    if(cnt == 0) return;
    int maxNo = 0;
    for(Client c : clientList) {
      if(maxNo < c.getClientNo()) {
        maxNo = c.getClientNo();
      }
    }
    this.maxNo = maxNo;
  }

  public void printAllClient(AccountDAO accountDAO) {
    System.out.println("[회원목록]");
    System.out.println("=====================================");
    System.out.println("\t No\t Id\t Pw\t Name\t (Account)");
    System.out.println("--------------------------------------");
    for (Client c : clientList) {
      System.out.println(c);
      System.out.print("[");
      for (Account a : accountDAO.getAccList()) {
        if (c.getId().equals(a.getClientId())) {
          System.out.print(a.getAccNumber()+" "+a.getMoney()+"원 ");
        }
      }
      System.out.print("]\n\n");
    }
    System.out.println("=====================================");
  }//eom

  public void updateClient() {
    String id = Util.getValue("id >> ");
    int idx = findClientId(id);
    if (idx == -1) {
      System.out.println("id가 존재하지 않습니다");
      return;
    }
    System.out.println(clientList[idx]);
    String pw = Util.getValue("pw >> ");
    String name = Util.getValue("name >> ");
    clientList[idx].setPw(pw);
    clientList[idx].setName(name);
  }

  private int findClientId(String id){
    for (int i = 0; i < cnt; i++) {
      if (id.equals(clientList[i].getId())) {
        return i;
      }
    }
    return -1;
  }

  public void deleteClient(AccountDAO accountDAO) {
    String id = Util.getValue("id >> ");
    int delIdx = findClientId(id);
    if (delIdx == -1) return;

    Client[] temp = clientList;
    clientList = new Client[cnt - 1];
    int idx=0;
    for (int i = 0; i < cnt; i++) {
      if (i!=delIdx) {
        clientList[idx++] = temp[i];
      }
    }
    cnt--;
    accountDAO.deleteAllAccount(id);
    updateMaxClientNo();
  }

  public String deleteClient(String log, AccountDAO accountDAO) {
    int delIdx = findClientId(log);
    if(!checkPw(delIdx)){
      return log;
    }
    Client[] temp = clientList;
    clientList = new Client[cnt - 1];
    int idx=0;
    for (int i = 0; i < cnt; i++) {
      if (i!=delIdx) {
        clientList[idx++] = temp[i];
      }
    }
    cnt--;
    accountDAO.deleteAllAccount(log);
    updateMaxClientNo();
    return null;
  }

  public String saveAsFileData() {
    if (cnt == 0) return "";
    String data = "";
    for (Client c : clientList) {
      data += c.saveToData();
    }
    return data;
  }

  public void AddClient() {
    String id = Util.getValue("id >> ");
    if (findClientId(id)!=-1) {
      System.out.println("id중복");
      return;
    }
    String pw = Util.getValue("pw >> ");
    String name = Util.getValue("name >> ");
    Client[] temp = clientList;
    clientList = new Client[cnt + 1];
    for (int i = 0; i < cnt; i++) {
      clientList[i] = temp[i];
    }
    clientList[cnt++] = new Client(++maxNo, id, pw, name);
  }

  public void withdrawClient(String log) {

  }
}
