package com.fasoo.lecture.rabbitmqchat.lecture4.sender;

import java.util.Scanner;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.fasoo.lecture.rabbitmqchat.lecture4.Lecture4Config;
import com.fasoo.lecture.rabbitmqchat.lecture4.dto.Chat;
import com.fasoo.lecture.rabbitmqchat.lecture4.dto.Command;

@Profile("lecture4-sender")
@Component
public class DataSender implements CommandLineRunner {
  @Autowired
  private RabbitTemplate template;

  @Override
  public void run(String... args) throws Exception {

    try (Scanner scanner = new Scanner(System.in)) {
      while (true) {
        System.out.println("INPUT : ");
        String message = scanner.nextLine();

        if (message.startsWith("/")) {
          String[] commandAndArgs = message.substring(1).split("\\s", 2);

          Command command = new Command();
          command.setBody(message);
          command.setCommand(commandAndArgs[0]);
          command.setArguments(commandAndArgs[1].split("\\s"));

          this.template.convertAndSend("request", "command." + commandAndArgs[0], command);
        } else {
          Chat chat = new Chat();
          chat.setBody(message);
          chat.setUsername(Lecture4Config.USERNAME);

          this.template.convertAndSend("request", "chat.user." + Lecture4Config.USERNAME, chat);
        }
      }
    }
  }

}
