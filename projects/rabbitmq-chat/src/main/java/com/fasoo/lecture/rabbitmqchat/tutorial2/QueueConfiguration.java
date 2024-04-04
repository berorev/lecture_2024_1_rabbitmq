package com.fasoo.lecture.rabbitmqchat.tutorial2;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("tutorial2")
@Configuration
public class QueueConfiguration {

  @Bean
  public Queue getQueue() {
    return new Queue("hello");
  }
}
