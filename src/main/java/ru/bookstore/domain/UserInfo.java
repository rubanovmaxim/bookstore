package ru.bookstore.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Rubanov.Maksim on 04.12.2019.
 */
public class UserInfo {


    @JsonProperty("username")
    private String userName;

    @JsonProperty("password")
    private String password;

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
