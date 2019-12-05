package ru.bookstore.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bookstore.repositories.BookRepository;
import ru.bookstore.repositories.PublishingHouseRepository;

import java.util.List;

@RestController
public class BookController {

    private BookRepository bookRepository;

    private PublishingHouseRepository pblishingHouseRepository;

    @Autowired
    public BookController(BookRepository bookRepository,PublishingHouseRepository pblishingHouseRepository) {
        this.bookRepository = bookRepository;
        this.pblishingHouseRepository = pblishingHouseRepository;

    }


    @GetMapping("/books/list")
    public ResponseEntity<List> getBookList() {
        return ResponseEntity.ok().body(bookRepository.findAll());
    }

    @GetMapping("/books/publishinghouse")
    public ResponseEntity<List> getPublishingHouseRepository() {
        return ResponseEntity.ok().body(pblishingHouseRepository.findAll());
    }

}
