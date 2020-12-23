package com.cvccorp.notifications.notifications.controller;

import com.cvccorp.notifications.notifications.dto.RequestMessage;
import com.cvccorp.notifications.notifications.service.NotificationsTopicProducer;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/notification")
public class NotificationsController {

    private NotificationsTopicProducer producer;

    @PostMapping(value = "/publish")
    public String publish(@RequestBody RequestMessage payload) {
        producer.publish(payload);
        return "success";
    }

}
