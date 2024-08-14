package dev.ascinfo.access_data_patterns.domain;

import java.util.List;
import java.util.UUID;

import dev.ascinfo.access_data_patterns.domain.model.User;
import dev.ascinfo.access_data_patterns.infrastructure.dto.UserDto;

public interface UserRepository {
  User findById(UUID id);
  List<User> findAll();
  void save(UserDto user);
  void delete(UUID id);
}
