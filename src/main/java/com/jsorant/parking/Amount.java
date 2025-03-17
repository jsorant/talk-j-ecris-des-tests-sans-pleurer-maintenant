package com.jsorant.parking;

import com.jsorant.library.Currency;

public record Amount(double quantity, Currency currency) {
  public static Amount of(double quantity, Currency currency) {
    return new Amount(quantity, currency);
  }
}
