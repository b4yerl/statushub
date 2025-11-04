package org.bayerl.auth.services;

import org.jboss.logging.Logger;

import org.bayerl.auth.dto.request.LoginRequest;
import org.bayerl.auth.models.User;
import org.bayerl.auth.repository.UserRepository;
import org.bayerl.auth.security.PasswordEncrypter;
import io.quarkus.arc.log.LoggerName;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
public class JwtAuthImplementation implements AutheticationStrategy {
  @Inject
  UserRepository repository;

  @Inject
  PasswordEncrypter passwordEncrypter;

  @LoggerName("org.bayerl.auth-jwt-implementation")
  Logger logger;
  
  @Override
  public boolean authenticate(LoginRequest input) {
    User user = repository.findByEmail(input.email());
    logger.info(repository.listAll());
    logger.info("User fetched from DB: " + user + " for email: " + input.email());
    if(user == null || !passwordEncrypter.verify(input.password(), user.getPassword())) {
      return false;
    }
    return true;
  }

}
