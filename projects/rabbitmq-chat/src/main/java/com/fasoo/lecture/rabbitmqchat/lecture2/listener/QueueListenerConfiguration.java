package com.fasoo.lecture.rabbitmqchat.lecture2.listener;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("listener")
@Configuration
public class QueueListenerConfiguration {

  @Bean
  public QueueListener listener() {
    return new QueueListener();
  }

  @Bean
  public Declarables topicBindings() {
    Queue commandQueue = new Queue("command");
    Queue userQueue = new Queue("user");
    Queue roomQueue = new Queue("room");

    TopicExchange requestTopicExchange = new TopicExchange("request");
    TopicExchange chatTopicExchange = new TopicExchange("chat");
    TopicExchange userTopicExchange = new TopicExchange("user");
    TopicExchange roomTopicExchange = new TopicExchange("room");



    return new Declarables(

        commandQueue, userQueue, roomQueue,

        requestTopicExchange, chatTopicExchange, userTopicExchange, roomTopicExchange,

        BindingBuilder.bind(commandQueue).to(requestTopicExchange).with("command.#"),
        BindingBuilder.bind(userQueue).to(userTopicExchange).with(""),
        BindingBuilder.bind(roomQueue).to(roomTopicExchange).with(""),
        BindingBuilder.bind(userTopicExchange).to(chatTopicExchange).with("*.user.#"),
        BindingBuilder.bind(roomTopicExchange).to(chatTopicExchange).with("*.room.#"),
        BindingBuilder.bind(chatTopicExchange).to(requestTopicExchange).with("chat.#"));
  }

}
