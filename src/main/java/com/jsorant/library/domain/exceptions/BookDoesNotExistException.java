package com.jsorant.library.domain.exceptions;

import java.util.Objects;

public class BookDoesNotExistException extends RuntimeException {

  private final String bookId;

  public BookDoesNotExistException(String bookId) {
    super("Cannot borro book with id " + bookId + " because it does not exist");
    this.bookId = bookId;
  }

  public String bookId() {
    return bookId;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    BookDoesNotExistException that = (BookDoesNotExistException) o;
    return Objects.equals(bookId, that.bookId);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(bookId);
  }
}
