package com.fasoo.lecture.rabbitmqchat.lecture4;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.ContentTypeDelegatingMessageConverter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.Jackson2XmlMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("lecture4")
@Configuration
public class Lecture4Config {
  public static final String USERNAME = "hyhan";

  @Bean("request")
  public TopicExchange requestTopicExchange() {
    return new TopicExchange("request");
  }

  @Bean
  public MessageConverter messageConverter() {
    ContentTypeDelegatingMessageConverter converter = new ContentTypeDelegatingMessageConverter();

    converter.addDelegate("application/json", new Jackson2JsonMessageConverter());
    converter.addDelegate("application/xml", new Jackson2XmlMessageConverter());

    return converter;
  }
}
