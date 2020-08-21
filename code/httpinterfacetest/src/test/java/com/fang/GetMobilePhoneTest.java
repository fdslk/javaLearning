package com.fang;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.URI;

/**
 * @author bestpay
 * @version V1.0
 * @Title: GetMobilePhoneTest
 * @Package
 * @Description: TODO
 * @date
 */
public class GetMobilePhoneTest {
    private CloseableHttpClient client;
    private CloseableHttpResponse response;

    @BeforeClass
    public void init(){
        client = HttpClients.createDefault();
    }
    @Test
    public void testCase1(){
        Assert.assertEquals(sendHttpGetRequest(client, "iphone 6S"),"{\"brand\":\"Apple\",\"model\":\"iphone 6S\",\"os\":\"IOS\"}");
    }
    @Test
    public void testCase2(){
        Assert.assertEquals(sendHttpGetRequest(client, ""), "");
    }

    private String sendHttpGetRequest(CloseableHttpClient client, String model) {
        String result = null;
        try{
            URI uri = new URIBuilder().setScheme("http").setHost("localhost").setPort(8080).setPath("/mobilePhone").addParameter("model", model).build();
            response = client.execute(new HttpGet(uri));
            result = EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
