package com.jsorant.library.domain;

import java.time.Instant;

public record Borrow(String borrowerEmail, String bookId, Instant date) {}
