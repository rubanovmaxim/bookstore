package ru.bookstore.rest;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.bookstore.domain.Notification;
import ru.bookstore.events.NotificationEvent;
import ru.bookstore.repositories.UserRepository;


@Api(tags = "NotificationController", description = "Контроллер уведомлений")
@RestController
public class NotificationController {

    private ApplicationEventPublisher applicationEventPublisher;
    private UserRepository userRepository;

    @Autowired
    public NotificationController(UserRepository userRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.userRepository = userRepository;
        this.applicationEventPublisher = applicationEventPublisher;

    }


    @ApiOperation(value = "Метод отправляет сообщение пользователю на почту и телефон.", response = String.class, tags = "sendNotification")
    @PostMapping("/notification/message/{message}")
    public ResponseEntity sendNotification(@RequestBody(required = true) Notification notification) {

        NotificationEvent notificationEvent = new NotificationEvent(this, notification);
        this.applicationEventPublisher.publishEvent(notificationEvent);
        return ResponseEntity.ok().build();
    }
}
