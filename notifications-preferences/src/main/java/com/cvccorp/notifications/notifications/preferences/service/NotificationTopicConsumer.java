package com.cvccorp.notifications.notifications.preferences.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
@EnableBinding(Sink.class)
public class NotificationTopicConsumer {

    private final ConfigurationService service;

    @StreamListener(target = Sink.INPUT, condition="headers['type']=='notification'")
    public void handle(@Payload String message, @Headers MessageHeaders headers) {
        log.info("Received message {} headers {}", message, headers);
        service.process(headers.get(KafkaHeaders.RECEIVED_MESSAGE_KEY).toString(), message);
    }

}
