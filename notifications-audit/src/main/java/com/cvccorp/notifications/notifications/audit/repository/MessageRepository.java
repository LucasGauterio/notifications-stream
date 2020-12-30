package com.cvccorp.notifications.notifications.audit.repository;

import com.cvccorp.notifications.notifications.audit.model.Message;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.List;
import java.util.Map;

public interface MessageRepository extends CrudRepository <Message, Long> {
    public List<Message> findAll ();
    @Query ("{\n" +
            "    \"nested\": {\n" +
            "      \"path\": \"map\",\n" +
            "      \"query\": {\n" +
            "        \"bool\": {\n" +
            "          \"must\": [\n" +
            "            { \"match\": { \"?0\": \"?1\"} }\n" +
            "          ]\n" +
            "        }\n" +
            "      },\n" +
            "      \"score_mode\": \"avg\"\n" +
            "    }\n" +
            "  }")
    public List<Message> findByField (String field, String value);
}
