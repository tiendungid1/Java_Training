package rxn.jdbc.core.kindergarten.module.auth.repository;

import java.sql.SQLException;
import java.util.Optional;
import rxn.jdbc.core.kindergarten.module.auth.model.Account;

public interface AuthRepository {
  Optional<Account> findByUsername(String username) throws SQLException;
}
