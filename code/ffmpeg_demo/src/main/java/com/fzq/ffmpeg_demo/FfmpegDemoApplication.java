package com.fzq.ffmpeg_demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FfmpegDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(FfmpegDemoApplication.class, args);
        System.out.println("hello ffmpeg");
    }

}
