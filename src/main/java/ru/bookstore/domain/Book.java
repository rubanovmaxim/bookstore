package ru.bookstore.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
//import org.hibernate.dialect.PostgreSQL10Dialect

@Entity
@Table(name="book")
public class Book {
    @Id
    @GeneratedValue(generator = "book_id_seq")
    @Column(name = "ID")
    private Long id;


    @JsonProperty("p_house_id")
    @Column(name = "P_HOUSE_ID")
    private Long pPouseId;


    @JsonProperty("name")
    @Column(name = "NAME")
    private String name;

    @JsonProperty("author")
    @Column(name = "AUTHOR")
    private String author;

    @JsonProperty("price")
    @Column(name = "PRICE")
    private double price;



    public Book() {

    }

    public Book(Long pPouseId, String name, String author, double price) {
        this.pPouseId = pPouseId;
        this.name = name;
        this.author = author;
        this.price = price;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
