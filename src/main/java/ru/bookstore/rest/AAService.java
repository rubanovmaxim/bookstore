package ru.bookstore.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.bookstore.domain.User;
//import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bookstore.repositories.UserRepository;

@RestController
public class AAService {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/public")
    public String publicPage() {
        return "public";
    }

    @GetMapping("/authentication")
    public String authenticatedPage() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        System.out.println(userDetails.getUsername());
        return "authenticated";
    }

    @GetMapping("/after_authentication")
    public String afterAuthentication() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        System.out.println(userDetails.getUsername());

        User user  = userRepository.findByUserName("dbadmin1");
        System.out.println(user.getEncrytedPassword());
        return "after_authentication";
    }
}
