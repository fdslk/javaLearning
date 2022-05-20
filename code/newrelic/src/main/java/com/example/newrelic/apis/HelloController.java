package com.example.newrelic.apis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class HelloController {
    @GetMapping("/hello")
    public String hello(HttpServletRequest request){
        Logger logger = LoggerFactory.getLogger(HelloController.class);
        logger.info(String.format("request Id: %s", request.getHeaderNames()));
        return "hello!";
    }
}
