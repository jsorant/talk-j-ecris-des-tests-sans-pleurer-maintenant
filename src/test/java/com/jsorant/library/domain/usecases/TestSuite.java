package com.jsorant.library.domain.usecases;

import com.jsorant.library.domain.Book;
import com.jsorant.library.domain.BookType;
import com.jsorant.library.domain.Borrows;
import com.jsorant.library.domain.events.BookBorrowed;
import com.jsorant.library.domain.exceptions.BookAlreadyBorrowedException;
import com.jsorant.library.domain.exceptions.BookNotOwnedByTheLibraryException;
import com.jsorant.library.domain.exceptions.BorrowerHasAlreadyFourBooksBorrowedException;
import com.jsorant.library.secondary.InMemoryBookRepository;
import com.jsorant.library.secondary.InMemoryBorrowRepository;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

// Book must be owned by the library
// Book must not be already borrowed
// Borrower must not be already borrowing four books
// DomainEvent must be returned

public class TestSuite {

    @Test
    void test1() {
        InMemoryBookRepository bookRepository = new InMemoryBookRepository();
        bookRepository.save(new Book("3214515512", "The Lord of the Rings", "JRR Tolkien", BookType.NOVEL));
        bookRepository.save(new Book("1234567890", "The Hobbit", "JRR Tolkien", BookType.NOVEL));
        bookRepository.save(new Book("4083U14844", "Harry Potter and the Philosopher's Stone", "JK Rowling", BookType.NOVEL));

        InMemoryBorrowRepository borrowsRepository = new InMemoryBorrowRepository();
        Borrows aliceBorrows = new Borrows("bob.doe@domain.fr").borrow("1234567890", Instant.parse("2025-04-13T10:00:00Z"));
        borrowsRepository.save(aliceBorrows);

        try { // already borrowed
            new BorrowBook(bookRepository, borrowsRepository)
                    .as("alice.doe@domain.fr")
                    .bookId("1234567890")
                    .date(Instant.parse("2025-04-14T10:00:00Z"))
                    .act();

            fail("Should have thrown an exception");
        } catch (RuntimeException e) {
            assertEquals(true, e instanceof BookAlreadyBorrowedException);
            assertEquals("1234567890", ((BookAlreadyBorrowedException) e).bookId());
        }

        BookBorrowed event = new BorrowBook(bookRepository, borrowsRepository)
                .as("alice.doe@domain.fr")
                .bookId("3214515512")
                .date(Instant.parse("2025-04-14T10:00:00Z"))
                .act();

        assertEquals(new BookBorrowed("alice.doe@domain.fr", "3214515512", Instant.parse("2025-04-14T10:00:00Z")), event);
    }

    @Test
    void test2() {
        InMemoryBookRepository bookRepository = new InMemoryBookRepository();
        InMemoryBorrowRepository borrowsRepository = new InMemoryBorrowRepository();

        try {
            new BorrowBook(bookRepository, borrowsRepository)
                    .as("alice.doe@domain.fr")
                    .bookId("1234567891")
                    .date(Instant.parse("2025-04-14T10:00:00Z"))
                    .act();

            fail("Should have thrown an exception");
        } catch (RuntimeException e) {
            assertEquals(true, e instanceof BookNotOwnedByTheLibraryException);
            assertEquals("1234567891", ((BookNotOwnedByTheLibraryException) e).bookId());
        }
    }

    @Test
    void test3() {
        InMemoryBookRepository bookRepository = new InMemoryBookRepository();
        bookRepository.save(new Book("4083U14844", "Harry Potter and the Philosopher's Stone", "JK Rowling", BookType.NOVEL));
        bookRepository.save(new Book("3214515512", "The Lord of the Rings", "JRR Tolkien", BookType.NOVEL));
        bookRepository.save(new Book("5341343136", "The Two Towers", "JRR Tolkien", BookType.NOVEL));
        bookRepository.save(new Book("6453424356", "The Return of the King", "JRR Tolkien", BookType.NOVEL));
        bookRepository.save(new Book("2534646466", "The Fellowship of the Ring", "JRR Tolkien", BookType.NOVEL));
        bookRepository.save(new Book("1234567890", "The Hobbit", "JRR Tolkien", BookType.NOVEL));

        InMemoryBorrowRepository borrowsRepository = new InMemoryBorrowRepository();
        Borrows aliceBorrows = new Borrows("alice.doe@domain.fr")
                .borrow("2534646466", Instant.parse("2025-04-13T10:00:00Z"))
                .borrow("6453424356", Instant.parse("2025-04-14T10:00:00Z"))
                .borrow("3214515512", Instant.parse("2025-04-15T10:00:00Z"))
                .borrow("5341343136", Instant.parse("2025-04-16T10:00:00Z"));
        borrowsRepository.save(aliceBorrows);

        try {
            new BorrowBook(bookRepository, borrowsRepository)
                    .as("alice.doe@domain.fr")
                    .bookId("1234567890")
                    .date(Instant.parse("2025-04-14T10:00:00Z"))
                    .act();

            fail("Should have thrown an exception");
        } catch (RuntimeException e) {
            assertEquals(true, e instanceof BorrowerHasAlreadyFourBooksBorrowedException);
            assertEquals("1234567890", ((BorrowerHasAlreadyFourBooksBorrowedException) e).bookId());
            assertEquals("alice.doe@domain.fr", ((BorrowerHasAlreadyFourBooksBorrowedException) e).borrowerEmail());
        }
    }

    @Test
    void test4() {
        InMemoryBookRepository bookRepository = new InMemoryBookRepository();
        bookRepository.save(new Book("3214515512", "The Lord of the Rings", "JRR Tolkien", BookType.NOVEL));
        bookRepository.save(new Book("1234567890", "The Hobbit", "JRR Tolkien", BookType.NOVEL));
        bookRepository.save(new Book("4083U14844", "Harry Potter and the Philosopher's Stone", "JK Rowling", BookType.NOVEL));

        InMemoryBorrowRepository borrowsRepository = new InMemoryBorrowRepository();

        BookBorrowed event = new BorrowBook(bookRepository, borrowsRepository)
                .as("alice.doe@domain.fr")
                .bookId("1234567890")
                .date(Instant.parse("2025-04-14T10:00:00Z"))
                .act();

        assertEquals(new BookBorrowed("alice.doe@domain.fr", "1234567890", Instant.parse("2025-04-14T10:00:00Z")), event);
    }
}
