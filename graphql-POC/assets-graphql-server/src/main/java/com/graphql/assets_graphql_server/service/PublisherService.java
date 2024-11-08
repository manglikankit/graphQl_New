package com.graphql.assets_graphql_server.service;

import com.graphql.assets_graphql_server.controller.GraphQLSubscriptionController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PublisherService {

    private final GraphQLSubscriptionController graphQLSubscriptionController;

    @Autowired
    public PublisherService(GraphQLSubscriptionController graphQLSubscriptionController) {
        this.graphQLSubscriptionController = graphQLSubscriptionController;
    }

    // Method to send message to all subscribers
    public void sendMessageToSubscribers(String message) {
        graphQLSubscriptionController.sendMessage(message);
    }
}
