package com.fasoo.lecture.rabbitmqchat.lecture2.listener;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("lecture2-listener")
@Configuration
public class QueueListenerConfiguration {

  @Bean
  public QueueListener listener() {
    return new QueueListener();
  }

  @Bean
  public Declarables topicBindings(@Qualifier("request") TopicExchange requestTopicExchange) {
    Queue commandQueue = new Queue(QueueName.COMMAND.queueName());
    Queue userQueue = new Queue(QueueName.USER.queueName());
    Queue roomQueue = new Queue(QueueName.ROOM.queueName());

    TopicExchange chatTopicExchange = new TopicExchange("chat");
    TopicExchange userTopicExchange = new TopicExchange("user");
    TopicExchange roomTopicExchange = new TopicExchange("room");

    return new Declarables(

        commandQueue, userQueue, roomQueue,

        requestTopicExchange, chatTopicExchange, userTopicExchange, roomTopicExchange,

        BindingBuilder.bind(commandQueue).to(requestTopicExchange).with("command.#"),
        BindingBuilder.bind(userQueue).to(userTopicExchange).with("#"),
        BindingBuilder.bind(roomQueue).to(roomTopicExchange).with("#"),
        BindingBuilder.bind(userTopicExchange).to(chatTopicExchange).with("*.user.#"),
        BindingBuilder.bind(roomTopicExchange).to(chatTopicExchange).with("*.room.#"),
        BindingBuilder.bind(chatTopicExchange).to(requestTopicExchange).with("chat.#"));
  }

}
