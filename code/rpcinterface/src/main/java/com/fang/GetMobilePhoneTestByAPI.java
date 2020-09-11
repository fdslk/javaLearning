package com.fang;

import com.alibaba.fastjson.JSON;
import com.lujiatao.rpcinterface.domain.MobilePhone;
import com.lujiatao.rpcinterface.dubbo.MobilePhoneService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * @author fangzengqiang
 * @version V1.0
 * @Title: GetMobilePhoneTestByAPI
 * @Package
 * @Description: TODO
 * @date
 */
@Slf4j
public class GetMobilePhoneTestByAPI {
    private MobilePhoneService mobilePhoneService;
    @BeforeClass
    public void init(){
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("RPCDubboConsumer");
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress("zookeeper://localhost:2181");
        ReferenceConfig<MobilePhoneService> referenceConfig = new ReferenceConfig<MobilePhoneService>();
        referenceConfig.setApplication(applicationConfig);
        referenceConfig.setRegistry(registryConfig);
        referenceConfig.setInterface(MobilePhoneService.class);
        referenceConfig.setVersion("1.0.0");
        mobilePhoneService = referenceConfig.get();
    }

    @Test
    public void testCase1(){
        MobilePhone mobilePhone = mobilePhoneService.getMobilePhone("iPhone 6s");
        log.info("mobilePhone===>result{}" + JSON.toJSONString(mobilePhone));
        Assert.assertEquals(JSON.toJSONString(mobilePhone), "{\"brand\":\"Apple\",\"model\":\"iphone 6s\", \"os\":\"IOS\"}");

    }
}
