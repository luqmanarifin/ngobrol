package com.ngobrol.server.connector;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;

/**
 * Created by luqmanarifin on 01/11/16.
 */
public class RabbitConnector {

  private static final String host = "localhost";
  private static final String prefix = "ngobrol_";

  private static ConnectionFactory factory = null;
  private static Connection connection = null;
  private static Channel channel = null;

  public RabbitConnector() {
    if (channel == null) {
      initiate();
    }
  }

  private void initiate() {
    try {
      if (factory == null) {
        factory = new ConnectionFactory();
      }
      factory.setHost(host);
      if (connection == null) {
        connection = factory.newConnection();
      }
      if (channel == null) {
        channel = connection.createChannel();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void sendToClient(String username, String message) {
    try {
      channel.basicPublish("", prefix + username, null, message.getBytes());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void sendToGroup(long groupId, String message) {
    try {
      channel.basicPublish(prefix + groupId, "", null, message.getBytes());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
