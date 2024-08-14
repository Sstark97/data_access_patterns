package dev.ascinfo.access_data_patterns.infrastructure.controller.rest;

import java.util.List;
import java.util.UUID;

import dev.ascinfo.access_data_patterns.domain.UserRepository;
import dev.ascinfo.access_data_patterns.domain.model.User;
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
@RequestMapping("/repository")
public class RepositoryPatternController {
  private final UserRepository userRepository;

  public RepositoryPatternController(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @GetMapping("/user/all")
  public ResponseEntity<List<User>> getAllUsers() {
    return ResponseEntity.ok(userRepository.findAll());
  }

  @PostMapping("/user")
  public ResponseEntity<Void> createUser(@RequestBody UserDto user) {
    userRepository.save(user);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/user")
  public ResponseEntity<User> getUser(@RequestParam UUID id) {
    return ResponseEntity.ok(userRepository.findById(id));
  }

  @DeleteMapping("/user")
  public ResponseEntity<Void> deleteUser(@RequestParam UUID id) {
    userRepository.delete(id);
    return ResponseEntity.ok().build();
  }
}
