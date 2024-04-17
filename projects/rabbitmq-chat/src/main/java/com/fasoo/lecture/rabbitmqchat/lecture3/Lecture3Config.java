package com.fasoo.lecture.rabbitmqchat.lecture3;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("lecture3")
@Configuration
public class Lecture3Config {

  @Bean("request")
  public TopicExchange requestTopicExchange() {
    return new TopicExchange("request");
  }
}
