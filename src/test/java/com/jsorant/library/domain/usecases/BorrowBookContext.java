package com.jsorant.library.domain.usecases;

import static com.jsorant.library.domain.BookFixture.harryPotter;
import static com.jsorant.library.domain.BookFixture.lordOfTheRings;

import com.jsorant.library.domain.Book;
import com.jsorant.library.domain.BookFixture;
import com.jsorant.library.domain.BookType;
import com.jsorant.library.secondary.FakeEmailSender;
import com.jsorant.library.secondary.InMemoryBookRepository;
import com.jsorant.library.secondary.InMemoryBorrowsRepository;

public class BorrowBookContext {

  private final InMemoryBookRepository bookRepository = new InMemoryBookRepository();
  private final InMemoryBorrowsRepository borrowsRepository = new InMemoryBorrowsRepository();
  private final FakeEmailSender emailSender = new FakeEmailSender();

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
