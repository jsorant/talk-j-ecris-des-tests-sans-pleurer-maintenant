package com.jsorant.library;

import static com.jsorant.library.BookFixture.harryPotter;
import static com.jsorant.library.BookFixture.lordOfTheRings;

public class BorrowBookContext {

  private final FakeEmailSender emailSender = new FakeEmailSender();
  private final InMemoryBookRepository bookRepository = new InMemoryBookRepository();
  private final InMemoryBorrowsRepository borrowsRepository = new InMemoryBorrowsRepository();
  private final BorrowBook borrowBook = new BorrowBook(bookRepository, borrowsRepository, emailSender);

  public BorrowBookContext() {
    populateBookRepository();
  }

  public FakeEmailSender emailSender() {
    return emailSender;
  }

  public BorrowBook getSut() {
    return borrowBook;
  }

  private void populateBookRepository() {
    bookRepository.save(harryPotter());
    bookRepository.save(lordOfTheRings());
    bookRepository.save(BookFixture.theTwoTowers());
    bookRepository.save(new Book("6453424356", "The Return of the King", "JRR Tolkien", BookType.NOVEL));
    bookRepository.save(new Book("2534646466", "The Fellowship of the Ring", "JRR Tolkien", BookType.NOVEL));
    bookRepository.save(BookFixture.theHobbit());
  }
}
