package com.FAM.messageApp.service;

import com.FAM.messageApp.dao.ChatRepository;
import com.FAM.messageApp.model.Chat;
import com.FAM.messageApp.model.Message;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.xml.datatype.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ChatService {
    private final ChatRepository chatRepository;

    public void createChat(Chat chat){
        chatRepository.save(chat);
    }

    public Chat createChat(String customerId, String representativeId){
        Chat chat = new Chat(customerId,representativeId);
        return chatRepository.save(chat);
    }

    public Chat getChatById(String chatId){
        Optional<Chat> chat = chatRepository.findChatById(chatId);
        if(chat.isPresent()){
            return chat.get();
        }
        throw new IllegalStateException("Not found");
    }

    public List<Chat> getAllChatsByUserId(String id){
        Optional<List<Chat>> optionalChat = chatRepository.findChatByCustomerIdOrRepresentativeId(id,id);
        if (optionalChat.isPresent()){
            return optionalChat.get();
        }
        throw new IllegalStateException("Not found");

    }
    public List<Chat> getAllChatsByCustomerId(String id) {
        Optional<List<Chat>> optionalChat = chatRepository.findChatByCustomerId(id);
        if (optionalChat.isPresent()){
            return optionalChat.get();
        }
        throw new IllegalStateException("Not found");
    }

    public List<Chat> getAllChatsByRepresentativeId(String id) {
        Optional<List<Chat>> optionalChat = chatRepository.findChatByRepresentativeId(id);
        if (optionalChat.isPresent()){
            return optionalChat.get();
        }
        throw new IllegalStateException("Not found");
    }

    public void deleteChatById(String id){
        chatRepository.deleteChatById(id);
    }

    public void deleteChatByUserId(String id){
        chatRepository.deleteChatsByCustomerId(id);
    }

    public void disableChatById(String chatId) {
        Chat chat = getChatById(chatId);
        chat.setActive(false);
        chatRepository.save(chat);
    }
}