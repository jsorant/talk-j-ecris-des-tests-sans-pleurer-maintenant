package com.jsorant.library.domain.usecases;

import com.jsorant.UnitTest;
import com.jsorant.library.domain.events.BookBorrowed;
import com.jsorant.library.domain.exceptions.BookAlreadyBorrowedException;
import com.jsorant.library.domain.exceptions.BookNotOwnedByTheLibraryException;
import com.jsorant.library.domain.exceptions.BorrowerHasAlreadyFourBooksBorrowedException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.jsorant.library.LibraryAssertions.assertThat;
import static com.jsorant.library.domain.BookFixture.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@UnitTest
@DisplayName("Borrow a book")
public class ReworkedBorrowBookTest {

    BorrowBookContext context = new BorrowBookContext();

    @Test
    void shouldReturnBookBorrowedEventWhenBookIsBorrowed() {
        BorrowBook borrowBook = context
                .withBookToBorrow(theHobbit())
                .buildBorrowBook();

        BookBorrowed bookBorrowed = borrowBook.act();

        assertThat(bookBorrowed)
                .refersToBook(theHobbit())
                .refersToBorrower(context.borrowerEmail())
                .wasDoneOn(context.borrowDate());
    }

    @Test
    void shouldThrowWhenBookIsAlreadyBorrowed() {
        BorrowBook borrowBook = context
                .withBookBorrowedBySomeoneElse(lordOfTheRings())
                .withBookToBorrow(lordOfTheRings())
                .buildBorrowBook();

        assertThatThrownBy(borrowBook::act)
                .isInstanceOf(BookAlreadyBorrowedException.class)
                .hasMessageContaining(lordOfTheRings().id());
    }

    @Test
    void shouldThrowWhenBookIsNotOwnedByTheLibrary() {
        BorrowBook borrowBook = context
                .withBookNotOwnedByTheLibrary(hungerGames())
                .withBookToBorrow(hungerGames())
                .buildBorrowBook();

        assertThatThrownBy(borrowBook::act)
                .isInstanceOf(BookNotOwnedByTheLibraryException.class)
                .hasMessageContaining(hungerGames().id());
    }

    @Test
    void shouldThrowWhenBorrowerHasAlreadyFourBooksBorrowed() {
        BorrowBook borrowBook = context
                .withBooksBorrowed(
                        lordOfTheRings(),
                        harryPotter(),
                        theTwoTowers(),
                        theReturnOfTheKing())
                .withBookToBorrow(theHobbit())
                .buildBorrowBook();

        assertThatThrownBy(borrowBook::act)
                .isInstanceOf(BorrowerHasAlreadyFourBooksBorrowedException.class)
                .hasMessageContaining(theHobbit().id())
                .hasMessageContaining(context.borrowerEmail());
    }
}
