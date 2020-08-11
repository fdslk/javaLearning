package com.fang.dubbo.provider.service.annotation;

import com.alibaba.dubbo.config.annotation.Service;

/**
 * @author syncwt
 * @version V1.0
 * @Title:
 * @Package
 * @Description: TODO
 * @date
 */
@Service(timeout = 5000)
public class ProviderServiceImplAnnotation implements ProviderServiceAnnotation {
    public String sayHelloAnnotation(String word) {
        return null;
    }
}
