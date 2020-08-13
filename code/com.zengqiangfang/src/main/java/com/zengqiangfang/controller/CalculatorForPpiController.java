package com.zengqiangfang.controller;

import com.zengqiangfang.service.CalculatorForPpiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author bestpay
 * @version V1.0
 * @Title: CalculatorForPpiController
 * @Package
 * @Description: TODO
 * @date
 */
@RestController
@Slf4j
public class CalculatorForPpiController {
    @Resource
    private CalculatorForPpiService calculatorForPpiService;

    @RequestMapping("/calculate")
    public String calculate(@RequestParam("width") int width, @RequestParam("height") int height, @RequestParam("size") int size){
        try {
            long result = calculatorForPpiService.calculate(width, height, size);
            log.info("=====================>result:" + result);
            log.info("=====================>param:{} %f, %f, %f" + width, height, size);
            return "{\"PPI\":" + result + "}";
        }catch (Exception e){
            e.printStackTrace();
            log.info("==============>exception{}" + e.getMessage());
            return "{\"PPI\":" + e.getMessage() + "}";
        }

    }
}
