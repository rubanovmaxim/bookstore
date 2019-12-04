package ru.bookstore.domain;

import javax.persistence.*;

/**
 * Created by Rubanov.Maksim on 04.12.2019.
 */


@Entity
@Table(name="\"role\"")
public class Role {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    @Column(name = "ROLE_ID")
    private Long roleId;

    @Column(name = "ROLE_NAME")
    private Long roleName;


    public Role() {

    }

    public Role(Long roleId, Long roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getRoleName() {
        return roleName;
    }

    public void setRoleName(Long roleName) {
        this.roleName = roleName;
    }
}
