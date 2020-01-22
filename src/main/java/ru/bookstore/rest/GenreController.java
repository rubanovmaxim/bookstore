package ru.bookstore.rest;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.bookstore.domain.Genre;
import ru.bookstore.domain.PublishingHouse;
import ru.bookstore.repositories.GenreRepository;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Api(tags = "GenreController", description = "Контроллер для работы с издательствами")
@RestController
public class GenreController {

    private final static Logger LOGGER = LoggerFactory.getLogger(GenreController.class);

    private RestTemplate restTemplate = new RestTemplate();
    private GenreRepository genreRepository;

    @Autowired
    public GenreController(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }


    @ApiOperation(value = "Получение жанра по id", response = List.class, tags = "getGenre")
    @GetMapping("/genre/{id}")
    public ResponseEntity<Genre> getGenre(@PathVariable(name = "id") long id) {
        ResponseEntity<Genre> result;
        try {
            URI url = new URI("http://localhost:8083/genre/" + id);
            result = restTemplate.getForEntity(url, Genre.class);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
        return result;
    }



    @ApiOperation(value = "Получение жанров по нескольким id", response = List.class, tags = "getGenresByIds")
    @PostMapping("/genre/list/ids")
    public ResponseEntity<List<Genre>> getGenresByIds(@RequestBody(required = true) List<Long> ids) {
        ResponseEntity<List<Genre>> result;
        Class clazz = new ArrayList<Genre>().getClass();
        try {
            URI url = new URI("http://localhost:8083/genre/list/ids");
            result = restTemplate.postForEntity(url, ids, clazz);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
        return result;
    }



    @ApiOperation(value = "Получение списка жанров", response = List.class, tags = "getGenreList")
    @GetMapping("/genre/list")
    public ResponseEntity<List<Genre>> getGenreList() {
        ResponseEntity<List<Genre>> result;
        Class clazz = new ArrayList<Genre>().getClass();
        try {
            URI url = new URI("http://localhost:8083/genre/list");
            result = restTemplate.getForEntity(url, clazz);

        } catch (URISyntaxException e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
        return result;
    }


    @ApiOperation(value = "Добавление нового жанра", response = Genre.class, tags = "addGenre")
    @PostMapping("/genre/new")
    public ResponseEntity<Genre> addGenre(@RequestBody(required = true) Genre genre) {
        ResponseEntity<Genre> result;
        try {
            URI url = new URI("http://localhost:8083/genre/new");
            result = restTemplate.postForEntity(url, genre, Genre.class);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
        return result;
    }


    @ApiOperation(value = "Редактирование жанра", response = Genre.class, tags = "updateGenre")
    @PostMapping("/genre/update")
    public ResponseEntity<Genre> updateGenre(@RequestBody(required = true) Genre genre) {
        ResponseEntity<Genre> result;
        try {
            URI url = new URI("http://localhost:8083/genre/update");
            result = restTemplate.postForEntity(url, genre, Genre.class);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
        return result;
    }

    @ApiOperation(value = "Удаление жанра из БД", response = PublishingHouse.class, tags = "deleteGenre")
    @DeleteMapping("/genre/delete/{id}")
    public ResponseEntity deleteGenre(@PathVariable("id") long id) {
        try {
            URI url = new URI("http://localhost:8083/genre/delete/" + id);
            restTemplate.delete(url);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }


}
