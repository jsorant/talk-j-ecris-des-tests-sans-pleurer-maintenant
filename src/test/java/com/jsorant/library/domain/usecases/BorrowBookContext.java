package com.jsorant.library.domain.usecases;

import com.jsorant.library.domain.Book;
import com.jsorant.library.domain.Borrows;
import com.jsorant.library.secondary.InMemoryBookRepository;
import com.jsorant.library.secondary.InMemoryBorrowRepository;

import java.time.Instant;

import static com.jsorant.library.domain.BookFixture.*;

public class BorrowBookContext {

    private final InMemoryBookRepository books = new InMemoryBookRepository();
    private final InMemoryBorrowRepository borrows = new InMemoryBorrowRepository();

    private Book bookToBorrow = theHobbit();
    private final Book notOwnedByTheLibraryBook = hungerGames();
    private final String borrowerEmail = "alice.doe@domain.fr";
    private final String anotherBorrowerEmail = "bob.doe@domain.fr";
    private final Instant borrowDate = Instant.parse("2025-04-14T10:00:00Z");

    public BorrowBookContext() {
        populateLibraryWithSomeBooks();
    }

    public String bookToBorrowId() {
        return bookToBorrow.id();
    }

    public Book bookToBorrow() {
        return bookToBorrow;
    }

    public String borrowerEmail() {
        return borrowerEmail;
    }

    public String anotherBorrowerEmail() {
        return anotherBorrowerEmail;
    }

    public Instant borrowDate() {
        return borrowDate;
    }

    public String idOfTheBookThatIsNotOwnedByTheLibrary() {
        return notOwnedByTheLibraryBook.id();
    }

    public BorrowBookContext butBorrowingABookThatIsNotOwnedByTheLibrary() {
        bookToBorrow = notOwnedByTheLibraryBook;

        return this;
    }

    public BorrowBookContext butWithAlreadyFourBooksBorrowed() {
        borrow(lordOfTheRings().id());
        borrow(harryPotter().id());
        borrow(theTwoTowers().id());
        borrow(theReturnOfTheKing().id());

        return this;
    }

    public BorrowBookContext butWithBookToBorrowAlreadyBorrowed() {
        Borrows anotherBorrowerBorrows = new Borrows(anotherBorrowerEmail()).borrow(bookToBorrow.id(), borrowDate);

        borrows.save(anotherBorrowerBorrows);

        return this;
    }

    public BorrowBook buildBorrowBook() {
        return new BorrowBook(books, borrows)
                .as(borrowerEmail)
                .bookId(bookToBorrow.id())
                .date(borrowDate);
    }

    private void populateLibraryWithSomeBooks() {
        books.save(harryPotter());
        books.save(lordOfTheRings());
        books.save(theTwoTowers());
        books.save(theReturnOfTheKing());
        books.save(theFellowshipOfTheRing());
        books.save(theHobbit());
    }

    private void borrow(String bookId) {
        new BorrowBook(books, borrows)
                .as(borrowerEmail)
                .bookId(bookId)
                .date(borrowDate)
                .act();
    }
}
