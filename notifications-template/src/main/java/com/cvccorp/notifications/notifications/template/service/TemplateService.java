package com.cvccorp.notifications.notifications.template.service;

import com.cvccorp.notifications.notifications.template.dto.RequestMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class TemplateService {

    private final NotificationTopicProducer producer;

    public void process(String key, String jsonMessage) {
        try {
            RequestMessage message = new JsonMapper().readValue(jsonMessage, RequestMessage.class);

            log.info("notification {}", message.getNotification());
            log.info("configuration {}", message.getConfiguration());

            message.setContent("<html><body><table><thead><tr><th>E-MAIL</th></tr></thead><tbody><tr><td>Olá Fulano,<br>este é um e-mail de teste</td></tr><tr><td>Atenciosamente,<br>Lucas Gautério</td></tr></tbody></table></body></html>");

            producer.publish(key, message, "email");
        } catch (
                JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
