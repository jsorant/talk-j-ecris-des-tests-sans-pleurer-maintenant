package com.jsorant.parking;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.jsorant.UnitTest;
import com.jsorant.library.Currency;
import java.time.Instant;
import org.junit.jupiter.api.Test;

@UnitTest
public class ParkingCostCalculatorTest {

  FakeDateProvider dateProvider = new FakeDateProvider(now());
  ParkingCostCalculator calculator = new ParkingCostCalculator(dateProvider);

  @Test
  void shouldNotCalculateParkingCostIfLeavingDateIsYesterday() {
    assertThatThrownBy(() -> calculator.parkingCostForLeavingDate(yesterday())).hasMessage("Cannot have a leaving date in the past");
  }

  @Test
  void shouldNotCalculateParkingCostIfLeavingDateIsIn8Days() {
    assertThatThrownBy(() -> calculator.parkingCostForLeavingDate(dateInEightDays()))
      .hasMessage("Cannot have a leaving date in more than seven days");
  }

  @Test
  void shouldCalculateParkingCostWhenStayingOneDay() {
    Amount cost = calculator.parkingCostForLeavingDate(tomorrow());

    assertThat(cost).isEqualTo(Amount.of(15, Currency.EURO));
  }

  private static String now() {
    return "2025-04-14T10:00:00Z";
  }

  private static Instant yesterday() {
    return Instant.parse("2025-04-13T10:00:00Z");
  }

  private static Instant tomorrow() {
    return Instant.parse("2025-04-15T10:00:00Z");
  }

  private static Instant dateInEightDays() {
    return Instant.parse("2025-04-21T15:00:00Z");
  }
}
