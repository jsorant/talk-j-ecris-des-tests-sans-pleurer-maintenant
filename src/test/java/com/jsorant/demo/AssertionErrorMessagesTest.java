package com.jsorant.demo;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AssertionErrorMessagesTest {

    @Test
    void listWithAssertTrue() {
        List<Integer> ints = List.of(1, 2, 3);

        assertTrue(ints.isEmpty());
    }

    @Test
    void listWithAssertThatIsEmpty() {
        List<Integer> ints = List.of(1, 2, 3);

        assertThat(ints).isEmpty();
    }

    @Test
    void optional() {
        Optional<String> maybeString = Optional.empty();

        assertThat(maybeString).contains("data");
    }

    @Test
    void exceptionPrecise() {
        assertThatThrownBy(() -> someCode())
                .isExactlyInstanceOf(RuntimeException.class)
                .hasMessage("Something went wrong");
    }

    @Test
    void exception() {
        assertThatThrownBy(() -> someCode())
                .isExactlyInstanceOf(RuntimeException.class)
                .hasMessageContaining("wrong");
    }

    private void someCode() {

    }
}
