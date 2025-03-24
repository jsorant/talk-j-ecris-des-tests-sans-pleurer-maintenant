package com.jsorant.library.domain.exceptions;

import java.util.Objects;

public class BookAlreadyBorrowedException extends RuntimeException {

    private final String bookId;

    public BookAlreadyBorrowedException(String bookId) {
        super("Cannot borrow book with id " + bookId + " because it is not available");
        this.bookId = bookId;
    }

    public String bookId() {
        return bookId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BookAlreadyBorrowedException that = (BookAlreadyBorrowedException) o;
        return Objects.equals(bookId, that.bookId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(bookId);
    }
}
