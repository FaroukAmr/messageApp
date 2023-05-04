package com.FAM.messageApp.chat.controller;

import com.FAM.messageApp.chat.model.ChatSession;
import com.FAM.messageApp.chat.model.ChatSessionService;
import com.FAM.messageApp.chat.model.IntiateChatRequest;
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
public class IntiateChatController {

    @Autowired
    private ChatSessionService chatSessionService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;


    @PostMapping(path = "/intiateChat")
    @CrossOrigin(originPatterns = "*", allowedHeaders = "*")
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
        ChatSession chatSession;
        try {
            chatSession= chatSessionService.createChatSession(userName,matchedUser);
            System.out.println("Chat session created " + chatSession.getChatId());
            System.out.println(chatSessionService.findByChatId(chatSession.getChatId()));
            //we send to the matched user a message so they subscribe to the chatsession
            messagingTemplate.convertAndSend("/topic/user/" + matchedUser, chatSession.getChatId());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(chatSession.getChatId());
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
