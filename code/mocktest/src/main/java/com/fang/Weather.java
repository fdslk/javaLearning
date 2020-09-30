package com.fang;

/**
 * @author fangzengqiang
 * @version V1.0
 * @Title: Weather
 * @Package
 * @Description: TODO
 * @date
 */
public class Weather {
    public String getWeather() {
        double tmp = Math.random();
        if(tmp < 0.25){
            return "晴";
        }else if(0.25 <= tmp && tmp < 0.5){
            return "多云";
        }else if(0.5 <= tmp && tmp < 0.75){
            return "阴";
        }else{
            return "雨";
        }
    }
}
