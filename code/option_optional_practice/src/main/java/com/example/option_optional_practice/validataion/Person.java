package com.example.option_optional_practice.validataion;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class Person {
    private final String name;
    private final Integer age;
}
