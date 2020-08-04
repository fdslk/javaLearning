package com.mine.test;

import com.mine.test.commonFunc.LoginPageCommonFun;
import org.testng.TestNG;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @Classname TestLogin
 * @Description TODO
 * @Date 2020/7/23 15:22
 * @Created by Administrator
 */
public class TestLogin extends TestNG {
    private static String userId = "907081174@qq.com";

    private static String pwd = "fdslk123456";

    @Test
    public void loginBaiduPan(){

        LoginPageCommonFun test = new LoginPageCommonFun();
        test.driver.get("http://pan.baidu.com/");
        LoginPageCommonFun test1 = new LoginPageCommonFun();
        test1.driver.get("http://pan.baidu.com/");

        String loginMethod;

//        Common.threadSleep(1000);

//        loginMethod = driver.findElement(By.cssSelector("#TANGRAM__PSP_4__footerULoginBtn")).getText();
//
//        if(loginMethod.equals("帐号密码登录")) {
//            driver.findElement(By.cssSelector("#TANGRAM__PSP_4__footerULoginBtn")).click();
//        }
//
//        for (char item : userId.toCharArray()) {
//            driver.findElement(By.cssSelector("#TANGRAM__PSP_4__userName")).sendKeys(String.valueOf(item));
//            Common.threadSleep(300);
//        }
//
//        for (char item : pwd.toCharArray()) {
//            driver.findElement(By.cssSelector("#TANGRAM__PSP_4__password")).sendKeys(String.valueOf(item));
//            Common.threadSleep(300);
//        }
//
//
//        driver.findElement(By.cssSelector("#TANGRAM__PSP_4__submit")).click();
//
//        Common.threadSleep(5000);
//
//        driver.findElement(By.cssSelector("#dialog1 > div.dialog-body > div > div.know-button > span")).click();
//
//        String userNickName = driver.findElement(By.xpath("/html/body/div[1]/div[1]/div/div/dl/dd[2]/span/span[2]")).getText();
//
//        Assert.assertEquals(userNickName, "我是背包客无悔");
//
//        driver.close();
    }
    @DataProvider(name = "data")
    public Object[][] dataProvider1(){
        return new Object[][]{
                new Object[]{"空账号", "正确密码", "账号不能为空"},
                new Object[]{"正确账号", "空密码", "密码不能为空"},
                new Object[]{"正确账号", "正确密码", "登陆成功"}
        };
    }
    @Test(dataProvider = "data")
    public void testDataProvider(String username, String password, String prompt){
        System.out.println(String.format("如果输入： %s, %s，提示：%s", username, password, prompt));
    }

    @Test
    public void testLoginBestPay(){

    }
}
