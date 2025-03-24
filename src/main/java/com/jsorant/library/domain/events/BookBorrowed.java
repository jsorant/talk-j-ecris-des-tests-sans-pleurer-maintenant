package com.jsorant.library.domain.events;

import java.time.Instant;

public record BookBorrowed(String borrowerEmail, String bookId, Instant date) {
}
