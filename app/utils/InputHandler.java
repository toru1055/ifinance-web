package utils;

public class InputHandler {
  public static String sanitize(String q) {
    if(q != null) {
      return q.replaceAll("[\"]", "\"\"");
    } else {
      return null;
    }
  }
}
