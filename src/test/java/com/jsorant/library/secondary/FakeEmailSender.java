package com.jsorant.library.secondary;

import static org.assertj.core.api.Assertions.assertThat;

import com.jsorant.library.domain.BookBorrowedEmail;
import com.jsorant.library.domain.ports.EmailSender;

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
