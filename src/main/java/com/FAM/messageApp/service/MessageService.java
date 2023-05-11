package com.FAM.messageApp.service;

import com.FAM.messageApp.dao.MessageRepository;
import com.FAM.messageApp.model.Message;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@AllArgsConstructor
@Service
@Slf4j
public class MessageService {
    private final MessageRepository messageRepository;
    private static String HASH_KEY = "message_cache";
    @Autowired
    private RedisTemplate redisTemplate;
    public List<Message> getAllMessagesForChat(String chatId) {
        // look in the cache
        HashOperations<String, String, List<Message>> hash = redisTemplate.opsForHash();
        List<Message> messages = hash.get(HASH_KEY,chatId);
        if(messages!=null) return messages;
        Optional<List<Message>> optionalMessage = messageRepository.findMessageByChatId(chatId);

        if (optionalMessage.isPresent()){
            // retrieve from mongoDB and add to the cache
            messages = optionalMessage.get();
            hash.put("chat_cache",chatId, messages);
            redisTemplate.expire(HASH_KEY + ":" + chatId, 10, TimeUnit.MINUTES);
            log.info("messages are retrived from the DB and added to the cache");
            return messages;
        }
        throw new IllegalStateException("Not found");
    }

    public void saveMessage(Message message){
        messageRepository.save(message);
        // save to the cache
        String chatId = message.getChatId();
        HashOperations<String, String, List<Message>> hash = redisTemplate.opsForHash();
        List<Message> messages = hash.get(HASH_KEY,chatId);
        if(messages==null) messages = new ArrayList<Message>();
        messages.add(message);
        hash.put("chat_cache",chatId, messages);
        redisTemplate.expire(HASH_KEY + ":" + chatId, 10, TimeUnit.MINUTES);
        log.info("Message is saved to the cache");
    }

    public void deleteMessagesByChatId(String chatId){
        messageRepository.deleteMessagesByChatId(chatId);
        // delete from the cache
        HashOperations<String, String, List<Message>> hash = redisTemplate.opsForHash();
        hash.delete("message_cache", chatId);
        log.info("messages are deleted from the cache");
    }

}