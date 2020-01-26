package ru.bookstore.rest;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.bookstore.domain.Notification;
import ru.bookstore.domain.NotificationProvider;
import ru.bookstore.domain.User;
import ru.bookstore.domain.enums.NotificationType;
import ru.bookstore.events.NotificationEvent;
import ru.bookstore.repositories.UserRepository;
import ru.bookstore.services.MyBean;

import java.util.Optional;


@Api(tags = "NotificationController", description = "Контроллер уведомлений")
@RestController
public class NotificationController {

    private final static Logger LOGGER = LoggerFactory.getLogger(NotificationController.class);

    private ApplicationEventPublisher applicationEventPublisher;

    private UserRepository userRepository;
//
//    @Autowired
//    private MyBean myBean;
//
@Value(value = "${kafka.topic-name}")
private String topicName;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String msg) {
        kafkaTemplate.send(topicName, msg);
    }

    @Autowired
    public NotificationController(ApplicationEventPublisher applicationEventPublisher, UserRepository userRepository) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.userRepository = userRepository;
    }

    @ApiOperation(value = "Метод отправляет сообщение пользователю на почту и телефон.", response = String.class, tags = "sendNotification")
    @PostMapping("/notification/message")
    public ResponseEntity sendNotification(@RequestBody(required = true) Notification notification) {
        sendMessage("aqsdasd");

        if(true) return ResponseEntity.ok().build();

        Optional<User> userOpt = userRepository.findById(notification.getUserId());
        if (!userOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There are no user with id=" + notification.getUserId());
        }
        User user = userOpt.get();
        if (user.getEMail() != null || !user.getEMail().isEmpty()) {
            sendNotification(notification.getMessage(), NotificationType.EMAIL, user.getEMail());
        }
        if (user.getPhone() != null || !user.getPhone().isEmpty()) {

            sendNotification(notification.getMessage(), NotificationType.PHONE, user.getPhone());
        }
        return ResponseEntity.ok().build();
    }

    private void sendNotification(String message, NotificationType notificationType, String receiver) {
        NotificationProvider notificationProvider = new NotificationProvider(message, notificationType, receiver);
        NotificationEvent notificationEvent = new NotificationEvent(this, notificationProvider);
        this.applicationEventPublisher.publishEvent(notificationEvent);
    }
}
