package com.fang.dubbo.consumer.configuration;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ConsumerConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author bestpay
 * @version V1.0
 * @Title: ConsumerConfiguration
 * @Package
 * @Description: TODO
 * @date
 */
@Configuration
@EnableDubbo(scanBasePackages = "com.fang.dubbo.provider.service.annotation")
@ComponentScan(value = {"com.fang.dubbo.consumer.Annotation"})
public class ConsumerConfiguration {
    @Bean// 应用配置
    public ApplicationConfig applicationConfig(){
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("dubbo-annotation-provider");
        Map<String, String> stringStringMap = new HashMap<String, String>();
//        stringStringMap.put("qos.enable", "false");
        stringStringMap.put("qos.enable", "false");
        stringStringMap.put("qos.accept.foreign.ip", "false");
        stringStringMap.put("qos.port", "33333");
        applicationConfig.setParameters(stringStringMap);
        return applicationConfig;
    }

    @Bean //服务消费者配置
    public ConsumerConfig consumerConfiguration(){
        ConsumerConfig consumerConfig = new ConsumerConfig();
        consumerConfig.setTimeout(3000);
        return consumerConfig;
    }
    @Bean
    public RegistryConfig registryConfig(){
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setProtocol("zookeeper");
        registryConfig.setAddress("127.0.0.1");
        registryConfig.setPort(2181);
        return registryConfig;
    }
}
