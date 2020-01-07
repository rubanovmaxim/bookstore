package ru.bookstore.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Rubanov.Maksim on 04.12.2019.
 */
public class Notification {

    @JsonProperty(value = "user_id", required = true)
    private long userId;

    @JsonProperty(value = "message", required = true)
    private String message;


    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
}
