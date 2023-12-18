package rxn.jdbc.core.kindergarten.enums;

public enum ClassStatus {
  MAX_STUDENTS_ALLOWED(15);

  private final int quantity;

  ClassStatus(int quantity) {
    this.quantity = quantity;
  }

  public int quantity() {
    return this.quantity;
  }
}
