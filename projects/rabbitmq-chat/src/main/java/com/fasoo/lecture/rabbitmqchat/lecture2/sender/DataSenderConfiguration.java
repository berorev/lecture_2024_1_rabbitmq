package com.fasoo.lecture.rabbitmqchat.lecture2.sender;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("lecture2-sender")
@Configuration
public class DataSenderConfiguration {

  @Bean
  public DataSender sender() {
    return new DataSender();
  }
}
