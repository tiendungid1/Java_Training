package rxn.jdbc.common.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;

/*
* @deprecated
* */
public class DateTimeUtil {
  private DateTimeUtil() {
    throw new AssertionError();
  }

  public static LocalDate getDateAfterDays(LocalDate date, int days) {
    return date.plus(Period.ofDays(days));
  }

  public static LocalDate getDateAfterMonths(LocalDate date, int months) {
    return date.plus(Period.ofMonths(months));
  }

  public static DayOfWeek getDayOfWeek(LocalDate date) {
    return date.getDayOfWeek();
  }

  public static int getDayOfMonth(LocalDate date) {
    return date.getDayOfMonth();
  }

  public static boolean isLeapYear(LocalDate date) {
    return date.isLeapYear();
  }

  public static boolean isBefore(LocalDate date1, LocalDate date2) {
    return date1.isBefore(date2);
  }

  public static boolean isAfter(LocalDate date1, LocalDate date2) {
    return date1.isAfter(date2);
  }

  public static int getDaysBetween(LocalDate date1, LocalDate date2) {
    return Period.between(date1, date2).getDays();
  }
}
