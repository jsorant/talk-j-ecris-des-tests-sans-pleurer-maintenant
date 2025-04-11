package com.jsorant.library.domain.usecases;

import com.jsorant.UnitTest;
import com.jsorant.library.domain.events.BookBorrowed;
import com.jsorant.library.domain.exceptions.BookAlreadyBorrowedException;
import com.jsorant.library.domain.exceptions.BookNotOwnedByTheLibraryException;
import com.jsorant.library.domain.exceptions.BorrowerHasAlreadyFourBooksBorrowedException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static com.jsorant.library.LibraryAssertions.assertThat;
import static com.jsorant.library.domain.BookFixture.*;
import static com.jsorant.library.domain.UserFixture.alice;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@UnitTest
@DisplayName("Borrow a book")
public class ReworkedBorrowBookTest {

    BorrowBookContext context = new BorrowBookContext();

    @Test
    void shouldReturnBookBorrowedEventWhenBookIsBorrowed() {
        BorrowBook borrowBook = context
                .withBookToBorrow(theHobbit())
                .by(alice())
                .on(aDate())
                .build();

        BookBorrowed bookBorrowed = borrowBook.act();

        assertThat(bookBorrowed)
                .refersToBook(theHobbit())
                .refersToBorrower(alice())
                .wasDoneOn(aDate());
    }

    @Test
    void shouldThrowWhenBookIsAlreadyBorrowed() {
        context.withBookBorrowedByBob(lordOfTheRings());

        BorrowBook borrowBook = context
                .withBookToBorrow(lordOfTheRings())
                .build();

        assertThatThrownBy(borrowBook::act)
                .isInstanceOf(BookAlreadyBorrowedException.class)
                .hasMessageContaining(lordOfTheRings().id());
    }

    @Test
    void shouldThrowWhenBookIsNotOwnedByTheLibrary() {
        context.withBookNotOwnedByTheLibrary(hungerGames());

        BorrowBook borrowBook = context
                .withBookToBorrow(hungerGames())
                .build();

        assertThatThrownBy(borrowBook::act)
                .isInstanceOf(BookNotOwnedByTheLibraryException.class)
                .hasMessageContaining(hungerGames().id());
    }

    @Test
    void shouldThrowWhenBorrowerHasAlreadyFourBooksBorrowed() {
        context.withBooksBorrowed(
                lordOfTheRings(),
                harryPotter(),
                theTwoTowers(),
                theReturnOfTheKing());

        BorrowBook borrowBook = context
                .withBookToBorrow(theHobbit())
                .build();

        assertThatThrownBy(borrowBook::act)
                .isInstanceOf(BorrowerHasAlreadyFourBooksBorrowedException.class)
                .hasMessageContaining(theHobbit().id())
                .hasMessageContaining(context.borrowerEmail());
    }

    private static Instant aDate() {
        return Instant.parse("2025-04-14T10:00:00Z");
    }
}
