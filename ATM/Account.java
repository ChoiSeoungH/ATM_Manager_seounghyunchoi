package ATM;

public class Account {
  String clientId;
  String accNumber;
  int money;

  public Account() {

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

  String saveToData() {
    return "%s/%s/%d\n".formatted(clientId,accNumber,money);
  }
}
