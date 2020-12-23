package com.cvccorp.notifications.notifications.preferences.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
@EnableBinding(Sink.class)
public class NotificationTopicConsumer {

    private final ConfigurationService service;

    @StreamListener(target = Sink.INPUT)
    public void consume(@Payload String message) {
        log.info("Received message {}", message);
        service.process(message);
    }

}
