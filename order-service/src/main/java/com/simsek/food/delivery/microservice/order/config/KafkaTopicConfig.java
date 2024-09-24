package com.simsek.food.delivery.microservice.order.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic paymentCompletedTopic() {
        return TopicBuilder.name("payment-completed")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic paymentFailedTopic() {
        return TopicBuilder.name("payment-failed")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic restaurantAcceptedTopic() {
        return TopicBuilder.name("restaurant-accepted")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic restaurantRejectedTopic() {
        return TopicBuilder.name("restaurant-rejected")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic deliveryStartedTopic() {
        return TopicBuilder.name("delivery-started")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic deliveryFailedTopic() {
        return TopicBuilder.name("delivery-failed")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic orderCreatedTopic() {
        return TopicBuilder.name("order-created")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic orderCompletedTopic() {
        return TopicBuilder.name("order-completed")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic orderFailedTopic() {
        return TopicBuilder.name("order-failed")
                .partitions(1)
                .replicas(1)
                .build();
    }
}
