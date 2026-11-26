package dk.via.sep3.ttot.service;

import dk.via.sep3.ttot.model.User;
import dk.via.sep3.ttot.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService
{
  private final UserRepository userRepository;

  public UserService(UserRepository userRepository)
  {
    this.userRepository = userRepository;
  }

  public Optional<User> getByUsername(String username)
  {
    return userRepository.findByUsername(username);
  }

  public Optional<User> register(String username, String password)
  {
    if (userRepository.existsByUsername(username))
    {
      return Optional.empty();
    }
    return Optional.of(userRepository.save(new User(username, password)));
  }
}
