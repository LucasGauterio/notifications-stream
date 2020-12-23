package com.cvccorp.notifications.notifications.service;

import com.cvccorp.notifications.notifications.dto.RequestMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
@EnableBinding(Source.class)
public class NotificationsTopicProducer {

    private final int TARGET_PARTITION = 0;

    private Source outputSource;

    public String publish(RequestMessage message) {
        String key = UUID.randomUUID().toString();
        log.info("Publishing {} message {}", TARGET_PARTITION, message);
        outputSource.output()
                .send(
                        MessageBuilder
                                .withPayload(message)
                                .setHeader(KafkaHeaders.MESSAGE_KEY, key.getBytes(StandardCharsets.UTF_8))
                                .setHeader(KafkaHeaders.PARTITION_ID, TARGET_PARTITION)
                                .build()
                );
        return key;
    }

}
