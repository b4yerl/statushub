package org.bayerl.auth.exceptions.mapper;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import org.bayerl.auth.exceptions.ErrorEntityResponse;

public class GenericExceptionMapper implements ExceptionMapper<Throwable> {
  @Override
  public Response toResponse(Throwable exception) {
    ErrorEntityResponse responsePayload = new ErrorEntityResponse(
        "INTERNAL_SERVER_ERROR",
        "Ocorreu um erro na aplicação"
    );

    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responsePayload).build();
  }
}
