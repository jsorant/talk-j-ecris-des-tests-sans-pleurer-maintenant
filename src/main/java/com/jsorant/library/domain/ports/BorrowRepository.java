package com.jsorant.library.domain.ports;

import com.jsorant.library.domain.Borrow;
import java.util.Optional;

public interface BorrowRepository {
  void save(Borrow borrow);

  Optional<Borrow> findForBookId(String bookId);

  int borrowsCountForBorrower(String borrowerEmail);
}
