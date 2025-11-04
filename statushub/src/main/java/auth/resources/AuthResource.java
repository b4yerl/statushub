package auth.resources;

import org.jboss.logging.Logger;

import auth.dto.request.LoginRequest;
import auth.dto.response.LoginResponse;
import auth.services.AuthService;
import io.quarkus.arc.log.LoggerName;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/auth")
public class AuthResource {

  @Inject
  AuthService service;

  @LoggerName("auth-resource")
  Logger logger;

  @POST
  @Path("/login")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response login(@Valid LoginRequest input) {
    try {
      LoginResponse responsePayload = service.authenticate(input);
      return Response.ok(responsePayload).build();
    } catch (IllegalArgumentException e) {
      return Response.status(Response.Status.UNAUTHORIZED).entity(e.getMessage()).build();
    }
  }
}
