package com.jsorant.library.domain;

import com.jsorant.library.domain.events.BookBorrowed;
import org.assertj.core.api.AbstractAssert;

import java.time.Instant;

public class BookBorrowedAssert extends AbstractAssert<BookBorrowedAssert, BookBorrowed> {
    public BookBorrowedAssert(BookBorrowed actual) {
        super(actual, BookBorrowedAssert.class);
    }

    public static BookBorrowedAssert assertThat(BookBorrowed event) {
        return new BookBorrowedAssert(event);
    }

    public BookBorrowedAssert refersToBorrower(String expectedEmail) {
        isNotNull();
        if (!actual.borrowerEmail().equals(expectedEmail)) {
            failWithMessage("Expected borrower email to be '%s' but was '%s'",
                    expectedEmail, actual.borrowerEmail());
        }
        return this;
    }

    public BookBorrowedAssert refersToBook(String expectedBookId) {
        isNotNull();
        if (!actual.bookId().equals(expectedBookId)) {
            failWithMessage("Expected book ID to be '%s' but was '%s'",
                    expectedBookId, actual.bookId());
        }
        return this;
    }

    public BookBorrowedAssert wasDoneOn(Instant expectedBorrowDate) {
        isNotNull();
        if (!actual.date().equals(expectedBorrowDate)) {
            failWithMessage("Expected borrow date to be '%s' but was '%s'",
                    expectedBorrowDate, actual.date());
        }
        return this;
    }
}
