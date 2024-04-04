package com.fasoo.lecture.rabbitmqchat.lecture2.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Profile;
import org.springframework.util.StopWatch;

@Profile("lecture2-listener")
public class QueueListener {

  @RabbitListener(queues = "command")
  public void listenCommandQueue(String in) throws InterruptedException {
    receive(in, QueueName.COMMAND);
  }

  @RabbitListener(queues = "user")
  public void listenUserQueue(String in) throws InterruptedException {
    receive(in, QueueName.USER);
  }

  @RabbitListener(queues = "room")
  public void listenRoomQueue(String in) throws InterruptedException {
    receive(in, QueueName.ROOM);
  }

  public void receive(String in, QueueName queueName) throws InterruptedException {
    StopWatch watch = new StopWatch();
    watch.start();
    System.out.println("instance " + queueName + " [x] Received '" + in + "'");
    doWork(in);
    watch.stop();
    System.out
        .println("instance " + queueName + " [x] Done in " + watch.getTotalTimeSeconds() + "s");
  }

  private void doWork(String in) throws InterruptedException {
    for (char ch : in.toCharArray()) {
      if (ch == '.') {
        Thread.sleep(1000);
      }
    }
  }
}
