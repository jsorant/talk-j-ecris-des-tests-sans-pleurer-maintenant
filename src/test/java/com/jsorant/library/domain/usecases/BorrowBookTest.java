package com.jsorant.library.domain.usecases;

import com.jsorant.UnitTest;
import com.jsorant.library.domain.events.BookBorrowed;
import com.jsorant.library.domain.exceptions.BookAlreadyBorrowedException;
import com.jsorant.library.domain.exceptions.BookNotOwnedByTheLibraryException;
import com.jsorant.library.domain.exceptions.BorrowerHasAlreadyFourBooksBorrowedException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.jsorant.library.LibraryAssertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@UnitTest
@DisplayName("Borrow a book")
public class BorrowBookTest {

    BorrowBookContext context = new BorrowBookContext();

    @Test
    void shouldReturnBookBorrowedEventWhenBookIsBorrowed() {
        BorrowBook borrowBook = context.buildBorrowBook();

        BookBorrowed bookBorrowed = borrowBook.act();

        assertThat(bookBorrowed)
                .refersToBook(context.bookToBorrowId())
                .refersToBorrower(context.borrowerEmail())
                .wasDoneOn(context.borrowDate());
    }

    @Test
    void shouldThrowWhenBookIsNotOwnedByTheLibrary() {
        BorrowBook borrowBook = context
                .butBorrowingABookThatIsNotOwnedByTheLibrary()
                .buildBorrowBook();

        assertThatThrownBy(borrowBook::act)
                .isInstanceOf(BookNotOwnedByTheLibraryException.class)
                .hasMessageContaining(context.idOfTheBookToBorrowThatIsNotOwnedByTheLibrary());
    }

    @Test
    void shouldThrowWhenBookIsAlreadyBorrowed() {
        BorrowBook borrowBook = context
                .butWithBookToBorrowAlreadyBorrowed()
                .buildBorrowBook();

        assertThatThrownBy(borrowBook::act)
                .isInstanceOf(BookAlreadyBorrowedException.class)
                .hasMessageContaining(context.bookToBorrowId());
    }

    @Test
    void shouldThrowWhenBorrowerHasAlreadyFourBooksBorrowed() {
        BorrowBook borrowBook = context
                .butWithAlreadyFourBooksBorrowed()
                .buildBorrowBook();

        assertThatThrownBy(borrowBook::act)
                .isInstanceOf(BorrowerHasAlreadyFourBooksBorrowedException.class)
                .hasMessageContaining(context.bookToBorrowId())
                .hasMessageContaining(context.borrowerEmail());
    }
}
