package com.jsorant.library.secondary;

import com.jsorant.library.domain.Borrow;
import com.jsorant.library.domain.ports.BorrowRepository;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryBorrowRepository implements BorrowRepository {

  private final Map<String, Borrow> borrows = new HashMap<>();

  @Override
  public void save(Borrow borrow) {
    borrows.put(borrow.bookId(), borrow);
  }

  @Override
  public Optional<Borrow> findForBookId(String bookId) {
    return Optional.ofNullable(borrows.get(bookId));
  }

  @Override
  public int borrowsCountForBorrower(String borrowerEmail) {
    return (int) borrows.values().stream().filter(borrow -> borrow.borrowerEmail().equals(borrowerEmail)).count();
  }
}
