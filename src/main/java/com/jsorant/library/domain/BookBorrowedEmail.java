package com.jsorant.library.domain;

import java.time.Instant;

public record BookBorrowedEmail(String borrowerEmail, String bookTitle, Instant date) {}
