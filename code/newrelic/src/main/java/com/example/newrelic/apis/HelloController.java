package com.example.newrelic.apis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
public class HelloController {
    @GetMapping("/hello")
    public String hello(HttpServletRequest request){
        log.debug(String.format("request Id: %s", request.getRequestedSessionId()));
        return "hello!";
    }
}
