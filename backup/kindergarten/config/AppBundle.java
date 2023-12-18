package rxn.jdbc.core.kindergarten.config;

import java.sql.SQLException;
import java.util.Objects;
import java.util.Scanner;
import rxn.jdbc.common.util.console.Console;
import rxn.jdbc.common.util.console.ConsoleInput;
import rxn.jdbc.core.kindergarten.module.auth.AuthController;
import rxn.jdbc.core.kindergarten.module.auth.model.LoginDto;
import rxn.jdbc.core.kindergarten.module.auth.repository.AuthRepositoryImpl;
import rxn.jdbc.core.kindergarten.module.auth.service.AuthServiceImpl;
import rxn.jdbc.core.kindergarten.module.student.StudentController;
import rxn.jdbc.core.kindergarten.module.student.repository.StudentRepositoryImpl;
import rxn.jdbc.core.kindergarten.module.student.service.StudentServiceImpl;

public class AppBundle {
  private final AuthController authController;
  private final StudentController studentController;
  private final RequestHandler requestHandler = new RequestHandler();
  private final String MENU_FOR_PARENTS =
      """
      Chọn chức năng (nhập số để chọn)
      1. Xem thông tin trẻ
      2. Cập nhật thông tin trẻ
      3. Đăng xuất
      """;
  private final String MENU_FOR_SCHOOL =
      """
      Chọn chức năng (nhập số để chọn)
      1. Thêm 1 trẻ vào cơ sở dữ liệu
      2. Thêm nhiều trẻ vào cơ sở dữ liệu
      3. Cập nhật thông tin trẻ
      4. Xóa trẻ
      5. Xem thông tin trẻ thuộc lớp quản lý
      6. Hiển thị thông tin trẻ trong tháng X năm 2023
      7. Hiển thị thông tin phụ huynh có 2 con học tại trường
      8. Hiển thị tỷ lệ nam, nữ của học sinh trong năm nay
      9. Thống kê trẻ học tại trường theo tuổi trong năm X
      10. Tạo tài khoản nhân viên, phụ huynh (admin only)
      11. Đăng xuất
      """;

  public AppBundle(Scanner scanner) {
    ConsoleInput.setScanner(scanner);
    authController = new AuthController(new AuthServiceImpl(new AuthRepositoryImpl()));
    studentController = new StudentController(new StudentServiceImpl(new StudentRepositoryImpl()));
    Console.feature("Xin chào!");
  }

  public void bootUp() {
    try {
      String welcomeMsg = "Nhấn 1 để đăng nhập hoặc 2 để thoát khỏi chương trình...";
      int request = requestHandler.collectRequestToken(welcomeMsg, RequestFrom.GLOBAL);
      Object dto = requestHandler.handleGlobalRequest(request);

      if (dto.equals(Response.QUIT)) {
        Console.feature("Tạm biệt, chúc 1 ngày tốt lành!");
        System.exit(0);
      }

      run((LoginDto) dto);
    } catch (SQLException e) {
      Console.emphasize(e.getMessage());
      Console.emphasize("Please try to login again");
      bootUp();
    }
  }

  public void run(LoginDto loginDto) {
    Object response = Response.CONTINUE;

    do {
      Console.newline();
      Console.emphasize("Hit enter to show main menu");
      ConsoleInput.waitForEnter();
      Console.clear();

      try {
        switch (loginDto.getRole()) {
          case ADMIN, EMPLOYEE -> response = requestHandler.transferAndProcess(
              requestHandler.collectRequestToken(MENU_FOR_SCHOOL, RequestFrom.SCHOOL),
              RequestFrom.SCHOOL,
              loginDto
          );
          case PARENT -> response = requestHandler.transferAndProcess(
              requestHandler.collectRequestToken(MENU_FOR_PARENTS, RequestFrom.PARENTS),
              RequestFrom.PARENTS,
              loginDto
          );
        }
      } catch (SQLException | IllegalAccessException | RuntimeException e) {
        Console.emphasize(e.getMessage());
      }
    } while (Objects.equals(response, Response.CONTINUE));

    // User has logout
    Console.clear();
    bootUp();
  }

  private enum RequestFrom {
    GLOBAL(1, 2),
    PARENTS(1, 3),
    SCHOOL(1, 11);

    private final int lowerBound;
    private final int upperBound;

    RequestFrom(int lowerBound, int upperBound) {
      this.lowerBound = lowerBound;
      this.upperBound = upperBound;
    }

    public int lowerBound() {
      return lowerBound;
    }

    public int upperBound() {
      return upperBound;
    }
  }

  private enum Response {
    CONTINUE, LOGOUT, QUIT
  }

  private class RequestHandler {
    int collectRequestToken(String menu, RequestFrom request) {
      Console.feature(menu);

      int req;
      do {
        req = (int) ConsoleInput.getInteger();
        if (req < request.lowerBound() || req > request.upperBound()) {
          Console.emphasize(
              "Please input number from " + request.lowerBound() + " to " + request.upperBound());
        }
      } while (req < request.lowerBound() || req > request.upperBound());

      return req;
    }

    Object transferAndProcess(int reqValue, RequestFrom request, LoginDto dto)
        throws SQLException, IllegalAccessException {
      Object res = null;
      switch (request) {
        case GLOBAL -> res = handleGlobalRequest(reqValue);
        case PARENTS -> res = handleParentRequest(reqValue, dto);
        case SCHOOL -> res = handleSchoolRequest(reqValue, dto);
      }
      return res;
    }

    Object handleGlobalRequest(int reqValue) throws SQLException {
      return reqValue == RequestFrom.GLOBAL.lowerBound() ? handleLoginRequest() : Response.QUIT;
    }

    Object handleLoginRequest() throws SQLException {
      return authController.login();
    }

    Response handleParentRequest(int reqValue, LoginDto dto)
        throws SQLException, IllegalAccessException {
      if (reqValue == RequestFrom.PARENTS.upperBound()) {
        return Response.LOGOUT;
      }

      switch (reqValue) {
        case 1 -> studentController.showStudent(dto);
        case 2 -> studentController.updateStudent(dto);
      }

      return Response.CONTINUE;
    }

    Response handleSchoolRequest(int reqValue, LoginDto dto)
        throws SQLException, IllegalAccessException {
      if (reqValue == RequestFrom.SCHOOL.upperBound()) {
        return Response.LOGOUT;
      }

      switch (reqValue) {
        case 1 -> studentController.addNewStudent();
        case 2 -> studentController.addNewStudents();
        case 3 -> studentController.updateStudent(dto);
        case 4 -> studentController.deleteStudent(dto);
        case 5 -> studentController.showStudent(dto);
        case 6 -> studentController.showStudentsByMonthIn2023();
        case 7 -> studentController.showParentsWithTwoChildren();
        case 8 -> studentController.showSexPercentageOfStudyingStudent();
        case 9 -> studentController.showStudentsByAgeInYear();
        case 10 -> authController.createNewAccount(dto.getRole());
      }

      return Response.CONTINUE;
    }
  }
}
