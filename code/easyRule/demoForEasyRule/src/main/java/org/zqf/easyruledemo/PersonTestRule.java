package org.zqf.easyruledemo;

import java.util.Objects;

public class PersonTestRule extends BasicTestRule {
    public PersonTestRule() {
        super("PersonTestRule", 1);
    }

    @Override
    protected boolean hitCondition(RuleResult creatureFacts) {
        return Objects.equals(creatureFacts.getClassType(), this.getClass().getSimpleName());
    }

    @Override
    protected String getType() {
        return PersonTestRule.class.toString();
    }
}
