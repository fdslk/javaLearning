package com.zengqiangfang.service;

import com.zengqiangfang.App;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * @author bestpay
 * @version V1.0
 * @Title: CalculatorForPpiServiceTest
 * @Package
 * @Description: TODO
 * @date
 */
@SpringBootTest(classes = {App.class})
public class CalculatorForPpiServiceTest extends AbstractTestNGSpringContextTests {
    @Autowired
    private CalculatorForPpiService calculatorForPpiService;
    private int width;
    private int height;
    private double size;

    @BeforeClass
    public void init(){
        width = 750;
        height = 1334;
        size = 4.7;
    }

    @Test
    public void testCase1(){
        Assert.assertEquals(326, calculatorForPpiService.calculate(width, height, size));
    }
    @Test
    public void testCase2(){
        Assert.assertEquals(-1, calculatorForPpiService.calculate(-1, height, size));
    }
    @Test
    public void testCase3(){
        Assert.assertEquals(0, height, size);
    }
    @Test
    public void testCase4(){
        Assert.assertEquals(width, -1, size);
    }
}