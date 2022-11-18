package org.zqf.easyruledemo;

import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngineParameters;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;


class RulesFacadeTest {
    private final DefaultRulesEngine rulesEngine;
    private final Rules rules;


    public RulesFacadeTest() {
        final RulesEngineParameters parameters = new RulesEngineParameters()
                .skipOnFirstAppliedRule(true)
                .skipOnFirstFailedRule(false)
                .skipOnFirstNonTriggeredRule(false);

        this.rules = new Rules();
        rules.register(new AnimalTestRule());
        rules.register(new PersonTestRule());
        rules.register(new UnmatchedTestRule());

        this.rulesEngine  = new DefaultRulesEngine(parameters);
    }

    @ParameterizedTest
    @CsvSource(value = {"PersonTestRule", "AnimalTestRule"})
    void shouldReturnMatchedClassName(String ruleName) {
        String expectedResult = String.format("The class is class org.zqf.easyruledemo.%s", ruleName);
        assertEquals(new RulesFacade(rulesEngine, rules).generate(ruleName).get().getResultType(), expectedResult);
    }

    @Test
    void shouldReturnUnMatchClassNameGivenRuleIsNotIncludeInRuleEngine() {
        String expectedResult = String.format("The class is class org.zqf.easyruledemo.%s", "UnmatchedTestRule");
        assertEquals(new RulesFacade(rulesEngine, rules).generate("unknown").get().getResultType(), expectedResult);

    }
}