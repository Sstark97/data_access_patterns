package dev.ascinfo.access_data_patterns.infrastructure.adapters;

import java.util.List;
import java.util.UUID;

import dev.ascinfo.access_data_patterns.domain.UserRepository;
import dev.ascinfo.access_data_patterns.domain.model.User;
import dev.ascinfo.access_data_patterns.infrastructure.dto.UserDto;
import dev.ascinfo.access_data_patterns.infrastructure.external.UserClient;
import org.springframework.stereotype.Component;

@Component
public class RestApiDAOAdapter implements UserRepository {
  private final PostgresUserDAOAdapter userDAO;
  private final UserClient userClient;

  public RestApiDAOAdapter(PostgresUserDAOAdapter userDAO, UserClient userClient) {
    this.userDAO = userDAO;
    this.userClient = userClient;
  }

  @Override
  public User findById(UUID id) {
    return userDAO.getById(id);
  }

  @Override
  public List<User> findAll() {
    List<User> users = userDAO.getAll();
    List<User> usersFromApi = userClient.retrieveUsers().stream().map(user -> new User(UUID.randomUUID(),
        user.username(), user.email())).toList();

    users.addAll(usersFromApi);

    return users;
  }

  @Override
  public void save(UserDto user) {
    userDAO.save(user);
  }

  @Override
  public void delete(UUID id) {
    userDAO.delete(id);
  }
}
