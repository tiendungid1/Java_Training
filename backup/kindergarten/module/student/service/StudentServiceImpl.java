package rxn.jdbc.core.kindergarten.module.student.service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import rxn.jdbc.common.database.dataHandler.DataRepository;
import rxn.jdbc.common.database.dataHandler.FieldDto;
import rxn.jdbc.common.database.dataHandler.QueryBuilder;
import rxn.jdbc.common.util.console.Console;
import rxn.jdbc.common.util.console.ConsoleInput;
import rxn.jdbc.core.kindergarten.enums.Role;
import rxn.jdbc.core.kindergarten.enums.Sex;
import rxn.jdbc.core.kindergarten.module.auth.model.LoginDto;
import rxn.jdbc.core.kindergarten.module.student.model.Student;
import rxn.jdbc.core.kindergarten.module.student.repository.StudentRepository;

public class StudentServiceImpl implements StudentService {
  private final StudentRepository studentRepository;
  private final InfoGetter infoGetter = new InfoGetter();

  public StudentServiceImpl(StudentRepository studentRepository) {
    this.studentRepository = studentRepository;
  }

  @Override
  public Student getStudentOfParent(LoginDto dto) throws SQLException, IllegalAccessException {
    if (studentRepository.isEmptyTable()) {
      throw new IllegalAccessException("Cơ sở dữ liệu trống, không thể thực hiện chức năng này");
    }

    Optional<Student> student = studentRepository.findByAccount(dto.getUsername());

    if (student.isEmpty()) {
      throw new NoSuchElementException(
          "Không tìm thấy trẻ tương ứng với tài khoản phụ huynh " + dto.getUsername());
    }

    return student.get();
  }

  @Override
  public List<Student> getStudentsOfTeacher(LoginDto dto)
      throws SQLException, IllegalAccessException {
    if (studentRepository.isEmptyTable()) {
      throw new IllegalAccessException("Cơ sở dữ liệu trống, không thể thực hiện chức năng này");
    }

    Set<Integer> studentIds = studentRepository.findStudentIdsInClass(dto.getUsername());

    if (studentIds.isEmpty()) {
      throw new IllegalAccessException(
          "Bạn chưa được chỉ định dạy bất kì lớp nào, không thể xem thông tin học sinh"
      );
    }

    return studentRepository.findByIds(studentIds.toArray());
  }

  @Override
  public Student getStudentOfTeacher(LoginDto dto) throws SQLException, IllegalAccessException {
    if (studentRepository.isEmptyTable()) {
      throw new IllegalAccessException("Cơ sở dữ liệu trống, không thể thực hiện chức năng này");
    }

    Set<Integer> studentIds = studentRepository.findStudentIdsInClass(dto.getUsername());

    if (studentIds.isEmpty()) {
      throw new IllegalAccessException(
          "Bạn chưa được chỉ định dạy bất kì lớp nào, không thể tiếp tục thực hiện chức năng"
      );
    }

    Console.info("Nhập mã trẻ");
    int id = (int) ConsoleInput.getInteger();
    if (!studentIds.contains(id)) {
      throw new IllegalAccessException("Bạn không có quyền xem thông tin trẻ với mã " + id);
    }

    Optional<Student> student = studentRepository.findById(id);
    if (student.isEmpty()) {
      throw new NoSuchElementException("Không tìm thấy trẻ tương ứng với mã " + id);
    }

    return student.get();
  }

  @Override
  public List<Student> getStudentByMonthInYear(int year) throws SQLException,
                                                                IllegalAccessException {
    if (studentRepository.isEmptyTable()) {
      throw new IllegalAccessException("Cơ sở dữ liệu trống, không thể thực hiện chức năng này");
    }

    return studentRepository.findByMonthInYear(ConsoleInput.getMonth().getValue(), year);
  }

  @Override
  public List<Map<String, Object>> getParentsByStudentCount(int studentCount)
      throws SQLException, IllegalAccessException {
    if (studentRepository.isEmptyTable()) {
      throw new IllegalAccessException("Cơ sở dữ liệu trống, không thể thực hiện chức năng này");
    }

    return studentRepository.findParentsByStudentCount(studentCount);
  }

  @Override
  public Map<String, Object> getSexPercentageOfStudyingStudent()
      throws SQLException, IllegalAccessException {
    if (studentRepository.isEmptyTable()) {
      throw new IllegalAccessException("Cơ sở dữ liệu trống, không thể thực hiện chức năng này");
    }

    return studentRepository.findSexPercentage();
  }

  @Override
  public List<Student> getStudentsInYearByAge(LocalDate schoolStartDate, int age)
      throws SQLException, IllegalAccessException {
    if (studentRepository.isEmptyTable()) {
      throw new IllegalAccessException("Cơ sở dữ liệu trống, không thể thực hiện chức năng này");
    }

    if (!studentRepository.existClassesInYear(schoolStartDate)) {
      return Collections.emptyList();
    }

    return studentRepository.findByAge(schoolStartDate, age);
  }

  @Override
  public int addStudent() throws SQLException {
    return DataRepository.insertOne("TREEM", infoGetter.getStudentInfo(new ArrayList<>()));
  }

  @Override
  public int addStudents() throws SQLException {
    List<List<FieldDto>> students = new ArrayList<>();

    do {
      students.add(infoGetter.getStudentInfo(new ArrayList<>()));
      Console.info("Enter 'c' to continue input info, or 'q' to begin inserting");
    } while (ConsoleInput.getString(false, false).equals("c"));

    return DataRepository.insertMany("TREEM", students);
  }

  @Override
  public int updateStudent(LoginDto dto) throws SQLException, IllegalAccessException {
    Optional<Student> std;

    if (dto.getRole().equals(Role.PARENT)) {
      std = Optional.of(getStudentOfParent(dto));
    } else {
      std = Optional.of(getStudentOfTeacher(dto));
    }

    Student student = std.get();
    Console.info("Đây là thông tin trẻ muốn cập nhật");
    Console.showObjectInfo(student);
    Console.info("Nhập 'y' để tiếp tục cập nhật, 'q' để kết thúc");

    if (ConsoleInput.getContinueCommand().equals("q")) {
      return 0;
    }

    Map<String, Object> updateDto = infoGetter.getInfoForUpdating(new HashMap<>(), dto.getRole());
    int classId = (Integer) updateDto.get("MaLop");

    if (!studentRepository.existsClass(classId)) {
      Console.emphasize("Không có lớp nào với mã " + classId);
      Console.emphasize("Mã lớp sẽ được giữ nguyên trong quá trình cập nhật");
      updateDto.put("MaLop", student.getMaLop());
      return takeDtoAndUpdate("TREEM", updateDto, "WHERE [MaTre] = " + student.getMaTre() + ";");
    }

    if (studentRepository.classIsFull(classId)) {
      Console.emphasize("Lớp học với mã " + classId + " đã đầy");
      Console.emphasize("Mã lớp sẽ được giữ nguyên trong quá trình cập nhật");
      updateDto.put("MaLop", student.getMaLop());
      return takeDtoAndUpdate("TREEM", updateDto, "WHERE [MaTre] = " + student.getMaTre() + ";");
    }

    return takeDtoAndUpdate("TREEM", updateDto, "WHERE [MaTre] = " + student.getMaTre() + ";");
  }

  private int takeDtoAndUpdate(String table, Map<String, Object> dto, String filter)
      throws SQLException {
    return DataRepository.update(QueryBuilder.buildUpdateQuery(table, dto, filter));
  }

  @Override
  public int deleteStudent(LoginDto dto) throws SQLException, IllegalAccessException {
    Optional<Student> std;

    if (dto.getRole().equals(Role.PARENT)) {
      std = Optional.of(getStudentOfParent(dto));
    } else {
      std = Optional.of(getStudentOfTeacher(dto));
    }

    Student student = std.get();
    Console.info("Đây là thông tin trẻ muốn xóa");
    Console.showObjectInfo(student);
    Console.info("Nhập 'y' nếu chắc chắn muốn xóa, 'q' để thoát");

    if (ConsoleInput.getContinueCommand().equals("q")) {
      return 0;
    }

    return DataRepository.delete("DELETE FROM [TREEM] WHERE [MaTre] = " + student.getMaTre());
  }

  private class InfoGetter {
    List<FieldDto> getStudentInfo(List<FieldDto> student) throws SQLException {
      Console.info("Nhập mã lớp sinh hoạt");
      int classId = (int) ConsoleInput.getInteger();

      if (!studentRepository.existsClass(classId)) {
        Console.emphasize("Không có lớp nào với mã " + classId);
        Console.emphasize("Vui lòng thử lại");
        return getStudentInfo(student);
      }

      if (studentRepository.classIsFull(classId)) {
        Console.emphasize("Lớp học với mã " + classId + " đã đầy");
        Console.emphasize("Vui lòng thử lại");
        return getStudentInfo(student);
      }

      student.add(new FieldDto("MaLop", classId));

      Console.info("Nhập họ tên trẻ");
      student.add(new FieldDto("HoTenTre", ConsoleInput.getString(false, true)));

      student.add(new FieldDto("NgaySinh", this.getDateOfBirth()));
      student.add(new FieldDto("GioiTinh", this.getSex().value()));
      getOptionalInfo(student, "HoTenBa", "họ tên ba");
      getOptionalInfo(student, "HoTenMe", "họ tên mẹ");
      getOptionalInfo(student, "SdtBa", "số điện thoại ba");
      getOptionalInfo(student, "SdtMe", "số điện thoại mẹ");
      getOptionalInfo(student, "DiaChi", "địa chỉ");

      return student;
    }

    LocalDate getDateOfBirth() {
      Console.info("Nhập ngày sinh");
      LocalDate date = ConsoleInput.getDate();
      int age = Period.between(date, LocalDate.now()).getYears();

      if (age < 3 || age > 5) {
        Console.emphasize("Trường chỉ nhận trẻ từ 3 - 5 tuổi");
        Console.emphasize("Vui lòng kiểm tra kỹ và nhập lại ngày sinh");
        Console.newline();
        return getDateOfBirth();
      }

      return date;
    }

    Sex getSex() {
      Console.info("Nhập giới tính: 'female' cho nữ, 'male' cho nam");
      String input = ConsoleInput.getString(false, false);

      Optional<Sex> sex = Arrays.stream(Sex.values()).filter(s -> s.name().equalsIgnoreCase(input))
          .findFirst();

      if (sex.isEmpty()) {
        Console.emphasize("Chuỗi nhập vào '" + input + "' không hợp lệ");
        Console.emphasize("Vui lòng thử lại");
        Console.newline();
        return getSex();
      }

      return sex.get();
    }

    void getOptionalInfo(List<FieldDto> student, String dbField, String naturalField) {
      Console.info("Bạn có muốn nhập thông tin " + naturalField + " của trẻ không?");
      Console.info("Nhấn 'y' nếu có, 'q' nếu không");

      if (ConsoleInput.getContinueCommand().equals("q")) {
        return;
      }

      Console.info("Nhập " + naturalField);

      switch (dbField) {
        case "HoTenBa", "HoTenMe", "DiaChi" -> student.add(
            new FieldDto(dbField, ConsoleInput.getString(false, true)));
        case "SdtBa", "SdtMe" -> student.add(new FieldDto(dbField, ConsoleInput.getMobile()));
      }
    }

    Map<String, Object> getInfoForUpdating(Map<String, Object> dto, Role role) {
      Console.feature("""
                      Chọn thông tin cập nhật (nhập số để chọn)
                      1. Mã lớp
                      2. Họ tên trẻ
                      3. Ngày sinh
                      4. Giới tính
                      5. Họ tên ba
                      6. Họ tên mẹ
                      7. Số điện thoại ba
                      8. Số điện thoại mẹ
                      9. Địa chỉ
                      10. Tài khoản phụ huynh
                      """);

      int choice;
      do {
        choice = (int) ConsoleInput.getInteger();
        if (choice < 1 || choice > 10) {
          Console.emphasize("Lựa chọn " + choice + " không hợp lệ, vui lòng thử lại");
        }
      } while (choice < 1 || choice > 10);

      Console.info("Nhập dữ liệu");

      switch (choice) {
        case 1 -> {
          if (role.equals(Role.PARENT)) {
            Console.emphasize("Không đủ quyền hạn để cập nhật thông tin này");
            break;
          }
          dto.put("MaLop", (int) ConsoleInput.getInteger());
        }
        case 2 -> dto.put("HoTenTre", ConsoleInput.getString(false, true));
        case 3 -> dto.put("NgaySinh", ConsoleInput.getDate());
        case 4 -> dto.put("GioiTinh", this.getSex());
        case 5 -> dto.put("HoTenBa", ConsoleInput.getString(false, true));
        case 6 -> dto.put("HoTenMe", ConsoleInput.getString(false, true));
        case 7 -> dto.put("SdtBa", ConsoleInput.getMobile());
        case 8 -> dto.put("SdtMe", ConsoleInput.getMobile());
        case 9 -> dto.put("DiaChi", ConsoleInput.getString(false, true));
        case 10 -> {
          if (!role.equals(Role.ADMIN)) {
            Console.emphasize("Không đủ quyền hạn để cập nhật thông tin này");
            break;
          }
          dto.put("TaiKhoanPH", ConsoleInput.getString(false, false));
        }
      }

      Console.info("Nhấn 'y' để chọn thêm thông tin, 'q' để bắt đầu cập nhật");

      return ConsoleInput.getContinueCommand().equals("y") ? getInfoForUpdating(dto, role) : dto;
    }
  }
}
