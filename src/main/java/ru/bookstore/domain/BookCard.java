package ru.bookstore.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class BookCard {

    @JsonProperty("book")
    private Book book;

    @JsonProperty("genres")
    private List<Genre> genre;

    @JsonProperty("publishing_house")
    private PublishingHouse publishingHouse;

    public BookCard() {
    }

    public BookCard(Book book, List<Genre> genre, PublishingHouse publishingHouse) {
        this.book = book;
        this.genre = genre;
        this.publishingHouse = publishingHouse;
    }


    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public List<Genre> getGenre() {
        return genre;
    }

    public void setGenre(List<Genre> genre) {
        this.genre = genre;
    }

    public PublishingHouse getPublishingHouse() {
        return publishingHouse;
    }

    public void setPublishingHouse(PublishingHouse publishingHouse) {
        this.publishingHouse = publishingHouse;
    }
}
