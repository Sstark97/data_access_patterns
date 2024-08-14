package dev.ascinfo.access_data_patterns.domain;

import java.util.List;
import java.util.UUID;

import dev.ascinfo.access_data_patterns.domain.model.User;
import dev.ascinfo.access_data_patterns.infrastructure.dto.UserDto;

public interface UserDAO {
  User getById(UUID id);
  List<User> getAll();
  void save(UserDto user);
  void update(User user);
  void delete(UUID id);
}
