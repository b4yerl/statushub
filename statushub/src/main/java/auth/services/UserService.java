package auth.services;

import auth.dto.request.RegisterUserRequest;
import auth.models.User;
import auth.repository.UserRepository;
import auth.security.PasswordEncrypter;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class UserService {
  @Inject
  UserRepository repository;

  @Inject
  PasswordEncrypter passwordEncrypter;
  
  @Transactional
  public void registerUser(RegisterUserRequest input) throws IllegalArgumentException {
    User user = new User();
    user.setEmail(input.email());
    user.setName(input.name());
    user.setPassword(passwordEncrypter.encrypt(input.password()));
    try {
      repository.persistAndFlush(user);
    } catch(PersistenceException e){
      throw new IllegalArgumentException("Error registering user: " + e.getMessage());
    }
  }

}
