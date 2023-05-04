package com.FAM.messageApp.controller;

import com.FAM.messageApp.model.Message;
import com.FAM.messageApp.service.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/message")
@AllArgsConstructor
public class MessageApiController {
    private final MessageService messageService;
    @GetMapping("/{chatId}")
    public List<Message> getMessageByChatId(@PathVariable String chatId){
        return messageService.getAllMessagesForChat(chatId);
    }
    @PostMapping
    public void postMessage(@RequestBody Message message){
        messageService.createNewMessage(message);
    }
    @DeleteMapping("/{chatId}")
    public void deleteMessages(@PathVariable String chatId){
        messageService.deleteMessagesByChatId(chatId);
    }
}