package com.fasoo.lecture.rabbitmqchat.lecture3.listener;

public enum QueueName {
  COMMAND, USER, ROOM;

  public String queueName() {
    return this.name().toLowerCase();
  }
}
