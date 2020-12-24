package com.cvccorp.notifications.notifications.template.service;

import com.cvccorp.notifications.notifications.template.dto.RequestMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
@EnableBinding(Source.class)
public class NotificationTopicProducer {

    private final Source outputSource;

    public String publish(String key, RequestMessage message, String channel) {
        if (StringUtils.isEmpty(key))
            key = UUID.randomUUID().toString();
        log.info("Publishing message {}", message);
        outputSource.output()
                .send(
                        MessageBuilder
                                .withPayload(message)
                                .setHeader(KafkaHeaders.MESSAGE_KEY, key.getBytes(StandardCharsets.UTF_8))
                                .setHeader("type","template")
                                .setHeader("channel", channel)
                                .build()
                );
        return key;
    }

}
