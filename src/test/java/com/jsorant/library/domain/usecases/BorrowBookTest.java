package com.jsorant.library.domain.usecases;

import com.jsorant.UnitTest;
import com.jsorant.library.domain.events.BookBorrowed;
import com.jsorant.library.domain.exceptions.BookAlreadyBorrowedException;
import com.jsorant.library.domain.exceptions.BookNotOwnedByTheLibraryException;
import com.jsorant.library.domain.exceptions.BorrowerHasAlreadyFourBooksBorrowedException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@UnitTest
@DisplayName("Borrow a book")
public class BorrowBookTest {

    BorrowBookContext context = new BorrowBookContext();

    @Test
    void shouldReturnBookBorrowedEventWhenBookIsBorrowed() {
        BorrowBook borrowBook = context.buildBorrowBook();

        BookBorrowed event = borrowBook.act();

        assertThat(event)
                .isEqualTo(new BookBorrowed(context.borrowerEmail(), context.bookToBorrowId(), context.borrowDate()));
    }

    @Test
    void shouldThrowWhenBookIsNotOwnedByTheLibrary() {
        BorrowBook borrowBook = context
                .butBorrowingABookThatIsNotOwnedByTheLibrary()
                .buildBorrowBook();

        assertThatThrownBy(borrowBook::act)
                .isEqualTo(new BookNotOwnedByTheLibraryException(context.idOfTheBookToBorrowThatIsNotOwnedByTheLibrary()));
    }

    @Test
    void shouldThrowWhenBookIsAlreadyBorrowed() {
        BorrowBook borrowBook = context
                .butWithBookToBorrowAlreadyBorrowed()
                .buildBorrowBook();

        assertThatThrownBy(borrowBook::act)
                .isEqualTo(new BookAlreadyBorrowedException(context.bookToBorrowId()));
    }

    @Test
    void shouldThrowWhenBorrowerHasAlreadyFourBooksBorrowed() {
        BorrowBook borrowBook = context
                .butWithAlreadyFourBooksBorrowed()
                .buildBorrowBook();

        assertThatThrownBy(borrowBook::act)
                .isEqualTo(new BorrowerHasAlreadyFourBooksBorrowedException(context.borrowerEmail(), context.bookToBorrowId()));
    }
}
