package com.example.tiktaktoe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
		"com.example.tiktaktoe",
		"com.example.tiktaktoe.repository",
		"com.example.tiktaktoe.model",
		"com.example.tiktaktoe.service.impl",
})
public class TiktaktoeApplication {

	public static void main(String[] args) {
		SpringApplication.run(TiktaktoeApplication.class, args);
	}

}
