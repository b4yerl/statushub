package auth.services;

import auth.dto.request.LoginRequest;

public interface AutheticationStrategy {
  public boolean authenticate(LoginRequest input);
}
