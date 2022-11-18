package org.zqf.easyruledemo;

import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngineParameters;
import org.jeasy.rules.core.DefaultRulesEngine;

public class Application {

    public static void main(String[] args) {
        final RulesEngineParameters parameters = new RulesEngineParameters()
                .skipOnFirstAppliedRule(true)
                .skipOnFirstFailedRule(false)
                .skipOnFirstNonTriggeredRule(false);
        DefaultRulesEngine rulesEngine = new DefaultRulesEngine(parameters);

        Rules rules = new Rules();
        rules.register(new AnimalTestRule());
        rules.register(new PersonTestRule());
        rules.register(new UnmatchedTestRule());
        rules.register(new AnnotationTestRule());

        System.out.println(new RulesFacade(rulesEngine, rules).generate("PersonTestRule").get().getResultType());
    }
}
