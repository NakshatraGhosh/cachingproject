package com.caching.demo;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class BookService {

  private final BookRepository repo;

  public BookService(BookRepository repo) {
    this.repo = repo;
  }

  // 1) READ: Cache by id
  @Cacheable(value = "books", key = "#id")
  public Book getById(Long id) {
    System.out.println("DB HIT for id=" + id);
    return repo.findById(id).orElse(null);
  }

  // 2) CREATE/UPDATE: Write to DB and refresh cache
  @CachePut(value = "books", key = "#result.id")
  public Book save(Book b) {
    System.out.println("DB SAVE for id=" + b.getId());
    return repo.save(b);
  }

  // 3) DELETE: Remove cache entry
  @CacheEvict(value = "books", key = "#id")
  public void delete(Long id) {
    System.out.println("DB DELETE for id=" + id);
    repo.deleteById(id);
  }

  // Optional: clear all cached books
  @CacheEvict(value = "books", allEntries = true)
  public void clearCache() {
    System.out.println("CACHE CLEARED");
  }
}