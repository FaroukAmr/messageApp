package com.FAM.messageApp.model;

public class ChatSession {
    private String chatId;
    private String user1;
    private String user2;

    public ChatSession(String chatId,String user1, String user2) {
        this.chatId = chatId;
        this.user1 = user1;
        this.user2 = user2;
    }

    public String getChatId() {
        return chatId;
    }

    public String getUser1() {
        return user1;
    }

    public String getUser2() {
        return user2;
    }

    public boolean containsUser(String username) {
        return user1.equals(username) || user2.equals(username);
    }

    public String getOtherUser(String username) {
        if (user1.equals(username)) {
            return user2;
        } else if (user2.equals(username)) {
            return user1;
        } else {
            return null;
        }
    }
}
