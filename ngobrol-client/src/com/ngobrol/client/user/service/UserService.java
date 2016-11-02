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
  static String queueName;

  public static String getQueueName() {
    return queueName;
  }

  public  UserService(RabbitConnector connector) throws IOException {
    this.connector = connector;

    Channel channel = connector.getChannel();
    this.queueName = channel.queueDeclare().getQueue();
  }

  public void register(String username, String password) {
    JSONObject registerMessage = new JSONObject();
    registerMessage.put("method", "register");
    registerMessage.put("username", username);
    registerMessage.put("password", password);
    registerMessage.put("queue_name", queueName);

    connector.sendMessageToServer(registerMessage.toJSONString());
  }

  public void login(String username, String password)  {
    JSONObject loginMessage = new JSONObject();
    loginMessage.put("method", "login");
    loginMessage.put("username", username);
    loginMessage.put("password", password);
    loginMessage.put("queue_name", queueName);

    connector.sendMessageToServer(loginMessage.toJSONString());
  }
}
