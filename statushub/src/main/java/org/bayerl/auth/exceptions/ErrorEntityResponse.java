package org.bayerl.auth.exceptions;

public class ErrorEntityResponse {
  public String error;
  public String message;
  public long timestamp;

  public ErrorEntityResponse(String error, String message) {
    this.error = error;
    this.message = message;
    this.timestamp = System.currentTimeMillis();
  }
}
