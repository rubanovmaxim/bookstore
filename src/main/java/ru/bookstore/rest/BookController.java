package ru.bookstore.rest;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.bookstore.domain.Book;
import ru.bookstore.domain.PublishingHouse;
import ru.bookstore.repositories.BookRepository;
import ru.bookstore.repositories.PublishingHouseRepository;

import java.util.List;
import java.util.Optional;


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
    public ResponseEntity addBook(@RequestBody(required = true) Book book) {
        Optional<PublishingHouse> publishingHouse = pblishingHouseRepository.findById(book.getpPouseId());
        if (!publishingHouse.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There are no publishing house with id="
                    + book.getpPouseId() + ".Please check 'p_house_id' field.");
        }
        book.setId(null);
        book = bookRepository.save(book);
        return ResponseEntity.ok().body(book);
    }


    @ApiOperation(value = "Метод редактирования информации о книгию Нужно передать JSON Book", response = Book.class, tags = "updateBook")
    @PutMapping("/books/update/{bookId}")
    public ResponseEntity<Book> updateBook(@PathVariable("bookId") long bookId, @RequestBody(required = true) Book book) {
        book.setId(bookId);
        book = bookRepository.save(book);
        return ResponseEntity.ok().body(book);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ObjectNotFoundException.class, ConstraintViolationException.class})
    @ApiOperation(value = "Метод удаления книг(и).Нужно передать JSON массив из id книг, удаляемых из БД", tags = "deleteBook")
    @DeleteMapping("/books/delete")
    public ResponseEntity deleteBook(@RequestBody(required = true) List<Long> bookIds) {
        if (bookIds != null) {
            for (Long bookId : bookIds) {
                bookRepository.deleteById(bookId);
            }
        }
        return ResponseEntity.ok().build();
    }

}
