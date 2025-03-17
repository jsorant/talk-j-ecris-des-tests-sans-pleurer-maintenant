package com.jsorant.parking;

import java.time.Instant;

public interface DateProvider {
  Instant now();
}
