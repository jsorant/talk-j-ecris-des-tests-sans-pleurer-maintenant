package com.jsorant.library;

import java.time.Instant;
import java.util.Optional;

public class BorrowBook {

  private final BookRepository bookRepository;
  private final BorrowsRepository borrowsRepository;
  private final FakeEmailSender emailSender;

  private String bookId;
  private String borrowerEmail;
  private Instant date;

  public BorrowBook(BookRepository bookRepository, BorrowsRepository borrowsRepository, FakeEmailSender emailSender) {
    this.bookRepository = bookRepository;
    this.borrowsRepository = borrowsRepository;
    this.emailSender = emailSender;
  }

  public BorrowBook as(String borrowerEmail) {
    this.borrowerEmail = borrowerEmail;
    return this;
  }

  public BorrowBook bookId(String bookId) {
    this.bookId = bookId;
    return this;
  }

  public BorrowBook date(Instant date) {
    this.date = date;
    return this;
  }

  public void act() {
    Optional<Book> book = bookRepository.get(bookId);

    if (book.isEmpty()) {
      throw new RuntimeException("Cannot borrow book with id " + bookId + " because it does not exist");
    }

    if (borrowsRepository.findForBookId(bookId).isPresent()) {
      throw new RuntimeException("Cannot borrow book with id " + bookId + " because it is not available");
    }

    int borrowsCount = borrowsRepository.borrowsCountForBorrower(borrowerEmail);
    if (borrowsCount >= 4) {
      throw new RuntimeException(
        "Cannot borrow book with id " + bookId + " because user " + borrowerEmail + " has already four books borrowed"
      );
    }

    Borrow borrow = new Borrow(borrowerEmail, bookId, date);
    borrowsRepository.save(borrow);

    emailSender.send(new BookBorrowedNotification(borrowerEmail, book.get().title(), date));
  }
}
