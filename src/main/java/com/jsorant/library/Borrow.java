package com.jsorant.library;

import java.time.Instant;

public record Borrow(String borrowerEmail, String bookId, Instant date) {}
