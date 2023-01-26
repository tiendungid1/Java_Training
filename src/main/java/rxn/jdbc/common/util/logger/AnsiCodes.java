package rxn.jdbc.common.util.logger;

public enum AnsiCodes {
  RESET("\u001B[0m"),
  BLACK("\u001B[30m"),
  RED("\u001B[31m"),
  RED_BACKGROUND("\u001B[41m"),
  GREEN("\u001B[32m"),
  YELLOW("\u001B[33m"),
  BLUE("\u001B[34m"),
  PURPLE("\u001B[35m"),
  CYAN("\u001B[36m"),
  WHITE("\u001B[37m");

  private final String code;

  private AnsiCodes(String code) {
    this.code = code;
  }

  public String code() {
    return this.code;
  }
}
