package ru.bookstore.domain;

import javax.persistence.*;

/**
 * Created by Rubanov.Maksim on 04.12.2019.
 */


@Entity
@Table(name="\"role\"")
public class Role {
    @Id
    @GeneratedValue(generator = "role_id_seq")
    @Column(name = "ROLE_ID")
    private Long roleId;

    @Column(name = "ROLE_NAME")
    private String roleName;


    public Role() {

    }

    public Role(Long roleId, String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
