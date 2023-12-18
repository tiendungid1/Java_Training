package rxn.jdbc.core.kindergarten.module.student.service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import rxn.jdbc.core.kindergarten.module.auth.model.LoginDto;
import rxn.jdbc.core.kindergarten.module.student.model.Student;

public interface StudentService {
  Student getStudentOfParent(LoginDto dto) throws SQLException, IllegalAccessException;

  List<Student> getStudentsOfTeacher(LoginDto dto) throws SQLException, IllegalAccessException;

  Student getStudentOfTeacher(LoginDto dto) throws SQLException, IllegalAccessException;

  List<Student> getStudentByMonthInYear(int year) throws SQLException, IllegalAccessException;

  List<Map<String, Object>> getParentsByStudentCount(int studentCount)
      throws SQLException, IllegalAccessException;

  Map<String, Object> getSexPercentageOfStudyingStudent()
      throws SQLException, IllegalAccessException;

  List<Student> getStudentsInYearByAge(LocalDate schoolStartDate, int age)
      throws SQLException, IllegalAccessException;

  int addStudent() throws SQLException;

  int addStudents() throws SQLException;

  int updateStudent(LoginDto dto) throws SQLException, IllegalAccessException;

  int deleteStudent(LoginDto dto) throws SQLException, IllegalAccessException;
}
