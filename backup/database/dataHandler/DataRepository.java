package rxn.jdbc.common.database.dataHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import rxn.jdbc.common.database.Jdbc;

public class DataRepository {

  /**
   * @param query Select query
   * @return A list contains map with key = db column, value = db column value
   * @throws SQLException
   */
  public static List<Map<String, Object>> find(String query) throws SQLException {
    try (
        Connection connection = Jdbc.getInstance().getConnection();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(query)
    ) {
      if (!rs.isBeforeFirst()) {
        return Collections.emptyList();
      }
      return listOfMapFromResultSet(rs);
    }
  }

  /**
   * @param query Select query
   * @return A map with key = db column, value = db column value.
   * Should use this instead of find() if database only return 1 record.
   * @throws SQLException
   */
  public static Map<String, Object> findOne(String query) throws SQLException {
    try (
        Connection connection = Jdbc.getInstance().getConnection();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(query)
    ) {
      if (!rs.isBeforeFirst()) {
        return Collections.emptyMap();
      }
      return mapFromResultSet(rs);
    }
  }

  /**
   * @param table Inserting table
   * @param dto   A map with key = db column, value = db column value.
   * @return Number of affected row
   * @throws SQLException
   */
  public static int insertOne(String table, List<FieldDto> dto) throws SQLException {
    String query = QueryBuilder.buildInsertQuery(table, dto);

    try (
        Connection connection = Jdbc.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(query)
    ) {
      for (int i = 0; i < dto.size(); i++) {
        statement.setObject(i + 1, dto.get(i).getValue());
      }

      return statement.executeUpdate();
    }
  }

  /**
   * @param table Inserting table
   * @param dtos  List of map with key = db column, value = db column value.
   * @return Number of affected rows
   * @throws SQLException
   */
  public static int insertMany(
      String table, List<List<FieldDto>> dtos
  ) throws SQLException {
    String query = QueryBuilder.buildInsertQuery(table, dtos.get(0));

    try (
        Connection connection = Jdbc.getInstance().getConnection()
    ) {
      connection.setAutoCommit(false);

      try (PreparedStatement statement = connection.prepareStatement(query)) {
        for (List<FieldDto> dto : dtos) {
          for (int i = 0; i < dto.size(); i++) {
            statement.setObject(i + 1, dto.get(i).getValue());
          }
          statement.addBatch();
          statement.clearParameters();
        }

        int insertRowCount = statement.executeBatch().length;
        connection.commit();
        return insertRowCount;
      } catch (SQLException e) {
        connection.rollback();
        throw e;
      } finally {
        connection.setAutoCommit(true);
      }
    }
  }

  public static List<Map<String, Object>> listOfMapFromResultSet(ResultSet rs) throws SQLException {
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

  public static Map<String, Object> mapFromResultSet(ResultSet rs) throws SQLException {
    ResultSetMetaData md = rs.getMetaData();
    int columns = md.getColumnCount();
    Map<String, Object> record = new HashMap<>();

    while (rs.next()) {
      for (int i = 1; i <= columns; ++i) {
        record.put(md.getColumnName(i), rs.getObject(i));
      }
    }

    return record;
  }

  public static int update(String query) throws SQLException {
    try (
        Connection connection = Jdbc.getInstance().getConnection();
        Statement statement = connection.createStatement()
    ) {
      connection.setAutoCommit(false);

      try {
        int updateRowCount = statement.executeUpdate(query);
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

  public static int delete(String query) throws SQLException {
    try (
        Connection connection = Jdbc.getInstance().getConnection();
        Statement statement = connection.createStatement()
    ) {
      connection.setAutoCommit(false);

      try {
        int deleteRowCount = statement.executeUpdate(query);
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
}
