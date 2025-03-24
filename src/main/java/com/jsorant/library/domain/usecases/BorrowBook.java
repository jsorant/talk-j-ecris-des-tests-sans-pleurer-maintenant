package com.jsorant.library.domain.usecases;

import com.jsorant.library.domain.Borrows;
import com.jsorant.library.domain.events.BookBorrowed;
import com.jsorant.library.domain.exceptions.BookAlreadyBorrowedException;
import com.jsorant.library.domain.exceptions.BookNotOwnedByTheLibraryException;
import com.jsorant.library.domain.ports.BookRepository;
import com.jsorant.library.domain.ports.BorrowRepository;

import java.time.Instant;

public class BorrowBook {

    private final BookRepository books;
    private final BorrowRepository borrows;

    private String bookId;
    private String borrowerEmail;
    private Instant date;

    public BorrowBook(BookRepository books, BorrowRepository borrows) {
        this.books = books;
        this.borrows = borrows;
    }

    public BorrowBook as(String borrowerEmail) {
        this.borrowerEmail = borrowerEmail;
        return this;
    }

    public BorrowBook bookId(String bookId) {
        this.bookId = bookId;
        return this;
    }

    public BorrowBook date(Instant date) {
        this.date = date;
        return this;
    }

    public BookBorrowed act() {
        ensureBookExists();

        ensureBookIsNotAlreadyBorrowed();

        Borrows borrowsForBorrower = borrows.getForBorrower(borrowerEmail).borrow(bookId, date);

        borrows.save(borrowsForBorrower);

        return new BookBorrowed(borrowerEmail, bookId, date);
    }

    private void ensureBookExists() {
        if (books.get(bookId).isEmpty()) {
            throw new BookNotOwnedByTheLibraryException(bookId);
        }
    }

    private void ensureBookIsNotAlreadyBorrowed() {
        if (borrows.isBorrowed(bookId)) {
            throw new BookAlreadyBorrowedException(bookId);
        }
    }
}
