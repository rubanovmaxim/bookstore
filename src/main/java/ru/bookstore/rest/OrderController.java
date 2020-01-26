package ru.bookstore.rest;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.bookstore.domain.*;
import ru.bookstore.domain.enums.OrderStatus;
import ru.bookstore.repositories.OrderContentRepository;
import ru.bookstore.repositories.OrderRepository;
import ru.bookstore.repositories.UserRepository;
import ru.bookstore.services.MyBean;

import java.util.*;

@Api(tags = "OrderController", description = "Контроллер для работы с заказами")
@RestController
public class OrderController {

    private final static Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    private OrderRepository orderRepository;
    private OrderContentRepository orderContentRepository;
    private UserRepository userRepository;
    private NotificationController notificationController;

//
//    @Autowired
//    private MyBean myBean;
//

    @Autowired
    public OrderController(OrderRepository orderRepository, OrderContentRepository orderContentRepository, UserRepository userRepository, NotificationController notificationController) {
        this.orderRepository = orderRepository;
        this.orderContentRepository = orderContentRepository;
        this.userRepository = userRepository;
        this.notificationController = notificationController;
    }

    @ApiOperation(value = "Информация по заказу.Список книг в заказе. В запросе нужно передать id заказа(orderId).", response = List.class, tags = "getOrderInfo")
    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<OrderContent>> getOrderInfo(@PathVariable(name = "orderId") Long orderId) {
        List<OrderContent> orderContent = orderContentRepository.findAllByOrderId(orderId);
        return ResponseEntity.ok().body(orderContent);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ObjectNotFoundException.class, ConstraintViolationException.class})
    @ApiOperation(value = "Отправка уведомления пользователю на email и телефон при подтверждении заказа", response = String.class, tags = "confirmOrder")
    @PostMapping("/order/confirm/{orderId}")
    public ResponseEntity confirmOrder(@PathVariable(name = "orderId", required = true) Long orderId) {
        User user = getCurrentUser();
        //ищем заказ сразу и по его id и по пользователю, что бы исключить отправку заказа другим пользователем.
        Optional<Order> orderOpt = orderRepository.findByIdAndUserId(orderId,user.getUserId());
        if (!orderOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There is no order with id=" + orderId + " or this order is not belong you");
        }

        Order order = orderOpt.get();
        // проверяем , что заказ находится в состоянии "Корзина", что бы его можно было подтвердить.
        //TODO передделать впоследствии на ORDER_FORMING(перейти к оформлению), когда это будет реализовано
         if(! order.getStatus().equalsIgnoreCase(OrderStatus.BASKET.name())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Order not in status=BASKET to be confirmed");
        }
        order.setStatus(OrderStatus.ORDER_CONFIRMED.name());
        order = orderRepository.save(order);

       // User user = userRepository.findById(order.getUserId()).get();
        Notification notification = new Notification();
        notification.setUserId(user.getUserId());
        notification.setMessage("Congradulation! Your order " + orderId + " will be delivered in 2 days.");
//        myBean.sendMessage("Tu-Tu");
//        notificationController.sendNotification(notification);
        return ResponseEntity.ok().build();
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ObjectNotFoundException.class, ConstraintViolationException.class})
    @ApiOperation(value = "Добавление книг(и) в заказ.JSON массив из id книг, добавляемых в заказ.\n" +
            "При этом в запросе нужно передать id заказа(orderId), если заказ новый передать '-1'.\n" +
            "После добавления вернется заказ ,в которои уже будет его id, по которому можно что-то добавлять в заказ.", response = OrderContent.class, tags = "addBookToOrder")
    @PostMapping("/order/add/book/{orderId}")
    public ResponseEntity addBookToOrder(@PathVariable(name = "orderId", required = true) Long orderId, @RequestBody(required = true) List<Long> bookIds) {
        if (bookIds == null || bookIds.size() == 0) {
            return ResponseEntity.ok().body(Collections.emptyList());
        }

        User user = getCurrentUser();
        if (orderId < 0) {
            Order order = new Order(user.getUserId(), OrderStatus.BASKET.name());
            order = orderRepository.save(order);
            orderId = order.getId();
        }else{
            //ищем заказ сразу и по его id и по пользователю, что бы исключить отправку заказа другим пользователем.
            Optional<Order> orderOpt = orderRepository.findByIdAndUserId(orderId,user.getUserId());
            if (!orderOpt.isPresent()) {
                return ResponseEntity.badRequest().body("There is no order with id=" + orderId + " or this order is not belong you");
            }

        }

        OrderContent orderContent = null;
        for (Long id : bookIds) {
            orderContent = new OrderContent(orderId, id);
            orderContentRepository.save(orderContent);

        }
        return ResponseEntity.ok().body(orderContentRepository.findAllByOrderId(orderId));
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ObjectNotFoundException.class, ConstraintViolationException.class})
    @ApiOperation(value = "Удаление книг(и) из заказа.JSON массив из id книг, удаляемых из заказа. При этом в запросе нужно передать id заказа(orderId).", response = ResponseEntity.class, tags = "deleteBookFromOrder")
    @DeleteMapping("/order/delete/book/{orderId}")
    public ResponseEntity deleteBookFromOrder(@PathVariable(name = "orderId") Long orderId, @RequestBody(required = true) List<Long> bookIds) {
        User user = getCurrentUser();
        //ищем заказ сразу и по его id и по пользователю, что бы исключить удаление заказа другим пользователем.
        Optional<Order> orderOpt = orderRepository.findByIdAndUserId(orderId,user.getUserId());
        if (!orderOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There is no order with id=" + orderId + " or this order is not belong you");
        }
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

