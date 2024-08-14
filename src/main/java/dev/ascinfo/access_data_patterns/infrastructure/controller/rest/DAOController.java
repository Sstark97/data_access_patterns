package dev.ascinfo.access_data_patterns.infrastructure.controller.rest;

import dev.ascinfo.access_data_patterns.infrastructure.adapters.PostgresUserDAOAdapter;
import dev.ascinfo.access_data_patterns.infrastructure.dto.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
