package ru.bookstore.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Rubanov.Maksim on 04.12.2019.
 */
public class UserInfo {


    @JsonProperty("username")
    private String userName;

    @JsonProperty("user_id")
    private long userId;

    @JsonProperty("e_mail")
    private String eMail;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("password")
    private String password;

    public String getUserName() {
        return userName;
    }

    public long getUserId() {
        return userId;
    }

    public String getEMail() {
        return eMail;
    }

    public String getPhone() {
        return phone;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @JsonProperty("e_mail")
    public void setEMail(String eMail) {
        this.eMail = eMail;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
