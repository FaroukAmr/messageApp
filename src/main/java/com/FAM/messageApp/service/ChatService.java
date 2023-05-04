package com.FAM.messageApp.service;

import com.FAM.messageApp.dao.ChatRepository;
import com.FAM.messageApp.model.Chat;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ChatService {
    private final ChatRepository chatRepository;

    public void createChat(Chat chat){
        chatRepository.save(chat);
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
}
