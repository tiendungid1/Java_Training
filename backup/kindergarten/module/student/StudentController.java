package rxn.jdbc.core.kindergarten.module.student;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import rxn.jdbc.common.util.console.Console;
import rxn.jdbc.common.util.console.ConsoleInput;
import rxn.jdbc.core.kindergarten.module.auth.model.LoginDto;
import rxn.jdbc.core.kindergarten.module.student.model.Student;
import rxn.jdbc.core.kindergarten.module.student.service.StudentService;

public class StudentController {
  private final StudentService studentService;

  public StudentController(StudentService studentService) {
    this.studentService = studentService;
  }

  public void showStudent(LoginDto dto) throws SQLException, IllegalAccessException {
    Console.feature(">>> HIỂN THỊ THÔNG TIN TRẺ <<<");

    switch (dto.getRole()) {
      case EMPLOYEE -> {
        Student student = studentService.getStudentOfTeacher(dto);
        Console.emphasize("Dưới đây là thông tin học sinh với mã " + student.getMaTre());
        Console.showObjectInfo(student);
      }
      case PARENT -> {
        Student student = studentService.getStudentOfParent(dto);
        Console.emphasize("Dưới đây là thông tin con của bạn");
        Console.showObjectInfo(student);
      }
    }
  }

  public void showStudentsByMonthIn2023() throws SQLException, IllegalAccessException {
    Console.feature(">>> HIỂN THỊ TRẺ EM SINH TRONG THÁNG BẤT KỲ NĂM 2023 <<<");
    List<Student> students = studentService.getStudentByMonthInYear(2023);
    if (students.isEmpty()) {
      Console.emphasize("Không có dữ liệu nào thỏa điều kiện");
      return;
    }
    students.forEach(Console::showObjectInfo);
  }

  public void showParentsWithTwoChildren() throws SQLException, IllegalAccessException {
    Console.feature(">>> HIỂN THỊ THÔNG TIN PHỤ HUYNH CÓ 2 TRẺ HỌC TẠI TRƯỜNG <<<");
    List<Map<String, Object>> parents = studentService.getParentsByStudentCount(2);
    if (parents.isEmpty()) {
      Console.emphasize("Không có phụ huynh nào có 2 con học tại trường");
      return;
    }
    printListOfMap(parents);
  }

  public void showSexPercentageOfStudyingStudent() throws SQLException, IllegalAccessException {
    Console.feature(">>> HIỂN THỊ TỶ LỆ GIỚI TÍNH CỦA TRẺ ĐANG HỌC TẠI TRƯỜNG <<<");
    Map<String, Object> result = studentService.getSexPercentageOfStudyingStudent();
    if (result.isEmpty()) {
      Console.emphasize("Năm nay trường đóng cửa, không có dữ liệu để tính toán");
      return;
    }
    printMap(result);
  }

  public void showStudentsByAgeInYear() throws SQLException, IllegalAccessException {
    Console.feature(">>> THỐNG KÊ TRẺ EM THEO TUỔI TRONG NĂM BẤT KỲ <<<");

    Console.info("Nhập năm");
    int year = ConsoleInput.getYear().getValue();
    LocalDate schoolStartDate = LocalDate.of(year, 9, 5);

    for (int age = 3; age <= 5; age++) {
      List<Student> students = studentService.getStudentsInYearByAge(schoolStartDate, age);

      Console.newline();
      Console.feature(">>> Hiển thị trẻ " + age + " tuổi trong năm học " + year + "-" + (year + 1));

      if (students.isEmpty()) {
        Console.emphasize("Không có trẻ nào " + age + " tuổi học tại trường trong năm tìm kiếm");
        continue;
      }

      students.forEach(Console::showObjectInfo);
    }
  }

  public void addNewStudent() throws SQLException {
    Console.feature(">>> THÊM 1 TRẺ VÀO CƠ SỞ DỮ LIỆU <<<");
    int rowAffected = studentService.addStudent();
    Console.info("Tác vụ chạy thành công, " + rowAffected + " trẻ được thêm");
  }

  public void addNewStudents() throws SQLException {
    Console.feature(">>> THÊM NHIỀU TRẺ VÀO CƠ SỞ DỮ LIỆU <<<");
    int rowsAffected = studentService.addStudents();
    Console.info("Tác vụ chạy thành công, " + rowsAffected + " trẻ được thêm");
  }

  public void updateStudent(LoginDto dto) throws SQLException, IllegalAccessException {
    Console.feature(">>> CẬP NHẬT THÔNG TIN TRẺ <<<");
    int rowAffected = studentService.updateStudent(dto);
    Console.info("Tác vụ chạy thành công, " + rowAffected + " trẻ được cập nhật");
  }

  public void deleteStudent(LoginDto dto) throws SQLException, IllegalAccessException {
    Console.feature(">>> XÓA THÔNG TIN TRẺ <<<");
    int rowAffected = studentService.deleteStudent(dto);
    Console.info("Tác vụ chạy thành công, đã xóa " + rowAffected + " trẻ");
  }

  private void printListOfMap(List<Map<String, Object>> list) {
    AtomicInteger recordCount = new AtomicInteger();
    list.forEach(map -> {
      Console.emphasize("Record " + recordCount.incrementAndGet());
      map.forEach((column, value) -> Console.info(column + ": " + value));
    });
  }

  private void printMap(Map<String, Object> map) {
    map.forEach((column, value) -> Console.info(column + ": " + value));
  }
}
