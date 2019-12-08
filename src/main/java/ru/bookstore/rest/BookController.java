package ru.bookstore.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bookstore.domain.Book;
import ru.bookstore.repositories.BookRepository;
import ru.bookstore.repositories.PublishingHouseRepository;

import java.util.List;

@RestController
public class BookController {

    private BookRepository bookRepository;

    private PublishingHouseRepository pblishingHouseRepository;

    @Autowired
    public BookController(BookRepository bookRepository, PublishingHouseRepository pblishingHouseRepository) {
        this.bookRepository = bookRepository;
        this.pblishingHouseRepository = pblishingHouseRepository;

    }


    @GetMapping("/books/list")
    public ResponseEntity<List> getBookList() {
        return ResponseEntity.ok().body(bookRepository.findAll());
    }


    @PostMapping("/books/new")
    public ResponseEntity<Book> addBook(@RequestBody(required = true) Book book) {
        book.setId(null);
        book = bookRepository.save(book);
        return ResponseEntity.ok().body(book);
    }


    @PutMapping("/books/update/{bookId}")
    public ResponseEntity<Book> updateBook(@PathVariable("bookId") long bookId, @RequestBody(required = true) Book book) {
        book.setId(bookId);
        book = bookRepository.save(book);
        return ResponseEntity.ok().body(book);
    }


    @DeleteMapping("/books/delete/{bookId}")
    public ResponseEntity deleteBook(@PathVariable("bookId") long bookId) {
        bookRepository.deleteById(bookId);
        return ResponseEntity.ok().build();
    }

}
