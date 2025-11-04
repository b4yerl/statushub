package org.bayerl.auth.exceptions;

public class InvalidCredentialsException extends ApiException {
  public InvalidCredentialsException() {
    super(401, "INVALID_CREDENTIALS", "Usuário Inválido");
  }
}
