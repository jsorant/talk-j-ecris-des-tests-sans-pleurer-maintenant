package com.jsorant.kata;

import java.time.Instant;

public class RealDateProvider implements DateProvider {

  @Override
  public Instant now() {
    return Instant.now();
  }
}
