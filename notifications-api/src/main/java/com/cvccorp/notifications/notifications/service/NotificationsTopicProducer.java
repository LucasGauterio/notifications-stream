package com.cvccorp.notifications.notifications.service;

import com.cvccorp.notifications.notifications.dto.RequestMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
@EnableBinding(Source.class)
public class NotificationsTopicProducer {

    private final Source outputSource;

    public void publish(RequestMessage message) {
        log.info("Publishing message {}", message);
        outputSource.output()
                .send(
                        MessageBuilder
                                .withPayload(message)
                                .build()
                );
    }

}
