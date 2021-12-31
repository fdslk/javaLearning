package com.example.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.junit.jupiter.MockServerExtension;
import org.mockserver.junit.jupiter.MockServerSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(MockServerExtension.class)
@MockServerSettings(ports = {9090})
public class ApiSetup {
    @Autowired
    protected WebTestClient testClient;

    protected ClientAndServer mockClient;

    @BeforeEach
    public void InitMockClientAndService(ClientAndServer clientAndServer){
        this.mockClient = clientAndServer;
        this.mockClient.reset();
    }
}
