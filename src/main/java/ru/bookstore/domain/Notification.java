package ru.bookstore.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Rubanov.Maksim on 04.12.2019.
 */
public class Notification {


    @JsonProperty("username")
    private String userName;

    @JsonProperty("e_mail")
    private String eMail;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("message")
    private String message;


    public String getUserName() {
        return userName;
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

    @JsonProperty("e_mail")
    public void setEMail(String eMail) {
        this.eMail = eMail;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
