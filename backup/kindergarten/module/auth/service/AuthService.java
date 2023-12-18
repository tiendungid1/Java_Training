package rxn.jdbc.core.kindergarten.module.auth.service;

import java.sql.SQLException;
import rxn.jdbc.core.kindergarten.enums.Role;
import rxn.jdbc.core.kindergarten.module.auth.model.LoginDto;

public interface AuthService {
  int createAccount(Role role)
      throws SQLException, IllegalAccessException;

  LoginDto login() throws SQLException;
}
