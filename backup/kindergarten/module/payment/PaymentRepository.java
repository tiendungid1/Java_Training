package rxn.jdbc.core.kindergarten.module.payment;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import rxn.jdbc.common.database.Jdbc;
import rxn.jdbc.core.kindergarten.enums.AbsentSession;

public class PaymentRepository {
  public List<Integer> findClassIdsInCurrentSchoolYear(LocalDate startDate) throws SQLException {
    String query = """
                   SELECT MaLop
                   FROM LOPSINHHOAT
                   WHERE NgayBD = ?;
                   """;

    try (
        Connection connection = Jdbc.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(query)
    ) {
      statement.setDate(1, Date.valueOf(startDate));

      try (ResultSet rs = statement.executeQuery()) {
        if (!rs.isBeforeFirst()) {
          return Collections.emptyList();
        }

        List<Integer> classIds = new ArrayList<>();
        while (rs.next()) {
          classIds.add(rs.getInt("MaLop"));
        }
        return classIds;
      }
    }
  }

  public BigDecimal findTalentClassFeesInMonth(int month, int studentId) throws SQLException {
    String query = """
                   SELECT SUM(HocPhiMoiThang) AS HocPhi
                   FROM LOPNANGKHIEU
                   JOIN DANGKYLOPNANGKHIEU
                   ON LOPNANGKHIEU.MaLopNK = DANGKYLOPNANGKHIEU.MaLopNK
                   WHERE MaTre = ? and MONTH(NgayBD) = ?
                   GROUP by MaTre;
                   """;

    try (
        Connection connection = Jdbc.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(query)
    ) {
      statement.setInt(1, studentId);
      statement.setInt(2, month);

      try (ResultSet rs = statement.executeQuery()) {
        if (!rs.isBeforeFirst()) {
          return new BigDecimal("0");
        }

        BigDecimal fees = null;
        while (rs.next()) {
          fees = new BigDecimal(String.valueOf(rs.getBigDecimal("HocPhi")));
        }
        return fees;
      }
    }
  }

  public BigDecimal findTemporaryTuitionInMonth(
      LocalDate startDate, LocalDate endDate, int classId, BigDecimal talentClassFees
  ) throws SQLException {
    String query = """
                   DECLARE @startDate DATE, @endDate DATE, @daysOfMonthExcludeWeekend INT;
                   SET @startDate = ?;
                   SET @endDate = ?;
                   SET @daysOfMonthExcludeWeekend =
                       (DATEDIFF(DAY, @startDate, @endDate) + 1)
                       - (DATEDIFF(WEEK, @startDate, @endDate) * 2)
                       - (CASE WHEN DATENAME(WEEKDAY, @startDate) = 'Sunday' THEN 1 ELSE 0 END)
                       - (CASE WHEN DATENAME(WEEKDAY, @endDate) = 'Saturday' THEN 1 ELSE 0 END);
                                      
                   SELECT (@daysOfMonthExcludeWeekend * TienAnMoiBuoi * 2 + HocPhiMoiThang + ?) AS HocPhi
                   FROM LOPSINHHOAT
                   WHERE MaLop = ?;
                   """;

    try (
        Connection connection = Jdbc.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(query)
    ) {
      statement.setDate(1, Date.valueOf(startDate));
      statement.setDate(2, Date.valueOf(endDate));
      statement.setBigDecimal(3, talentClassFees);
      statement.setInt(4, classId);

      try (ResultSet rs = statement.executeQuery()) {
        BigDecimal tuition = null;
        while (rs.next()) {
          tuition = new BigDecimal(String.valueOf(rs.getBigDecimal("HocPhi")));
        }
        return tuition;
      }
    }
  }

  public boolean existBaseRecords(int month, int year) throws SQLException {
    String query = """
                   SELECT TOP(1) MaTT
                   FROM THANHTOAN
                   WHERE MONTH(NgayThanhToan) = ? AND YEAR(NgayThanhToan) = ?;
                   """;

    try (
        Connection connection = Jdbc.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(query)
    ) {
      statement.setInt(1, month);
      statement.setInt(2, year);

      try (ResultSet rs = statement.executeQuery()) {
        return rs.isBeforeFirst();
      }
    }
  }

  public List<Integer> findStudentIdsInClass(int classId) throws SQLException {
    String query = """
                   SELECT MaTre
                   FROM TREEM
                   WHERE MaLop = ?
                   """;

    try (
        Connection connection = Jdbc.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(query)
    ) {
      statement.setInt(1, classId);

      try (ResultSet rs = statement.executeQuery()) {
        List<Integer> studentIds = new ArrayList<>();

        while (rs.next()) {
          studentIds.add(rs.getInt("MaTre"));
        }

        return studentIds;
      }
    }
  }

  public int findAbsentSessionsOfStudent(
      int studentId, int classId, LocalDate startDate, LocalDate endDate
  ) throws SQLException {
    String query = """
                   SELECT Vang
                   FROM DANHGIATRE
                   WHERE MaTre = ? AND MaLop = ? AND NgayDanhGia BETWEEN ? AND ?;
                   """;

    try (
        Connection connection = Jdbc.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(query)
    ) {
      statement.setInt(1, studentId);
      statement.setInt(2, classId);
      statement.setDate(3, Date.valueOf(startDate));
      statement.setDate(4, Date.valueOf(endDate));

      try (ResultSet rs = statement.executeQuery()) {
        if (!rs.isBeforeFirst()) {
          return 0;
        }

        int absentSessionCount = 0;

        while (rs.next()) {
          int value = rs.getShort("Vang");

          if (value == AbsentSession.MORNING.value()) {
            absentSessionCount++;
          } else if (value == AbsentSession.AFTERNOON.value()) {
            absentSessionCount++;
          } else if (value == AbsentSession.ALL_DAY.value()) {
            absentSessionCount += 2;
          }
        }

        return absentSessionCount;
      }
    }
  }

  public int updateActualPaymentOfStudent(
      ActualPaymentDto dto
  ) throws SQLException {
    String query = """
                   DECLARE @classId INT, @studentId INT;
                   DECLARE @reducedMealCost MONEY;
                                      
                   SET @classId = ?;
                   SET @studentId = ?;
                   SET @reducedMealCost = (
                       SELECT TienAnMoiBuoi * ?
                       FROM LOPSINHHOAT
                       WHERE MaLop = @classId
                   );
                                      
                   UPDATE THANHTOAN
                   SET TienThucTe = TienTamThoi - @reducedMealCost - ?
                   WHERE MaTre = @studentId AND MaLop = @classId AND NgayThanhToan = ?;
                                      
                   UPDATE THANHTOAN
                   SET TrangThai = 1
                   WHERE MaTre = @studentId AND MaLop = @classId AND NgayThanhToan = ?;
                   """;

    try (Connection connection = Jdbc.getInstance().getConnection()) {
      connection.setAutoCommit(false);

      try (PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setInt(1, dto.getClassId());
        statement.setInt(2, dto.getStudentId());
        statement.setInt(3, dto.getAbsentSessionCount());
        statement.setBigDecimal(4, dto.getTalentClassFees());
        statement.setDate(5, Date.valueOf(dto.getDatePreviousMonth()));
        statement.setDate(6, Date.valueOf(dto.getDate()));

        int updateRowCount = statement.executeUpdate();
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

  public List<Integer> findClassIdsOfTeacher(int teacherId) throws SQLException {
    String query = """
                   SELECT MaLop
                   FROM LOPSINHHOAT
                   WHERE MaGV1 = ? OR MaGV2 = ?;
                   """;

    try (
        Connection connection = Jdbc.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(query)
    ) {
      statement.setInt(1, teacherId);
      statement.setInt(2, teacherId);

      try (ResultSet rs = statement.executeQuery()) {
        if (!rs.isBeforeFirst()) {
          return Collections.emptyList();
        }

        List<Integer> classIds = new ArrayList<>();
        while (rs.next()) {
          classIds.add(rs.getInt("MaLop"));
        }
        return classIds;
      }
    }
  }

  public List<Integer> findStudentIdsByParentAccount(String account) throws SQLException {
    String query = """
                   SELECT MaTre
                   FROM TREEM
                   WHERE TaiKhoanPH = ?;
                   """;

    try (
        Connection connection = Jdbc.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(query)
    ) {
      statement.setString(1, account);

      try (ResultSet rs = statement.executeQuery()) {
        if (!rs.isBeforeFirst()) {
          return Collections.emptyList();
        }

        List<Integer> studentIds = new ArrayList<>();
        while (rs.next()) {
          studentIds.add(rs.getInt("MaTre"));
        }
        return studentIds;
      }
    }
  }

  public BigDecimal findTuitionSumOfStudent(int studentId, LocalDate payDate) throws SQLException {
    String query = """
                   SELECT SUM(TienThucTe) AS HocPhi
                   FROM THANHTOAN
                   WHERE MaTre = ? AND NgayThanhToan = ?
                   GROUP BY MaTre;
                   """;

    try (
        Connection connection = Jdbc.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(query)
    ) {
      statement.setInt(1, studentId);
      statement.setDate(2, Date.valueOf(payDate));

      try (ResultSet rs = statement.executeQuery()) {
        if (!rs.isBeforeFirst()) {
          return new BigDecimal("0");
        }

        BigDecimal tuition = null;
        while (rs.next()) {
          tuition = new BigDecimal(
              String.valueOf(rs.getBigDecimal("HocPhi"))
          );
        }
        return tuition;
      }
    }
  }

  public int insert(List<PaymentDto> dtos) throws SQLException {
    String query = """
                   INSERT INTO THANHTOAN (MaTre, MaLop, NgayThanhToan, TienTamThoi, TienThucTe)
                   VALUES (?, ?, ?, ?, ?);
                   """;

    try (Connection connection = Jdbc.getInstance().getConnection()) {
      connection.setAutoCommit(false);

      try (PreparedStatement statement = connection.prepareStatement(query)) {
        for (PaymentDto dto : dtos) {
          statement.setInt(1, dto.getMaTre());
          statement.setInt(2, dto.getMaLop());
          statement.setDate(3, Date.valueOf(dto.getNgayThanhToan()));
          statement.setBigDecimal(4, dto.getTienTamThoi());
          statement.setBigDecimal(5, dto.getTienTamThoi());
          statement.addBatch();
          statement.clearParameters();
        }

        int insertRowsCount = statement.executeBatch().length;
        connection.commit();
        return insertRowsCount;
      } catch (SQLException e) {
        connection.rollback();
        throw e;
      } finally {
        connection.setAutoCommit(true);
      }
    }
  }
}
