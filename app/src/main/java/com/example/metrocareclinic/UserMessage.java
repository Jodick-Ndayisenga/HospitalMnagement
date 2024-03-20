package com.example.metrocareclinic;

import androidx.annotation.NonNull;

public class UserMessage {
    String id, userEmail, userMessage, UserName;
    public UserMessage(){};

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public String getUserName() {
        return UserName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }
}
