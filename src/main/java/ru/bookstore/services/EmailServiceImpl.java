package ru.bookstore.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import ru.bookstore.domain.NotificationProvider;
import ru.bookstore.domain.enums.NotificationType;
import ru.bookstore.events.NotificationEvent;

/**
 * Created by Rubanov.Maksim on 17.12.2019.
 */
@Component("email_notification")
public class EmailServiceImpl implements NotificationListener {

    private final static Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Autowired
    public JavaMailSender emailSender;


    @Override
    public void onApplicationEvent(NotificationEvent notificationEvent) {
        if (notificationEvent.getNotificationProvider().getNotificationType() != NotificationType.EMAIL) {
            return;
        }
        NotificationProvider notificationProvider = notificationEvent.getNotificationProvider();
        LOGGER.info("Try to send email message:" + notificationProvider.getMessage() + " to email:" + notificationProvider.getReceiver());

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(notificationProvider.getReceiver());
        message.setSubject("Заказ книг(и)");
        message.setText(notificationProvider.getMessage());
        emailSender.send(message);

        LOGGER.info("Email was send");
    }
}
