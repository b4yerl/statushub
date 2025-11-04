package org.bayerl.auth.exceptions.mapper;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.bayerl.auth.exceptions.ApiException;
import org.bayerl.auth.exceptions.ErrorEntityResponse;

@Provider
public class AuthExceptionMapper implements ExceptionMapper<ApiException> {
  @Override
  public Response toResponse(ApiException exception) {
    ErrorEntityResponse responsePayload = new ErrorEntityResponse(
        exception.getError(),
        exception.getMessage()
    );

    return Response.status(exception.getStatusCode()).entity(responsePayload).build();
  }
}
