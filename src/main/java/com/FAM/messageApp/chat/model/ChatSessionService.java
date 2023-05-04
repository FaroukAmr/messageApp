package com.FAM.messageApp.chat.model;

import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class ChatSessionService {

    private Map<String,ChatSession> sessionMap ;


    // Make the constructor private to prevent instantiation from outside
    private ChatSessionService() {
        sessionMap = new HashMap<>();
    }

    // Static factory method to return the singleton instance
    public static ChatSessionService getInstance() {
        return ChatSessionServiceHolder.INSTANCE;
    }

    public List<ChatSession> getChatSessionsOfUser(String userName) {
        List<ChatSession> retList = new ArrayList<>();
        for(ChatSession chatSession : sessionMap.values())
        {
            if(chatSession.containsUser(userName))
            {
                retList.add(chatSession);
            }
        }

        return retList;
    }

    private static class ChatSessionServiceHolder {
        private static final ChatSessionService INSTANCE = new ChatSessionService();
    }

    public void save(ChatSession chatSession) {
        sessionMap.put(chatSession.getChatId(),chatSession);
    }

    public ChatSession findByChatId(String chatId) {
        return sessionMap.get(chatId);
    }

    public ChatSession createChatSession(String username1, String username2) {
        // generate a unique chat ID
        String chatId = UUID.randomUUID().toString();

        // create a new ChatSession instance
        ChatSession chatSession = new ChatSession(chatId, username1, username2);

        // save the new ChatSession instance
        save(chatSession);

        return chatSession;
    }

    public ArrayList<ChatSession> getAllChatSessions() {
        return new ArrayList<>(sessionMap.values());
    }

}
