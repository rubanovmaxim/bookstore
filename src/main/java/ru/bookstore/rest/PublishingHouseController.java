package ru.bookstore.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bookstore.domain.PublishingHouse;
import ru.bookstore.repositories.PublishingHouseRepository;

import java.util.List;

@RestController
public class PublishingHouseController {


    private PublishingHouseRepository pblishingHouseRepository;

    @Autowired
    public PublishingHouseController(PublishingHouseRepository pblishingHouseRepository) {

        this.pblishingHouseRepository = pblishingHouseRepository;

    }


    @GetMapping("/publishinghouse/list")
    public ResponseEntity<List> getPublishingHouseRepository() {
        return ResponseEntity.ok().body(pblishingHouseRepository.findAll());
    }


    @PostMapping("/publishinghouse/new")
    public ResponseEntity<PublishingHouse> addPublishingHouse(@RequestBody(required = true) PublishingHouse pHouse) {
        pHouse.setId(null);
        pHouse = pblishingHouseRepository.save(pHouse);
        return ResponseEntity.ok().body(pHouse);
    }


    @PutMapping("/publishinghouse/update/{pHouseId}")
    public ResponseEntity<PublishingHouse> updatePublishingHouse(@PathVariable("pHouseId") long pHouseId,
                                                                 @RequestBody(required = true) PublishingHouse pHouse) {
        pHouse.setId(pHouseId);
        pHouse = pblishingHouseRepository.save(pHouse);
        return ResponseEntity.ok().body(pHouse);
    }


    @DeleteMapping("/publishinghouse/delete/{pHouseId}")
    public ResponseEntity deletePublishingHouse(@PathVariable("pHouseId") long pHouseId) {
        pblishingHouseRepository.deleteById(pHouseId);
        return ResponseEntity.ok().build();
    }

}
