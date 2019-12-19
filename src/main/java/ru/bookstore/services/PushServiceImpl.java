package ru.bookstore.services;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.bookstore.domain.Notification;
import ru.bookstore.events.NotificationEvent;


/**
 * Created by Rubanov.Maksim on 17.12.2019.
 */
@Component("push_notification")
public class PushServiceImpl implements NotificationListener {
    public static final String ACCOUNT_SID = "ACf0121f8793eb1aae75b24a9daec1b3f7";
    public static final String AUTH_TOKEN = "6f15198b70b994f17b144ffabdbc2a9d";

    private final static Logger LOGGER = LoggerFactory.getLogger(PushServiceImpl.class);

//    @Override
//    public boolean sendMessage(UserInfo userInfo, String message) {

//        TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
//
//        // Build the parameters
//        List<NameValuePair> params = new ArrayList<NameValuePair>();
//        params.add(new BasicNameValuePair("To", "+14159352345"));
//        params.add(new BasicNameValuePair("From", "+14158141829"));
//        params.add(new BasicNameValuePair("Body", "Where's Wallace?"));
//
//        MessageFactory messageFactory = client.getAccount().getMessageFactory();
//        Message message1 = messageFactory.create(params);
//
//        System.out.println(message1.getSid());
//
////        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
////        Message message1 = Message.creator(
////                new PhoneNumber("+12532313166"),
////                new PhoneNumber("+79632463534"),
////                "Sample Twilio SMS using Java")
////                .create();
//        return false;
//    }

    //@Async
    @Override
    public void onApplicationEvent(NotificationEvent notificationEvent) {
        Notification notification = notificationEvent.getNotification();
        LOGGER.info("Try to send SMS message:" + notification.getMessage() + " on phone:" + notification.getPhone());
    }
}
