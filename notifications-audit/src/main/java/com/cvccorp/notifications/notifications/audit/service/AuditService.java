package com.cvccorp.notifications.notifications.audit.service;

import com.cvccorp.notifications.notifications.audit.dto.RequestMessage;
import com.cvccorp.notifications.notifications.audit.model.Message;
import com.cvccorp.notifications.notifications.audit.repository.MessageRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.index.query.NestedQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
        } catch (
                JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public SearchHits<Message> searchNested(String path, String field, String value){
        NestedQueryBuilder nestedQueryBuilder = QueryBuilders.nestedQuery(
            path,
            QueryBuilders.matchQuery(field, value),
            ScoreMode.None
            );
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
            .withQuery(nestedQueryBuilder)
            .build();
        SearchHits<Message> messages = elasticSearchRestTemplate.search(searchQuery, Message.class, IndexCoordinates.of("message"));
        messages.forEach(item -> { log.info("NESTED > {}", item.getContent());});
        return messages;
    }

    public List<Message> searchExample(String path, String field, String value){
        List <Message> messages = repository.findByField(path, field, value);
        messages.forEach(item -> { log.info("EXAMPLE > {}", item.getMap());});
        return messages;
    }

}
