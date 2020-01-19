package ru.bookstore;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.bookstore.cache.GenreCache;
import ru.bookstore.domain.Genre;

@EnableAsync
@EnableCaching
@RestController
@SpringBootApplication
public class Main {

    @Autowired
    private CacheManager cacheManager;


    @Autowired
    GenreCache genreCache;


    @GetMapping("/")
    public String home() {
        return "home";
    }


    @GetMapping("/item/{itemId}")
    @ResponseBody
    public ResponseEntity<Genre> getItem(@PathVariable long itemId) {

        System.out.println("RestController..  " + cacheManager.getCacheNames());
        long start = System.currentTimeMillis();
//        Optional<Genre> oenreOpt = genreRepository.findById(itemId);

        Genre item = genreCache.getGenre(itemId);
        long end = System.currentTimeMillis();
        System.out.println("Took : " + ((end - start) / 1000 + " sec."));
        return new ResponseEntity<Genre>(item, HttpStatus.OK);
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}
