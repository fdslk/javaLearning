package com.fang;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * @author fangzengqiang
 * @version V1.0
 * @Title: WorkTest
 * @Package
 * @Description: TODO
 * @date
 */
public class WorkTest {
    @InjectMocks
    private Work work;
    @Mock
    private Weather weather;

    @BeforeClass
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCase1(){
        Mockito.when(weather.getWeather()).thenReturn("晴");
        int actual = work.getWorkTime();
        int expected = 6;
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testCase2(){
        Mockito.when(weather.getWeather()).thenReturn("多云");
        int actual = work.getWorkTime();
        int expected = 7;
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testCase3(){
        Mockito.when(weather.getWeather()).thenReturn("雨");
        int actual = work.getWorkTime();
        int expected = 5;
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testCase4(){
        Mockito.when(weather.getWeather()).thenReturn("阴");
        int acutal = work.getWorkTime();
        int expected = 8;
        Assert.assertEquals(acutal, expected);
    }
}
