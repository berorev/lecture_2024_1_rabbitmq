package com.fasoo.lecture.rabbitmqchat.lecture5.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Profile;
import org.springframework.util.StopWatch;

import com.fasoo.lecture.rabbitmqchat.lecture5.QueueName;
import com.fasoo.lecture.rabbitmqchat.lecture5.dto.ChatDto;
import com.fasoo.lecture.rabbitmqchat.lecture5.dto.CommandDto;

@Profile("lecture5-listener")
public class QueueListener {

  @RabbitListener(queues = "user")
  public void listenUserQueue(ChatDto chatDto) throws InterruptedException {
    receiveChat(chatDto, QueueName.USER);
  }

  @RabbitListener(queues = "room")
  public void listenRoomQueue(ChatDto chatDto) throws InterruptedException {
    receiveChat(chatDto, QueueName.ROOM);
  }

  @RabbitListener(queues = "dead-letter")
  public void listenDeadLetterQueue(ChatDto chatDto) throws InterruptedException {
    System.out.println("instance DeadLetter [x] Received '" + chatDto.getBody() + "'");
  }

  public void receiveChat(ChatDto chatDto, QueueName queueName) throws InterruptedException {
    StopWatch watch = new StopWatch();
    watch.start();
    System.out.println("instance " + queueName + " [x] Received '" + chatDto.getBody() + "'");
    doWork(chatDto);
    watch.stop();
    System.out
        .println("instance " + queueName + " [x] Done in " + watch.getTotalTimeSeconds() + "s");
  }

  private void doWork(ChatDto chat) throws InterruptedException {
    System.out.println("  - processing...");
    Thread.sleep(1000);
  }
}
