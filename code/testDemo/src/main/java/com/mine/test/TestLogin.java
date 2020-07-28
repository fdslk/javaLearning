package com.mine.test;

import com.mine.test.commonFunc.Common;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @Classname TestLogin
 * @Description TODO
 * @Date 2020/7/23 15:22
 * @Created by Administrator
 */
public class TestLogin {
    private static String userId = "907081174@qq.com";

    private static String pwd = "fdslk123456";

    @Test
    public void loginBaiduPan(){
        System.setProperty("webdriver.chrome.driver", "F:\\setup\\chromedriver_win32\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("http://pan.baidu.com/");

        String loginMethod;

        Common.threadSleep(1000);

        loginMethod = driver.findElement(By.cssSelector("#TANGRAM__PSP_4__footerULoginBtn")).getText();

        if(loginMethod.equals("帐号密码登录")) {
            driver.findElement(By.cssSelector("#TANGRAM__PSP_4__footerULoginBtn")).click();
        }

        for (char item : userId.toCharArray()) {
            driver.findElement(By.cssSelector("#TANGRAM__PSP_4__userName")).sendKeys(String.valueOf(item));
            Common.threadSleep(300);
        }

        for (char item : pwd.toCharArray()) {
            driver.findElement(By.cssSelector("#TANGRAM__PSP_4__password")).sendKeys(String.valueOf(item));
            Common.threadSleep(300);
        }


        driver.findElement(By.cssSelector("#TANGRAM__PSP_4__submit")).click();

        Common.threadSleep(5000);

        driver.findElement(By.cssSelector("#dialog1 > div.dialog-body > div > div.know-button > span")).click();

        String userNickName = driver.findElement(By.xpath("/html/body/div[1]/div[1]/div/div/dl/dd[2]/span/span[2]")).getText();

        Assert.assertEquals(userNickName, "我是背包客无悔");

        driver.close();
    }
}
