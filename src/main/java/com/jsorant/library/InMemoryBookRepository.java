package com.jsorant.library;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryBookRepository implements BookRepository {

  private final Map<String, Book> books = new HashMap<>();

  public InMemoryBookRepository() {}

  @Override
  public void save(Book book) {
    books.put(book.id(), book);
  }

  @Override
  public Optional<Book> get(String id) {
    return Optional.ofNullable(books.get(id));
  }
}
