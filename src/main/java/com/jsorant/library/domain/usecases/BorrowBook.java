package com.jsorant.library.domain.usecases;

import com.jsorant.library.domain.Book;
import com.jsorant.library.domain.BookBorrowedEmail;
import com.jsorant.library.domain.Borrows;
import com.jsorant.library.domain.ports.BookRepository;
import com.jsorant.library.domain.ports.BorrowRepository;
import com.jsorant.library.domain.ports.EmailSender;
import java.time.Instant;
import java.util.Optional;

public class BorrowBook {

  private final BookRepository books;
  private final BorrowRepository borrows;
  private final EmailSender emailSender;

  private String bookId;
  private String borrowerEmail;
  private Instant date;

  public BorrowBook(BookRepository books, BorrowRepository borrows, EmailSender emailSender) {
    this.books = books;
    this.borrows = borrows;
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
    Book book = retrieveBook();

    ensureBookIsNotAlreadyBorrowed();

    Borrows borrowsForBorrower = borrows.getForBorrower(borrowerEmail).borrow(bookId, date);

    borrows.save(borrowsForBorrower);

    emailSender.send(new BookBorrowedEmail(borrowerEmail, book.title(), date));
  }

  private void ensureBookIsNotAlreadyBorrowed() {
    if (borrows.isBorrowed(bookId)) {
      throw new RuntimeException("Cannot borrow book with id " + bookId + " because it is not available");
    }
  }

  private Book retrieveBook() {
    Optional<Book> book = books.get(bookId);

    if (book.isEmpty()) {
      throw new RuntimeException("Cannot borrow book with id " + bookId + " because it does not exist");
    }
    return book.get();
  }
}
