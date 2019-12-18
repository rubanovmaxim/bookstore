package ru.bookstore.rest;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

import java.util.List;
import java.util.stream.Collectors;


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


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ObjectNotFoundException.class, ConstraintViolationException.class})
    @PostMapping(value = "/registration", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String registration(@RequestBody(required = true) UserInfo userInfo) {
        User user = new User();
        user.setUserName(userInfo.getUserName());
        user.setEncrytedPassword(encrytePassword(userInfo.getPassword()));
        user.setEMail(userInfo.getEMail());
        user.setPhone(userInfo.getPhone());
        user = userRepository.save(user);
        UserRole userRole = new UserRole();
        userRole.setUserId(user.getUserId());
        userRole.setRoleId(RoleEnum.USER.getRoleId());
        userRole = userRoleRepository.save(userRole);
        return "registration was successful";
    }

    @ApiOperation(value = "Метод для аутентификации", tags = "authenticatedPage")
    @GetMapping("/authentication")
    public String authenticatedPage() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        System.out.println(userDetails.getUsername());
        return "login was successful";
    }

    @ApiOperation(value = "Метод возвращает список пользователей", response = List.class, tags = "getUserList")
    @GetMapping("/user/list")
    public ResponseEntity<List<UserInfo>> getUserList() {
        List<User> users = userRepository.findAll();
        List<UserInfo> result = users.stream().map(user -> {
            UserInfo userInfo = new UserInfo();
            userInfo.setUserId(user.getUserId());
            userInfo.setUserName(user.getUserName());
            userInfo.setEMail(user.getEMail());
            userInfo.setPhone(user.getPhone());
            return userInfo;
        }).collect(Collectors.toList());
        return ResponseEntity.ok().body(result);
    }

    public static String encrytePassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }
}
