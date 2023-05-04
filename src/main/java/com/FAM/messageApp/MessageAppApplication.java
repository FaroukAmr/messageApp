package com.FAM.messageApp;

import com.FAM.messageApp.dao.ChatRepository;
import com.FAM.messageApp.model.Chat;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Arrays;

@SpringBootApplication
@CrossOrigin(originPatterns = "*", allowedHeaders = "*")
public class MessageAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(MessageAppApplication.class, args);
	}

	// They are to test inputting data inside mongoDB
	/*
	// Insert Chats Records in Database
	@Bean
	CommandLineRunner runner1(ChatRepository chatRepository){
		return args -> {
			Chat chat1 = new Chat("customer1", "rep1", "Chat 1", true);
			 Chat chat2 = new Chat("customer2", "rep2", "Chat 2", true);
			 Chat chat3 = new Chat("customer3", "rep3", "Chat 3", false);
			System.out.println("insert chat... "+chat1 );
			chatRepository.saveAll(Arrays.asList(chat1, chat2, chat3));
		};
	}
	// Delete Chat Record using customerId
	@Bean
	CommandLineRunner runner2(ChatRepository chatRepository){
		return args -> {
			String customerId =  "customer1";
			System.out.println("Delete chat with ID... "+customerId );
			chatRepository.deleteChatByCustomerId(customerId);
		};
	}
	 */
}