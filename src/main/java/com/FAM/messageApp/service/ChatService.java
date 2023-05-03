package com.FAM.messageApp.service;

import com.FAM.messageApp.dao.ChatRepository;
import com.FAM.messageApp.model.Chat;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@AllArgsConstructor
@Service
public class ChatService {
    private final ChatRepository chatRepository;
    public List<Chat> getAllChats() {
        return chatRepository.findAll();
    }
}
