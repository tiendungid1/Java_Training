package rxn.jdbc.common.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import rxn.jdbc.old.builder.QueryBuilder;
import rxn.jdbc.old.builder.dto.SortConditionDto;

public class DaoImpl<T> implements Dao<T> {

  @Override
  public List<T> getByColumnsSorted(
      String dbName,
      String table,
      List<String> selectingColumns,
      List<SortConditionDto> sortDtoList,
      int page,
      int pageSize)
      throws SQLException {
    return null;
  }

  @Override
  public List<T> getByColumnsWithConditionsSorted(
      String dbName,
      String table,
      List<String> selectingColumns,
      String condition,
      List<SortConditionDto> sortDtoList,
      int page,
      int pageSize)
      throws SQLException {
    return null;
  }

  @Override
  public void insertOne(String dbName, T object) throws SQLException {}

  @Override
  public int insertMany(String dbName, List<T> objects) throws SQLException {
    return 0;
  }

  @Override
  public int update(
      String dbName, String table, Map<String, String> updatingColumns, String condition)
      throws SQLException {
    try (Connection connection = Database.getInstance().getConnection(dbName)) {
      connection.setAutoCommit(false);

      try (Statement statement = connection.createStatement()) {
        int updateRowCount =
            statement.executeUpdate(
                QueryBuilder.getInstance()
                    .createUpdateQuery(table)
                    .add(updatingColumns)
                    .newline()
                    .add(condition)
                    .build());

        connection.commit();
        return updateRowCount;
      } catch (SQLException e) {
        connection.rollback();
        throw e;
      } finally {
        connection.setAutoCommit(true);
      }
    }
  }

  @Override
  public int delete(String dbName, String table, String condition) throws SQLException {
    try (Connection connection = Database.getInstance().getConnection(dbName)) {
      connection.setAutoCommit(false);

      try (Statement statement = connection.createStatement()) {
        int deleteRowCount =
            statement.executeUpdate(
                QueryBuilder.getInstance().createDeleteQuery(table).add(condition).build());

        connection.commit();
        return deleteRowCount;
      } catch (SQLException e) {
        connection.rollback();
        throw e;
      } finally {
        connection.setAutoCommit(true);
      }
    }
  }

  @Override
  public List<Map<String, Object>> runUserDefinedSelectQuery(String dbName, String query)
      throws SQLException {
    if (!isSelectQuery(query)) return Collections.emptyList();

    try (Connection connection = Database.getInstance().getConnection(dbName);
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(query)) {
      return resultSetToList(rs);
    }
  }

  private List<Map<String, Object>> resultSetToList(ResultSet rs) throws SQLException {
    ResultSetMetaData md = rs.getMetaData();
    int columns = md.getColumnCount();
    List<Map<String, Object>> list = new ArrayList<>();

    while (rs.next()) {
      Map<String, Object> row = new HashMap<>(columns);
      for (int i = 1; i <= columns; ++i) {
        row.put(md.getColumnName(i), rs.getObject(i));
      }
      list.add(row);
    }

    return list;
  }

  private boolean isSelectQuery(String query) {
    return query.substring(0, 6).equalsIgnoreCase("select");
  }
}
