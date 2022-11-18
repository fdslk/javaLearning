package org.zqf.easyruledemo;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.core.BasicRule;

public abstract class BasicTestRule extends BasicRule {
    public static final String FACTS = "facts";

    public BasicTestRule(final String name, final int priority) {
        super(name, DEFAULT_DESCRIPTION, priority);
    }

    protected abstract boolean hitCondition(final RuleResult creatureFacts);

    protected abstract String getType();

    @Override
    public final boolean evaluate(final Facts facts) {
        return hitCondition(facts.get(FACTS));
    }

    @Override
    public final void execute(final Facts facts) {
        RuleResult ruleResult = facts.get(FACTS);
        ruleResult.setResultType(String.format("The class is %s", getType()));
    }
}
