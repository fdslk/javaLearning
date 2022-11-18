package org.zqf.easyruledemo;

import java.util.List;

import static java.util.Arrays.asList;

public class UnmatchedTestRule extends BasicTestRule{

    private List<String> classList = asList("PersonTestRule", "AnimalTestRule");

    public UnmatchedTestRule() {
        super("UnmatchedTestRule", 3);
    }

    @Override
    protected boolean hitCondition(RuleResult creatureFacts) {
        String simpleClassName = this.getClass().getSimpleName();
        return "".equals(simpleClassName) || !classList.contains(simpleClassName);
    }

    @Override
    protected String getType() {
        return UnmatchedTestRule.class.toString();
    }
}
