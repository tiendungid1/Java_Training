package rxn.jdbc.common.database.dataHandler;

import java.util.List;
import java.util.Map;
import rxn.jdbc.common.util.validator.Data;

public class QueryBuilder {
  private QueryBuilder() {
    throw new AssertionError();
  }

  public static String buildInsertQuery(String table, List<FieldDto> insertingFields) {
    StringBuilder query = new StringBuilder();
    StringBuilder questionMarks = new StringBuilder();

    query.append("INSERT INTO [");
    query.append(table);
    query.append("]");
    query.append(" (");

    insertingFields.forEach(dto -> {
      query.append(dto.getColumn());
      query.append(", ");
      questionMarks.append("?, ");
    });

    query.setLength(query.length() - 2);
    query.trimToSize();
    questionMarks.setLength(questionMarks.length() - 2);
    questionMarks.trimToSize();

    query.append(")\nVALUES (");
    query.append(questionMarks);
    query.append(");");

    return query.toString();
  }

  public static String buildUpdateQuery(
      String table, Map<String, Object> updatingColumns, String filter
  ) {
    StringBuilder query = new StringBuilder();

    query.append("UPDATE ");
    query.append(table);
    query.append("\nSET ");

    updatingColumns.forEach((column, value) -> {
      query.append("[");
      query.append(column);
      query.append("] = ");

      if (value instanceof Integer || value instanceof Double) {
        query.append(value);
      } else if (Data.isAlphabeticUnicode((String) value)) {
        query.append("N'");
        query.append(value);
        query.append("'");
      } else {
        query.append("'");
        query.append(value);
        query.append("'");
      }

      query.append(", ");
    });

    query.setLength(query.length() - 2);
    query.trimToSize();

    query.append("\n");
    query.append(filter);
    query.append(";");

    return query.toString();
  }
}
