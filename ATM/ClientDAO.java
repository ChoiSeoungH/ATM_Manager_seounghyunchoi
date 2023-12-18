package ATM;

public class ClientDAO {
  Client[] clientList;
  int cnt;
  Util u;
  int maxNo;

  public ClientDAO() {
    u = new Util();
  }

  String login() {
    String id = u.getValue("id >> ");
    int idx = findClientId(id);
    if (idx==-1) {
      System.out.println("id가 존재하지 않습니다");
      return null;
    }
    return checkPw(idx)? id:null;
  }
  boolean checkPw(int idx){
    String pw = u.getValue("pw >> ");
    if (!clientList[idx].pw.equals(pw)) {
      System.out.println("pw가 일치하지 않습니다.");
      return false;
    }
    return true;
  }

  void addClientFromData(String clientData) {
    String[] temp = clientData.split("\n");
    cnt = temp.length;
    clientList = new Client[cnt];
    int idx=0;
    for (String t : temp) {
      String[] info = t.split("/");
      clientList[idx++] = new Client(Integer.parseInt(info[0]), info[1], info[2], info[3]);
    }
  }

  void updateMaxClientNo() {
    if(cnt == 0) return;
    int maxNo = 0;
    for(Client c : clientList) {
      if(maxNo < c.clientNo) {
        maxNo = c.clientNo;
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
      for (Account a : accountDAO.accList) {
        if (c.id.equals(a.clientId)) {
          System.out.print(a.accNumber+" "+a.money+"원 ");
        }
      }
      System.out.print("]\n\n");
    }
    System.out.println("=====================================");
  }//eom

  public void updateClient() {
    String id = u.getValue("id >> ");
    int idx = findClientId(id);
    if (idx == -1) {
      System.out.println("id가 존재하지 않습니다");
      return;
    }
    System.out.println(clientList[idx]);
    String pw = u.getValue("pw >> ");
    String name = u.getValue("name >> ");
    clientList[idx].pw = pw;
    clientList[idx].name = name;
  }

  int findClientId(String id){
    for (int i = 0; i < cnt; i++) {
      if (id.equals(clientList[i].id)) {
        return i;
      }
    }
    return -1;
  }

  void deleteClient(AccountDAO accountDAO) {
    String id = u.getValue("id >> ");
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

  String deleteClient(String log, AccountDAO accountDAO) {
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

  String saveAsFileData() {
    if (cnt == 0) return "";
    String data = "";
    for (Client c : clientList) {
      data += c.saveToData();
    }
    return data;
  }

  public void AddClient() {
    String id = u.getValue("id >> ");
    if (findClientId(id)!=-1) {
      System.out.println("id중복");
      return;
    }
    String pw = u.getValue("pw >> ");
    String name = u.getValue("name >> ");
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
