package com.FAM.messageApp.controller;


import com.FAM.messageApp.dao.ChatRepository;
import com.FAM.messageApp.exception.ResourceNotFoundException;
import com.FAM.messageApp.model.Chat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api")
public class ChatController {

    @Autowired
    ChatRepository chatRepository;

    @PostMapping("/chats")
    public Chat addChat(@RequestBody Chat chat){
        chatRepository.save(chat);
        return chat;

    }

    @GetMapping("/chats/{id}")
    public ResponseEntity<Chat> findById(@PathVariable("id") Integer chatId){
        Chat chat=chatRepository.findById(chatId).orElseThrow(
                () -> new ResourceNotFoundException("Chat not found" + chatId));
        return ResponseEntity.ok().body(chat);
    }



    @GetMapping("/chats")
    public List<Chat> getChats(){

        return chatRepository.findAll();
    }

    @PutMapping("chats/{id}")
    public ResponseEntity<Chat> updateChat(@PathVariable(value = "id") Integer chatId,
                                                  @RequestBody Chat chatDetails) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new ResourceNotFoundException("Chat not found for this id :: " + chatId));
        chat.setTitle(chatDetails.getTitle());
        final Chat updatedChat = chatRepository.save(chat);
        return ResponseEntity.ok(updatedChat);

    }

    @DeleteMapping("chats/{id}")
    public ResponseEntity<Void> deleteChat(@PathVariable(value = "id") Integer chatId) {
        Chat chat = chatRepository.findById(chatId).orElseThrow(
                () -> new ResourceNotFoundException("Chat not found::: " + chatId));
        chatRepository.delete(chat);
        return ResponseEntity.ok().build();
    }

}

