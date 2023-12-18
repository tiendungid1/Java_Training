package rxn.jdbc.core.kindergarten.module.auth;

import java.sql.SQLException;
import rxn.jdbc.common.util.console.Console;
import rxn.jdbc.core.kindergarten.enums.Role;
import rxn.jdbc.core.kindergarten.module.auth.model.LoginDto;
import rxn.jdbc.core.kindergarten.module.auth.service.AuthService;

public class AuthController {
  private final AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  public void createNewAccount(Role role) throws SQLException, IllegalAccessException {
    Console.info(">>> CREATING NEW ACCOUNT <<<");
    int rowAffected = authService.createAccount(role);
    Console.info("Finish creating, " + rowAffected + " row affected");
  }

  public LoginDto login() throws SQLException {
    Console.info(">>> LOGIN TO ACCOUNT <<<");
    return authService.login();
  }
}
