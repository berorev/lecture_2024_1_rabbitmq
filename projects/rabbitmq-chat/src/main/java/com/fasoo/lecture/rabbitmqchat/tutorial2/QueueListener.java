package com.fasoo.lecture.rabbitmqchat.tutorial2;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@RabbitListener(queues = "hello")
public class QueueListener {
  @RabbitHandler
  public void receive(String in) throws InterruptedException {

  }
}
