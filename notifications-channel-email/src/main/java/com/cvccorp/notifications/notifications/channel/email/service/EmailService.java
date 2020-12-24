package com.cvccorp.notifications.notifications.channel.email.service;

import com.cvccorp.notifications.notifications.channel.email.dto.Channel;
import com.cvccorp.notifications.notifications.channel.email.dto.RequestMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Slf4j
@Service
@AllArgsConstructor
public class EmailService {

    private final NotificationTopicProducer producer;

    public void process(String key, String jsonMessage) {
        try {
            RequestMessage message = new JsonMapper().readValue(jsonMessage, RequestMessage.class);
            log.info("-> EMAIL");
            Channel email = message.getConfiguration().getChannels().stream().filter(channel -> "email".equals(channel.getType())).findFirst().orElse(null);
            log.info("--> CONFIGURATION {}", email.getConfiguration());
            log.info("--> CONTENT {}", message.getContent());
            log.info("--> ATTACHMENTS {}", message.getNotification().getAttachments());
            HashMap<String, String> statusMap = message.getChannelStatusMap();
            if (statusMap == null)
                statusMap = new HashMap<>();
            statusMap.put("email", "delivered");
            message.setChannelStatusMap(statusMap);
            producer.publish(key, message);
        } catch (
                JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
