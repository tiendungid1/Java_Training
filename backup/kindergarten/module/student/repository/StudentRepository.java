package rxn.jdbc.core.kindergarten.module.student.repository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import rxn.jdbc.core.kindergarten.module.student.model.Student;

public interface StudentRepository {
  Optional<Student> findById(int id) throws SQLException;

  List<Student> findByIds(Object[] ids) throws SQLException;

  Optional<Student> findByAccount(String account) throws SQLException;

  List<Student> findByMonthInYear(int month, int year) throws SQLException;

  List<Map<String, Object>> findParentsByStudentCount(int count) throws SQLException;

  Map<String, Object> findSexPercentage() throws SQLException;

  List<Student> findByAge(LocalDate schoolStartDate, int age) throws SQLException;

  boolean existClassesInYear(LocalDate schoolStartDate) throws SQLException;

  boolean classIsFull(int classId) throws SQLException;

  boolean existsClass(int classId) throws SQLException;

  boolean isEmptyTable() throws SQLException;

  Set<Integer> findStudentIdsInClass(String teacherAccount) throws SQLException;
}
