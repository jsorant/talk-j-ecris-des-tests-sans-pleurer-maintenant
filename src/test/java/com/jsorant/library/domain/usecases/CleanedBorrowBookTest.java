package com.jsorant.library.domain.usecases;

import static com.jsorant.library.domain.BookFixture.*;
import static com.jsorant.library.domain.usecases.BorrowBookFixture.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.jsorant.UnitTest;
import com.jsorant.library.domain.*;
import com.jsorant.library.secondary.FakeEmailSender;
import com.jsorant.library.secondary.InMemoryBookRepository;
import com.jsorant.library.secondary.InMemoryBorrowsRepository;
import java.time.Instant;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@UnitTest
@DisplayName("Borrow a book")
public class CleanedBorrowBookTest {

  BorrowBookContext context = new BorrowBookContext();
  BorrowBook borrowBook = context.getSut();

  @Test
  void shouldThrowWhenBookDoesNotExists() {
    String bookId = idOfABookThatDoesNotExist();

    assertThatThrownBy(() -> borrowBook.borrow(borrowerEmail(), bookId, borrowDate()))
      .hasMessage("Cannot borrow book with id " + bookId + " because it does not exist");
  }

  @Test
  void shouldSendAnEmailToTheBorrowerWhenBookIsBorrowed() {
    borrowBook.borrow(borrowerEmail(), bookToBorrow().id(), borrowDate());

    BookBorrowedEmail expectedEmail = new BookBorrowedEmail(borrowerEmail(), bookToBorrow().title(), borrowDate());
    context.emailSender().assertLastEmailSentIs(expectedEmail);
  }

  @Test
  void test2() {
    InMemoryBookRepository bookRepository = new InMemoryBookRepository();
    bookRepository.save(lordOfTheRings());
    bookRepository.save(BookFixture.theHobbit());
    bookRepository.save(harryPotter());

    InMemoryBorrowsRepository borrowsRepository = new InMemoryBorrowsRepository();
    borrowsRepository.save(new Borrow("alice.doe@domain.fr", "1234567890", Instant.parse("2025-04-13T10:00:00Z")));

    // test book already borrowed
    try {
      FakeEmailSender emailSender = new FakeEmailSender();
      BorrowBook borrowBook = new BorrowBook(bookRepository, borrowsRepository, emailSender)
        .as(borrowerEmail())
        .bookId("1234567890")
        .date(borrowDate());

      borrowBook.act();

      fail("Should have thrown an exception");
    } catch (RuntimeException e) {
      assertEquals("Cannot borrow book with id 1234567890 because it is not available", e.getMessage());
    }
  }

  @Test
  void test3() {
    InMemoryBookRepository bookRepository = new InMemoryBookRepository();
    bookRepository.save(harryPotter());
    bookRepository.save(lordOfTheRings());
    bookRepository.save(BookFixture.theTwoTowers());
    bookRepository.save(new Book("6453424356", "The Return of the King", "JRR Tolkien", BookType.NOVEL));
    bookRepository.save(new Book("2534646466", "The Fellowship of the Ring", "JRR Tolkien", BookType.NOVEL));
    bookRepository.save(BookFixture.theHobbit());

    InMemoryBorrowsRepository borrowsRepository = new InMemoryBorrowsRepository();
    borrowsRepository.save(new Borrow(borrowerEmail(), "3214515512", Instant.parse("2025-04-10T10:00:00Z")));
    borrowsRepository.save(new Borrow(borrowerEmail(), "5341343136", Instant.parse("2025-04-11T10:00:00Z")));
    borrowsRepository.save(new Borrow(borrowerEmail(), "6453424356", Instant.parse("2025-04-12T10:00:00Z")));
    borrowsRepository.save(new Borrow(borrowerEmail(), "2534646466", Instant.parse("2025-04-13T10:00:00Z")));

    // test has already four books borrowed
    try {
      FakeEmailSender emailSender = new FakeEmailSender();
      BorrowBook borrowBook = new BorrowBook(bookRepository, borrowsRepository, emailSender)
        .as(borrowerEmail())
        .bookId("1234567890")
        .date(borrowDate());

      borrowBook.act();

      fail("Should have thrown an exception");
    } catch (RuntimeException e) {
      assertEquals(
        "Cannot borrow book with id 1234567890 because user jeremy.sorant@domain.fr has already four books borrowed",
        e.getMessage()
      );
    }
  }
}
