package ru.bookstore.events;

import org.springframework.context.ApplicationEvent;
import ru.bookstore.domain.Notification;

/**
 * Created by Rubanov.Maksim on 17.12.2019.
 */
public class NotificationEvent extends ApplicationEvent {

    private Notification notification;

    public NotificationEvent(Object source, Notification notification) {
        super(source);
        this.notification = notification;
    }

    public Notification getNotification() {
        return notification;
    }
}
