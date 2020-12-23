package com.cvccorp.notifications.notifications.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.RoundRobinAssignor;
import org.apache.kafka.clients.consumer.internals.AbstractPartitionAssignor;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.utils.CircularIterator;
import org.apache.kafka.common.utils.Utils;

import java.util.*;
import java.util.Map.Entry;

@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public abstract class SpecificAssignor extends RoundRobinAssignor {

    private int specific_partition;

    public Map<String, List<TopicPartition>> assign(Map<String, Integer> partitionsPerTopic, Map<String, Subscription> subscriptions) {
        Map<String, List<TopicPartition>> assignment = super.assign(partitionsPerTopic, subscriptions);
        Iterator<Entry<String, List<TopicPartition>>> entries = assignment.entrySet().iterator();
        while(entries.hasNext()){
            Entry<String, List<TopicPartition>> entry = entries.next();
            List<TopicPartition> partitions  = entry.getValue();
            assignment.put(entry.getKey(),Collections.singletonList(partitions.get(specific_partition)));
        }
        log.info("assignment {}",assignment);
        return assignment;
    }

    @Override
    public String name() {
        return "specific";
    }
}
