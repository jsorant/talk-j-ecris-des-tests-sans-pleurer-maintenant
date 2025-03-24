package com.jsorant.library.domain;

import com.jsorant.library.domain.exceptions.BorrowerHasAlreadyFourBooksBorrowedException;

import java.time.Instant;
import java.util.List;
import java.util.stream.Stream;

public final class Borrows {

    private final String borrowerEmail;

    private final List<Borrow> borrows;

    public Borrows(String borrowerEmail, List<Borrow> borrows) {
        this.borrowerEmail = borrowerEmail;
        this.borrows = borrows;
    }

    public Borrows(String borrowerEmail) {
        this(borrowerEmail, List.of());
    }

    public String borrowerEmail() {
        return borrowerEmail;
    }

    public Borrows borrow(String bookId, Instant date) {
        ensureCanBorrowAnotherBook(bookId);

        Borrow borrow = new Borrow(borrowerEmail, bookId, date);

        return new Borrows(borrowerEmail, Stream.concat(borrows.stream(), Stream.of(borrow)).toList());
    }

    public boolean isBorrowed(String bookId) {
        return borrows.stream().anyMatch(borrow -> borrow.bookId().equals(bookId));
    }

    private void ensureCanBorrowAnotherBook(String bookId) {
        if (borrows.size() >= 4) {
            throw new BorrowerHasAlreadyFourBooksBorrowedException(borrowerEmail, bookId);
        }
    }
}
