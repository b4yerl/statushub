package org.bayerl.auth.security;

import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PasswordEncrypter {
  public String encrypt(String password) {
    String hashPassword = BcryptUtil.bcryptHash(password);
    return hashPassword;
  }

  public boolean verify(String password, String hashPassword) {
    return BcryptUtil.matches(password, hashPassword);
  }
}
