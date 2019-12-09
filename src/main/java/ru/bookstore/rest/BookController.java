package ru.bookstore.rest;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bookstore.domain.Book;
import ru.bookstore.repositories.BookRepository;
import ru.bookstore.repositories.PublishingHouseRepository;

import java.util.List;


@Api(tags = "BookController", description = "Контроллер для работы с книгами")
@RestController
public class BookController {

    private BookRepository bookRepository;

    private PublishingHouseRepository pblishingHouseRepository;

    @Autowired
    public BookController(BookRepository bookRepository, PublishingHouseRepository pblishingHouseRepository) {
        this.bookRepository = bookRepository;
        this.pblishingHouseRepository = pblishingHouseRepository;

    }

    @ApiOperation(value = "Метод возвращает список книг", response = List.class, tags = "getBookList")
    @GetMapping("/books/list")
    public ResponseEntity<List<Book>> getBookList() {
        return ResponseEntity.ok().body(bookRepository.findAll());
    }


    @ApiOperation(value = "Метод для добавления книги в БД", response = Book.class, tags = "addBook")
    @PostMapping("/books/new")
    public ResponseEntity<Book> addBook(@RequestBody(required = true) Book book) {
        book.setId(null);
        book = bookRepository.save(book);
        return ResponseEntity.ok().body(book);
    }


    @ApiOperation(value = "Метод редактирования информации о книги", response = Book.class, tags = "updateBook")
    @PutMapping("/books/update/{bookId}")
    public ResponseEntity<Book> updateBook(@PathVariable("bookId") long bookId, @RequestBody(required = true) Book book) {
        book.setId(bookId);
        book = bookRepository.save(book);
        return ResponseEntity.ok().body(book);
    }

    @ApiOperation(value = "Метод удаления книги", tags = "deleteBook")
    @DeleteMapping("/books/delete/{bookId}")
    public ResponseEntity deleteBook(@PathVariable("bookId") long bookId) {
        bookRepository.deleteById(bookId);
        return ResponseEntity.ok().build();
    }

}
