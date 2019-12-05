package ru.bookstore.domain;

import javax.persistence.*;
//import org.hibernate.dialect.PostgreSQL10Dialect

@Entity
@Table(name="book")
public class Book {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    @Column(name = "ID")
    private Long id;

    @Column(name = "P_HOUSE_ID")
    private Long pPouseId;


    @Column(name = "NAME")
    private String name;

    @Column(name = "AUTHOR")
    private String author;


    public Book() {

    }

    public Book(Long pPouseId, String name, String author) {
        this.pPouseId = pPouseId;
        this.name = name;
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getpPouseId() {
        return pPouseId;
    }

    public void setpPouseId(Long pPouseId) {
        this.pPouseId = pPouseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
