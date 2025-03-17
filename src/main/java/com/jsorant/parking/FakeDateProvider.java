package com.jsorant.parking;

import java.time.Instant;

public class FakeDateProvider implements DateProvider {

  private Instant now = Instant.now();

  public FakeDateProvider(String now) {
    setNow(now);
  }

  @Override
  public Instant now() {
    return now;
  }

  public void setNow(String now) {
    this.now = Instant.parse(now);
  }
}
