package com.jsorant.library;

import static com.jsorant.library.BookFixture.theHobbit;

import java.time.Instant;

public class BorrowBookFixture {

  public static Book bookToBorrow() {
    return theHobbit();
  }

  public static Instant borrowDate() {
    return Instant.parse("2025-04-14T10:00:00Z");
  }

  public static String borrowerEmail() {
    return "jeremy.sorant@domain.fr";
  }
}
