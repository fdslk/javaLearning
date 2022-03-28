package com.customstarter.demo.helloservicestarter;

public class HelloServiceImpl implements HelloService {
    @Override
    public void Hello() {
        System.out.println("Hello from the default starter.");
    }
}
