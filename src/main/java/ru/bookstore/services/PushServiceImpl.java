package ru.bookstore.services;


import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.bookstore.domain.NotificationProvider;
import ru.bookstore.domain.enums.NotificationType;
import ru.bookstore.events.NotificationEvent;


/**
 * Created by Rubanov.Maksim on 17.12.2019.
 */
@Component("push_notification")
public class PushServiceImpl implements NotificationListener {
//    public static final String ACCOUNT_SID = "ACf0121f8793eb1aae75b24a9daec1b3f7";
//    public static final String AUTH_TOKEN = "6f15198b70b994f17b144ffabdbc2a9d";

    public static final String ACCOUNT_SID = "AC18515cbf3f9ccededdcc3f1d30d731e8";
    public static final String AUTH_TOKEN = "f33e19966c18376a13d324ce7a4dafc3";

    private final static Logger LOGGER = LoggerFactory.getLogger(PushServiceImpl.class);


    //@Async
    @Override
    public void onApplicationEvent(NotificationEvent notificationEvent) {

        if (notificationEvent.getNotificationProvider().getNotificationType() != NotificationType.PHONE) {
            return;
        }
        NotificationProvider notificationProvider = notificationEvent.getNotificationProvider();
        LOGGER.info("Try to send SMS message:" + notificationProvider.getMessage() + " on phone:" + notificationProvider.getReceiver());
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new PhoneNumber(notificationProvider.getReceiver()),
                //new PhoneNumber("+12532313166"),
                new PhoneNumber("+12512572745"),
                notificationProvider.getMessage())
                .create();
        LOGGER.info("SMS was sent.");
    }
}
