import java.io.*;
import java.lang.reflect.Array;
import java.nio.Buffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

  public static void main(String[] args) throws IOException, InterruptedException {

    String[] alphabet = {
      "A", "B", "C", "D", "E", "F", "G", "H", "I",
      "J", "K", "L", "M", "N", "O", "P", "Q", "R",
      "S", "T", "U", "V", "W", "X", "Y", "Z"
    };

    for (String s : alphabet) {
      String url = "https://www.mithrilandmages.com/utilities/CityBrowse.php?letter=" + s + "&country=united_states_city_names";
      ScrapePage sp = new ScrapePage(url);

      String l = sp.getAttribute("#medNameColumns", "text");
      String[] names = l.split(" ");
      for (String name : names) {
        System.out.println(name);
      }
    }

  }


  static void readNames(String name) throws IOException {
    Scanner scan = new Scanner(Paths.get(name));

    FileWriter writer1 = new FileWriter("cleanFN.txt", true);
    FileWriter writer2 = new FileWriter("cleanLN.txt", true);

    while (scan.hasNextLine()) {
      String next = scan.nextLine();
      if (next.contains(",")) {
        String nat = next.substring(0, next.indexOf(","));
        next = next.substring(nat.length() + 1);
        if (next.contains(",")) {
          String firstName = next.substring(0, next.indexOf(","));
          String lastName = next.substring(next.indexOf(",") + 1);

          if (nat.equals("Argentin")) {
            nat = "Argentina";
          }
          if (nat.equals("Netherla")) {
            nat = "Holland";
          }
          if (nat.equals("Bosnia a")) {
            nat = "Bosnia and Herzegovina";
          }
          if (nat.equals("United S")) {
            nat = "United States";
          }
          if (nat.equals("Australi")) {
            nat = "Australia";
          }
          if (nat.equals("Turkmeni")) {
            nat = "Turkmenistan";
          }
          if (nat.equals("Monteneg")) {
            nat = "Montenegro";
          }
          if (nat.equals("Czech Re")) {
            nat = "Czech Republic";
          }
          if (nat.equals("Costa Ri")) {
            nat = "Costa Rica";
          }
          if (nat.equals("South Af")) {
            nat = "South Africa";
          }
          if (nat.equals("Northern")) {
            nat = "Northern Ireland";
          }
          if (nat.equals("Switzerl")) {
            nat = "Switzerland";
          }
          if (nat.equals("Republic")) {
            nat = "Republic of Ireland";
          }
          writer1.append(nat + "," + firstName + "\n");
          writer2.append(nat + "," + lastName + "\n");
          writer1.flush();
          writer2.flush();
        }
      }
    }

    writer1.close();
    writer2.close();

  }

}
//