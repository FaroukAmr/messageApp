package com.FAM.messageApp.controller;

import com.FAM.messageApp.model.Chat;
import com.FAM.messageApp.service.ChatService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/chat")
@AllArgsConstructor
public class ChatApiController {
    private final ChatService chatService;

    @GetMapping("/{id}")
    public List<Chat> getAllChatsByCustomerId(@PathVariable String id){
        return chatService.getAllChatsByCustomerId(id);
    }
    @GetMapping("/representative/{id}")
    public List<Chat> getAllChatsByRepresentativeId(@PathVariable String id){
        return chatService.getAllChatsByRepresentativeId(id);
    }
    @PostMapping
    public void createChat(@RequestBody Chat chat){
        chatService.createChat(chat);
    }
    @DeleteMapping("/{id}")
    public void deleteChatById(@PathVariable String id){
        chatService.deleteChatById(id);

    }
    @DeleteMapping("/user/{id}")
    public void deleteChatByUserId(@PathVariable String id){
        chatService.deleteChatById(id);
    }
}