package com.jsorant.library.domain.ports;

import com.jsorant.library.domain.BookBorrowedEmail;

public interface EmailSender {
  void send(BookBorrowedEmail email);
}
