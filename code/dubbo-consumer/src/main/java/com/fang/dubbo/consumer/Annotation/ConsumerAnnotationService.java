package com.fang.dubbo.consumer.Annotation;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fang.dubbo.provider.service.annotation.ProviderServiceAnnotation;
import org.springframework.stereotype.Component;

/**
 * @author bestpay
 * @version V1.0
 * @Title: CusumerAnnotationService
 * @Package
 * @Description: TODO
 * @date
 */
@Component("annotatedConsumer")
public class ConsumerAnnotationService {

    @Reference
    private ProviderServiceAnnotation providerServiceAnnotation;

    public String doSayHello(String name){
        return providerServiceAnnotation.sayHelloAnnotation(name);
    }
}
