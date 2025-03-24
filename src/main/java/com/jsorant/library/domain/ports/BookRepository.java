package com.jsorant.library.domain.ports;

import com.jsorant.library.domain.Book;
import java.util.Optional;

public interface BookRepository {
  void save(Book book);

  Optional<Book> get(String id);
}
