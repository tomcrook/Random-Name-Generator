import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Given the link to a website, this class will scrape the specific elements that you desire.
 */
public class ScrapePage {

  Document document;

  public ScrapePage(String url) throws IOException {
    this.document = Jsoup.connect(url).get();
  }

  public ScrapePage(String html, boolean h) {
    this.document = Jsoup.parse(html);
  }

  /**
   * Given an html Css Selector, this method will return all of the sentences from the text.
   * @param selector : The Css Selector to a body of text
   * @return a list of sentences from this text body
   */
  public String[] getSentences(String selector) {
    String body = document.select(selector).text();

    String[] arr = body.split("\\.");
    for (int i = 0; i < arr.length; i++) {
      if (arr[i].length() != 0) {
        if (arr[i].charAt(0) == ' ') {
          arr[i] = arr[i].substring(1);
        }
      }
      arr[i] = arr[i] + ".";
    }

    return arr;
  }

  /**
   * Given an html selector (to recurse through), this method finds all of a specific attribute
   * @param selector : The Css Selector to an element you want to recurse through, with an x in the slot to iterate over
   * @param attribute : The specific attribute to return
   * @return a list of attributes as strings
   */
  public String[] getAttributes(String selector, String attribute, char identifier) {
    String firstHalf = selector.substring(0, selector.indexOf(identifier));
    String secondHalf = selector.substring(firstHalf.length() + 1);
    ArrayList<String> strings = new ArrayList<String>();

    int i = 1;
    while(true) {
      String link = firstHalf + i + secondHalf;
      System.out.println(link);

      try {
        String add = "";
        if (attribute.equals("text")) {
          add = document.select(link).text();
        } else {
          add = document.select(link).attr(attribute);
        }
        if (!add.equals("")) {
          strings.add(add);
        }
      } catch (Exception e) {
        break;
      }

      i++;
    }
    String[] arr = new String[strings.size()];
    for (int x = 0; x < arr.length; x++) {
      arr[x] = strings.get(x);
    }
    return arr;
  }

  /**
   * Given an html selector (to recurse through), this method finds all of a specific attribute
   * @param selector : The Css Selector to an element you want to recurse through, with an x in the slot to iterate over
   * @param attribute : The specific attribute to return
   * @return a list of attributes as strings
   */
  public String[] getAttributes(String selector, String attribute, char identifier, int start, int end) {
    String firstHalf = selector.substring(0, selector.indexOf(identifier));
    String secondHalf = selector.substring(firstHalf.length() + 1);
    ArrayList<String> strings = new ArrayList<String>();

    int i = start;
    while(i <= end) {
      String link = firstHalf + i + secondHalf;

      try {
        String add = "";
        if (attribute.equals("html")) {
          add = document.select(link).outerHtml();
        } else if (attribute.equals("text")) {
          add = document.select(link).text();
        } else {
          add = document.select(link).attr(attribute);
        }
        if (!add.equals("")) {
          strings.add(add);
        }
      } catch (Exception e) {

      }

      i++;
    }
    String[] arr = new String[strings.size()];
    for (int x = 0; x < arr.length; x++) {
      arr[x] = strings.get(x);
    }
    return arr;
  }

  /**
   * Given an html selector (to recurse through), this method finds all of a specific attribute
   * @param selector : The Css Selector to an element you want to recurse through, with an x in the slot to iterate over
   * @param attribute : The specific attribute to return
   * @return a list of attributes as strings
   */
  public String[] getAttributes(String selector, String attribute, char identifier, char identifier2 , int start, int end, int start2, int end2) {
    String firstHalf = selector.substring(0, selector.indexOf(identifier));
    String middle = selector.substring(firstHalf.length() + 1, selector.indexOf(identifier2, firstHalf.length() + 1));
    String secondHalf = selector.substring(firstHalf.length() + 1 + middle.length() + 1);
    ArrayList<String> strings = new ArrayList<String>();
    int i = start;
    while(i <= end) {
      int j = start2;
      while (j <= end2) {
        String link = firstHalf + i + middle + j + secondHalf;
        try {
          String add = "";
          if (attribute.equals("html")) {
            add = document.select(link).outerHtml();
          } else if (attribute.equals("text")) {
            add = document.select(link).text();
          } else {
            add = document.select(link).attr(attribute);
          }
          if (!add.equals("")) {
            strings.add(add);
          }
        } catch (Exception e) {

        }
        j++;
      }
      i++;
    }
    String[] arr = new String[strings.size()];
    for (int x = 0; x < arr.length; x++) {
      arr[x] = strings.get(x);
    }
    return arr;
  }

  public boolean hasTable() {
    return this.document.outerHtml().contains("</table");
  }

  public String tableHTML() {
    if (this.hasTable()) {
      return this.document.outerHtml().substring(this.document.outerHtml().indexOf("<table"), this.document.outerHtml().indexOf("</table"));
    } else {
      return null;
    }
  }

  public String tableID() {
    if (this.hasTable()) {
      String html = this.document.outerHtml();
      if (html.contains("<table id=")) {
        html = html.substring(html.indexOf("<table id="));
        html = html.substring(html.indexOf("\"") + 1);
        return html.substring(0, html.indexOf("\""));
      } else {
        return null;
      }
    }
    return null;
  }

  public String getAttribute(String selector, String attr) {
    if (attr.equals("text")) {
      return document.select(selector).text();
    }
    return document.select(selector).attr(attr);
  }

  public String[] getAllLinks() {
    Elements links = document.select("a[href]");
    String[] ret = new String[links.size()];
    for (int i = 0; i < links.size(); i++) {
      ret[i] = links.get(i).attr("href");
    }
    return ret;
  }

  public String getFirstName() {
    //#main-content > div.container > div.row.mt-3 > div:nth-child(2) > dl > dd:nth-child(2)
    String name = document.select("#main-content > div.container > div.row.mt-3 > div:nth-child(2) > dl > dd:nth-child(2)").text();
    String nat = document.select("#main-content > div.container > div.row.mt-3 > div:nth-child(2) > dl > dd:nth-child(6) > a").text();
    String firstName = name.substring(0, name.indexOf(" "));
    String lastName = name.substring(name.indexOf(" ") + 1);
    return firstName;
  }

  public String getLastName() {
    String name = document.select("#main-content > div.container > div.row.mt-3 > div:nth-child(2) > dl > dd:nth-child(2)").text();
    String nat = document.select("#main-content > div.container > div.row.mt-3 > div:nth-child(2) > dl > dd:nth-child(6) > a").text();
    String firstName = name.substring(0, name.indexOf(" "));
    String lastName = name.substring(name.indexOf(" ") + 1);
    return lastName;
  }

  public String getNation() {
    //#main-content > div.container > div.row.mt-3 > div:nth-child(2) > dl > dd:nth-child(2)
    String name = document.select("#main-content > div.container > div.row.mt-3 > div:nth-child(2) > dl > dd:nth-child(2)").text();
    String nat = document.select("#main-content > div.container > div.row.mt-3 > div:nth-child(2) > dl > dd:nth-child(6) > a").text();
    String firstName = name.substring(0, name.indexOf(" "));
    String lastName = name.substring(name.indexOf(" ") + 1);
    return nat;
  }


}
