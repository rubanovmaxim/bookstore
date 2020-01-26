package ru.bookstore.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;


//@Component
public class MyBean {

    private final KafkaTemplate kafkaTemplate;

    @Value(value = "${kafka.topic-name}")
    private String topicName;

    @Autowired
    public MyBean(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String msg) {
        kafkaTemplate.send(topicName, msg);
    }

}