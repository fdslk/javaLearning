package com.fang;

import org.apache.http.client.methods.CloseableHttpResponse;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;


/**
 * @author fangzengqiang
 * @version V1.0
 * @Title: SaveMobilePhoneTest
 * @Package
 * @Description: TODO
 * @date
 */
public class SaveMobilePhoneTest {
    private CloseableHttpClient client;
    private CloseableHttpResponse response;
    @BeforeClass
    public void init(){
        client = HttpClients.createDefault();
    }

    @AfterClass
    public void clear(){
        try{
            response.close();
            client.close();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private String sendHttPostRequest(CloseableHttpClient client, String stringEntity){
        String result = null;
        try {
            HttpPost httpPost = new HttpPost("http://localhost:8080/mobilePhone");
            httpPost.setEntity(new StringEntity(stringEntity, ContentType.APPLICATION_JSON));
            response = client.execute(httpPost);
            result = EntityUtils.toString(response.getEntity());
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return result;
    }
}
