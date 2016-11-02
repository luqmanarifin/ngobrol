package com.ngobrol.server;

import com.ngobrol.server.connector.RabbitConnector;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

/**
 * Created by luqmanarifin on 02/11/16.
 */
public class Server extends RabbitConnector {

  private static final String QUEUE_NAME = "ngobrol";

  public Server() {
  }

  public void run() {
    Consumer consumer = new DefaultConsumer(channel) {
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope,
                                 AMQP.BasicProperties properties, byte[] body) throws IOException {
        String message = new String(body, "UTF-8");
        System.out.println(" [x] Received: " + message);
      }
    };
    try {
      channel.basicConsume(QUEUE_NAME, true, consumer);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
