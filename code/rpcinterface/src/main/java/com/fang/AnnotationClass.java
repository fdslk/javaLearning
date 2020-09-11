package com.fang;

import com.lujiatao.rpcinterface.domain.MobilePhone;
import com.lujiatao.rpcinterface.dubbo.MobilePhoneService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

/**
 * @author fangzengqiang
 * @version V1.0
 * @Title: AnnotationClass
 * @Package
 * @Description: TODO
 * @date
 */
@Component("annotationClass")
public class AnnotationClass {
    @Reference(version = "1.0.0")
    private MobilePhoneService mobilePhoneService;
    public MobilePhone getMobilePhone(String model){
        return mobilePhoneService.getMobilePhone(model);
    }
}
