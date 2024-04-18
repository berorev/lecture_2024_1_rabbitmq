package com.fasoo.lecture.rabbitmqchat.lecture4.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Profile;
import org.springframework.util.StopWatch;

import com.fasoo.lecture.rabbitmqchat.lecture4.QueueName;
import com.fasoo.lecture.rabbitmqchat.lecture4.dto.Chat;

@Profile("lecture4-listener")
public class QueueListener {

  @RabbitListener(queues = "command")
  public void listenCommandQueue(Chat chat) throws InterruptedException {
    receive(chat, QueueName.COMMAND);
  }

  @RabbitListener(queues = "user")
  public void listenUserQueue(Chat chat) throws InterruptedException {
    receive(chat, QueueName.USER);
  }

  @RabbitListener(queues = "room")
  public void listenRoomQueue(Chat chat) throws InterruptedException {
    receive(chat, QueueName.ROOM);
  }

  @RabbitListener(queues = "dead-letter")
  public void listenDeadLetterQueue(Chat chat) throws InterruptedException {
    System.out.println("instance DeadLetter [x] Received '" + chat.getBody() + "'");
  }

  public void receive(Chat chat, QueueName queueName) throws InterruptedException {
    StopWatch watch = new StopWatch();
    watch.start();
    System.out.println("instance " + queueName + " [x] Received '" + chat.getBody() + "'");
    doWork(chat);
    watch.stop();
    System.out
        .println("instance " + queueName + " [x] Done in " + watch.getTotalTimeSeconds() + "s");
  }

  private void doWork(Chat chat) throws InterruptedException {
    System.out.println("  - processing...");
    Thread.sleep(1000);
  }
}
