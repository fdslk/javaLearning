package com.fang;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.URI;

/**
 * @author fangzengqiang
 * @version V1.0
 * @Title: RAP2Test
 * @Package
 * @Description: TODO
 * @date
 */
public class RAP2Test {

    private CloseableHttpClient client;
    private CloseableHttpResponse response;

    @BeforeClass
    public void init(){
        client = HttpClients.createDefault();
    }

    @Test
    public void testCase1(){
        JSONObject expectedDate = new JSONObject().put("weather", "晴");
        JSONObject expected = new JSONObject().put("code", 0).put("msg", "Success").put("data", expectedDate);
        JSONObject actual = null;
        try {
            URI uri = new URIBuilder().setScheme("http").setHost("rap2api.taobao.org").
                    setPath("/app/mock/267703/weather").build();
            response = client.execute(new HttpGet(uri));
            actual = new JSONObject(EntityUtils.toString(response.getEntity()));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @AfterClass
    public void clear(){
        try{
            response.close();
            client.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
