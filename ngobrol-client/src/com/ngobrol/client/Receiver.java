package com.ngobrol.client;

import com.ngobrol.client.common.Parser;
import com.ngobrol.client.connector.RabbitConnector;
import com.ngobrol.client.group.Group;
import com.ngobrol.client.user.User;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.sun.org.apache.xpath.internal.SourceTree;

import java.io.IOException;
import java.util.List;

/**
 * Created by husni on 02/11/16.
 */
public class Receiver {
  private static final String prefix = "ngobrol_";
  private static String queueName;
  private RabbitConnector connector;
  private User user;

  private String tag;

  public void setUser(User user) {
    this.user = user;
  }

  public Receiver(RabbitConnector connector, String queueName) {
    this.connector = connector;
    this.queueName = queueName;
  }

  private void handleRequest(String message, String method) {
    if (method.equals("login_reply")) {
      if (Parser.getStatus(message).equals("ok")) {
        String token = Parser.getToken(message);
        user.setToken(token);

        try {
          Consumer consumer = new DefaultConsumer(connector.getChannel()) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
              String message = new String(body, "UTF-8");
              String description = Parser.getDescription(message);
              System.out.println(message);
              if (description != null) {
                System.out.println(description);
              }
              String method = Parser.getMethod(message);
              handleRequest(message, method);
            }
          };

          connector.getChannel().basicConsume(prefix + user.getUsername(), true, consumer);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }

    if (method.equals("send_client_reply")) {
      if (Parser.getStatus(message).equals("ok")) {

      }
    }

    if (method.equals("send_group_reply")) {
      if (Parser.getStatus(message).equals("ok")) {

      }
    }

    if (method.equals("get_friend_reply")) {
      if (Parser.getStatus(message).equals("ok")) {
        System.out.println("Teman yang Anda miliki:");
        List<String> friends = Parser.getFriends(message);
        for (String friend : friends) {
          System.out.println(friend);
        }
      }
    }

    if (method.equals("get_group_reply")) {
      if (Parser.getStatus(message).equals("ok")) {
        System.out.println("Group yang Anda miliki:");
        List<Group> groups = Parser.getGroups(message);
        for (Group g : groups) {
          System.out.println("ID : " + g.getId() + " Name: " + g.getName());
        }
      }
    }

    if (method.equals("send_client")) {
      System.out.println(Parser.getUsernameFrom(message) + " > " + Parser.getMessage(message));
    }

//    if (method.equals("send_group")) {
//      System.out.println("Pesan dari grup " + Parser.getGroupName(message));
//      System.out.println(Parser.getUsernameFrom(message) + " > " + Parser.getMessage(message));
//    }
  }

  public void run() {
    Consumer consumer = new DefaultConsumer(connector.getChannel()) {
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope,
                                 AMQP.BasicProperties properties, byte[] body) throws IOException {
        String message = new String(body, "UTF-8");
        System.out.println(Parser.getDescription(message));
        String method = Parser.getMethod(message);
        handleRequest(message, method);
      }
    };
    try {
      System.out.println("luqman jancok");
      tag = connector.getChannel().basicConsume(queueName, true, consumer);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
