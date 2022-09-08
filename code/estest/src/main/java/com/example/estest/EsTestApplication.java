package com.example.estest;

//import org.apache.log4j.BasicConfigurator;
//import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EsTestApplication {
//	private final static Logger logger = Logger.getLogger(EsTestApplication.class);

	public static void main(String[] args) {
//		BasicConfigurator.configure();
		SpringApplication.run(EsTestApplication.class, args);
	}
}
