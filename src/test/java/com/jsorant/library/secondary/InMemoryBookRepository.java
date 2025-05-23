package com.jsorant.library.secondary;

import com.jsorant.library.domain.Book;
import com.jsorant.library.domain.ports.BookRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryBookRepository implements BookRepository {

    private final Map<String, Book> books = new HashMap<>();

    @Override
    public void save(Book book) {
        books.put(book.id(), book);
    }

    @Override
    public Optional<Book> get(String id) {
        return Optional.ofNullable(books.get(id));
    }

    public void remove(Book book) {
        books.remove(book.id());
    }
}
