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
  protected static Channel channel = null;

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

  public void sendToQueue(String queueName, String message) {
    try {
      channel.basicPublish("", queueName, null, message.getBytes());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void createGroupExchange(long groupId) {
    try {
      channel.exchangeDeclare(prefix + groupId, "fanout");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void createUserQueue(String username) {
    try {
      channel.queueDeclare(prefix + username, true, false, false, null);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void bind(String username, long groupId) {
    try {
      channel.queueBind(prefix + username, prefix + groupId, "");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void unbind(String username, long groupId) {
    try {
      channel.queueUnbind(prefix + username, prefix + groupId, "");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
