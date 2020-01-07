package ru.bookstore.domain;

import javax.persistence.*;

/**
 * Created by Rubanov.Maksim on 03.12.2019.
 */
@Entity
@Table(name="\"users\"")
public class User {

    @Id
    @GeneratedValue(generator = "user_id_seq")
//    @SequenceGenerator(name = "user_id_seq", sequenceName = "user_id_seq", allocationSize =1)
    @Column(name = "id")
    private long Id;

    @Column(name = "username")
    private String userName;

    @Column(name = "e_mail")
    private String eMail;

    @Column(name = "phone")
    private String phone;

    @Column(name = "password")
    private String encrytedPassword;

    @Column(name = "ENABLED")
    private boolean enabled = true;


    public User() {
    }

    public User(String userName, String eMail, String phone, String encrytedPassword, boolean enabled) {
        this.userName = userName;
        this.eMail = eMail;
        this.phone = phone;
        this.encrytedPassword = encrytedPassword;
        this.enabled = enabled;
    }

    public long getUserId() {
        return Id;
    }

    public void setUserId(long userId) {
        this.Id = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEMail() {
        return eMail;
    }

    public void setEMail(String eMail) {
        this.eMail = eMail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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