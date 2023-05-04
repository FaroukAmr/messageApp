package com.FAM.messageApp.service;

import com.FAM.messageApp.dao.MessageRepository;
import com.FAM.messageApp.model.Message;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class MessageService {
    private final MessageRepository messageRepository;
    public List<Message> getAllMessagesForChat(String chatId) {
        Optional<List<Message>> optionalMessage = messageRepository.findMessageByChatId(chatId);

        if (optionalMessage.isPresent()){
            return optionalMessage.get();
        }
        throw new IllegalStateException("Not found");
    }

    public void createNewMessage(Message message){
        System.out.println(message);
        messageRepository.save(message);
    }

}
