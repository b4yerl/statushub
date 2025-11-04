package org.bayerl.auth.security;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class JwtTokenGenerator {
  private final int THIRTY_MINUTES = 30 * 60;

  public String generateToken(String userEmail) {
    return Jwt.issuer("statushub")
        .upn(userEmail)
        .groups("User")
        .expiresAt(THIRTY_MINUTES)
        .claim("email", userEmail).sign();
  }

}
