package com.fasoo.lecture.rabbitmqchat.lecture4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasoo.lecture.rabbitmqchat.lecture4.dto.ResourcePathRequest;
import com.fasoo.lecture.rabbitmqchat.lecture4.dto.TopicPathRequest;
import com.fasoo.lecture.rabbitmqchat.lecture4.dto.UserPathRequest;
import com.fasoo.lecture.rabbitmqchat.lecture4.dto.VhostPathRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Profile("lecture4-listener")
@RestController
@RequestMapping("/rabbit/auth")
public class AuthorizationController {

  @Autowired
  ObjectMapper objectMapper;

  @GetMapping
  public String index() {
    return "index";
  }

  @PostMapping(path = "/user", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  public String postUser(UserPathRequest request) throws JsonProcessingException {

    System.out.println("postUser " + objectMapper.writeValueAsString(request));

    if (request.getUsername().startsWith("user") && "pass".equals(request.getPassword())) {
      return "allow administrator";
    } else {
      return "deny";
    }
  }

  @PostMapping("/vhost")
  public String postVhost(VhostPathRequest request) throws JsonProcessingException {
    if ("chat".equals(request.getVhost())) {
      return "allow";
    }

    return "deny";
  }

  @PostMapping("/resource")
  public String postResource(ResourcePathRequest request) throws JsonProcessingException {
    String username = request.getUsername();
    String resource = request.getResource(); // exchange, queue, topic
    String name = request.getName(); // resource name
    String permission = request.getPermission(); // configure, read, write

    if ("exchange".equals(resource) && "request".equals(name) && "write".equals(permission)) {
      return "allow";
    }

    if ("queue".equals(resource)
        && ("user." + username).equals(name)
        && ("configure".equals(permission)
            || "read".equals(permission)
            || "write".equals(permission))) {
      return "allow";
    }

    return "deny";
  }

  @PostMapping("/topic")
  public String postTopic(TopicPathRequest request) throws JsonProcessingException {
    String username = request.getUsername();
    String resource = request.getResource(); // topic
    String name = request.getName(); // exchange name
    String permission = request.getPermission(); // read, write
    String routing_key = request.getRouting_key(); // 보내는 메시지(permission=write) 또는 queue binding(permission=read) 할 때의 routing key

    if ("topic".equals(resource)
        && "user".equals(name)
        && "read".equals(permission)
        && ("chat.user." + username).equals(routing_key)) {
      return "allow";
    }

    if ("topic".equals(resource)
        && "request".equals(name)
        && "write".equals(permission)
        && ("chat.user." + username).equals(routing_key)) {
      return "allow";
    }

    return "deny";
  }
}
