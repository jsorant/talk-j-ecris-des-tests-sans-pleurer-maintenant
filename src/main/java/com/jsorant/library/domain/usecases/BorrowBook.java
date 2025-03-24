package com.jsorant.library.domain.usecases;

import com.jsorant.library.domain.Book;
import com.jsorant.library.domain.BookBorrowedEmail;
import com.jsorant.library.domain.Borrow;
import com.jsorant.library.domain.ports.BookRepository;
import com.jsorant.library.domain.ports.BorrowsRepository;
import com.jsorant.library.domain.ports.EmailSender;
import java.time.Instant;
import java.util.Optional;

public class BorrowBook {

  private final BookRepository bookRepository;
  private final BorrowsRepository borrowsRepository;
  private final EmailSender emailSender;

  private String bookId;
  private String borrowerEmail;
  private Instant date;

  public BorrowBook(BookRepository bookRepository, BorrowsRepository borrowsRepository, EmailSender emailSender) {
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

  public void borrow(String userEmail, String bookId, Instant date) {
    this.as(userEmail).bookId(bookId).date(date).act();
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

    emailSender.send(new BookBorrowedEmail(borrowerEmail, book.get().title(), date));
  }
}
