package com.fasoo.lecture.rabbitmqchat.lecture2.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Profile;
import org.springframework.util.StopWatch;

@Profile("listener")
public class QueueListener {

  @RabbitListener(queues = "command")
  public void listenCommandQueue(String in) throws InterruptedException {
    receive(in, 1);
  }

  @RabbitListener(queues = "user")
  public void listenUserQueue(String in) throws InterruptedException {
    receive(in, 1);
  }

  public void receive(String in, int receiver) throws InterruptedException {
    StopWatch watch = new StopWatch();
    watch.start();
    System.out.println("instance " + receiver + " [x] Received '" + in + "'");
    doWork(in);
    watch.stop();
    System.out
        .println("instance " + receiver + " [x] Done in " + watch.getTotalTimeSeconds() + "s");
  }

  private void doWork(String in) throws InterruptedException {
    for (char ch : in.toCharArray()) {
      if (ch == '.') {
        Thread.sleep(1000);
      }
    }
  }
}
