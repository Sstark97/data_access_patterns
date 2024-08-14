package dev.ascinfo.access_data_patterns.infrastructure.controller.rest;

import java.util.UUID;

import dev.ascinfo.access_data_patterns.domain.model.User;
import dev.ascinfo.access_data_patterns.infrastructure.adapters.PostgresUserDAOAdapter;
import dev.ascinfo.access_data_patterns.infrastructure.dto.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dao")
public class DAOController {
  private final PostgresUserDAOAdapter daoAdapter;

  public DAOController(PostgresUserDAOAdapter daoAdapter) {
    this.daoAdapter = daoAdapter;
  }

  @PostMapping("/user")
  public ResponseEntity<Void> createUser(@RequestBody UserDto user) {
    daoAdapter.save(user);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/user")
  public ResponseEntity<User> getUser(@RequestParam UUID id) {
    return ResponseEntity.ok(daoAdapter.getById(id));
  }

  @PutMapping("/user")
  public ResponseEntity<Void> updateUser(@RequestBody User user) {
    daoAdapter.update(user);
    return ResponseEntity.ok().build();
  }
}
