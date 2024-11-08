package com.graphql.assets_graphql_server.controller;

import com.graphql.assets_graphql_server.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/publish")
public class PublisherController {

    private final PublisherService publisherService;

    @Autowired
    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @PostMapping("/send")
    public String sendMessage(@RequestParam String message) {
        // Send message to subscribers
        publisherService.sendMessageToSubscribers(message);
        return "Message sent to subscribers: " + message;
    }
}
