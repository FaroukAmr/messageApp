package com.FAM.messageApp;

import com.FAM.messageApp.dao.ChatRepository;
import com.FAM.messageApp.model.Chat;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;

import javax.management.Query;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;


@SpringBootApplication
public class MessageAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(MessageAppApplication.class, args);
	}


	@Bean
	CommandLineRunner runner(ChatRepository repository, MongoTemplate mongoTemplate){
		return args -> {
			Chat chat = new Chat("123","123","Helo", LocalDateTime.now());
			String id = "6452b813e99b970b661c04cc";
			repository.findChatById(id)
					.ifPresentOrElse(s->{
						System.out.println(s);
					},()->{
						System.out.println("No");
					});
		};
	}
}
