package com.jsorant.library;

import com.jsorant.library.domain.BookBorrowedAssert;
import com.jsorant.library.domain.events.BookBorrowed;
import org.assertj.core.api.Assertions;

public class LibraryAssertions extends Assertions {
    public static BookBorrowedAssert assertThat(BookBorrowed event) {
        return new BookBorrowedAssert(event);
    }
}
