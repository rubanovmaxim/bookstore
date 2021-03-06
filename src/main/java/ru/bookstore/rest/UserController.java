package ru.bookstore.rest;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.bookstore.domain.User;
import ru.bookstore.domain.UserInfo;
import ru.bookstore.domain.UserRole;
import ru.bookstore.domain.enums.RoleEnum;
import ru.bookstore.repositories.UserRepository;
import ru.bookstore.repositories.UserRoleRepository;

//import org.springframework.security.core.userdetails.User;

@Api(tags = "UserController", description = "Контроллер для авторизации, регистрации пользователей")
@RestController
public class UserController {


    private UserRepository userRepository;


    private UserRoleRepository userRoleRepository;

    @Autowired
    public UserController(UserRepository userRepository, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }
//
//    @GetMapping("/public")
//    public String publicPage() {
//        return "public";
//    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ObjectNotFoundException.class, ConstraintViolationException.class})
    @PostMapping(value = "/registration", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String registration(@RequestBody(required = true) UserInfo userInfo) {
        User user = new User();
        user.setUserName(userInfo.getUserName());
        user.setEncrytedPassword(encrytePassword(userInfo.getPassword()));
        user = userRepository.save(user);
        UserRole userRole = new UserRole();
        userRole.setUserId(user.getUserId());
        userRole.setRoleId(RoleEnum.USER.getRoleId());
        userRole = userRoleRepository.save(userRole);
        return "registration was successful";
    }

    @ApiOperation(value = "Метод для аутентификации",  tags = "authenticatedPage")
    @GetMapping("/authentication")
    public String authenticatedPage() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        System.out.println(userDetails.getUsername());
        return "login was successful";
    }

//    @GetMapping("/after_authentication")
//    public String afterAuthentication() {
//        SecurityContext securityContext = SecurityContextHolder.getContext();
//        Authentication authentication = securityContext.getAuthentication();
//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//        System.out.println(userDetails.getUsername());
//
//        User user = userRepository.findByUserName("admin");
//        System.out.println(user.getEncrytedPassword());
//        return "after_authentication";
//    }


    public static String encrytePassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }
}
