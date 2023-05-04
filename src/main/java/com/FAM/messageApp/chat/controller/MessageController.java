package com.FAM.messageApp.chat.controller;

import com.FAM.messageApp.chat.model.ChatSession;
import com.FAM.messageApp.chat.model.ChatSessionService;
import com.FAM.messageApp.chat.model.MessageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(originPatterns = "*", allowedHeaders = "*")
public class MessageController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private ChatSessionService chatSessionService;
    @MessageMapping("/chat/{chatID}")
    public void sendMessage(@DestinationVariable String chatID, MessageModel message) {
        System.out.println("handling send message: " + message + " to: " + chatID);
        //Authenticate here
        String userName = message.getFromUser();
        //here we check if the user can send to the chat Session
        ChatSession chatSession = chatSessionService.findByChatId(chatID);
        boolean userIsInSession = chatSession.containsUser(userName);
//        boolean isExists = UserStorage.getInstance().getUsers().contains(chatID);
        boolean isExists = true;
        System.out.println("isExists " + isExists + "userIsInSession" + userIsInSession);
        //*****
        if (isExists && userIsInSession) {
            System.out.println("Sending message to the chat");
            simpMessagingTemplate.convertAndSend("/topic/chat/" + chatID, message);
        }
        //save to database Here
    }
}
