package com.jsorant.library.domain;

import java.time.Instant;

public record BookBorrowed(String borrowerEmail, String bookId, Instant date) {}
