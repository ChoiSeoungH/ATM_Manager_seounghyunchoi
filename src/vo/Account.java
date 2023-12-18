package src.vo;

public class Account {
  private String clientId;
  private String accNumber;
  private int money;

  public Account() {

  }

  public String getClientId() {
    return clientId;
  }

  public String getAccNumber() {
    return accNumber;
  }

  public int getMoney() {
    return money;
  }

  public void setMoney(int money) {
    this.money = money;
  }

  public Account(String clientId, String accNumber, int money) {
    this.clientId = clientId;
    this.accNumber = accNumber;
    this.money = money;
  }

  @Override
  public String toString() {
    String data = clientId + "\t" + accNumber + "\t" + money;
    return data;
  }

  public String saveToData() {
    return "%s/%s/%d\n".formatted(clientId,accNumber,money);
  }
}
