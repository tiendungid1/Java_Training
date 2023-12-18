package rxn.jdbc.core.kindergarten.module.payment;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class PaymentService {
  private final PaymentRepository paymentRepository = new PaymentRepository();

  public static void main(String[] args) throws SQLException, IllegalAccessException {
    System.out.println(
        new PaymentService().getTuitionPreviousMonth("DungNT")
    );
  }

  public int autoInsertBasePaymentRecords() throws SQLException {
    LocalDate currentDate = LocalDate.now();
    Month currentMonth = currentDate.getMonth();
    int currentYear = currentDate.getYear();

    if (paymentRepository.existBaseRecords(currentMonth.getValue(), currentYear)) {
      return 0;
    }

    List<Integer> classIds = paymentRepository.findClassIdsInCurrentSchoolYear(
        School.ACADEMIC_YEAR_START_DATE.get(currentYear)
    );

//    School has closed this year or school records has not been updated yet
    if (classIds.isEmpty()) {
      return 0;
    }

    List<PaymentDto> paymentDtos = new ArrayList<>();

    for (int classId : classIds) {
      List<Integer> studentIds = paymentRepository.findStudentIdsInClass(classId);

      for (int studentId : studentIds) {
        LocalDate startDate, endDate;

        switch (currentMonth) {
          case SEPTEMBER -> {
            startDate = School.ACADEMIC_YEAR_START_DATE.get(currentYear);
            endDate = School.MONTH_END_DATE.get(currentMonth.getValue() + 1, currentYear);
          }
          case DECEMBER -> {
            startDate = School.MONTH_START_DATE.get(currentMonth.getValue(), currentYear);
            endDate = School.MONTH_END_DATE.get(1, currentYear + 1);
          }
          case MAY -> {
            startDate = School.MONTH_START_DATE.get(currentMonth.getValue(), currentYear);
            endDate = School.ACADEMIC_YEAR_END_DATE.get(currentYear);
          }
          default -> {
            startDate = School.MONTH_START_DATE.get(currentMonth.getValue(), currentYear);
            endDate = School.MONTH_END_DATE.get(currentMonth.getValue() + 1, currentYear);
          }
        }

        BigDecimal talentClassFees = paymentRepository.findTalentClassFeesInMonth(
            currentMonth.getValue(),
            studentId
        );

        BigDecimal tuitionFees = paymentRepository.findTemporaryTuitionInMonth(
            startDate, endDate, classId, talentClassFees
        );

        PaymentDto paymentDto = new PaymentDto(studentId, classId, startDate, tuitionFees);
        paymentDtos.add(paymentDto);
      }
    }

    return paymentRepository.insert(paymentDtos);
  }

  public int updatePayment(int studentId, int classId) throws SQLException {
    LocalDate currentDate = LocalDate.now();
    Month currentMonth = currentDate.getMonth();

//  Business operation not supported
    if (currentMonth.equals(Month.SEPTEMBER)) {
      return 0;
    }

    int currentYear = currentDate.getYear();

    LocalDate startDatePreviousMonth, endDatePreviousMonth;

    switch (currentMonth) {
      case JANUARY -> {
        startDatePreviousMonth = School.MONTH_START_DATE.get(currentMonth.getValue() - 1,
            currentYear - 1);
        endDatePreviousMonth = School.MONTH_END_DATE.get(1, currentYear);
      }
      case JUNE -> {
        startDatePreviousMonth = School.MONTH_START_DATE.get(currentMonth.getValue() - 1,
            currentYear);
        endDatePreviousMonth = School.ACADEMIC_YEAR_END_DATE.get(currentYear);
      }
      default -> {
        startDatePreviousMonth = School.MONTH_START_DATE.get(currentMonth.getValue() - 1,
            currentYear);
        endDatePreviousMonth = School.MONTH_END_DATE.get(currentMonth.getValue(), currentYear);
      }
    }

    int absentSessionCount = paymentRepository.findAbsentSessionsOfStudent(
        studentId,
        classId,
        startDatePreviousMonth,
        endDatePreviousMonth
    );

    BigDecimal talentClassFees = paymentRepository.findTalentClassFeesInMonth(
        currentMonth.getValue() - 1,
        studentId
    );

    BigDecimal zeroTalentClassFees = new BigDecimal("0");

    if (talentClassFees.compareTo(zeroTalentClassFees) == 0) {
      talentClassFees = zeroTalentClassFees;
    }

    return paymentRepository.updateActualPaymentOfStudent(
        new ActualPaymentDto(
            studentId, classId, absentSessionCount, talentClassFees,
            School.MONTH_START_DATE.get(currentMonth.getValue(), currentYear),
            startDatePreviousMonth
        )
    );
  }

  public BigDecimal getTuitionPreviousMonth(String parentAccount)
      throws SQLException, IllegalAccessException {
    List<Integer> studentIds = paymentRepository.findStudentIdsByParentAccount(parentAccount);

    if (studentIds.isEmpty()) {
      throw new IllegalAccessException("This parent account is not available");
    }

    LocalDate currentDate = LocalDate.now();
    Month currentMonth = currentDate.getMonth();
    int currentYear = currentDate.getYear();
    LocalDate startDatePreviousMonth;

    if (currentMonth.equals(Month.JANUARY)) {
      startDatePreviousMonth =
          School.MONTH_START_DATE.get(currentMonth.getValue() - 1, currentYear - 1);
    } else {
      startDatePreviousMonth =
          School.MONTH_START_DATE.get(currentMonth.getValue() - 1, currentYear);
    }

    BigDecimal tuitionSum = new BigDecimal("0");

    for (int studentId : studentIds) {
      BigDecimal tuition = paymentRepository.findTuitionSumOfStudent(studentId,
          startDatePreviousMonth);
      if (tuition != null) {
        tuitionSum = tuitionSum.add(tuition);
      }
    }

    return tuitionSum;
  }

//  public BigDecimal getTuitionNextMonth(String parentAccount)
//      throws SQLException, IllegalAccessException {
//    List<Integer> studentIds = paymentRepository.findStudentIdsByParentAccount(parentAccount);
//
//    if (studentIds.isEmpty()) {
//      throw new IllegalAccessException("This parent account is not available");
//    }
//
//    LocalDate currentDate = LocalDate.now();
//    Month currentMonth = currentDate.getMonth();
//    int currentYear = currentDate.getYear();
//
//    LocalDate startDate, endDate;
//
//    switch (currentMonth) {
//      case NOVEMBER -> {
//        startDate = School.MONTH_START_DATE.get(12, currentYear);
//        endDate = School.MONTH_END_DATE.get(1, currentYear + 1);
//      }
//      case DECEMBER -> {
//        startDate = School.MONTH_START_DATE.get(1, currentYear + 1);
//        endDate = School.MONTH_END_DATE.get(2, currentYear + 1);
//      }
//      case MARCH -> {
//        startDate = School.MONTH_START_DATE.get(4, currentYear);
//        endDate = School.MONTH_END_DATE.get(5, currentYear);
//      }
//      case APRIL -> {
//        startDate = School.MONTH_START_DATE.get(5, currentYear);
//        endDate = School.ACADEMIC_YEAR_END_DATE.get(currentYear);
//      }
//      default -> {
//        startDate = School.MONTH_START_DATE.get(currentMonth.getValue() + 1, currentYear);
//        endDate = School.MONTH_END_DATE.get(currentMonth.getValue() + 2, currentYear);
//      }
//    }
//
//    BigDecimal tuitionSum = new BigDecimal("0");
//
//    for (int studentId : studentIds) {
//      BigDecimal tuition = paymentRepository.findTuitionSumOfStudent(studentId,
//          startDatePreviousMonth);
//      if (tuition != null) {
//        tuitionSum = tuitionSum.add(tuition);
//      }
//    }
//
//    return tuitionSum;
//  }

  private enum School {
    ACADEMIC_YEAR_START_DATE(LocalDate.of(1000, 9, 5)),
    ACADEMIC_YEAR_END_DATE(LocalDate.of(1000, 5, 25)),
    MONTH_START_DATE(LocalDate.of(1000, 1, 5)),
    MONTH_END_DATE(LocalDate.of(1000, 1, 4));

    private final LocalDate date;

    School(LocalDate date) {
      this.date = date;
    }

    public LocalDate get(int year) {
      return date.withYear(year);
    }

    public LocalDate get(int month, int year) {
      return date.withMonth(month).withYear(year);
    }
  }
}
