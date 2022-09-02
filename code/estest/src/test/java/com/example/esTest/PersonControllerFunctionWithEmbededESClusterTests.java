package com.example.estest;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PersonControllerFunctionWithEmbededESClusterTests extends BaseIntegrationTest {
    @Test
    void name() {
        assertThat(1).isEqualTo(1);
    }
}
