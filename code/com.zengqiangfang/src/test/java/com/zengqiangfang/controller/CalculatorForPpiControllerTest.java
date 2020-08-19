package com.zengqiangfang.controller;

import com.zengqiangfang.App;
import com.zengqiangfang.service.CalculatorForPpiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * @author bestpay
 * @version V1.0
 * @Title: CalculatorForPpiControllerTest
 * @Package
 * @Description: TODO
 * @date
 */
@SpringBootTest(classes = App.class)
public class CalculatorForPpiControllerTest extends AbstractTestNGSpringContextTests {
    @Autowired
    private CalculatorForPpiController calculatorForPpiController;
    private MockMvc mockMvc;
    private String width;
    private String height;
    private String size;
    private RequestBuilder requestBuilder;

    @BeforeClass
    public void init(){
        mockMvc = MockMvcBuilders.standaloneSetup(calculatorForPpiController).build();
        width = "750";
        height = "1334";
        size = "4.7";
    }

    @Test
    public void test1(){
        sendRequest(width, height, size, "326");
    }
    @Test
    public void test2(){
        sendRequest("-1", height, size, "-1");
    }

    private void sendRequest(String width, String height, String size, String expected) {
        requestBuilder = MockMvcRequestBuilders.post("/calculate")
                .param("width", width).param("height", height).param("size", size);
        try{
            mockMvc.perform(requestBuilder).andExpect(MockMvcResultMatchers.jsonPath("PPI").value(expected));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}