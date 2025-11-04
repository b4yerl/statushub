package org.bayerl.auth.exceptions;

public class EmailTakenException extends ApiException {
  public EmailTakenException() {
    super(400, "BAD_REQUEST", "Email já cadastrado");
  }
}
