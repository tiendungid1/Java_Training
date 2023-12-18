package rxn.jdbc.core.kindergarten.module.auth.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import rxn.java5.ObjectBuilder;
import rxn.jdbc.common.database.Jdbc;
import rxn.jdbc.core.kindergarten.enums.Role;
import rxn.jdbc.core.kindergarten.module.auth.model.Account;

public class AuthRepositoryImpl implements AuthRepository {

  @Override
  public Optional<Account> findByUsername(String username) throws SQLException {
    String query = """
                   SELECT [TenDN]
                         ,[MatKhau]
                         ,[VaiTro]
                   FROM [truong_mam_non_star].[dbo].[TAIKHOAN]
                   WHERE [TenDN] = ?;
                   """;

    try (
        Connection connection = Jdbc.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(query)
    ) {
      statement.setString(1, username);

      try (ResultSet rs = statement.executeQuery()) {
        if (!rs.isBeforeFirst()) {
          return Optional.empty();
        }

        return Optional.of(dtoFromResultSet(rs));
      }
    }
  }

  private Account dtoFromResultSet(ResultSet rs) throws SQLException {
    Account account = null;

    while (rs.next()) {
      Role role = null;

      switch (rs.getShort("VaiTro")) {
        case 1 -> role = Role.ADMIN;
        case 2 -> role = Role.EMPLOYEE;
        case 3 -> role = Role.PARENT;
      }

      account = ObjectBuilder.of(Account::new)
          .with(Account::setUsername, rs.getString("TenDN"))
          .with(Account::setPassword, rs.getString("MatKhau"))
          .with(Account::setRole, role)
          .build();
    }

    return account;
  }
}
