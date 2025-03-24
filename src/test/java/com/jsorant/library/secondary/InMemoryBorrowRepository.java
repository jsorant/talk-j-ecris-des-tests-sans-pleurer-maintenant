package com.jsorant.library.secondary;

import com.jsorant.library.domain.Borrows;
import com.jsorant.library.domain.ports.BorrowRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryBorrowRepository implements BorrowRepository {

    private final Map<String, Borrows> borrows = new HashMap<>();

    @Override
    public boolean isBorrowed(String bookId) {
        return borrows.values().stream().anyMatch(borrows -> borrows.isBorrowed(bookId));
    }

    @Override
    public Borrows getForBorrower(String borrowerEmail) {
        return Optional.ofNullable(borrows.get(borrowerEmail)).orElse(new Borrows(borrowerEmail));
    }

    @Override
    public void save(Borrows borrows) {
        this.borrows.put(borrows.borrowerEmail(), borrows);
    }
}
