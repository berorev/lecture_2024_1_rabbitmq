package com.fasoo.lecture.rabbitmqchat.lecture4.sender;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.ConditionalRejectingErrorHandler;
import org.springframework.amqp.rabbit.listener.FatalExceptionStrategy;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.util.ErrorHandler;

import com.fasoo.lecture.rabbitmqchat.lecture4.Lecture4Config;
import com.fasoo.lecture.rabbitmqchat.lecture4.QueueName;
import com.fasoo.lecture.rabbitmqchat.lecture4.error.CustomErrorHandler;
import com.fasoo.lecture.rabbitmqchat.lecture4.error.CustomFatalExceptionStrategy;

@Profile("lecture4-sender")
@Configuration
public class DataSenderConfiguration {

  @Bean
  public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
      ConnectionFactory connectionFactory,
      MessageConverter messageConverter) {
    SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
    factory.setConnectionFactory(connectionFactory);
    factory.setMessageConverter(messageConverter);
    factory.setErrorHandler(rejectErrorHandler());
    factory.setPrefetchCount(3);
    factory.setConcurrentConsumers(3);
    factory.setMaxConcurrentConsumers(3);

    return factory;
  }

  @Bean
  public SimpleRabbitListenerContainerFactory chatListenerContainerFactory(
      ConnectionFactory connectionFactory,
      MessageConverter messageConverter) {
    SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
    factory.setConnectionFactory(connectionFactory);
    factory.setMessageConverter(messageConverter);
    factory.setErrorHandler(rejectErrorHandler());
    factory.setPrefetchCount(250);
    factory.setConcurrentConsumers(1);
    factory.setMaxConcurrentConsumers(1);

    return factory;
  }

  @Bean
  public ErrorHandler customErrorHandler() {
    return new CustomErrorHandler();
  }

  @Bean
  public ErrorHandler rejectErrorHandler() {
    return new ConditionalRejectingErrorHandler(customExceptionStrategy());
  }

  @Bean
  public FatalExceptionStrategy customExceptionStrategy() {
    return new CustomFatalExceptionStrategy();
  }

  @Bean
  public RabbitTemplate
      rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
    RabbitTemplate template = new RabbitTemplate(connectionFactory);
    template.setMessageConverter(messageConverter);
    return template;
  }

  public Queue myUserQueue() {
    return QueueBuilder.durable("user." + Lecture4Config.USERNAME)
        .deadLetterExchange("")
        .deadLetterRoutingKey(QueueName.DEAD_LETTER_QUEUE.queueName())
        .build();
  }
}
