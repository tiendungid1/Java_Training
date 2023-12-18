package rxn.jdbc.core.kindergarten.enums;

public enum Sex {
  MALE(1),
  FEMALE(0);

  private final int value;

  Sex(int value) {
    this.value = value;
  }

  public int value() {
    return value;
  }
}
