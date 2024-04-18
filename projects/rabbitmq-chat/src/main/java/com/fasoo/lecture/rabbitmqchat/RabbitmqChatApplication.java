package com.fasoo.lecture.rabbitmqchat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RabbitmqChatApplication {

  public static void main(String[] args) {
    SpringApplication.run(RabbitmqChatApplication.class, args);
  }

}
