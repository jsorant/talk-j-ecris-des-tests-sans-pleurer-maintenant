package com.jsorant.library.domain;

import java.time.Instant;
import java.util.List;
import java.util.stream.Stream;

public final class Borrows {

  private final String borrowerEmail;

  private final List<Borrow> borrows;

  public Borrows(String borrowerEmail, List<Borrow> borrows) {
    this.borrowerEmail = borrowerEmail;
    this.borrows = borrows;
  }

  public Borrows(String borrowerEmail) {
    this(borrowerEmail, List.of());
  }

  public String borrowerEmail() {
    return borrowerEmail;
  }

  public Borrows borrow(String bookId, Instant date) {
    if (borrows.size() >= 4) {
      throw new RuntimeException(
        "Cannot borrow book with id " + bookId + " because user " + borrowerEmail + " has already four books borrowed"
      );
    }

    Borrow borrow = new Borrow(borrowerEmail, bookId, date);

    return new Borrows(borrowerEmail, Stream.concat(borrows.stream(), Stream.of(borrow)).toList());
  }

  public boolean isBorrowed(String bookId) {
    return borrows.stream().anyMatch(borrow -> borrow.bookId().equals(bookId));
  }
}
