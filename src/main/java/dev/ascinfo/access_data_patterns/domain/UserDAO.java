package dev.ascinfo.access_data_patterns.domain;

import java.util.List;

import dev.ascinfo.access_data_patterns.domain.model.User;
import dev.ascinfo.access_data_patterns.infrastructure.dto.UserDto;

public interface UserDAO {
  User getById(int id);
  List<User> getAll();
  void save(UserDto user);
  void update(User user);
  void delete(int id);
}
