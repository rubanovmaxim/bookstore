package ru.bookstore.events;

import org.springframework.context.ApplicationEvent;
import ru.bookstore.domain.NotificationProvider;

/**
 * Created by Rubanov.Maksim on 17.12.2019.
 */
public class NotificationEvent extends ApplicationEvent {

    private NotificationProvider notificationProvider;

    public  NotificationEvent(Object source, NotificationProvider notificationProvider) {
        super(source);
        this.notificationProvider = notificationProvider;

    }

    public NotificationProvider getNotificationProvider() {
        return notificationProvider;
    }


}
