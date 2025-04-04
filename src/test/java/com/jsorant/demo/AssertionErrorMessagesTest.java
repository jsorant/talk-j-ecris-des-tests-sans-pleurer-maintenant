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

        //assertThat(ints).isEmpty();
        //assertThat(ints).isNotEmpty();
        //assertThat(ints).hasSize(3);
        //assertThat(ints).contains(1);
        //assertThat(ints).containsAll(List.of(1, 2));
        //assertThat(ints).containsExactly(1, 2, 3);
    }

    @Test
    void optional() {
        Optional<String> maybeString = Optional.empty();

        assertThat(maybeString).contains("data");
        assertThat(maybeString).isEmpty();
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
