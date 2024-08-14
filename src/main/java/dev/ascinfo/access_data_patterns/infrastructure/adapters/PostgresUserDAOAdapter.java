package dev.ascinfo.access_data_patterns.infrastructure.adapters;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import dev.ascinfo.access_data_patterns.domain.UserDAO;
import dev.ascinfo.access_data_patterns.domain.model.User;
import dev.ascinfo.access_data_patterns.infrastructure.dto.UserDto;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PostgresUserDAOAdapter implements UserDAO {

  private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  public PostgresUserDAOAdapter(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
    this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
  }

  @Override
  public User getById(UUID id) {
    return namedParameterJdbcTemplate.query("SELECT * FROM \"user\" WHERE id = :id",
        Map.of("id", id.toString()),
        rs -> {
          rs.next();
          return new User(UUID.fromString(rs.getString("id")), rs.getString("username"), rs.getString("email"));
        });
  }

  @Override
  public List<User> getAll() {
    return List.of();
  }

  @Override
  public void save(UserDto user) {
    namedParameterJdbcTemplate.update("INSERT INTO \"user\" (id, username, email) VALUES (:id, :username, :email)",
        Map.of("id", UUID.randomUUID(), "username", user.username(), "email", user.email()));
  }

  @Override
  public void update(User user) {
    namedParameterJdbcTemplate.update("UPDATE \"user\" SET username = :username, email = :email WHERE id = :id",
        Map.of("id", user.id().toString(), "username", user.username(), "email", user.email()));
  }

  @Override
  public void delete(UUID id) {
    namedParameterJdbcTemplate.update("DELETE FROM \"user\" WHERE id = :id", Map.of("id", id.toString()));
  }
}
