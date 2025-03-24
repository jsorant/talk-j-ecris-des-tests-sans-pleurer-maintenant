package com.jsorant.library.domain.ports;

import com.jsorant.library.domain.Borrows;

public interface BorrowRepository {
    boolean isBorrowed(String bookId);

    Borrows getForBorrower(String borrowerEmail);

    void save(Borrows borrowsForBorrower);
}
