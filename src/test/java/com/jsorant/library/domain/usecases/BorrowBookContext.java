package com.jsorant.library.domain.usecases;

import com.jsorant.library.domain.Borrows;
import com.jsorant.library.secondary.InMemoryBookRepository;
import com.jsorant.library.secondary.InMemoryBorrowRepository;

import java.time.Instant;

import static com.jsorant.library.domain.BookFixture.*;
import static com.jsorant.library.domain.usecases.BorrowBookFixture.*;

public class BorrowBookContext {

    private final InMemoryBookRepository books = new InMemoryBookRepository();
    private final InMemoryBorrowRepository borrows = new InMemoryBorrowRepository();

    private String bookToBorrowId = bookToBorrowId();
    private String borrowerEmail = borrowerEmail();
    private Instant borrowDate = borrowDate();

    public BorrowBookContext() {
        populateBookRepository();
    }

    public BorrowBookContext butWithBookToBorrowId(String bookId) {
        bookToBorrowId = bookId;

        return this;
    }

    public BorrowBookContext butWithAlreadyFourBooksBorrowed() {
        Borrows borrowerBorrows = new Borrows(borrowerEmail);

        for (String bookId : fourOtherBookIds()) {
            borrowerBorrows = borrowerBorrows.borrow(bookId, borrowDate);
        }

        borrows.save(borrowerBorrows);

        return this;
    }

    public BorrowBookContext butWithBookToBorrowAlreadyBorrowed() {
        Borrows anotherBorrowerBorrows = new Borrows(anotherBorrowerEmail()).borrow(bookToBorrowId, borrowDate);

        borrows.save(anotherBorrowerBorrows);

        return this;
    }

    public BorrowBook buildBorrowBook() {
        return new BorrowBook(books, borrows)
                .as(borrowerEmail)
                .bookId(bookToBorrowId)
                .date(borrowDate);
    }

    private void populateBookRepository() {
        books.save(harryPotter());
        books.save(lordOfTheRings());
        books.save(theTwoTowers());
        books.save(theReturnOfTheKing());
        books.save(theFellowshipOfTheRing());
        books.save(theHobbit());
    }
}
