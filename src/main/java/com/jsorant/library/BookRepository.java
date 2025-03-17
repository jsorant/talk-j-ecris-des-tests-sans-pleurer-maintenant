package com.jsorant.library;

import java.util.Optional;

public interface BookRepository {
  void save(Book book);

  Optional<Book> get(String id);
}
