package org.zqf.easyruledemo;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngineParameters;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.jeasy.rules.mvel.MVELRuleFactory;
import org.jeasy.rules.support.reader.YamlRuleDefinitionReader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.InputStream;
import java.io.InputStreamReader;

import static org.junit.jupiter.api.Assertions.assertEquals;


class RulesFacadeTest {
    private final DefaultRulesEngine rulesEngine;
    private final Rules rules;
    private MVELRuleFactory ruleFactory = new MVELRuleFactory(new YamlRuleDefinitionReader());
    private InputStream resourceAsStream = RulesFacadeTest.class.getResourceAsStream("/rule-data/fileRule.yml");
    private InputStream singleResourceAsStream = RulesFacadeTest.class.getResourceAsStream("/rule-data/singleFileRule.yml");
    private Facts facts = new Facts();

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

    @Test
    void shouldMatchRuleByYmlPattern() throws Exception {
        assert resourceAsStream != null;
        Rule weatherRule = ruleFactory.createRule(new InputStreamReader(singleResourceAsStream));

        facts.put("rain", true);

        Rules rules = new Rules();
        rules.register(weatherRule);

        rulesEngine.fire(rules, facts);
    }

    @ParameterizedTest
    @CsvSource({"20,true", "18,false", "17,false"})
    void shouldMatchRuleGivenMultipleRulesYml(Integer age, Boolean isAdult) throws Exception {
        assert resourceAsStream != null;
        Rules multipleRules = ruleFactory.createRules(new InputStreamReader(resourceAsStream));

        Person person = new Person("foo", age);
        facts.put("person", person);

        rulesEngine.fire(multipleRules, facts);

        assertEquals(person.isAdult(), isAdult);
    }
}
