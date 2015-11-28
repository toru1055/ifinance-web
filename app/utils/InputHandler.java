package utils;

public class InputHandler {
  public static String sanitize(String q) {
    if(q != null) {
      q = q.replaceAll("　", " ");
      q = q.replaceAll(" ", "%");
      return q.replaceAll("[\"]", "\"\"");
    } else {
      return null;
    }
  }
}
