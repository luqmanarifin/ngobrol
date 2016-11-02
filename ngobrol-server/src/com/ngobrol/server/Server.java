package com.ngobrol.server;

import com.ngobrol.server.common.Parser;
import com.ngobrol.server.connector.RabbitConnector;
import com.ngobrol.server.friend.service.FriendService;
import com.ngobrol.server.group.service.GroupService;
import com.ngobrol.server.membership.service.MembershipService;
import com.ngobrol.server.user.User;
import com.ngobrol.server.user.service.UserService;
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

  private static FriendService friendService;
  private static GroupService groupService;
  private static MembershipService membershipService;
  private static UserService userService;

  public Server() {
    friendService = new FriendService();
    groupService = new GroupService();
    membershipService = new MembershipService();
    userService = new UserService();
  }

  private void handleRequest(String message, String method) {
    if (method.equals("register")) {
      User user = new User(Parser.getUsername(message), Parser.getPassword(message), "");
      userService.registerUser(Parser.getQueueName(message), user);

    } else if (method.equals("login")) {
      User user = new User(Parser.getUsername(message), Parser.getPassword(message), "");
      userService.loginUser(Parser.getQueueName(message), user);

    } else if (method.equals("add_friend")) {
      User from = new User(Parser.getUsernameFrom(message), "", Parser.getToken(message));
      User to = new User(Parser.getUsernameTo(message), "", "");
      friendService.addFriend(from, to);

    } else if (method.equals("get_friend")) {
      User user = new User(Parser.getUsername(message), "", Parser.getToken(message));
      friendService.getFriends(user);

    } else if (method.equals("create_group")) {
      User user = new User(Parser.getUsername(message), "", Parser.getToken(message));
      groupService.createGroup(user, Parser.getGroupName(message));

    } else if (method.equals("get_group")) {
      User user = new User(Parser.getUsername(message), "", Parser.getToken(message));
      membershipService.getGroups(user);

    } else if (method.equals("add_member_to_group")) {
      User adder = new User(Parser.getUsernameAdder(message), "", Parser.getToken(message));
      User toAdd = new User(Parser.getUsernameToAdd(message), "", "");
      membershipService.addMemberToGroup(adder, toAdd, Parser.getGroupId(message));

    } else if (method.equals("quit_from_group")) {
      User user = new User(Parser.getUsername(message), "", Parser.getToken(message));
      membershipService.quitFromGroup(user, Parser.getGroupId(message));

    } else if (method.equals("send_client")) {
      User from = new User(Parser.getUsernameFrom(message), "", Parser.getToken(message));
      User to = new User(Parser.getUsernameTo(message), "", "");
      userService.sendToClient(from, to, Parser.getMessage(message));

    } else if (method.equals("send_group")) {
      User from = new User(Parser.getUsernameFrom(message), "", Parser.getToken(message));
      userService.sendToGroup(from, Parser.getGroupIdTo(message), Parser.getMessage(message));

    } else {
      System.out.println("Message tidak dikenali.");
    }
  }

  public void run() {
    Consumer consumer = new DefaultConsumer(channel) {
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope,
                                 AMQP.BasicProperties properties, byte[] body) throws IOException {
        String message = new String(body, "UTF-8");
        System.out.println(" [x] Received: " + message);
        String method = Parser.getMethod(message);
        handleRequest(message, method);
      }
    };
    try {
      channel.basicConsume(QUEUE_NAME, true, consumer);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
