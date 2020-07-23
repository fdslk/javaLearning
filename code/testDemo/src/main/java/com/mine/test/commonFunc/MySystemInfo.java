package com.mine.test.commonFunc;

import com.vimalselvam.testng.SystemInfo;
import org.testng.collections.Maps;

import java.util.Map;

/**
 * @Classname MySystemInfo
 * @Description TODO
 * @Date 2020/7/23 18:21
 * @Created by Administrator
 */
public class MySystemInfo implements SystemInfo {
    @Override
    public Map<String, String> getSystemInfo(){
        Map<String, String> systemInfo = Maps.newHashMap();
        systemInfo.put("Test Env", "46");
        systemInfo.put("Browser", "chrome");
        systemInfo.put("operator", "fzq");
        return systemInfo;
    }
}
