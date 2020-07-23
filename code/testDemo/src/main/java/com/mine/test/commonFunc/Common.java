package com.mine.test.commonFunc;

/**
 * @Classname common
 * @Description TODO
 * @Date 2020/7/23 16:38
 * @Created by Administrator
 */
public class Common {
    /**
     * 线程休眠
     * @param sleepSpan
     */
    public static void threadSleep(long sleepSpan){
        try {
            Thread.sleep(sleepSpan);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
