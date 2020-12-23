package com.cvccorp.notifications.notifications.service;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface Sink2 {
    String INPUT = "input2";

    @Input("input2")
    SubscribableChannel input();
}
