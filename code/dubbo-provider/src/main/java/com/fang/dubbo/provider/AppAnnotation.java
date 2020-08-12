package com.fang.dubbo.provider;

import com.fang.dubbo.provider.configuration.DubboConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

/**
 * @author AppAnnotation
 * @version V1.0
 * @Title:
 * @Package
 * @Description: TODO
 * @date
 */
public class AppAnnotation {
    public static void main(String[] args) throws IOException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DubboConfiguration.class);
        context.start();
        System.in.read();
    }
}
