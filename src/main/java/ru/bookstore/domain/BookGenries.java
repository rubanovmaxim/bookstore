package ru.bookstore.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

/**
 * Created by Rubanov.Maksim on 03.01.2020.
 */

@Entity
@Table(name="book_genres")
public class BookGenries {

    @Id
    @GeneratedValue(generator = "book_genres_id_seq")
    @Column(name = "ID")
    private Long id;


    @Column(name = "BOOK_ID")
    private Long bookId;

    @Column(name = "GENRE_ID")
    private Long genreId;


    public BookGenries() {

    }

    public BookGenries(Long bookId, Long genreId) {
        this.bookId = bookId;
        this.genreId = genreId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Long getGenreId() {
        return genreId;
    }

    public void setGenreId(Long genreId) {
        this.genreId = genreId;
    }
}