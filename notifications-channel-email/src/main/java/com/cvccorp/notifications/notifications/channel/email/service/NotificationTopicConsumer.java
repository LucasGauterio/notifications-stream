package com.cvccorp.notifications.notifications.channel.email.service;

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

import java.nio.charset.StandardCharsets;

@Service
@Slf4j
@AllArgsConstructor
@EnableBinding(Sink.class)
public class NotificationTopicConsumer {

    private final EmailService service;

    @StreamListener(target = Sink.INPUT, condition="headers['status']=='templated' and headers['type']=='email'")
    public void handle(@Payload String message, @Headers MessageHeaders headers) {
        log.info("Received message {} headers {}", message, headers);
        service.process(
                new String((byte[])headers.get(KafkaHeaders.RECEIVED_MESSAGE_KEY), StandardCharsets.UTF_8)
                , message);
    }

}
