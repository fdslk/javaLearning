package com.easyrule.demo;

import org.jeasy.rules.api.Rules;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class EasyruleDemoApplicationTests {

	@Autowired
	@Qualifier("BasicVegetableRule")
	private Rules basicVegetableRules;

	@Test
	void contextLoads() {
	}

	@Test
	void shouldReturnSubClassBasedOnBasicClass() {
		assertThat(basicVegetableRules.iterator().next()).isEqualTo(2);
	}
}
