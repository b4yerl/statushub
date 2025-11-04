package org.bayerl.auth.services;

import org.bayerl.auth.exceptions.InvalidCredentialsException;
import org.jboss.logging.Logger;

import org.bayerl.auth.dto.request.LoginRequest;
import org.bayerl.auth.dto.response.LoginResponse;
import org.bayerl.auth.repository.UserRepository;
import org.bayerl.auth.security.JwtTokenGenerator;
import org.bayerl.auth.security.PasswordEncrypter;
import io.quarkus.arc.log.LoggerName;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Any;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;

@ApplicationScoped
public class AuthService {

  @Inject
  UserRepository repository;

  @LoggerName("org.bayerl.auth-resource")
  Logger logger;

  @Inject
  @Any
  Instance<AutheticationStrategy> strategies;

  @Inject
  PasswordEncrypter passwordEncrypter;

  @Inject
  JwtTokenGenerator jwtTokenGenerator;
  
  public LoginResponse authenticate(LoginRequest input) throws InvalidCredentialsException {
    AutheticationStrategy strategy = selectStrategyImpl();
    if(!strategy.authenticate(input)) {
      logger.info("Authentication failed for user: " + input.email());
      throw new InvalidCredentialsException();
    }
    return new LoginResponse(jwtTokenGenerator.generateToken(input.email()));
  }

  public AutheticationStrategy selectStrategyImpl() {
    // Apenas uma implementação por enquanto, mas futuramente pode haver mais como OAuth
    return strategies.get();
  }
}
