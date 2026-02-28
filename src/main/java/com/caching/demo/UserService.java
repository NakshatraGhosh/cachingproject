package com.caching.demo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  // fake DB for demo (replace with JPA repository later)
  private final Map<Long, UserDto> db = new ConcurrentHashMap<>();

  public UserService() {
    db.put(1L, new UserDto(1L, "Nakshatra"));
    db.put(2L, new UserDto(2L, "Demo User"));
  }

  // 1) Cache read
  @Cacheable(value = "users", key = "#id")
  public UserDto getUser(Long id) {
    System.out.println("DB HIT (not cache) for id=" + id);
    sleep(1200); // simulate slow DB
    return db.get(id);
  }

  // 2) Update cache when saving/updating
  @CachePut(value = "users", key = "#result.id")
  public UserDto saveUser(UserDto user) {
    System.out.println("DB SAVE for id=" + user.getId());
    db.put(user.getId(), user);
    return user;
  }

  // 3) Remove cache entry when deleting
  @CacheEvict(value = "users", key = "#id")
  public void deleteUser(Long id) {
    System.out.println("DB DELETE for id=" + id);
    db.remove(id);
  }

  // 4) Clear whole cache
  @CacheEvict(value = "users", allEntries = true)
  public void clearCache() {
    System.out.println("CACHE CLEARED");
  }

  private void sleep(long ms) {
    try { Thread.sleep(ms); } catch (InterruptedException ignored) {}
  }
}