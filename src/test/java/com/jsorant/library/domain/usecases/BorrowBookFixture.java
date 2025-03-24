package com.jsorant.library.domain.usecases;

import java.time.Instant;

public class BorrowBookFixture {
    public static Instant borrowDate() {
        return Instant.parse("2025-04-14T10:00:00Z");
    }

    public static String borrowerEmail() {
        return "alice.doe@domain.fr";
    }

    public static String anotherBorrowerEmail() {
        return "bob.doe@domain.fr";
    }

    public static String bookToBorrowId() {
        return "1234567890";
    }
}
