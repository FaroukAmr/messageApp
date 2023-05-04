package com.FAM.messageApp.model;

public class MessageModel {

    private String message;
    private String fromUser;
    private String chatID;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    @Override
    public String toString() {
        return "MessageModel{" +
                "message='" + message + '\'' +
                ", fromUser='" + fromUser + '\'' +
                '}';
    }
}
