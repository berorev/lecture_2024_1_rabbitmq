package com.fasoo.lecture.rabbitmqchat.lecture4.listener;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.fasoo.lecture.rabbitmqchat.lecture4.QueueName;

@Profile("lecture4-listener")
@Configuration
public class QueueListenerConfiguration {

  @Bean
  public QueueListener listener() {
    return new QueueListener();
  }

  @Bean
  public Declarables topicBindings(@Qualifier("request") TopicExchange requestTopicExchange) {
    Queue deadLetterQueue = new Queue(QueueName.DEAD_LETTER_QUEUE.queueName());
    Queue commandQueue = new Queue(QueueName.COMMAND.queueName());
    // Queue userQueue = new Queue(QueueName.USER.queueName());
    Queue userQueue =
        QueueBuilder.durable(QueueName.USER.queueName())
            .deadLetterExchange("")
            .deadLetterRoutingKey(QueueName.DEAD_LETTER_QUEUE.queueName())
            .build();
    Queue roomQueue = new Queue(QueueName.ROOM.queueName());

    TopicExchange chatTopicExchange = new TopicExchange("chat");

    FanoutExchange userFanoutExchange = new FanoutExchange("user");
    FanoutExchange roomFanoutExchange = new FanoutExchange("room");

    return new Declarables(

        deadLetterQueue,
        commandQueue,
        userQueue,
        roomQueue,

        requestTopicExchange,
        chatTopicExchange,
        userFanoutExchange,
        roomFanoutExchange,

        BindingBuilder.bind(commandQueue).to(requestTopicExchange).with("command.#"),
        BindingBuilder.bind(userQueue).to(userFanoutExchange),
        BindingBuilder.bind(roomQueue).to(roomFanoutExchange),
        BindingBuilder.bind(userFanoutExchange).to(chatTopicExchange).with("*.user.#"),
        BindingBuilder.bind(roomFanoutExchange).to(chatTopicExchange).with("*.room.#"),
        BindingBuilder.bind(chatTopicExchange).to(requestTopicExchange).with("chat.#"));
  }

}
