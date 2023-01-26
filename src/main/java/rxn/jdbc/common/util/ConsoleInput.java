package rxn.jdbc.common.util;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import rxn.jdbc.common.util.validator.Data;
import rxn.jdbc.common.util.logger.Console;

public class ConsoleInput {
  private static Scanner scanner;

  private ConsoleInput() {
    throw new AssertionError();
  }

  public static void setScanner(Scanner sc) {
    scanner = sc;
  }

  private static String getAsciiInput(String s, boolean fnCalledFirstTime) {
    if (Data.isMeaningfulString(s)) return s;

    if (!Data.isMeaningfulString(s) && !fnCalledFirstTime) {
      Console.emphasize("Input string is empty, please type something");
    }

    boolean isAscii;

    do {
      s = scanner.nextLine();
      isAscii = Data.isAscii(s);
      if (!isAscii) {
        Console.emphasize("Accept english character only, please try again");
      }
    } while (!isAscii);

    return getAsciiInput(s.trim(), false);
  }

  private static String getUnicodeInput(String s, boolean fnCalledFirstTime) {
    if (Data.isMeaningfulString(s)) return s;

    if (!Data.isMeaningfulString(s) && !fnCalledFirstTime) {
      Console.emphasize("Input string is empty, please type something");
    }

    return getUnicodeInput(scanner.nextLine().trim(), false);
  }

  public static String getString(boolean unicodeInputAllowed) {
    if (unicodeInputAllowed) return getUnicodeInput("", true);
    return getAsciiInput("", true);
  }

  public static String getMultiLinesString() {
    List<String> data = new ArrayList<>();

    while (true) {
      String line = scanner.nextLine();
      if ("".equalsIgnoreCase(line)) break;
      data.add(line);
    }

    return String.join("\n", data).trim();
  }

  public static long getInteger() {
    try {
      return Integer.parseInt(getAsciiInput("", true));
    } catch (NumberFormatException e) {
      Console.emphasize("Accept input in number format (0 - 9) only, please try again");
      return getInteger();
    }
  }

  public static double getFloatingPoint() {
    try {
      return Double.parseDouble(getAsciiInput("", true));
    } catch (NumberFormatException e) {
      Console.emphasize("Accept input in number format (0 - 9) only, please try again");
      return getFloatingPoint();
    }
  }

  public static LocalDate getDate() {
    Console.info("Enter day");
    int day = (int) getInteger();
    Console.info("Enter month");
    int month = (int) getInteger();
    Console.info("Enter year");
    int year = (int) getInteger();

    try {
      return LocalDate.of(year, month, day);
    } catch (DateTimeException e) {
      Console.warning(e.getMessage());
      Console.emphasize("Please input your date again");
      return getDate();
    }
  }

  public static LocalTime getTime() {
    Console.info("Enter hour");
    int hour = (int) getInteger();
    Console.info("Enter minute");
    int minute = (int) getInteger();

    try {
      return LocalTime.of(hour, minute);
    } catch (DateTimeException e) {
      Console.warning(e.getMessage());
      Console.emphasize("Please input your time again");
      return getTime();
    }
  }

  public static LocalDateTime getDateTime() {
    Console.info("Enter day");
    int day = (int) getInteger();
    Console.info("Enter month");
    int month = (int) getInteger();
    Console.info("Enter year");
    int year = (int) getInteger();
    Console.info("Enter hour");
    int hour = (int) getInteger();
    Console.info("Enter minute");
    int minute = (int) getInteger();

    try {
      return LocalDateTime.of(year, month, day, hour, minute);
    } catch (DateTimeException e) {
      Console.warning(e.getMessage());
      Console.emphasize("Please input your date and time again");
      return getDateTime();
    }
  }

  public static void waitForEnter() {
    scanner.nextLine();
  }
}
