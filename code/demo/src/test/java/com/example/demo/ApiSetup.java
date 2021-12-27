package com.example.demo;

import org.junit.jupiter.api.BeforeEach;
import org.mockserver.integration.ClientAndServer;

public class ApiSetup {
    protected ClientAndServer mockClient;

    @BeforeEach
    public void InitMockClientAndService(){
        this.mockClient = new ClientAndServer(9999);
    }
}
