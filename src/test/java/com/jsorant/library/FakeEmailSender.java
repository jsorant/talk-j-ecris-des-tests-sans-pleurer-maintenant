package com.jsorant.library;

import static org.assertj.core.api.Assertions.assertThat;

public class FakeEmailSender implements EmailSender {

  private BookBorrowedEmail lastEmailSent;

  public BookBorrowedEmail lastEmailSent() {
    return lastEmailSent;
  }

  @Override
  public void send(BookBorrowedEmail email) {
    lastEmailSent = email;
  }

  public void assertLastEmailSentIs(BookBorrowedEmail expectedEmail) {
    assertThat(lastEmailSent).isEqualTo(expectedEmail);
  }
}
