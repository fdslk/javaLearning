package com.fang;

import com.alibaba.fastjson.JSON;
import com.lujiatao.rpcinterface.domain.MobilePhone;
import com.lujiatao.rpcinterface.dubbo.MobilePhoneService;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * @author fangzengqiang
 * @version V1.0
 * @Title: GetMobilePhoneTest
 * @Package
 * @Description: TODO
 * @date
 */
public class GetMobilePhoneTest {
    private ClassPathXmlApplicationContext context;
    private MobilePhoneService mobilePhoneService;

    @BeforeClass
    public void init(){
        context = new ClassPathXmlApplicationContext("consumer.xml");
        context.start();
        mobilePhoneService = (MobilePhoneService)context.getBean("mobilePhoneService");
    }

    @Test
    public void testCase1(){
        Assert.assertEquals("{\"brand\":\"Apple\",\"model\":\"iphone 6s\", \"os\":\"IOS\"}", invokeGetMobilePhoneMethod("iphone 6s"));
    }

    @Test
    public void testCase2(){
        Assert.assertEquals("null", invokeGetMobilePhoneMethod(""));
    }

    @Test
    public void testCase3(){
        Assert.assertEquals("null", invokeGetMobilePhoneMethod(null));
    }

    private String invokeGetMobilePhoneMethod(String model) {
        MobilePhone mobilePhone = mobilePhoneService.getMobilePhone(model);
        return JSON.toJSONString(mobilePhone);
    }

    @AfterClass
    public void clear(){
        context.close();
    }
}
