package com.example.option_optional_practice;

import io.vavr.Tuple;
import io.vavr.Tuple3;
import io.vavr.control.Option;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OptionTests {
    @Test
    void givenValue_whenCreatesOption_thenCorrect() {
        Option<Object> nullValue = Option.of(null);
        Option<String> stringValue = Option.of("val");

        assertThat(nullValue.isEmpty()).isTrue();
        assertThat(nullValue.toString()).isEqualTo("None");
        assertThat(stringValue.get()).isEqualTo("val");
    }

    @Test
    void givenNull_whenCreatesOption_thenCorrect() {
        Option<Object> nullValue = Option.of(null);
        assertThat(nullValue.getOrElse("default")).isEqualTo("default");
    }

    @Test
    public void whenCreatesTuple_thenCorrect2() {
        Tuple3<String, Integer, Double> java8 = Tuple.of("Java", 8, null);
        String element1 = java8._1;
        int element2 = java8._2();

        assertEquals("Java", element1);
        assertEquals(8, element2);
        assertEquals(1.8, java8._3(), 0.1);
    }
}
