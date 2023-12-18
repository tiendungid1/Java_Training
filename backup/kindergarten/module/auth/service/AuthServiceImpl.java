package rxn.jdbc.core.kindergarten.module.auth.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import rxn.java5.ObjectBuilder;
import rxn.jdbc.common.database.dataHandler.DataRepository;
import rxn.jdbc.common.database.dataHandler.FieldDto;
import rxn.jdbc.common.util.console.Console;
import rxn.jdbc.common.util.console.ConsoleInput;
import rxn.jdbc.core.kindergarten.enums.Role;
import rxn.jdbc.core.kindergarten.module.auth.model.Account;
import rxn.jdbc.core.kindergarten.module.auth.model.LoginDto;
import rxn.jdbc.core.kindergarten.module.auth.repository.AuthRepository;

public class AuthServiceImpl implements AuthService {

  private final AuthRepository authRepository;

  public AuthServiceImpl(AuthRepository authRepository) {
    this.authRepository = authRepository;
  }

  @Override
  public int createAccount(Role role)
      throws SQLException, IllegalAccessException {
    if (!role.equals(Role.ADMIN)) {
      throw new IllegalAccessException("Không đủ quyền hạn để thực hiện chức năng này");
    }

    List<FieldDto> newAccount = new ArrayList<>();

    Console.info("Nhập tên đăng nhập");
    String username = ConsoleInput.getString(false, false);

    Optional<Account> account = authRepository.findByUsername(username);

    if (account.isPresent()) {
      Console.emphasize("Tài khoản đã tồn tại, vui lòng nhập username khác");
      createAccount(role);
    }

    newAccount.add(new FieldDto("TenDN", username));

    Console.info("Nhập mật khẩu");
    newAccount.add(new FieldDto("MatKhau", ConsoleInput.getString(true, false)));

    newAccount.add(new FieldDto("VaiTro", getInputRole()));

    return DataRepository.insertOne("TAIKHOAN", newAccount);
  }

  @Override
  public LoginDto login() throws SQLException {
    Console.info("Nhập tên đăng nhập");
    String username = ConsoleInput.getString(false, false);
    Console.info("Nhập mật khẩu");
    String password = ConsoleInput.getString(true, false);

    Optional<Account> account = authRepository.findByUsername(username);

    if (account.isEmpty()) {
      Console.emphasize("Tài khoản hoặc mật khẩu không đúng, vui lòng thử lại");
      return login();
    }

    if (!account.get().getPassword().equals(password)) {
      Console.emphasize("Tài khoản hoặc mật khẩu không đúng, vui lòng thử lại");
      return login();
    }

    return ObjectBuilder.of(LoginDto::new)
        .with(LoginDto::setUsername, account.get().getUsername())
        .with(LoginDto::setRole, account.get().getRole())
        .build();
  }

  private Role getInputRole() {
    Console.info("Nhập vai trò: 'employee' cho nhân viên, 'parent' cho phụ huynh");
    String input = ConsoleInput.getString(false, false);

    Optional<Role> role = Arrays.stream(Role.values())
        .filter(r -> r.name().equalsIgnoreCase(input))
        .findFirst();

    if (role.isEmpty()) {
      Console.emphasize("Chuỗi nhập vào '" + input + "' không hợp lệ");
      Console.emphasize("Vui lòng thử lại");
      Console.newline();
      return getInputRole();
    }

    return role.get();
  }
}
