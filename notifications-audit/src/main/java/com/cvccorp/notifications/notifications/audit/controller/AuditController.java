package com.cvccorp.notifications.notifications.audit.controller;

import com.cvccorp.notifications.notifications.audit.model.Message;
import com.cvccorp.notifications.notifications.audit.service.AuditService;
import lombok.AllArgsConstructor;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/audit")
@AllArgsConstructor
public class AuditController {

    private final AuditService service;

    @GetMapping(value = "/example/search")
    public List<Message> searchExample(@RequestParam String path, @RequestParam String field, @RequestParam String value) {
        return service.searchExample(path, field, value);
    }

    @GetMapping(value = "/nested/search")
    public SearchHits<Message> searchNested(@RequestParam String path, @RequestParam String field, @RequestParam String value) {
        return service.searchNested(path, field, value);
    }

}