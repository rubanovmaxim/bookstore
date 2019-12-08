package ru.bookstore.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.bookstore.domain.*;
import ru.bookstore.domain.enums.OrderStatus;
import ru.bookstore.repositories.OrderContentRepository;
import ru.bookstore.repositories.OrderRepository;
import ru.bookstore.repositories.UserRepository;

import java.util.List;

@RestController
public class OrderController {

    private OrderRepository orderRepository;
    private OrderContentRepository orderContentRepository;
    private UserRepository userRepository;

    @Autowired
    public OrderController(OrderRepository orderRepository, OrderContentRepository orderContentRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.orderContentRepository = orderContentRepository;
        this.userRepository = userRepository;
    }


    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<OrderContent>> addBookFromOrder(@PathVariable(name="orderId") Long orderId) {
        List<OrderContent> orderContent = orderContentRepository.findAllByOrderId(orderId);
        return ResponseEntity.ok().body(orderContent);
    }


    @PostMapping("/order/add/book/{orderId}")
    public ResponseEntity<OrderContent> addBookFromOrder(@PathVariable(name="orderId",required = false) Long orderId,@RequestBody(required = true) Book book) {
        if(orderId < 0 ){
            User user = getCurrentUser();
            Order order  = new Order(user.getUserId(),OrderStatus.BASKET.name());
            order = orderRepository.save(order);
            orderId = order.getId();
        }
        OrderContent orderContent = new OrderContent(orderId,book.getId());
        orderContentRepository.save(orderContent);
        return ResponseEntity.ok().body(orderContent);
    }


    @DeleteMapping("/order/delete/book/{orderId}")
    public ResponseEntity deleteBookFromOrder(@PathVariable(name="orderId") Long orderId,@RequestBody(required = true) Book book) {
        OrderContent orderContent = new OrderContent(orderId,book.getId());
        orderContentRepository.delete(orderContent);
        return ResponseEntity.ok().build();
    }


    private User getCurrentUser() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        System.out.println(userDetails.getUsername());
        return userRepository.findByUserName(userDetails.getUsername());
    }


}
