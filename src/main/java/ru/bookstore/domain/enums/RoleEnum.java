package ru.bookstore.domain.enums;

/**
 * Created by Rubanov.Maksim on 04.12.2019.
 */
public enum RoleEnum {
    ADMIN(1),
    USER(2);

    private int roleId;
    private RoleEnum(int roleId){
        this.roleId = roleId;
    }

    public int getRoleId() {
        return roleId;
    }
}
