package org.zqf.aop.AspectJService;

public class SecuredMethod {
    @Secured(isLocked = true)
    public void lockedMethod() {
    }

    @Secured
    public void unlockedMethod() {
    }
}
