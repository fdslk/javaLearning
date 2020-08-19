package com.zengqiangfang.service;

import org.springframework.stereotype.Service;

/**
 * @author bestpay
 * @version V1.0
 * @Title: CalculatorForPpiService
 * @Package
 * @Description: TODO
 * @date
 */
@Service
public class CalculatorForPpiService {
    public long calculate(int width, int height, double size){
        if(width > 0 && height > 0 && size > 0){
            return Math.round(Math.pow((Math.pow(width, 2) + Math.pow(height, 2))/ size, 0.5));
        }
        return -1;
    }
}
