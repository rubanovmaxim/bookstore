package ru.bookstore.rest;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.bookstore.domain.PublishingHouse;
import ru.bookstore.repositories.PublishingHouseRepository;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Api(tags = "PublishingHouseController", description = "Контроллер для работы с издательствами")
@RestController
public class PublishingHouseController {

    private final static Logger LOGGER = LoggerFactory.getLogger(PublishingHouseController.class);

    private RestTemplate restTemplate = new RestTemplate();
    private PublishingHouseRepository pblishingHouseRepository;

    @Autowired
    public PublishingHouseController(PublishingHouseRepository pblishingHouseRepository) {
        this.pblishingHouseRepository = pblishingHouseRepository;
    }


    @ApiOperation(value = "Получение издательства по id", response = List.class, tags = "getPublishingHouse")
    @GetMapping("/publishinghouse/{id}")
    public ResponseEntity<PublishingHouse> getPublishingHouse(@PathVariable(name = "id") long id) {
        ResponseEntity<PublishingHouse> result;
        try {
            URI url = new URI("http://localhost:8083/publishinghouse/" + id);
            result = restTemplate.getForEntity(url, PublishingHouse.class);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
        return result;
    }

    @ApiOperation(value = "Получение списка издателей", response = List.class, tags = "getPublishingHouseList")
    @GetMapping("/publishinghouse/list")
    public ResponseEntity<List<PublishingHouse>> getPublishingHouseList() {
        ResponseEntity<List<PublishingHouse>> result;
        Class clazz = new ArrayList<PublishingHouse>().getClass();
        try {
            URI url = new URI("http://localhost:8083/publishinghouse/list");
            result = restTemplate.getForEntity(url, clazz);

        } catch (URISyntaxException e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
        return result;
    }

    @ApiOperation(value = "Добавление нового издателя", response = PublishingHouse.class, tags = "addPublishingHouse")
    @PostMapping("/publishinghouse/new")
    public ResponseEntity<PublishingHouse> addPublishingHouse(@RequestBody(required = true) PublishingHouse pHouse) {
        ResponseEntity<PublishingHouse> result;
        try {
            URI url = new URI("http://localhost:8083/publishinghouse/new");
            result = restTemplate.postForEntity(url, pHouse, PublishingHouse.class);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
        return result;
    }

    @ApiOperation(value = "Изменение инвормации о издателе", response = PublishingHouse.class, tags = "updatePublishingHouse")
    @PostMapping("/publishinghouse/update")
    public ResponseEntity<PublishingHouse> updatePublishingHouse(@RequestBody(required = true) PublishingHouse pHouse) {
        ResponseEntity<PublishingHouse> result;
        try {
            URI url = new URI("http://localhost:8083/publishinghouse/update");
            result = restTemplate.postForEntity(url, pHouse, PublishingHouse.class);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
        return result;
    }

    @ApiOperation(value = "Удаление издателя из БД", response = PublishingHouse.class, tags = "deletePublishingHouse")
    @DeleteMapping("/publishinghouse/delete/{pHouseId}")
    public ResponseEntity deletePublishingHouse(@PathVariable("pHouseId") long pHouseId) {
        try {
            URI url = new URI("http://localhost:8083/publishinghouse/delete/" + pHouseId);
            restTemplate.delete(url);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }



}
