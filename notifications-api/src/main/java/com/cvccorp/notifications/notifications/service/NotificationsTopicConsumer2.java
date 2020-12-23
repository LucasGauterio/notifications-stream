package com.cvccorp.notifications.notifications.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
@EnableBinding(Sink2.class)
public class NotificationsTopicConsumer2 {

    private CallbackService service;

    @StreamListener(target = Sink2.INPUT)
    public void handle(@Payload String message, @Headers MessageHeaders headers) {
        log.info("Received {} message {} headers {}", 0, message, headers);
        service.process("", message);
    }

}
