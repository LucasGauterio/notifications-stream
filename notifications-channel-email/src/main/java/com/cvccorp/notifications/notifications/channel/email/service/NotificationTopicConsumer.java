package com.cvccorp.notifications.notifications.channel.email.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
@EnableBinding(Sink.class)
public class NotificationTopicConsumer {

    private final EmailService service;

    @StreamListener(target = Sink.INPUT)
    public void consume(@Payload String message) {
        log.info("Received message {}", message);
        service.process(message);
    }

}
