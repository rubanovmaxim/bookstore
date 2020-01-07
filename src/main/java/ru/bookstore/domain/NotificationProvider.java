package ru.bookstore.domain;

import ru.bookstore.domain.enums.NotificationType;

public class NotificationProvider {

    private String message;
    private NotificationType notificationType;
    private String receiver;

    public NotificationProvider(String message, NotificationType notificationType, String receiver) {
        this.message = message;
        this.notificationType = notificationType;
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public String getReceiver() {
        return receiver;
    }
}
