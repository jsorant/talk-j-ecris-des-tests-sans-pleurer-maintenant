package com.jsorant.library.domain.usecases;

import com.jsorant.library.domain.Book;
import com.jsorant.library.domain.Borrows;
import com.jsorant.library.secondary.InMemoryBookRepository;
import com.jsorant.library.secondary.InMemoryBorrowRepository;

import java.time.Instant;
import java.util.List;

import static com.jsorant.library.domain.BookFixture.*;

public class BorrowBookContext {

    private final InMemoryBookRepository books = new InMemoryBookRepository();
    private final InMemoryBorrowRepository borrows = new InMemoryBorrowRepository();

    private final List<Book> booksOwnedByTheLibrary = List.of(
            harryPotter(),
            lordOfTheRings(),
            theTwoTowers(),
            theReturnOfTheKing(),
            theFellowshipOfTheRing(),
            theHobbit()
    );
    
    private final Book notOwnedByTheLibraryBook = hungerGames();
    private Book bookToBorrow = theHobbit();
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
        borrow(lordOfTheRings());
        borrow(harryPotter());
        borrow(theTwoTowers());
        borrow(theReturnOfTheKing());

        return this;
    }

    public BorrowBookContext butWithBookToBorrowAlreadyBorrowed() {
        Borrows anotherBorrowerBorrows = new Borrows(anotherBorrowerEmail())
                .borrow(bookToBorrow.id(), borrowDate);

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
        booksOwnedByTheLibrary.forEach(books::save);
    }

    private void borrow(Book book) {
        new BorrowBook(books, borrows)
                .as(borrowerEmail)
                .bookId(book.id())
                .date(borrowDate)
                .act();
    }
}
