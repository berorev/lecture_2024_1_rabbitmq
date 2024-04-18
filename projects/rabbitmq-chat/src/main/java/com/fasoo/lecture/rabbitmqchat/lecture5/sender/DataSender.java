package com.fasoo.lecture.rabbitmqchat.lecture5.sender;

import java.util.Scanner;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.fasoo.lecture.rabbitmqchat.lecture5.Lecture5Config;
import com.fasoo.lecture.rabbitmqchat.lecture5.dto.ChatDto;
import com.fasoo.lecture.rabbitmqchat.lecture5.dto.CommandDto;

@Profile("lecture5-sender")
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

          CommandDto commandDto = new CommandDto();
          commandDto.setBody(message);
          commandDto.setCommand(commandAndArgs[0]);
          commandDto.setArguments(commandAndArgs[1].split("\\s"));

          Object result =
              this.template
                  .convertSendAndReceive("request", "command." + commandAndArgs[0], commandDto);
          System.out.println(" [.] Got '" + result + "'");
        } else {
          ChatDto chatDto = new ChatDto();
          chatDto.setBody(message);
          chatDto.setUsername(Lecture5Config.USERNAME);

          this.template.convertAndSend("request", "chat.user." + Lecture5Config.USERNAME, chatDto);
        }
      }
    }
  }

}
