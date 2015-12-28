import java.util.*;

class dateTimeConverter {
  public static void main(String[] args) {
    Date dateTime = new Date(Long.parseLong(args[0]));
    System.out.println("Datetime ..." + dateTime);
  }
}
