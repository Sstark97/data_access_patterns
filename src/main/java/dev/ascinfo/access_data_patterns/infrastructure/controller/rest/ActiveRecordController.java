package dev.ascinfo.access_data_patterns.infrastructure.controller.rest;

import java.util.UUID;

import dev.ascinfo.access_data_patterns.domain.model.User;
import dev.ascinfo.access_data_patterns.infrastructure.active_record.PostgresUserActiveRecord;
import dev.ascinfo.access_data_patterns.infrastructure.dto.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/active_record")
public class ActiveRecordController {
  private final PostgresUserActiveRecord postgresUserActiveRecord;

  public ActiveRecordController(PostgresUserActiveRecord postgresUserActiveRecord) {
    this.postgresUserActiveRecord = postgresUserActiveRecord;
  }

  @PostMapping("/user")
  public ResponseEntity<Void> createUser(@RequestBody UserDto user) {
    postgresUserActiveRecord.setUsername(user.username());
    postgresUserActiveRecord.setEmail(user.email());
    postgresUserActiveRecord.save();
    return ResponseEntity.ok().build();
  }

  @GetMapping("/user")
  public ResponseEntity<User> getUser(@RequestParam UUID id) {
    return ResponseEntity.ok(postgresUserActiveRecord.findById(id));
  }

  @PutMapping("/user")
  public ResponseEntity<Void> updateUser(@RequestBody User user) {
    postgresUserActiveRecord.setId(user.id().toString());
    postgresUserActiveRecord.setUsername(user.username());
    postgresUserActiveRecord.setEmail(user.email());
    postgresUserActiveRecord.update();
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/user")
  public ResponseEntity<Void> deleteUser(@RequestParam UUID id) {
    postgresUserActiveRecord.setId(id.toString());
    postgresUserActiveRecord.delete();
    return ResponseEntity.ok().build();
  }
}
