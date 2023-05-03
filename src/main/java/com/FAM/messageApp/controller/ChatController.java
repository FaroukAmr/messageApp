package com.FAM.messageApp.controller;

import com.FAM.messageApp.model.Chat;
import com.FAM.messageApp.service.ChatService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/chat")
@AllArgsConstructor
public class ChatController {
    private final ChatService chatService;
    @GetMapping
    public List<Chat> getAllChats(){
        return chatService.getAllChats();
    }
}
