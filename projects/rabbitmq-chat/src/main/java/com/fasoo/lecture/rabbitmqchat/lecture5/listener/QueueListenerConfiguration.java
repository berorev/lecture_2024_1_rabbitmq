package com.fasoo.lecture.rabbitmqchat.lecture5.listener;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.fasoo.lecture.rabbitmqchat.lecture5.QueueName;

@Profile("lecture5-listener")
@Configuration
public class QueueListenerConfiguration {

  @Bean
  public RabbitAdmin amqpAdmin(ConnectionFactory connectionFactory) {
    return new RabbitAdmin(connectionFactory);
  }

  @Bean
  public QueueListener listener() {
    return new QueueListener();
  }

  @Bean("room")
  public TopicExchange roomTopicExchange() {
    return new TopicExchange("room");
  }

  @Bean
  public Declarables topicBindings(
      @Qualifier("request") TopicExchange requestTopicExchange,
      @Qualifier("user") TopicExchange userTopicExchange) {
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

    TopicExchange roomTopicExchange = roomTopicExchange();

    return new Declarables(

        deadLetterQueue,
        commandQueue,
        userQueue,
        roomQueue,

        requestTopicExchange,
        chatTopicExchange,
        // userTopicExchange,
        // roomTopicExchange,

        BindingBuilder.bind(commandQueue).to(requestTopicExchange).with("command.#"),
        BindingBuilder.bind(userQueue).to(userTopicExchange).with("#"),
        BindingBuilder.bind(roomQueue).to(roomTopicExchange).with("#"),
        BindingBuilder.bind(userTopicExchange).to(chatTopicExchange).with("*.user.#"),
        BindingBuilder.bind(roomTopicExchange).to(chatTopicExchange).with("*.room.#"),
        BindingBuilder.bind(chatTopicExchange).to(requestTopicExchange).with("chat.#"));
  }

}
