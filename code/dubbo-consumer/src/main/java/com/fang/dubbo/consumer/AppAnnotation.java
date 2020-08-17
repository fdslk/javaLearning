package com.fang.dubbo.consumer;

import com.fang.dubbo.consumer.Annotation.ConsumerAnnotationService;
import com.fang.dubbo.consumer.configuration.ConsumerConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

/**
 * @author bestpay
 * @version V1.0
 * @Title: AppAnnotation
 * @Package
 * @Description: TODO
 * @date
 */
public class AppAnnotation {
    public static void main(String[] args) throws IOException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConsumerConfiguration.class);
        context.start();
        ConsumerAnnotationService consumerAnnotationService = context.getBean(ConsumerAnnotationService.class);
        String hello = consumerAnnotationService.doSayHello("annotation");
        System.out.println("result: " + hello);
    }
}
