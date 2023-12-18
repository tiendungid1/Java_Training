package rxn.jdbc.core.kindergarten.module.payment;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ActualPaymentDto {
  private final int studentId;
  private final int classId;
  private final int absentSessionCount;
  private final BigDecimal talentClassFees;
  private final LocalDate date;
  private final LocalDate datePreviousMonth;

  public ActualPaymentDto(
      int studentId, int classId, int absentSessionCount, BigDecimal talentClassFees,
      LocalDate date,
      LocalDate datePreviousMonth
  ) {
    this.studentId = studentId;
    this.classId = classId;
    this.absentSessionCount = absentSessionCount;
    this.talentClassFees = talentClassFees;
    this.date = date;
    this.datePreviousMonth = datePreviousMonth;
  }

  public int getStudentId() {
    return studentId;
  }

  public int getClassId() {
    return classId;
  }

  public int getAbsentSessionCount() {
    return absentSessionCount;
  }

  public BigDecimal getTalentClassFees() {
    return talentClassFees;
  }

  public LocalDate getDate() {
    return date;
  }

  public LocalDate getDatePreviousMonth() {
    return datePreviousMonth;
  }
}
