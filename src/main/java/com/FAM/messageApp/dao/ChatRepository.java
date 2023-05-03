package com.FAM.messageApp.dao;

import com.FAM.messageApp.model.Chat;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ChatRepository extends MongoRepository<Chat,String> {
    Optional<Chat>  findChatById(String chatId);
    Optional<Chat>  findChatByRepresentativeId(String representativeId);
    Optional<Chat>  findChatByCustomerId(String customerId);
}
