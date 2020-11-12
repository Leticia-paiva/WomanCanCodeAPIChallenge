package com.example.demo.resources;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

@SpringBootApplication
@RequestMapping("/")
public class HomeResources {
	
	public static void main(String[] args) {
		SpringApplication.run(HomeResources.class, args);
	}

	@GetMapping
	public String getHello() {
		return "ta funcionando";
	}
}
