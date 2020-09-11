package com.fang;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author fangzengqiang
 * @version V1.0
 * @Title: ConsumerConfiguration
 * @Package
 * @Description: TODO
 * @date
 */
@ComponentScan(value = ("com.lujiatao.rpcinterfacetest"))
@Configuration
@EnableDubbo
@PropertySource("application.properties")
public class ConsumerConfiguration {

}
