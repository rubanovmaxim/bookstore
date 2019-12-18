package ru.bookstore.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.bookstore.domain.Notification;
import ru.bookstore.events.NotificationEvent;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by Rubanov.Maksim on 17.12.2019.
 */
@Component("email_notification")
public class EmailServiceImpl implements NotificationService {

    private final static Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);
    private final static String username = "rubanov.test.otus@mail.ru";
    private final static String password = "test.otus";


    @Override
    public void onApplicationEvent(NotificationEvent notificationEvent) {
        Notification notification = notificationEvent.getNotification();
        LOGGER.info("Try to send email message:" + notification.getMessage() + " to email:" + notification.getEMail());
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.mail.ru");
        prop.put("mail.smtp.port", "25");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.socketFactory.port", "25");
        prop.put("mail.smtp.starttls.enable", "true");
//        prop.put("mail.transport.protocol", "smtps");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");


        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("rubanov.test.otus@mail.ru"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(notification.getEMail())
            );
            message.setSubject("Заказ книги");
            message.setText(notification.getMessage());

            Transport.send(message);

            LOGGER.info("Email was send");

        } catch (MessagingException e) {
            e.printStackTrace();
        }


    }
}
