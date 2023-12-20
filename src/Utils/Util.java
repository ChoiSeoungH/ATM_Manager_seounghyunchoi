package src.Utils;

import src.dao.AccountDAO;
import src.dao.ClientDAO;

import java.io.*;
import java.util.Scanner;

public class Util {
  private final static String CUR_PATH = System.getProperty("user.dir") + "\\src\\";
  private static Scanner sc = new Scanner(System.in);
  private static String filename;

  public Util() {
    sc = new Scanner(System.in);
  }

  public static void printAllData() {
    printData("client.txt");
    printData("account.txt");

  }

  private static void printData(String fileName) {
    System.out.println("===================" + fileName + "===================");
    System.out.println(loadData(fileName));
  }

  public static void tempData() {
    String userData = "1001/test01/pw1/김철수\n";
    userData += "1002/test02/pw2/이영희\n";
    userData += "1003/test03/pw3/신민수\n";
    userData += "1004/test04/pw4/최상민";

    String accountData = "test01/1111-1111-1111/8000\n";
    accountData += "test02/2222-2222-2222/5000\n";
    accountData += "test01/3333-3333-3333/11000\n";
    accountData += "test03/4444-4444-4444/9000\n";
    accountData += "test01/5555-5555-5555/54000\n";
    accountData += "test02/6666-6666-6666/1000\n";
    accountData += "test03/7777-7777-7777/1000\n";
    accountData += "test04/8888-8888-8888/1000";
    saveData("client.txt", userData);
    saveData("account.txt", accountData);

  }//eom


  public static String getValue(String msg) {
    System.out.print(msg);
    return sc.next();
  }//eom

  public static int getValue(String msg, int start, int end) {
    while (true) {
      System.out.print(msg);
      try {
        int val = sc.nextInt();
        if (val < start || val > end) {
          System.out.printf("%d ~ %d 사이의 값 입력 %n", start, end);
          continue;
        }
        return val;
      } catch (Exception e) {
        sc.nextLine();
        System.out.println("숫자를 입력해주세요");
      }

    }//eow
  }//eom

  private static void saveData(String filename, String data) {
    try (FileWriter fw = new FileWriter(CUR_PATH + filename)) {
      fw.write(data);
      System.out.println(filename + "저장 성공");
    } catch (IOException e) {
      System.out.println(filename + "저장 실패");
    }
  }

  private static String loadData(String filename) {
    String data = "";
    try (BufferedReader br = new BufferedReader(new FileReader(CUR_PATH + filename))) {
      String line;
      while ((line = br.readLine()) != null) {
        data += line + "\n";
      }
      data = data.substring(0, data.length() - 1);
//      System.out.println("불러오기 완료");
    } catch (FileNotFoundException e) {
      System.out.println("해당 파일이 없습니다");
    } catch (IOException e) {
      System.out.println("입출력 에러");
    }
    return data;
  }

  public static void loadFromFile(AccountDAO accountDAO, ClientDAO clientDAO) {
    String clientData = loadData("client.txt");
    String accountData = loadData("account.txt");

    accountDAO.addAccountFromData(accountData);
    clientDAO.addClientFromData(clientData);
    clientDAO.updateMaxClientNo();
  }

  public static void saveDataToFile(AccountDAO accountDAO, ClientDAO clientDAO) {
    String accountData = accountDAO.saveAsFileData();
    String clientData = clientDAO.saveAsFileData();
    saveData("account.txt", accountData);
    saveData("client.txt", clientData);
  }
}
