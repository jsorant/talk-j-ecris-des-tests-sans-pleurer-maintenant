package com.jsorant.library.domain.exceptions;

import java.util.Objects;

public class BookNotOwnedByTheLibraryException extends RuntimeException {

    private final String bookId;

    public BookNotOwnedByTheLibraryException(String bookId) {
        super("Cannot borrow book with id " + bookId + " because it is not owned by the library");
        this.bookId = bookId;
    }

    public String bookId() {
        return bookId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BookNotOwnedByTheLibraryException that = (BookNotOwnedByTheLibraryException) o;
        return Objects.equals(bookId, that.bookId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(bookId);
    }
}
