package com.caching.demo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BookController {

  private final BookService service;

  public BookController(BookService service) {
    this.service = service;
  }

  @GetMapping("/{id}")
  public ResponseEntity<Book> get(@PathVariable Long id) {
    Book b = service.getById(id);
    return (b == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok(b);
  }

  @PostMapping
  public Book create(@RequestBody Book b) {
    b.setId(null); // ensure new insert
    return service.save(b);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Book> update(@PathVariable Long id, @RequestBody Book b) {
    b.setId(id);
    return ResponseEntity.ok(service.save(b));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/cache")
  public ResponseEntity<Void> clearCache() {
    service.clearCache();
    return ResponseEntity.noContent().build();
  }
}