package org.zqf.aop.AspectJService;

import org.junit.Test;

public class AnnotationAspectTest {
    @Test
    public void testMethod() {
        SecuredMethod service = new SecuredMethod();
        service.unlockedMethod();
        service.lockedMethod();
    }
}
