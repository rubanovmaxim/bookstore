package ru.bookstore.domain;

import javax.persistence.*;

/**
 * Created by Rubanov.Maksim on 04.12.2019.
 */


@Entity
@Table(name="authorities")
public class Authority {

    @Id
    @GeneratedValue(generator = "authority_id_seq")
    @Column(name = "id")
    private Long Id;

    @Column(name = "username")
    private String username;

    @Column(name = "authority")
    private String authority;


    public Authority() {

    }

    public Authority(String username, String authority) {
        this.username = username;
        this.authority = authority;
    }


    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        this.Id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
//    CREATE TABLE authorities (
//        username VARCHAR(50) NOT NULL,
//    authority VARCHAR(50) NOT NULL,
//    FOREIGN KEY (username) REFERENCES users(username)
//        );
