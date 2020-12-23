package com.cvccorp.notifications.notifications.service;

import com.cvccorp.notifications.notifications.dto.RequestMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
@EnableBinding(Sink.class)
public class NotificationsTopicConsumer {

    private CallbackService service;

    @StreamListener(target = Sink.INPUT)
    public void handle(@Payload String message) {
        log.info("Received message {}", message);
        service.process("", message);
    }

}
