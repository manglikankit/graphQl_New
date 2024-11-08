package com.graphql.assets_graphql_server.controller;

import jakarta.annotation.PostConstruct;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CopyOnWriteArraySet;

@Controller
public class GraphQLSubscriptionController {

    // Sinks will be used to emit messages to the subscribers
//    private final Sinks.Many<String> messageSink = Sinks.many().multicast().onBackpressureBuffer();
    private final Sinks.Many<String> messageSink = Sinks.many().replay().all();
    private final CopyOnWriteArraySet<String> activeSubscribers = new CopyOnWriteArraySet<>();
    // Subscriber will receive the messages from this subscription
    @SubscriptionMapping
    public Flux<String> messageReceived() {
        String subscriberId = Thread.currentThread().getName();
        activeSubscribers.add(subscriberId);
//         Stream messages to the subscriber and keep the connection open
        return messageSink.asFlux()  // Return a Flux to allow multiple messages
                .doFinally(signal -> {
                    // Remove subscriber when the connection is closed
                    activeSubscribers.remove(subscriberId);
                });
         // For pending msg retrievals but still graphql doesn't provide this feature. will require like kafka or RbitMQ etc.
//        List<String> pendingMessages = messageRepository.findAll();
//        // Return pending messages first, then continue with real-time stream
//        return Flux.fromIterable(pendingMessages)
//                .concatWith(messageSink.asFlux());
    }

    // Simulate sending a message to all subscribers
    public void sendMessage(String message) {
        boolean success = messageSink.tryEmitNext(message).isSuccess();
        if (success) {
            System.out.println("Message sent: " + message);
        } else {
            System.out.println("Failed to emit message: " + message);
        }
       }
    public void simulateMessageSending() {
        // Example of simulating message sending every 3 seconds
        new Thread(() -> {
            try {
                while (true) {
                    Thread.sleep(3000);  // Wait 3 seconds between each message
                    Instant instant = Instant.ofEpochMilli(System.currentTimeMillis());
                    String formattedDateTime = DateTimeFormatter
                            .ofPattern("yyyy-MM-dd HH:mm:ss") // Define your format
                            .withZone(ZoneId.systemDefault()) // Use system default time zone
                            .format(instant);

                    sendMessage("Message at " + formattedDateTime);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

@PostConstruct
public void init() {
    simulateMessageSending();  // Start sending messages periodically
}
}