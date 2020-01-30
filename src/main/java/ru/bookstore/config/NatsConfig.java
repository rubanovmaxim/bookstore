package ru.bookstore.config;

import io.nats.client.Connection;
import io.nats.client.Nats;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * Created by Rubanov.Maksim on 29.01.2020.
 */
@Configuration
public class NatsConfig {

    @Bean
    public Connection natsConnection( @Value("${nats.usl}") String url) throws IOException, InterruptedException {
        return Nats.connect(url);
    }
}
