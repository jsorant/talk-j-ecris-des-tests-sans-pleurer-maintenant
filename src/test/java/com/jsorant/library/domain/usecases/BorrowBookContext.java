package com.jsorant.library.domain.usecases;

import com.jsorant.library.domain.Book;
import com.jsorant.library.domain.BookType;
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
        Borrows borrowerBorrows = new Borrows(borrowerEmail)
                .borrow("3214515512", Instant.parse("2025-04-10T10:00:00Z"))
                .borrow("5341343136", Instant.parse("2025-04-11T10:00:00Z"))
                .borrow("6453424356", Instant.parse("2025-04-12T10:00:00Z"))
                .borrow("2534646466", Instant.parse("2025-04-13T10:00:00Z"));
        borrows.save(borrowerBorrows);

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
        books.save(new Book("6453424356", "The Return of the King", "JRR Tolkien", BookType.NOVEL));
        books.save(new Book("2534646466", "The Fellowship of the Ring", "JRR Tolkien", BookType.NOVEL));
        books.save(theHobbit());
    }

    public BorrowBookContext butWithBookToBorrowAlreadyBorrowed() {
        Borrows anotherBorrowerBorrows = new Borrows(anotherBorrowerEmail()).borrow(bookToBorrowId, borrowDate);

        borrows.save(anotherBorrowerBorrows);

        return this;
    }
}
