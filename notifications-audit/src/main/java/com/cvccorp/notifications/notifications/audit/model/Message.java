package com.cvccorp.notifications.notifications.audit.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Map;
@Builder
@Data
@Document(indexName = "message")
public class Message {
    @Id
    private String id;
    @Field(type = FieldType.Nested)
    private Map<String, Object> map;
}
