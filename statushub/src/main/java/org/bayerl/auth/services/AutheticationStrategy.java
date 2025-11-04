package org.bayerl.auth.services;

import org.bayerl.auth.dto.request.LoginRequest;

public interface AutheticationStrategy {
  public boolean authenticate(LoginRequest input);
}
