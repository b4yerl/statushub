package org.bayerl.auth.resources;

import org.bayerl.auth.dto.request.RegisterUserRequest;
import org.bayerl.auth.services.UserService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/user")
public class UserResource {
  @Inject
  UserService service;

  @POST
  @Path("/register")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response register(@Valid RegisterUserRequest input) {
    service.registerUser(input);
    return Response.status(Response.Status.CREATED).build();
  }
}
