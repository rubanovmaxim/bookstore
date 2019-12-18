package ru.bookstore.services;

import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import ru.bookstore.events.NotificationEvent;

/**
 * Created by Rubanov.Maksim on 17.12.2019.
 */
public interface NotificationService extends ApplicationListener<NotificationEvent> {
    @Async
    @Override
    public void onApplicationEvent(NotificationEvent notificationEvent);
}
