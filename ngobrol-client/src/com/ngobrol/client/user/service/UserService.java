package com.ngobrol.client.user.service;

import com.ngobrol.client.connector.RabbitConnector;
import com.rabbitmq.client.Channel;
import org.json.simple.JSONObject;

import java.io.IOException;

/**
 * Created by husni on 02/11/16.
 */
public class UserService {
  RabbitConnector connector;

  public  UserService(RabbitConnector connector) {
    this.connector = connector;
  }

  public void register(String username, String password) throws IOException {
    Channel channel = connector.getChannel();
    String queueName = channel.queueDeclare().getQueue();

    JSONObject registerMessage = new JSONObject();
    registerMessage.put("method", "register");
    registerMessage.put("username", username);
    registerMessage.put("password", password);
    registerMessage.put("queue_name", queueName);

    connector.sendMessageToServer(registerMessage.toJSONString());
  }

  public void login(String username, String password) throws IOException {
    Channel channel = connector.getChannel();
    String queueName = channel.queueDeclare().getQueue();

    JSONObject loginMessage = new JSONObject();
    loginMessage.put("method", "login");
    loginMessage.put("username", username);
    loginMessage.put("password", password);
    loginMessage.put("queue_name", queueName);

    connector.sendMessageToServer(loginMessage.toJSONString());
  }
}
