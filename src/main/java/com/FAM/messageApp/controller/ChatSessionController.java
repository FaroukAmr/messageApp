package com.FAM.messageApp.controller;

import com.FAM.messageApp.model.ChatSession;
import com.FAM.messageApp.service.ChatSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(originPatterns = "*", allowedHeaders = "*")
public class ChatSessionController {

    @Autowired
    private ChatSessionService chatSessionService;

    @GetMapping("/chatSessions")
    @CrossOrigin(originPatterns = "*", allowedHeaders = "*")
    public List<ChatSession> getAllChatSessions() {
        return new ArrayList<>(chatSessionService.getAllChatSessions());
    }

    @GetMapping("/chatSessions/{userName}")
    public List<ChatSession> getChatSessionsOfUser(@PathVariable String userName) {
        //have to authenticate first
        return new ArrayList<>(chatSessionService.getChatSessionsOfUser(userName));
    }
}
