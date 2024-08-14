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

  private static final String ID = "id";
  private static final String USERNAME = "username";
  private static final String EMAIL = "email";
  private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  public PostgresUserDAOAdapter(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
    this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
  }

  @Override
  public User getById(UUID id) {
    return namedParameterJdbcTemplate.query("SELECT * FROM \"user\" WHERE id = :id",
        Map.of(ID, id.toString()),
        rs -> {
          rs.next();
          return new User(UUID.fromString(rs.getString(ID)), rs.getString(USERNAME), rs.getString(
              EMAIL));
        });
  }

  @Override
  public List<User> getAll() {
    return namedParameterJdbcTemplate.query("SELECT * FROM \"user\"",
        (rs, rowNum) -> new User(UUID.fromString(rs.getString(ID)), rs.getString(USERNAME), rs.getString(
            EMAIL)));
  }

  @Override
  public void save(UserDto user) {
    namedParameterJdbcTemplate.update("INSERT INTO \"user\" (id, username, email) VALUES (:id, :username, :email)",
        Map.of(ID, UUID.randomUUID(), USERNAME, user.username(), EMAIL, user.email()));
  }

  @Override
  public void update(User user) {
    namedParameterJdbcTemplate.update("UPDATE \"user\" SET username = :username, email = :email WHERE id = :id",
        Map.of(ID, user.id().toString(), USERNAME, user.username(), EMAIL, user.email()));
  }

  @Override
  public void delete(UUID id) {
    namedParameterJdbcTemplate.update("DELETE FROM \"user\" WHERE id = :id", Map.of(ID, id.toString()));
  }
}
