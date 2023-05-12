package com.FAM.messageApp.controller;

import com.FAM.messageApp.model.Chat;
import com.FAM.messageApp.service.ChatService;
import com.FAM.messageApp.model.IntiateChatRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class InitiateChatController {

    @Autowired
    private ChatService chatSessionService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;


    @PostMapping(path = "/initiateChat")
    public ResponseEntity<String> intiateChat(@RequestBody IntiateChatRequest requestBody, HttpServletRequest request)
    {
        System.out.println("handling intiate chat request: " );
        System.out.println(requestBody);

        String userName = requestBody.getUserName();
        //we should authenticate the user here an that he doesn't have any active sessions
        //..

        //Here we should find the user that would be matched
        String matchedUser = findUser();

        //Here We should create the chat session
        Chat chatSession;
        try {
            chatSession= chatSessionService.createChat(userName,matchedUser);
            System.out.println("Chat session created " + chatSession.getId()+"  " +matchedUser);
            System.out.println(chatSessionService.getChatById(chatSession.getId()));
            //we send to the matched user a message, so they subscribe to the chatsession
            messagingTemplate.convertAndSend("/topic/user/" + matchedUser, chatSession.getId());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(chatSession.getId());
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create chat session: " + e.getMessage());
        }
    }

    private String findUser() {
        return "Mohamed";
    }
}