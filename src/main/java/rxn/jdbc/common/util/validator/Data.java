package rxn.jdbc.common.util.validator;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class Data {
  private Data() {
    throw new AssertionError();
  }

  public static boolean isAscii(String s) {
    return StandardCharsets.US_ASCII.newEncoder().canEncode(s);
  }

  public static boolean isInteger(String s) {
    try {
      Integer.parseInt(s);
    } catch (NumberFormatException e) {
      return false;
    }
    return true;
  }

  public static boolean isDouble(String s) {
    try {
      Double.parseDouble(s);
    } catch (NumberFormatException e) {
      return false;
    }
    return true;
  }

  public static boolean isMeaningfulString(String s) {
    return !Objects.equals(s, "");
  }
}
