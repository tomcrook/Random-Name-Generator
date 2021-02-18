import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Nation {

  ArrayList<String> firstNames;
  ArrayList<String> lastNames;
  String name;

  Nation(String name, String docFN, String docLN) throws IOException {
    this.name = name;
    this.firstNames = readNames(docFN, true);
    this.lastNames = readNames(docLN, false);

  }

  private ArrayList<String> readNames(String doc, boolean first) throws IOException {
    Scanner scanner = new Scanner(Paths.get(doc));
    ArrayList<String> list = new ArrayList<String>();
    while(scanner.hasNextLine()) {
      String next = scanner.nextLine();
      if (!next.equals("")) {
        if (first) {
          if (!list.contains(next)) {
            list.add(next);
          }
        } else {
          if (!list.contains(next)) {
            list.add(next);
          }
        }
      }
    }

    return list;
  }

  public String generateName() {
    if (name.equals("Brazil") && Math.random() > 0.3) {
      return firstNames.get((int)(firstNames.size() * Math.random()));
    }
    return firstNames.get((int)(firstNames.size() * Math.random())) + " " + lastNames.get((int)(lastNames.size() * Math.random())) ;
  }

  public boolean find(String o) {
    return o.equals(this.name);
  }

}
