package com.jsorant.library.domain.usecases;

import com.jsorant.library.domain.Book;
import com.jsorant.library.domain.BookFixture;
import com.jsorant.library.domain.BookType;
import com.jsorant.library.secondary.InMemoryBookRepository;
import com.jsorant.library.secondary.InMemoryBorrowRepository;

import static com.jsorant.library.domain.BookFixture.harryPotter;
import static com.jsorant.library.domain.BookFixture.lordOfTheRings;

public class BorrowBookContext {

    private final InMemoryBookRepository books = new InMemoryBookRepository();
    private final InMemoryBorrowRepository borrows = new InMemoryBorrowRepository();

    private final BorrowBook borrowBook = new BorrowBook(books, borrows);

    public BorrowBookContext() {
        populateBookRepository();
    }

    public BorrowBook getSut() {
        return borrowBook;
    }

    private void populateBookRepository() {
        books.save(harryPotter());
        books.save(lordOfTheRings());
        books.save(BookFixture.theTwoTowers());
        books.save(new Book("6453424356", "The Return of the King", "JRR Tolkien", BookType.NOVEL));
        books.save(new Book("2534646466", "The Fellowship of the Ring", "JRR Tolkien", BookType.NOVEL));
        books.save(BookFixture.theHobbit());
    }
}
