package rxn.jdbc.common.database.dataHandler;

public class FieldDto {
  private String column;
  private Object value;

  public FieldDto(String column, Object value) {
    this.column = column;
    this.value = value;
  }

  public String getColumn() {
    return column;
  }

  public void setColumn(String column) {
    this.column = column;
  }

  public Object getValue() {
    return value;
  }

  public void setValue(Object value) {
    this.value = value;
  }
}
