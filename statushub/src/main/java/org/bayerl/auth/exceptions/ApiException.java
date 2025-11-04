package org.bayerl.auth.exceptions;

import jakarta.ws.rs.core.Response;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class ApiException extends RuntimeException {
  private final int statusCode;
  private final String error;

  public ApiException(int statusCode, String error, String message) {
    super(message);
    this.statusCode = statusCode;
    this.error = error;
  }
}
