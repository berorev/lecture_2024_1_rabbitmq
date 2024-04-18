package com.fasoo.lecture.rabbitmqchat.lecture4;

public enum QueueName {
  COMMAND,
  USER,
  ROOM,
  DEAD_LETTER_QUEUE;

  public String queueName() {
    return this.name().toLowerCase();
  }
}
