package com.jsorant.library;

import java.util.Optional;

public interface BorrowsRepository {
  void save(Borrow borrow);

  Optional<Borrow> findForBookId(String bookId);

  int borrowsCountForBorrower(String borrowerEmail);
}
