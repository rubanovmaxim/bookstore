package ru.bookstore.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Rubanov.Maksim on 03.01.2020.
 */

@Entity
@Table(name="genre")
public class Genre implements Serializable {

    @Id
    @GeneratedValue(generator = "genre_id_seq")
    @Column(name = "ID")
    private Long id;


    @JsonProperty("name")
    @Column(name = "NAME")
    private String name;

    public Genre() {

    }

    public Genre(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

