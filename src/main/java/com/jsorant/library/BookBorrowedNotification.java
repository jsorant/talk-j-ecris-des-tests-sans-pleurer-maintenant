package com.jsorant.library;

import java.time.Instant;

public record BookBorrowedNotification(String borrowerEmail, String bookTitle, Instant date) {}
