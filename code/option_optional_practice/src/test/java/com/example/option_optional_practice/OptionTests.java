package com.example.option_optional_practice;

import com.example.option_optional_practice.validataion.PersonValidator;
import io.vavr.Function0;
import io.vavr.Tuple;
import io.vavr.Tuple3;
import io.vavr.control.Option;
import io.vavr.control.Try;
import org.junit.jupiter.api.Test;

import java.util.function.BiFunction;
import java.util.function.Function;

import static io.vavr.API.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test
    void shouldNotThrowExceptionWhenWrapLogicBlock() {
        assertTrue(Try.of(()-> 1/0).isFailure());
    }

    @Test
    void shouldReturnFunctionThenReturnCorrectResult() {
        Function<Integer, Integer> square = (num) -> num * num;
        assertThat(square.apply(2)).isEqualTo(4);
    }

    @Test
    void shouldReturnBiFunctionThenReturnCorrectResult() {
        BiFunction<Integer, Integer, Integer> sum = Integer::sum;
        assertThat(sum.apply(1, 2)).isEqualTo(3);
    }

    @Test
    void shouldReturnFunction1ThenReturnCorrectResult() {
        Function0<String> getClazzName = () -> this.getClass().getName();
        String clzName = getClazzName.apply();
        assertThat(getClazzName.apply()).isEqualTo(clzName);
    }

    @Test
    void shouldReturnCorrectMessageWhenPersonNameAndAgeAreCorrect() {
        PersonValidator personValidator = new PersonValidator();
        assertThat(personValidator.validationPerson("Json", 30).toString()).isEqualTo("Valid(Person(name=Json, age=30))");
        Option<String> when = Option.when(true, "");
    }

    @Test
    void shouldReturnCorrectValueWhenMatchRelatedCases() {
        int input = 2;
        String output = Match(input).of(
                Case($(1), "one"),
                Case($(2), "two"),
                Case($(3), "three"),
                Case($(), "?"));

        assertThat(output).isEqualTo("two");
    }
}
