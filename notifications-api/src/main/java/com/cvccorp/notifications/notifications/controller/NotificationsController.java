package com.cvccorp.notifications.notifications.controller;

import com.cvccorp.notifications.notifications.dto.RequestMessage;
import com.cvccorp.notifications.notifications.dto.ResponseMessage;
import com.cvccorp.notifications.notifications.service.NotificationsTopicProducer;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/notification")
public class NotificationsController {

    private NotificationsTopicProducer producer;

    @PostMapping(value = "/publish")
    public ResponseMessage publish(@RequestBody RequestMessage payload) {
        return new ResponseMessage(producer.publish(payload),null);
    }

}
