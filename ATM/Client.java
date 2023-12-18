package ATM;

public class Client {
  int clientNo;
  String id;
  String pw;
  String name;

  public Client(int clientNo, String id, String pw, String name) {
    this.clientNo = clientNo;
    this.id = id;
    this.pw = pw;
    this.name = name;
  }

  public Client() {

  }

  @Override
  public String toString() {
    String data = clientNo + "\t" + id + "\t" + pw + "\t" + name;
    return data;
  }

  String saveToData() {
    return "%d/%s/%s/%s\n".formatted(clientNo,id,pw,name);
  }
}
