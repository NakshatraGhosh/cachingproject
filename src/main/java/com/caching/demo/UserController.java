package com.caching.demo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

  private final UserService service;

  public UserController(UserService service) {
    this.service = service;
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserDto> get(@PathVariable Long id) {
    UserDto u = service.getUser(id);
    return (u == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok(u);
  }

  @PostMapping
  public UserDto save(@RequestBody UserDto user) {
    return service.saveUser(user);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    service.deleteUser(id);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/cache")
  public ResponseEntity<Void> clearCache() {
    service.clearCache();
    return ResponseEntity.noContent().build();
  }
}