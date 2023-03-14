package org.zqf.aop.AspectJService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdderAfterReturnAspect {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    public void afterReturn(Object returnValue) {
        logger.info("value return was {}",  returnValue);
    }
}
