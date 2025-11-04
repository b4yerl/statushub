package org.bayerl.auth.services;

import org.bayerl.auth.dto.request.RegisterUserRequest;
import org.bayerl.auth.exceptions.EmailTakenException;
import org.bayerl.auth.models.User;
import org.bayerl.auth.repository.UserRepository;
import org.bayerl.auth.security.PasswordEncrypter;
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
  public void registerUser(RegisterUserRequest input) throws EmailTakenException {
    User user = new User();
    user.setEmail(input.email());
    user.setName(input.name());
    user.setPassword(passwordEncrypter.encrypt(input.password()));
    try {
      repository.persistAndFlush(user);
    } catch(PersistenceException e){
      throw new EmailTakenException();
    }
  }

}
