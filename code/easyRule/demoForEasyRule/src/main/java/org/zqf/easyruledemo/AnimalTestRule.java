package org.zqf.easyruledemo;

import java.util.Objects;

public class AnimalTestRule extends BasicTestRule {
    public AnimalTestRule() {
        super("AnimalTestRule", 2);
    }

    @Override
    protected boolean hitCondition(RuleResult creatureFacts) {
        return Objects.equals(creatureFacts.getClassType(), this.getClass().getSimpleName());
    }

    @Override
    protected String getType() {
        return AnimalTestRule.class.toString();
    }
}
