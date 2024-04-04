package com.fasoo.lecture.rabbitmqchat.lecture2;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("lecture2")
@Configuration
public class Lecture2Config {

  @Bean("request")
  public TopicExchange requestTopicExchange() {
    return new TopicExchange("request");
  }
}
