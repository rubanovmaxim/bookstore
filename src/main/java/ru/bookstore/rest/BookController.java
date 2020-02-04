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
import ru.bookstore.domain.*;
import ru.bookstore.repositories.BookGenriesRepository;
import ru.bookstore.repositories.BookRepository;
import ru.bookstore.repositories.PublishingHouseRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Api(tags = "BookController", description = "Контроллер для работы с книгами")
@RestController
public class BookController {



    private BookRepository bookRepository;

    private BookGenriesRepository bookGenriesRepository;

    private GenreController genreController;

    private PublishingHouseRepository pblishingHouseRepository;

    private PublishingHouseController publishingHouseController;

    @Autowired
    public BookController(BookRepository bookRepository, BookGenriesRepository bookGenriesRepository, GenreController genreController,
                          PublishingHouseRepository pblishingHouseRepository, PublishingHouseController publishingHouseController) {
        this.bookRepository = bookRepository;
        this.bookGenriesRepository = bookGenriesRepository;
        this.genreController = genreController;
        this.pblishingHouseRepository = pblishingHouseRepository;
        this.publishingHouseController = publishingHouseController;
    }

    @ApiOperation(value = "Метод возвращает карточку книги", response = BookCard.class, tags = "getBookCard")
    @GetMapping("/book/bookcard/{id}")
    public ResponseEntity<BookCard> getBookCard(@PathVariable("id") long id) {
        Optional<Book> bookOpt = bookRepository.findById(id);
        if (!bookOpt.isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        Book book = bookOpt.get();
        BookCard bookCard = new BookCard();
        bookCard.setBook(book);

        List<BookGenries> bookGenries = bookGenriesRepository.findAllByBookId(id);
        List<Long> genreIds = bookGenries.stream()
                .map(bg -> bg.getGenreId())
                .collect(Collectors.toList());

        if (genreIds.size()>0){
            List<Genre> genre = genreController.getGenresByIds(genreIds).getBody();
            bookCard.setGenre(genre);
        }

        PublishingHouse pHouse = publishingHouseController.getPublishingHouse(book.getPhouseId()).getBody();
        bookCard.setPublishingHouse(pHouse);

        return ResponseEntity.ok().body(bookCard);
    }


    @ApiOperation(value = "Метод возвращает список книг", response = List.class, tags = "getBookList")
    @GetMapping("/books/list")
    public ResponseEntity<List<Book>> getBookList() {
        return ResponseEntity.ok().body(bookRepository.findAll());
    }


    @ApiOperation(value = "Метод для добавления книги в БД", response = Book.class, tags = "addBook")
    @PostMapping("/books/new")
    public ResponseEntity addBook(@RequestBody(required = true) Book book) {
        Optional<PublishingHouse> publishingHouse = pblishingHouseRepository.findById(book.getPhouseId());
        if (!publishingHouse.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There are no publishing house with id="
                    + book.getPhouseId() + ".Please check 'p_house_id' field.");
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
