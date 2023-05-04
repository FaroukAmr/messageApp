package com.FAM.messageApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@CrossOrigin(originPatterns = "*", allowedHeaders = "*")
public class MessageAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(MessageAppApplication.class, args);
	}
}