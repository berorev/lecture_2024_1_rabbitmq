package com.fasoo.lecture.rabbitmqchat.lecture5.listener;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.fasoo.lecture.rabbitmqchat.lecture5.dto.CommandDto;

@Profile("lecture5-listener")
@Component
@RabbitListener(queues = "command")
public class CommandListener {
  @Autowired
  RabbitAdmin admin;

  @Autowired
  @Qualifier("room")
  TopicExchange roomTopicExchange;

  @RabbitHandler
  public String receive(CommandDto commandDto) throws InterruptedException {
    String command = commandDto.getCommand();
    String[] args = commandDto.getArguments();
    if ("create".equals(command)) {
      String roomName = args[0];

      FanoutExchange roomOneExchange = new FanoutExchange("room." + roomName);

      admin.declareExchange(roomOneExchange);
      admin.declareBinding(
          BindingBuilder.bind(roomOneExchange).to(roomTopicExchange).with("*.room." + roomName));
    } else if ("invite".equals(command)) {
      String roomName = args[0];
      String username = args[1];

      // ...
    }

    return "ok";
  }
}
