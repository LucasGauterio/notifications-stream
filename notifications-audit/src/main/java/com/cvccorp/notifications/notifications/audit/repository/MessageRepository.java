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
            "      \"path\": \"?0\",\n" +
            "      \"query\": {\n" +
            "        \"bool\": {\n" +
            "          \"must\": [\n" +
            "            { \"match\": { \"?1\": \"?2\"} }\n" +
            "          ]\n" +
            "        }\n" +
            "      },\n" +
            "      \"score_mode\": \"none\"\n" +
            "    }\n" +
            "  }")
    public List<Message> findByField (String path, String field, String value);
}
