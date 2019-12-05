package ru.bookstore.domain;

import javax.persistence.*;

/**
 * Created by Rubanov.Maksim on 03.12.2019.
 */
@Entity
@Table(name="\"user\"")
public class User {

    @Id
    @GeneratedValue(generator = "user_id_seq")
//    @SequenceGenerator(name = "user_id_seq", sequenceName = "user_id_seq", allocationSize =1)
    @Column(name = "USER_ID")
    private long userId;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "ENCRYTED_PASSWORD")
    private String encrytedPassword;

    @Column(name = "ENABLED")
    private boolean enabled = true;

    public User() {
    }


    public User(long userId, String userName, String encrytedPassword, boolean enabled) {
        this.userId = userId;
        this.userName = userName;
        this.encrytedPassword = encrytedPassword;
        this.enabled = enabled;
    }


    public long getUserId() {
        return userId;
    }


    public void setUserId(long userId) {
        this.userId = userId;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEncrytedPassword() {
        return encrytedPassword;
    }


    public void setEncrytedPassword(String encrytedPassword) {
        this.encrytedPassword = encrytedPassword;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return this.userName + "/" + this.encrytedPassword;
    }


}