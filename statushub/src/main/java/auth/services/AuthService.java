package auth.services;

import org.jboss.logging.Logger;

import auth.dto.request.LoginRequest;
import auth.dto.response.LoginResponse;
import auth.repository.UserRepository;
import auth.security.JwtTokenGenerator;
import auth.security.PasswordEncrypter;
import io.quarkus.arc.log.LoggerName;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Any;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;

@ApplicationScoped
public class AuthService {

  @Inject
  UserRepository repository;

  @LoggerName("auth-resource")
  Logger logger;

  @Inject
  @Any
  Instance<AutheticationStrategy> strategies;

  @Inject
  PasswordEncrypter passwordEncrypter;

  @Inject
  JwtTokenGenerator jwtTokenGenerator;
  
  public LoginResponse authenticate(LoginRequest input) throws IllegalArgumentException {
    AutheticationStrategy strategy = selectStrategyImpl();
    if(!strategy.authenticate(input)) {
      logger.info("Authentication failed for user: " + input.email());
      throw new IllegalArgumentException("Invalid credentials");
    }
    return new LoginResponse(jwtTokenGenerator.generateToken(input.email()));
  }

  public AutheticationStrategy selectStrategyImpl() {
    // Apenas uma implementação por enquanto, mas futuramente pode haver mais como OAuth
    return strategies.get();
  }
}
