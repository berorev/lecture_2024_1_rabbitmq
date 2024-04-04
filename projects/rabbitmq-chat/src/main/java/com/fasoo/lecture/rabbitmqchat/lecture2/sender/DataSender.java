package com.fasoo.lecture.rabbitmqchat.lecture2.sender;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;

@Profile("sender")
public class DataSender {
  @Autowired
  private RabbitTemplate template;

  @Autowired
  private TopicExchange topic;

  private AtomicInteger index = new AtomicInteger(0);
  private AtomicInteger count = new AtomicInteger(0);

  private final String[] keys = {"quick.orange.rabbit", "lazy.orange.elephant", "quick.orange.fox",
      "lazy.brown.fox", "lazy.pink.rabbit", "quick.brown.fox"};

  @Scheduled(fixedDelay = 1000, initialDelay = 500)
  public void send() {
    StringBuilder builder = new StringBuilder("Hello to ");
    if (this.index.incrementAndGet() == keys.length) {
      this.index.set(0);
    }
    String key = keys[this.index.get()];
    builder.append(key).append(' ');
    builder.append(this.count.incrementAndGet());

    String message = builder.toString();
    template.convertAndSend(topic.getName(), key, message);
    System.out.println(" [x] Sent '" + message + "'");
  }

}
