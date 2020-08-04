package com.mine.test.commonFunc;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * @author syncwt
 * @version V1.0
 * @Title:
 * @Package
 * @Description: TODO
 * @date
 */
public class BasicConfig {
    public static WebDriver driver;

   static{
       System.setProperty("webdriver.chrome.driver", "F:\\setup\\chromedriver_win32\\chromedriver.exe");
       driver = new ChromeDriver();
   }
}
