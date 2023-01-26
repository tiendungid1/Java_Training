package rxn.jdbc.common.util.logger;

public class Console {

  private static final String SECTION_SEPARATOR =
      """

      ----------------------------------

      """;
  private static final StringBuilder sb = new StringBuilder();

  private Console() {
    throw new AssertionError();
  }

  public static void feature(String out) {
    sb.append(AnsiCodes.YELLOW.code());
    sb.append(out);
    sb.append(AnsiCodes.RESET.code());
    System.out.println(sb);
    resetStringBuilder();
  }

  public static void info(String out) {
    sb.append(AnsiCodes.BLUE.code());
    sb.append("[");
    sb.append(out);
    sb.append("]");
    sb.append(AnsiCodes.RESET.code());
    System.out.println(sb);
    resetStringBuilder();
  }

  public static void infoInline(String out) {
    sb.append(AnsiCodes.BLUE.code());
    sb.append(out);
    sb.append(AnsiCodes.RESET.code());
    System.out.print(sb);
    resetStringBuilder();
  }

  public static void warning(String out) {
    sb.append(AnsiCodes.RED.code());
    sb.append("WARNING: ");
    sb.append(out);
    sb.append(AnsiCodes.RESET.code());
    System.out.println(sb);
    resetStringBuilder();
  }

  public static void emphasize(String out) {
    sb.append(AnsiCodes.RED.code());
    sb.append("[");
    sb.append(out);
    sb.append("]");
    sb.append(AnsiCodes.RESET.code());
    System.out.println(sb);
    resetStringBuilder();
  }

  public static void error(String out) {
    sb.append(AnsiCodes.RED_BACKGROUND.code());
    sb.append("ERROR:");
    sb.append(AnsiCodes.RESET.code());
    sb.append(" ");
    sb.append(AnsiCodes.RED.code());
    sb.append(out);
    sb.append(AnsiCodes.RESET.code());
    System.out.println(sb);
    resetStringBuilder();
  }

  public static void clear() {
    System.out.println(SECTION_SEPARATOR);
  }

  public static void newline() {
    System.out.println();
  }

  private static void resetStringBuilder() {
    sb.setLength(0);
    sb.trimToSize();
  }
}
