package ru.bookstore;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableAsync
@EnableCaching
@RestController
@SpringBootApplication
public class Main {

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/message")
    public String getMessage() {
        return "Working ...!!";
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}
