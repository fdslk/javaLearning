package com.mine.test;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Administrator
 * @version 1.0.0
 * @ClassName TC1.java
 * @Description TODO
 * @createTime 2020年07月15日 10:22:00
 */
public class TC1 {
    @Test
    public void case1(){
        Assert.assertEquals(1, 1);
    }
    @Test
    public void case2(){
        Assert.assertEquals("hello", "hello");
    }
}
