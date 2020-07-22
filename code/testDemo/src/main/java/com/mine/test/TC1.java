package com.mine.test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * @author Administrator
 * @version 1.0.0
 * @ClassName TC1.java
 * @Description TODO
 * @createTime 2020年07月15日 10:22:00
 */
public class TC1 {
    @BeforeClass
    public void beforeClass(){
        System.out.println("用例前执行打印本句！");
        System.out.println("每条Test用例是互不相干的");
        System.out.println("用例开始执行…………");
    }

    @AfterClass
    public void afterClass(){
        System.out.println("all cases finish ");
    }


    @Test
    public void case1(){
        // 指定Chromedriver的位置
        System.setProperty("webdriver.chrome.driver", "E:\\setup\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("http://www.baidu.com/");
        driver.close();
    }
    @Test
    public void actions(){
        System.setProperty("webdriver.chrome.driver", "E:\\setup\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("http://www.zentao.net/user-login.html");

        driver.findElement(By.id("account")).sendKeys("zqfangmaster@163.com");

        driver.findElement(By.id("password")).sendKeys("fdslk123456");

        driver.findElement(By.id("submit")).click();

        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        String text = driver.findElement(By.cssSelector("#siteNav > a:nth-child(1)")).getText();

        Assert.assertEquals(text, "fzq");

        driver.close();
    }

    @Test
    public void case2(){
        Assert.assertTrue(true);
        System.out.println("case2");
    }
}
