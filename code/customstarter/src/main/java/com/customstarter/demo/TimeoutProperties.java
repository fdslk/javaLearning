package com.customstarter.demo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@ConfigurationProperties(prefix = "timeout")
@Configuration
public class TimeoutProperties {
    private String test;
    private String staging;
    private String production;
}
