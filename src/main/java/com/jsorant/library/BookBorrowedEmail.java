package com.jsorant.library;

import java.time.Instant;

public record BookBorrowedEmail(String borrowerEmail, String bookTitle, Instant date) {}
