package com.jsorant.library.domain.usecases;

import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.jsorant.UnitTest;
import com.jsorant.library.domain.Book;
import com.jsorant.library.domain.BookBorrowed;
import com.jsorant.library.domain.BookType;
import com.jsorant.library.domain.Borrows;
import com.jsorant.library.domain.exceptions.BookAlreadyBorrowedException;
import com.jsorant.library.secondary.InMemoryBookRepository;
import com.jsorant.library.secondary.InMemoryBorrowRepository;
import java.time.Instant;
import org.junit.jupiter.api.Test;

@UnitTest
public class BorrowBookTest {

  @Test
  void shouldNotBorrowBookWhenBookNotExists() {
    InMemoryBookRepository bookRepository = new InMemoryBookRepository();
    InMemoryBorrowRepository borrowsRepository = new InMemoryBorrowRepository();

    try {
      BorrowBook borrowBook = new BorrowBook(bookRepository, borrowsRepository)
        .as("jeremy.sorant@domain.fr")
        .bookId("1234567891")
        .date(Instant.parse("2025-04-14T10:00:00Z"));

      borrowBook.act();

      fail("Should have thrown an exception");
    } catch (RuntimeException e) {
      assertEquals("Cannot borrow book with id 1234567891 because it does not exist", e.getMessage());
    }
  }

  @Test
  void shouldNotBorrowBookWhenBookIsNotAvailable() {
    InMemoryBookRepository bookRepository = new InMemoryBookRepository();
    bookRepository.save(new Book("3214515512", "The Lord of the Rings", "JRR Tolkien", BookType.NOVEL));
    bookRepository.save(new Book("1234567890", "The Hobbit", "JRR Tolkien", BookType.NOVEL));
    bookRepository.save(new Book("4083U14844", "Harry Potter and the Philosopher's Stone", "JK Rowling", BookType.NOVEL));

    InMemoryBorrowRepository borrowsRepository = new InMemoryBorrowRepository();
    Borrows aliceBorrows = new Borrows("alice.doe@domain.fr").borrow("1234567890", Instant.parse("2025-04-13T10:00:00Z"));
    borrowsRepository.save(aliceBorrows);

    try {
      BorrowBook borrowBook = new BorrowBook(bookRepository, borrowsRepository)
        .as("jeremy.sorant@domain.fr")
        .bookId("1234567890")
        .date(Instant.parse("2025-04-14T10:00:00Z"));

      borrowBook.act();

      fail("Should have thrown an exception");
    } catch (RuntimeException e) {
      assertEquals(true, e instanceof BookAlreadyBorrowedException);
      assertEquals("1234567890", ((BookAlreadyBorrowedException) e).bookId());
    }
  }

  @Test
  void shouldNotBorrowBookWhenBorrowerIsAlreadyBorrowingFourBooks() {
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
      BorrowBook borrowBook = new BorrowBook(bookRepository, borrowsRepository)
        .as("alice.doe@domain.fr")
        .bookId("1234567890")
        .date(Instant.parse("2025-04-14T10:00:00Z"));

      borrowBook.act();

      fail("Should have thrown an exception");
    } catch (RuntimeException e) {
      assertEquals(
        "Cannot borrow book with id 1234567890 because user alice.doe@domain.fr has already four books borrowed",
        e.getMessage()
      );
    }
  }

  @Test
  void shouldSendAnEmailToTheBorrowerWhenBookIsBorrowed() {
    InMemoryBookRepository bookRepository = new InMemoryBookRepository();
    bookRepository.save(new Book("3214515512", "The Lord of the Rings", "JRR Tolkien", BookType.NOVEL));
    bookRepository.save(new Book("1234567890", "The Hobbit", "JRR Tolkien", BookType.NOVEL));
    bookRepository.save(new Book("4083U14844", "Harry Potter and the Philosopher's Stone", "JK Rowling", BookType.NOVEL));

    InMemoryBorrowRepository borrowsRepository = new InMemoryBorrowRepository();

    BorrowBook borrowBook = new BorrowBook(bookRepository, borrowsRepository)
      .as("jeremy.sorant@domain.fr")
      .bookId("1234567890")
      .date(Instant.parse("2025-04-14T10:00:00Z"));

    BookBorrowed event = borrowBook.act();

    assertEquals(new BookBorrowed("jeremy.sorant@domain.fr", "1234567890", Instant.parse("2025-04-14T10:00:00Z")), event);
  }
}
