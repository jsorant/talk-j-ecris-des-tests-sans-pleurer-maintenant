package com.jsorant.library;

public class FakeEmailSender implements EmailSender {

  private BookBorrowedEmail lastEmailSent;

  public BookBorrowedEmail lastEmailSent() {
    return lastEmailSent;
  }

  @Override
  public void send(BookBorrowedEmail email) {
    lastEmailSent = email;
  }
}
