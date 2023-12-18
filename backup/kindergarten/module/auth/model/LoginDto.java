package rxn.jdbc.core.kindergarten.module.auth.model;

import rxn.jdbc.core.kindergarten.enums.Role;

public class LoginDto {
  private String username;
  private Role role;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }
}
