package ru.bookstore.rest;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

@Api(tags = "OrderController", description = "Контроллер для работы с заказами")
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

    @ApiOperation(value = "Информация по заказу", response = List.class, tags = "getOrderInfo")
    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<OrderContent>> getOrderInfo(@PathVariable(name="orderId") Long orderId) {
        List<OrderContent> orderContent = orderContentRepository.findAllByOrderId(orderId);
        return ResponseEntity.ok().body(orderContent);
    }


    @ApiOperation(value = "Добавление книги в щзаказ", response = OrderContent.class, tags = "addBookFromOrder")
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


    @ApiOperation(value = "Удаление книги из заказа", response = ResponseEntity.class, tags = "deleteBookFromOrder")
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
