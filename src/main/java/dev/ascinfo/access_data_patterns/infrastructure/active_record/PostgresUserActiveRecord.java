package dev.ascinfo.access_data_patterns.infrastructure.active_record;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import dev.ascinfo.access_data_patterns.domain.model.User;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PostgresUserActiveRecord {
  @Value("${data.username}")
  private String dataBaseUser;
  @Value("${data.password}")
  private String password;
  private String id;
  private String username;
  private String email;
  private Connection connection;

  @PostConstruct
  public void init() {
    try {
      connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/data_patterns",
          dataBaseUser, password);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public User findById(UUID id) {
    try(PreparedStatement ps = connection.prepareStatement("SELECT * FROM \"user\" WHERE id = ?")) {
      ps.setString(1, id.toString());
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        return new User(UUID.fromString(rs.getString("id")), rs.getString("username"),
            rs.getString("email"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  public void save() {
    this.id = UUID.randomUUID().toString();
    try (PreparedStatement ps = connection.prepareStatement(
        "INSERT INTO \"user\" (id, username, email) VALUES (?, ?, ?)",
        Statement.RETURN_GENERATED_KEYS)) {
      ps.setString(1, this.id);
      ps.setString(2, this.username);
      ps.setString(3, this.email);
      ps.executeUpdate();
      cleanFields();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void update() {
    if(userExists()) {
      try (PreparedStatement ps = connection.prepareStatement(
          "UPDATE \"user\" SET username = ?, email = ? WHERE id = ?")) {
        ps.setString(1, this.username);
        ps.setString(2, this.email);
        ps.setString(3, this.id);
        ps.executeUpdate();
        cleanFields();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  public void delete() {
    try (PreparedStatement ps = connection.prepareStatement("DELETE FROM \"user\" WHERE id = ?")) {
      ps.setString(1, this.id);
      ps.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private boolean userExists() {
    try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM \"user\" WHERE id = ?")) {
      ps.setString(1, this.id);
      ResultSet rs = ps.executeQuery();
      return rs.next();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  private void cleanFields() {
    this.id = null;
    this.username = null;
    this.email = null;
  }

  // Getters y setters
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}