package rxn.jdbc.core.kindergarten.enums;

public enum AbsentSession {
  NONE(0),
  MORNING(1),
  AFTERNOON(2),
  ALL_DAY(3);

  private final int value;

  AbsentSession(int value) {
    this.value = value;
  }

  public int value() {
    return value;
  }
}
