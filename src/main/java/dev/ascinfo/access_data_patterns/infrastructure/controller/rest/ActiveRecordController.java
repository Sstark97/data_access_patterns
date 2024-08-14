package dev.ascinfo.access_data_patterns.infrastructure.controller.rest;

import dev.ascinfo.access_data_patterns.domain.model.User;
import dev.ascinfo.access_data_patterns.infrastructure.active_record.PostgresUserActiveRecord;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/active_record")
public class ActiveRecordController {
  private final PostgresUserActiveRecord postgresUserActiveRecord;

  public ActiveRecordController(PostgresUserActiveRecord postgresUserActiveRecord) {
    this.postgresUserActiveRecord = postgresUserActiveRecord;
  }

  @PostMapping("/user")
  public ResponseEntity<Void> createUser(@RequestBody User user) {
    postgresUserActiveRecord.setUsername(user.username());
    postgresUserActiveRecord.setEmail(user.email());
    postgresUserActiveRecord.save();
    return ResponseEntity.ok().build();
  }
}
