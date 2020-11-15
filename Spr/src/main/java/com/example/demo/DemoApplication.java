package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

//Pulls on component scanning, autoconfig, and property support
public class DemoApplication {

	public static void main(String[] args) {

	    SpringApplication.run(DemoApplication.class, args);
	}

}
