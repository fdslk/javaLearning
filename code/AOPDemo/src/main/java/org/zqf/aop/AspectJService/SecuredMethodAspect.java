package org.zqf.aop.AspectJService;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class SecuredMethodAspect {
    //cutPoint
    @Pointcut("@annotation(secured)")
    public void callAt(Secured secured) {
    }

    //Advice
    @Around("callAt(secured)")
    public Object around(ProceedingJoinPoint proceedingJoinPoint,
                         Secured secured) throws Throwable {
        System.out.println("-------------test---------------");
        return secured.isLocked() ? null : proceedingJoinPoint.proceed();
    }
}
