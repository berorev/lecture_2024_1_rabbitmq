package com.fasoo.lecture.rabbitmqchat.lecture3.sender;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("lecture3-sender")
@Configuration
public class DataSenderConfiguration {

  @Bean
  public DataSender sender() {
    return new DataSender();
  }
}
