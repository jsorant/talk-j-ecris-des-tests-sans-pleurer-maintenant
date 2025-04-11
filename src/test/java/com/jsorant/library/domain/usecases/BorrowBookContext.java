package com.jsorant.library.domain.usecases;

import com.jsorant.library.domain.Book;
import com.jsorant.library.secondary.InMemoryBookRepository;
import com.jsorant.library.secondary.InMemoryBorrowRepository;

import java.time.Instant;
import java.util.List;

import static com.jsorant.library.domain.BookFixture.*;
import static com.jsorant.library.domain.UserFixture.bob;

public class BorrowBookContext {

    private final InMemoryBookRepository books = new InMemoryBookRepository();
    private final InMemoryBorrowRepository borrows = new InMemoryBorrowRepository();

    private final List<Book> booksOwnedByTheLibrary = List.of(
            harryPotter(),
            lordOfTheRings(),
            theTwoTowers(),
            theReturnOfTheKing(),
            theFellowshipOfTheRing(),
            theHobbit(),
            hungerGames()
    );

    private Book bookToBorrow = theHobbit();
    private String borrowerEmail = "alice.doe@domain.fr";
    private Instant borrowDate = Instant.parse("2025-04-14T10:00:00Z");

    public BorrowBookContext() {
        populateLibraryWithSomeBooks();
    }

    public String borrowerEmail() {
        return borrowerEmail;
    }

    public void withBookBorrowedByBob(Book book) {
        new BorrowBook(books, borrows)
                .by(bob())
                .bookId(book.id())
                .date(borrowDate)
                .act();
    }

    public void withBookNotOwnedByTheLibrary(Book book) {
        books.remove(book);
    }

    public void withBooksBorrowed(Book... books) {
        List.of(books).forEach(this::borrow);
    }

    public BorrowBookContext withBookToBorrow(Book book) {
        bookToBorrow = book;
        return this;
    }

    public BorrowBookContext by(String userEmail) {
        borrowerEmail = userEmail;
        return this;
    }

    public BorrowBookContext on(Instant date) {
        borrowDate = date;
        return this;
    }

    public BorrowBook build() {
        return new BorrowBook(books, borrows)
                .by(borrowerEmail)
                .bookId(bookToBorrow.id())
                .date(borrowDate);
    }

    private void populateLibraryWithSomeBooks() {
        booksOwnedByTheLibrary.forEach(books::save);
    }

    private void borrow(Book book) {
        new BorrowBook(books, borrows)
                .by(borrowerEmail)
                .bookId(book.id())
                .date(borrowDate)
                .act();
    }
}
