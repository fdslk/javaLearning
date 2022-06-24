package com.example.option_optional_practice;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

;

public class OptionalTests {
    @Test
    void shouldReturnOptionalWhenUseOptionalOfCreatObject() {
        Optional<String> s = Optional.of("test");
        assertThat(s).isPresent();
        assertThat(s.get()).isEqualTo("test");
    }

    @ParameterizedTest
    @CsvSource({"test, test", "tttt,tttt"})
    void shouldReturnOptionalWhenUseOptionalOfNullableCreatObject(String actualString, String expectedString) {
        Optional<String> s = Optional.ofNullable(actualString);
        assertThat(s.get()).isEqualTo(expectedString);
    }

    @ParameterizedTest
    @CsvSource({",test2", "test,test"})
    void shouldReturnDefaultValueWhenUseOrElseAndContentIsNull(String actualString, String expectedString) {
        String s = Optional.ofNullable(actualString).orElse(getContent(expectedString));
        assertThat(s).isEqualTo(expectedString);
    }

    @Test
    void shouldThrowCustomExceptionWhenSetANullValueForOptionalOf() {
        String nullContent = null;
        Exception illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> Optional.ofNullable(nullContent).orElseThrow(IllegalArgumentException::new));
        assertThat(illegalArgumentException.getClass()).isEqualTo(IllegalArgumentException.class);
    }

    @Test
    void shouldReturnTheValueIsMoreThan5AndIsMultiplesOfTwoWhenUsingfilter() {
        List<Integer> numList = Arrays.asList(2, 4, 6, 9, 10, 15, 17, 20);

        Predicate<Integer> predicate = num -> num > 5 && num % 2 == 0;

        List<Integer> collect = numList.stream().filter(predicate)
                .collect(Collectors.toList());
        assertThat(collect.size()).isEqualTo(3);
    }

    @ParameterizedTest
    @CsvSource({"153", "370", "371", "407"})
    void shouldCheckShuiXianHuaNumIsTrueWhenNum153Input(String exceptedNum) {
        Integer num = Integer.valueOf(exceptedNum);
        assertTrue(Optional.ofNullable(num)
                .filter(i -> (
                                Math.pow(i % 10, 3) + Math.pow(i / 10 % 10, 3) + Math.pow(i / 100, 3) == i
                        ))
                .isPresent());
    }

    @Test
    void shouldReturnNewObjectWhenUseMapOperation() {
        List<Integer> integers = Arrays.asList(2, 4, 6, 9, 10, 15, 17, 20);
        assertThat(Optional.ofNullable(integers)
                .map(List::size).get()).isEqualTo(8);

        assertThat(Optional.ofNullable(integers)
                .map(nums -> nums.get(0)).get()).isEqualTo(2);

        assertThat(Optional.ofNullable(integers)
                .map(list -> list.stream().collect(Collectors.averagingDouble(x -> x))).get())
                .isEqualTo(10.375);

        Optional<List<Integer>> integerList = Optional.ofNullable(integers)
                .map(list -> list.stream().collect(Collectors.toList()).stream()
                        .filter(num -> num > 5 && num % 2 == 0)
                        .collect(Collectors.toList()));

        assertThat(integerList.get().size())
                .isEqualTo(3);
    }

    @Test
    void shouldReturnNewObjectWithoutUnwrapWhenCallFlatMap() {
        Person person = new Person("john", 26, "123456");
        Optional<Person> personOptional = Optional.of(person);

        Optional<Optional<String>> nameOptionalWrapper
                = personOptional.map(Person::getName);
        Optional<String> nameOptional
                = nameOptionalWrapper.orElseThrow(IllegalArgumentException::new);
        String name1 = nameOptional.orElse("");
        assertEquals("john", name1);

        String name = personOptional
                .flatMap(Person::getName)
                .orElse("");
        assertEquals("john", name);


        List<Integer> integers = Arrays.asList(2, 4, 6, 9, 10, 15, 17, 20);
        List<Integer> integerList = Optional.ofNullable(integers)
                .flatMap(list -> Optional.of(list.stream().collect(Collectors.toList())
                        .stream().filter(num -> num > 5)
                        .collect(Collectors.toList())))
                .get();
        assertThat(integerList.size()).isEqualTo(6);
    }

    private String getContent(String content) {
        System.out.println(String.format("the input content is %s", content));
        return content;
    }
}

class Person {
    public Person(String name, int age, String password) {
        this.name = name;
        this.age = age;
        this.password = password;
    }

    private String name;
    private int age;
    private String password;

    public Optional<String> getName() {
        return Optional.ofNullable(name);
    }

    public Optional<Integer> getAge() {
        return Optional.ofNullable(age);
    }

    public Optional<String> getPassword() {
        return Optional.ofNullable(password);
    }

    // normal constructors and setters
}
