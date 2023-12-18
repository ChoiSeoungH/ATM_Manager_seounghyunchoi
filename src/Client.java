package src;

public class Client {
  private int clientNo;
  private String id;
  private String pw;
  private String name;

  public int getClientNo() {
    return clientNo;
  }

  public String getId() {
    return id;
  }

  public String getPw() {
    return pw;
  }

  public void setPw(String pw) {
    this.pw = pw;
  }

  public void setName(String name) {
    this.name = name;
  }

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

  public String saveToData() {
    return "%d/%s/%s/%s\n".formatted(clientNo,id,pw,name);
  }
}
