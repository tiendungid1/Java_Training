package rxn.jdbc.core.kindergarten.enums;

public enum Role {
  ADMIN(1),
  EMPLOYEE(2),
  PARENT(3);

  private final int value;

  Role(int value) {
    this.value = value;
  }

  public int value() {
    return this.value;
  }
}
