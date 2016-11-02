package com.ngobrol.client.connector;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by husni on 02/11/16.
 */

public class RabbitConnector {
  private static final String PREFIX = "ngobrol_";
  private static final String SERVER_QUEUE = "ngobrol";

  private static ConnectionFactory factory = null;
  private static Connection connection = null;
  private static Channel channel = null;

  public static Channel getChannel() {
    return channel;
  }

  public RabbitConnector(String host) {
    if (channel == null) {
      initiate(host);
    }

  }

  private void initiate(String host) {
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

  public void sendMessageToServer(String message) {
    try {
      channel.queueDeclare(SERVER_QUEUE, true, false, false, null);
      channel.basicPublish("", SERVER_QUEUE, null, message.getBytes("UTF-8"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void close() {
    try {
      channel.close();
      connection.close();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (TimeoutException e) {
      e.printStackTrace();
    }
  }
}
