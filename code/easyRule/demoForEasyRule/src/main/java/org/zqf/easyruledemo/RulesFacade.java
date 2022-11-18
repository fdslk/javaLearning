package org.zqf.easyruledemo;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;

import java.util.Optional;

public class RulesFacade {
    public static final String FACTS = "facts";

    private final RulesEngine rulesEngine;
    private final Rules rules;

    public RulesFacade(RulesEngine rulesEngine, Rules rules) {
        this.rulesEngine = rulesEngine;
        this.rules = rules;
    }

    public Optional<RuleResult> generate(String classType) {
        final Facts facts = new Facts();
        RuleResult ruleResult = new RuleResult();
        ruleResult.setClassType(classType);
        facts.put(FACTS, ruleResult);
        rulesEngine.fire(rules, facts);
        return Optional.ofNullable(ruleResult);
    }
}
