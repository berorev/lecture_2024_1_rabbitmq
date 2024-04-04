package com.fasoo.lecture.rabbitmqchat.lecture2;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("lecture2")
@Configuration
public class QueueConfiguration {

  @Bean
  public TopicExchange topicRequest() {
    return new TopicExchange("request");
  }

}
