package rxn.jdbc.common.database;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import rxn.jdbc.old.builder.dto.SortConditionDto;

public interface Dao<T> {
  List<T> getByColumnsSorted(
      String dbName,
      String table,
      List<String> selectingColumns,
      List<SortConditionDto> sortDtoList,
      int page,
      int pageSize)
      throws SQLException;

  List<T> getByColumnsWithConditionsSorted(
      String dbName,
      String table,
      List<String> selectingColumns,
      String condition,
      List<SortConditionDto> sortDtoList,
      int page,
      int pageSize)
      throws SQLException;

  void insertOne(String dbName, T object) throws SQLException;

  int insertMany(String dbName, List<T> objects) throws SQLException;

  int update(String dbName, String table, Map<String, String> updatingColumns, String condition)
      throws SQLException;

  int delete(String dbName, String table, String condition) throws SQLException;

  List<Map<String, Object>> runUserDefinedSelectQuery(String dbName, String query)
      throws SQLException;
}
