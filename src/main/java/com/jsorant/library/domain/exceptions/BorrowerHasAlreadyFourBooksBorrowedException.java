package com.jsorant.library.domain.exceptions;

import java.util.Objects;

public class BorrowerHasAlreadyFourBooksBorrowedException extends RuntimeException {
    private final String borrowerEmail;
    private final String bookId;

    public BorrowerHasAlreadyFourBooksBorrowedException(String borrowerEmail, String bookId) {
        super("Cnnot borrow book with id " + bookId + " because user " + borrowerEmail + " has already four books borrowed");
        this.borrowerEmail = borrowerEmail;
        this.bookId = bookId;
    }

    public String borrowerEmail() {
        return borrowerEmail;
    }

    public String bookId() {
        return bookId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BorrowerHasAlreadyFourBooksBorrowedException that = (BorrowerHasAlreadyFourBooksBorrowedException) o;
        return Objects.equals(borrowerEmail, that.borrowerEmail) && Objects.equals(bookId, that.bookId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(borrowerEmail, bookId);
    }
}
