package com.jsorant.library.domain.usecases;

import java.time.Instant;
import java.util.List;

import static com.jsorant.library.domain.BookFixture.*;

// Move into context ???

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
        return theHobbit().id();
    }

    public static List<String> fourOtherBookIds() {
        return List.of(
                lordOfTheRings().id(),
                harryPotter().id(),
                theTwoTowers().id(),
                theReturnOfTheKing().id()
        );
    }
}
