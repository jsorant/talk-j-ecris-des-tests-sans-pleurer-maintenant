package com.jsorant.library.domain.usecases;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExamplesTest {

    @Test
    void list() {
        List<Integer> ints = List.of(1, 2, 3);

        assertTrue(ints.isEmpty());

        //assertThat(ints).isEmpty();
    }

    @Test
    void optional() {
        Optional<String> maybeString = Optional.empty();

        Assertions.assertThat(maybeString).contains("data");
    }

    @Test
    void exception() {
        Assertions.assertThatThrownBy(() -> someCode())
                .isExactlyInstanceOf(RuntimeException.class)
                .hasMessage("Something went wrong");

        Assertions.assertThatThrownBy(() -> someCode())
                .isExactlyInstanceOf(RuntimeException.class)
                .hasMessageContaining("wrong");
    }

    private void someCode() {

    }
}
