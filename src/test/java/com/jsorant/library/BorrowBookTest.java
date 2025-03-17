package com.jsorant.library;

import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.jsorant.UnitTest;
import java.time.Instant;
import org.junit.jupiter.api.Test;

@UnitTest
public class BorrowBookTest {

  // Check exists
  // Check is available

  @Test
  void shouldNotBorrowBookWhenBookNotExists() {
    InMemoryBookRepository bookRepository = new InMemoryBookRepository();
    InMemoryBorrowsRepository borrowsRepository = new InMemoryBorrowsRepository();

    try {
      FakeEmailSender emailSender = new FakeEmailSender();
      BorrowBook borrowBook = new BorrowBook(bookRepository, borrowsRepository, emailSender)
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

    InMemoryBorrowsRepository borrowsRepository = new InMemoryBorrowsRepository();
    borrowsRepository.save(new Borrow("alice.doe@domain.fr", "1234567890", Instant.parse("2025-04-13T10:00:00Z")));

    try {
      FakeEmailSender emailSender = new FakeEmailSender();
      BorrowBook borrowBook = new BorrowBook(bookRepository, borrowsRepository, emailSender)
        .as("jeremy.sorant@domain.fr")
        .bookId("1234567890")
        .date(Instant.parse("2025-04-14T10:00:00Z"));

      borrowBook.act();

      fail("Should have thrown an exception");
    } catch (RuntimeException e) {
      assertEquals("Cannot borrow book with id 1234567890 because it is not available", e.getMessage());
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

    InMemoryBorrowsRepository borrowsRepository = new InMemoryBorrowsRepository();
    borrowsRepository.save(new Borrow("jeremy.sorant@domain.fr", "3214515512", Instant.parse("2025-04-10T10:00:00Z")));
    borrowsRepository.save(new Borrow("jeremy.sorant@domain.fr", "5341343136", Instant.parse("2025-04-11T10:00:00Z")));
    borrowsRepository.save(new Borrow("jeremy.sorant@domain.fr", "6453424356", Instant.parse("2025-04-12T10:00:00Z")));
    borrowsRepository.save(new Borrow("jeremy.sorant@domain.fr", "2534646466", Instant.parse("2025-04-13T10:00:00Z")));

    try {
      FakeEmailSender emailSender = new FakeEmailSender();
      BorrowBook borrowBook = new BorrowBook(bookRepository, borrowsRepository, emailSender)
        .as("jeremy.sorant@domain.fr")
        .bookId("1234567890")
        .date(Instant.parse("2025-04-14T10:00:00Z"));

      borrowBook.act();

      fail("Should have thrown an exception");
    } catch (RuntimeException e) {
      assertEquals(
        "Cannot borrow book with id 1234567890 because user jeremy.sorant@domain.fr has already four books borrowed",
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

    InMemoryBorrowsRepository borrowsRepository = new InMemoryBorrowsRepository();

    FakeEmailSender emailSender = new FakeEmailSender();

    BorrowBook borrowBook = new BorrowBook(bookRepository, borrowsRepository, emailSender)
      .as("jeremy.sorant@domain.fr")
      .bookId("1234567890")
      .date(Instant.parse("2025-04-14T10:00:00Z"));

    borrowBook.act();

    assertEquals(
      new BookBorrowedNotification("jeremy.sorant@domain.fr", "The Hobbit", Instant.parse("2025-04-14T10:00:00Z")),
      emailSender.lastNotification()
    );
  }
}
