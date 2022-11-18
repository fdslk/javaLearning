package org.zqf.easyruledemo;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;

import java.util.Objects;

@Rule(name = "AnnotationTestRule", description = "using annotation to match rule")
public class AnnotationTestRule {
    @Condition
    public boolean hitCondition(@Fact("facts") RuleResult ruleResult) {
        return Objects.equals(ruleResult.getClassType(), this.getClass().getSimpleName());
    }

    @Action
    public void getType() {
        System.out.println(AnnotationTestRule.class);
    }
}
