package com.easyrule.demo;

import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.Rules;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class RuleBeans {
    @Bean("BasicVegetableRule")
    public Rules BasicVegetableRuleFacade(final Set<BasicVegetableRule> basicVegetableRules) {
        return new Rules(basicVegetableRules.stream().map(Rule.class::cast).collect(Collectors.toSet()));
    }
}
