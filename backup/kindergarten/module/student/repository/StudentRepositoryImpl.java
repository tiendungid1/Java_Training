package rxn.jdbc.core.kindergarten.module.student.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import rxn.java5.ObjectBuilder;
import rxn.jdbc.common.database.Jdbc;
import rxn.jdbc.common.database.dataHandler.DataRepository;
import rxn.jdbc.core.kindergarten.enums.ClassStatus;
import rxn.jdbc.core.kindergarten.enums.Sex;
import rxn.jdbc.core.kindergarten.module.student.model.Student;

public class StudentRepositoryImpl implements StudentRepository {
  @Override
  public Optional<Student> findById(int id) throws SQLException {
    String query = """
                   SELECT [MaTre]
                         ,[MaLop]
                         ,[HoTenTre]
                         ,[NgaySinh]
                         ,[GioiTinh]
                         ,[HoTenBa]
                         ,[HoTenMe]
                         ,[SdtBa]
                         ,[SdtMe]
                         ,[DiaChi]
                         ,[TaiKhoanPH]
                   FROM [truong_mam_non_star].[dbo].[TREEM]
                   WHERE [MaTre] = ?
                   """;

    try (
        Connection connection = Jdbc.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(query)
    ) {
      statement.setInt(1, id);

      try (ResultSet rs = statement.executeQuery()) {
        if (!rs.isBeforeFirst()) {
          return Optional.empty();
        }
        return Optional.of(dtoFromResultSet(rs));
      }
    }
  }

  @Override
  public List<Student> findByIds(Object[] ids) throws SQLException {
    String query = """
                   SELECT [MaTre]
                         ,[MaLop]
                         ,[HoTenTre]
                         ,[NgaySinh]
                         ,[GioiTinh]
                         ,[HoTenBa]
                         ,[HoTenMe]
                         ,[SdtBa]
                         ,[SdtMe]
                         ,[DiaChi]
                         ,[TaiKhoanPH]
                   FROM [truong_mam_non_star].[dbo].[TREEM]
                   WHERE [MaTre] IN (?)
                   """;

    try (
        Connection connection = Jdbc.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(query)
    ) {
      statement.setArray(1, connection.createArrayOf("INT", ids));

      try (ResultSet rs = statement.executeQuery()) {
        if (!rs.isBeforeFirst()) {
          return Collections.emptyList();
        }
        return listFromResultSet(rs);
      }
    }
  }

  @Override
  public Optional<Student> findByAccount(String account) throws SQLException {
    String query = """
                   SELECT [MaTre]
                         ,[MaLop]
                         ,[HoTenTre]
                         ,[NgaySinh]
                         ,[GioiTinh]
                         ,[HoTenBa]
                         ,[HoTenMe]
                         ,[SdtBa]
                         ,[SdtMe]
                         ,[DiaChi]
                         ,[TaiKhoanPH]
                   FROM [truong_mam_non_star].[dbo].[TREEM]
                   WHERE [TaiKhoanPH] = ?
                   """;

    try (
        Connection connection = Jdbc.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(query)
    ) {
      statement.setString(1, account);

      try (ResultSet rs = statement.executeQuery()) {
        if (!rs.isBeforeFirst()) {
          return Optional.empty();
        }
        return Optional.of(dtoFromResultSet(rs));
      }
    }
  }

  @Override
  public List<Student> findByMonthInYear(int month, int year) throws SQLException {
    String query = """
                   SELECT [MaTre]
                         ,[MaLop]
                         ,[HoTenTre]
                         ,[NgaySinh]
                         ,[GioiTinh]
                         ,[HoTenBa]
                         ,[HoTenMe]
                         ,[SdtBa]
                         ,[SdtMe]
                         ,[DiaChi]
                         ,[TaiKhoanPH]
                   FROM [truong_mam_non_star].[dbo].[TREEM]
                   WHERE MONTH([NgaySinh]) = ? AND YEAR([NgaySinh]) = ?;
                   """;

    try (
        Connection connection = Jdbc.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(query)
    ) {
      statement.setInt(1, month);
      statement.setInt(2, year);

      try (ResultSet rs = statement.executeQuery()) {
        if (!rs.isBeforeFirst()) {
          return Collections.emptyList();
        }
        return listFromResultSet(rs);
      }
    }
  }

  @Override
  public List<Map<String, Object>> findParentsByStudentCount(int count) throws SQLException {
    String query = """
                   SELECT [HoTenBa], [SdtBa], [HoTenMe], [SdtMe]
                   FROM [truong_mam_non_star].[dbo].[TREEM]
                   GROUP BY [HoTenBa], [SdtBa], [HoTenMe], [SdtMe]
                   HAVING COUNT([HoTenBa]) = ? AND COUNT([HoTenMe]) = ?;
                   """;

    try (
        Connection connection = Jdbc.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(query)
    ) {
      statement.setInt(1, count);
      statement.setInt(2, count);

      try (ResultSet rs = statement.executeQuery()) {
        if (!rs.isBeforeFirst()) {
          return Collections.emptyList();
        }
        return DataRepository.listOfMapFromResultSet(rs);
      }
    }
  }

  @Override
  public Map<String, Object> findSexPercentage() throws SQLException {
    String query = """
                   SELECT
                       100 * SUM(CASE WHEN [GioiTinh] = 1 THEN 1 ELSE 0 END) / count([MaTre]) AS male_percentage,
                       100 * SUM(CASE WHEN [GioiTinh] = 0 THEN 1 ELSE 0 END) / count([MaTre]) AS female_percentage
                   FROM [truong_mam_non_star].[dbo].[TREEM]
                   WHERE [MaLop] IN (
                       SELECT [MaLop]
                       FROM [truong_mam_non_star].[dbo].[LOPSINHHOAT]
                       WHERE GETDATE() BETWEEN [NgayBD] AND [NgayKT]
                   );
                   """;
    return DataRepository.findOne(query);
  }

  @Override
  public List<Student> findByAge(LocalDate schoolStartDate, int age) throws SQLException {
    String query = """
                   SELECT [te].[MaTre]
                         ,[te].[MaLop]
                         ,[te].[HoTenTre]
                         ,[te].[NgaySinh]
                         ,[te].[GioiTinh]
                         ,[te].[HoTenBa]
                         ,[te].[HoTenMe]
                         ,[te].[SdtBa]
                         ,[te].[SdtMe]
                         ,[te].[DiaChi]
                         ,[te].[TaiKhoanPH]
                   FROM [truong_mam_non_star].[dbo].[TREEM] AS te
                   JOIN [truong_mam_non_star].[dbo].[LOPSINHHOAT] AS lsh
                   ON [te].[MaLop] = [lsh].[MaLop]
                   WHERE
                       (? BETWEEN [lsh].[NgayBD] AND [lsh].[NgayKT]) AND
                       (? >= DATEADD(YEAR, ?, [te].[NgaySinh]) AND
                       ? < DATEADD(YEAR, ?, [te].[NgaySinh]));
                   """;

    try (
        Connection connection = Jdbc.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(query)
    ) {
      Date sqlDate = Date.valueOf(schoolStartDate);
      statement.setDate(1, sqlDate);
      statement.setDate(2, sqlDate);
      statement.setInt(3, age);
      statement.setDate(4, sqlDate);
      statement.setInt(5, age + 1);

      try (ResultSet rs = statement.executeQuery()) {
        if (!rs.isBeforeFirst()) {
          return Collections.emptyList();
        }
        return listFromResultSet(rs);
      }
    }
  }

  @Override
  public boolean existClassesInYear(LocalDate schoolStartDate) throws SQLException {
    String query = """
                   SELECT [MaLop]
                   FROM [truong_mam_non_star].[dbo].[LOPSINHHOAT]
                   WHERE ? BETWEEN [NgayBD] AND [NgayKT];
                   """;

    try (
        Connection connection = Jdbc.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(query)
    ) {
      statement.setDate(1, Date.valueOf(schoolStartDate));

      try (ResultSet rs = statement.executeQuery()) {
        return rs.isBeforeFirst();
      }
    }
  }

  @Override
  public boolean classIsFull(int classId) throws SQLException {
    String query = """
                   SELECT COUNT([MaTre]) AS SoLuong
                   FROM [truong_mam_non_star].[dbo].[TREEM]
                   WHERE [MaLop] = ?
                   GROUP BY [MaLop];
                   """;

    try (
        Connection connection = Jdbc.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(query)
    ) {
      statement.setInt(1, classId);

      try (ResultSet rs = statement.executeQuery()) {
        boolean isFull = false;
        while (rs.next()) {
          isFull = rs.getInt("SoLuong") >= ClassStatus.MAX_STUDENTS_ALLOWED.quantity();
        }
        return isFull;
      }
    }
  }

  @Override
  public boolean existsClass(int classId) throws SQLException {
    String query = """
                   SELECT [MaLop]
                   FROM [truong_mam_non_star].[dbo].[LOPSINHHOAT]
                   WHERE [MaLop] = ?;
                   """;

    try (
        Connection connection = Jdbc.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(query)
    ) {
      statement.setInt(1, classId);

      try (ResultSet rs = statement.executeQuery()) {
        return rs.isBeforeFirst();
      }
    }
  }

  @Override
  public boolean isEmptyTable() throws SQLException {
    String query = """
                   SELECT TOP(1) [MaTre]
                   FROM [truong_mam_non_star].[dbo].[TREEM]
                   """;

    try (
        Connection connection = Jdbc.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet rs = statement.executeQuery()
    ) {
      return !rs.isBeforeFirst();
    }
  }

  @Override
  public Set<Integer> findStudentIdsInClass(String teacherAccount) throws SQLException {
    String query = """
                   DECLARE @teacherId INT;
                   SET @teacherId = (SELECT MaNV FROM NHANVIEN WHERE TaiKhoanNV = ?);
                                      
                   SELECT MaTre
                   FROM TREEM
                   WHERE MaLop IN (
                       SELECT MaLop
                       FROM LOPSINHHOAT
                       WHERE MaGV1 = @teacherId OR MaGV2 = @teacherId
                   );
                   """;

    try (
        Connection connection = Jdbc.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(query)
    ) {
      statement.setString(1, teacherAccount);

      try (ResultSet rs = statement.executeQuery()) {
        if (!rs.isBeforeFirst()) {
          return Collections.emptySet();
        }

        Set<Integer> studentIds = new HashSet<>();
        while (rs.next()) {
          studentIds.add(rs.getInt("MaTre"));
        }
        return studentIds;
      }
    }
  }

  private List<Student> listFromResultSet(ResultSet rs) throws SQLException {
    List<Student> students = new ArrayList<>();

    while (rs.next()) {
      students.add(ObjectBuilder.of(Student::new)
          .with(Student::setMaTre, rs.getInt("MaTre"))
          .with(Student::setMaLop, rs.getInt("MaLop"))
          .with(Student::setHoTenTre, rs.getString("HoTenTre"))
          .with(Student::setNgaySinh,
              rs.getDate("NgaySinh").toLocalDate())
          .with(Student::setGioiTinh, rs.getBoolean("GioiTinh") ? Sex.MALE : Sex.FEMALE)
          .with(Student::setHoTenBa, rs.getString("HoTenBa"))
          .with(Student::setHoTenMe, rs.getString("HoTenMe"))
          .with(Student::setSdtBa, rs.getString("SdtBa"))
          .with(Student::setSdtMe, rs.getString("SdtMe"))
          .with(Student::setDiaChi, rs.getString("DiaChi"))
          .with(Student::setTaiKhoanPH, rs.getString("TaiKhoanPH"))
          .build());
    }

    return students;
  }

  private Student dtoFromResultSet(ResultSet rs) throws SQLException {
    Student student = null;

    while (rs.next()) {
      student = ObjectBuilder.of(Student::new)
          .with(Student::setMaTre, rs.getInt("MaTre"))
          .with(Student::setMaLop, rs.getInt("MaLop"))
          .with(Student::setHoTenTre, rs.getString("HoTenTre"))
          .with(Student::setNgaySinh,
              rs.getDate("NgaySinh").toLocalDate())
          .with(Student::setGioiTinh, rs.getBoolean("GioiTinh") ? Sex.MALE : Sex.FEMALE)
          .with(Student::setHoTenBa, rs.getString("HoTenBa"))
          .with(Student::setHoTenMe, rs.getString("HoTenMe"))
          .with(Student::setSdtBa, rs.getString("SdtBa"))
          .with(Student::setSdtMe, rs.getString("SdtMe"))
          .with(Student::setDiaChi, rs.getString("DiaChi"))
          .with(Student::setTaiKhoanPH, rs.getString("TaiKhoanPH"))
          .build();
    }

    return student;
  }
}
