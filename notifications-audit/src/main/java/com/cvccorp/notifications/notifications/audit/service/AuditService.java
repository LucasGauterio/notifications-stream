package com.cvccorp.notifications.notifications.audit.service;

import com.cvccorp.notifications.notifications.audit.dto.Channel;
import com.cvccorp.notifications.notifications.audit.dto.Configuration;
import com.cvccorp.notifications.notifications.audit.dto.RequestMessage;
import com.cvccorp.notifications.notifications.audit.model.Message;
import com.cvccorp.notifications.notifications.audit.repository.MessageRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;

@Slf4j
@Service
@AllArgsConstructor
public class AuditService {

    private final MessageRepository repository;
    private final ElasticsearchRestTemplate elasticSearchRestTemplate;
    public void process(String key, String jsonMessage) {
        try {
            RequestMessage message = new RequestMessage(new JsonMapper().readValue(jsonMessage, Map.class));
            log.info(">>>>>  Message {}", message);
            repository.save(
                    Message.builder().map(message.getMap()).build()
            );

            /*
            NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                    .withQuery(matchQuery("map.producer", "notifications-preferences"))
                    .build();

            SearchHits<Message> messages = elasticSearchRestTemplate.search(searchQuery, Message.class, IndexCoordinates.of("message"));
            messages.forEach(item -> { log.info("*****> {}", item.getContent().getMap().get("producer"));});
            ap <String,Object> filter = new HashMap<>();
            filter.put("producer","notifications-channel-email");
            */
            List <Message> messages = repository.findByField("map.producer.keyword","notifications-preferences");
            messages.forEach(item -> { log.info("*****> {}", item.getMap().get("producer"));});

        } catch (
                JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
