package com.fang;

/**
 * @author fangzengqiang
 * @version V1.0
 * @Title: Work
 * @Package
 * @Description: TODO
 * @date
 */
public class Work {
    private Weather weather;

    public int getWorkTime(){
        String result = weather.getWeather();

        switch (result){
            case "晴":
                return 6;
            case "多云":
                return 7;
            case "阴":
                return 8;
            default:
                return 5;
        }
    }
}
