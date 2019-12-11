package ru.bookstore.rest;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.bookstore.domain.Order;
import ru.bookstore.domain.OrderContent;
import ru.bookstore.domain.User;
import ru.bookstore.domain.enums.OrderStatus;
import ru.bookstore.repositories.OrderContentRepository;
import ru.bookstore.repositories.OrderRepository;
import ru.bookstore.repositories.UserRepository;

import java.util.ArrayList;
import java.util.Collections;
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

    @ApiOperation(value = "Информация по заказу.Список книг в заказе. В запросе нужно передать id заказа(orderId).", response = List.class, tags = "getOrderInfo")
    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<OrderContent>> getOrderInfo(@PathVariable(name = "orderId") Long orderId) {
        List<OrderContent> orderContent = orderContentRepository.findAllByOrderId(orderId);
        return ResponseEntity.ok().body(orderContent);
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ObjectNotFoundException.class, ConstraintViolationException.class})
    @ApiOperation(value = "Добавление книг(и) в заказ.JSON массив из id книг, добавляемых в заказ. При этом в запросе нужно передать id заказа(orderId), если заказ новый передать '-1'.", response = OrderContent.class, tags = "addBookFromOrder")
    @PostMapping("/order/add/book/{orderId}")
    public ResponseEntity<List<OrderContent>> addBookFromOrder(@PathVariable(name = "orderId", required = true) Long orderId, @RequestBody(required = true) List<Long> bookIds) {
        if (bookIds == null || bookIds.size() == 0) {
            return ResponseEntity.ok().body(Collections.emptyList());
        }

        if (orderId < 0) {
            User user = getCurrentUser();
            Order order = new Order(user.getUserId(), OrderStatus.BASKET.name());
            order = orderRepository.save(order);
            orderId = order.getId();
        }

        List<OrderContent> result = new ArrayList<>();
        OrderContent orderContent = null;
        for (Long id : bookIds) {
            orderContent = new OrderContent(orderId, id);
            orderContentRepository.save(orderContent);
            result.add(orderContent);
        }
        return ResponseEntity.ok().body(result);
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ObjectNotFoundException.class, ConstraintViolationException.class})
    @ApiOperation(value = "Удаление книг(и) из заказа.JSON массив из id книг, удаляемых из заказа. При этом в запросе нужно передать id заказа(orderId).", response = ResponseEntity.class, tags = "deleteBookFromOrder")
    @DeleteMapping("/order/delete/book/{orderId}")
    public ResponseEntity deleteBookFromOrder(@PathVariable(name = "orderId") Long orderId, @RequestBody(required = true) List<Long> bookIds) {
        for (Long id : bookIds) {
            OrderContent orderContent = new OrderContent(orderId, id);
            orderContentRepository.delete(orderContent);
        }
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
