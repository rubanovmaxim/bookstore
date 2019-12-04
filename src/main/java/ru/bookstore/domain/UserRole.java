package ru.bookstore.domain;

import javax.persistence.*;

/**
 * Created by Rubanov.Maksim on 04.12.2019.
 */
@Entity
@Table(name="user_role")
public class UserRole {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    @Column(name = "ID")
    private long Id;

    @Column(name = "USER_ID")
    private long userId;

    @Column(name = "ROLE_ID")
    private long roleId;

    public UserRole() {
    }

    public UserRole(long id, long userId, long roleId) {
        Id = id;
        this.userId = userId;
        this.roleId = roleId;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }
}