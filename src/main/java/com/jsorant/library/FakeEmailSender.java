package com.jsorant.library;

public class FakeEmailSender {

  private BookBorrowedNotification lastNotification;

  public BookBorrowedNotification lastNotification() {
    return lastNotification;
  }

  public void send(BookBorrowedNotification notification) {
    lastNotification = notification;
  }
}
