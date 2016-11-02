package com.ngobrol.client;

import com.ngobrol.client.chat.service.ChatService;
import com.ngobrol.client.connector.RabbitConnector;
import com.ngobrol.client.friend.service.FriendService;
import com.ngobrol.client.group.service.GroupService;
import com.ngobrol.client.user.User;
import com.ngobrol.client.user.service.UserService;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;
import java.util.Scanner;

/**
 * Created by husni on 02/11/16.
 */
public class Main {
  public static void main(String[] args) {
    RabbitConnector connector = new RabbitConnector("192.168.43.24");
    User user = null;

    try {
      Scanner scanner = new Scanner(System.in);
      UserService us = new UserService(connector);
      FriendService fs = new FriendService(connector);
      GroupService gs = new GroupService(connector);
      ChatService cs = new ChatService(connector);

      Receiver receiver = new Receiver(connector, us.getQueueName());
      receiver.run();

      System.out.println("*********** SIMPLE CHATTING ***********");
      System.out.println("Pilih menu: ");

      System.out.println("register");
      System.out.println("login");
      System.out.println("add_friend");
      System.out.println("get_friend");
      System.out.println("create_group");
      System.out.println("get_group");
      System.out.println("add_member");
      System.out.println("quit_group");
      System.out.println("chat");
      System.out.println("chat_group");
      System.out.println("exit");
      System.out.println();

      String menu = "";
      while (!menu.equals("exit")) {
        menu = scanner.nextLine();

        if (menu.equals("register")){
          System.out.println("masukan username: ");
          String username = scanner.nextLine();
          System.out.println("masukan password: ");
          String password = scanner.nextLine();

          us.register(username, password);
        }

        if (menu.equals("login")) {
          System.out.println("masukan username: ");
          String username = scanner.nextLine();
          System.out.println("masukan password: ");
          String password = scanner.nextLine();

          user = new User(username, password);
          receiver.setUser(user);
          fs.setUser(user);
          gs.setUser(user);
          cs.setUser(user);

          us.login(user.getUsername(), user.getPassword());
        }

        if (menu.equals("add_friend")) {
          System.out.println("masukan username teman yang ingin ditambahkan: ");
          String username = scanner.nextLine();

          fs.addFriend(username);
        }

        if (menu.equals("get_friend")) {
          fs.getFriend();
        }

        if (menu.equals("create_group")) {
          System.out.println("masukan nama group: ");
          String groupName = scanner.nextLine();
          gs.createGroup(groupName);
        }

        if (menu.equals("add_member")) {
          System.out.println("masukan username yang ingin dimasukan: ");
          String username = scanner.nextLine();

          System.out.println("masukan id group: ");
          int groupId = Integer.parseInt(scanner.nextLine());

          gs.addMembertoGroup(groupId, username);
        }

        if (menu.equals("chat")) {
          System.out.println("masukan username penerima: ");
          String username = scanner.nextLine();

          System.out.println("masukan pesan Anda: ");
          String message = scanner.nextLine();

          System.out.println(user.getUsername() + " > " + message);
          cs.sendChat(username, message);
        }

        if (menu.equals("get_group")) {
          gs.getGroup();
        }

        if (menu.equals("chat_group")) {
          System.out.println("masukan id group: ");
          int groupId = Integer.parseInt(scanner.nextLine());

          System.out.println("masukan pesan Anda: ");
          String message = scanner.nextLine();

          gs.sendChatToGroup(groupId, message);
        }

        if (menu.equals("quit_group")) {
          System.out.println("masukan id group: ");
          int groupId = Integer.parseInt(scanner.nextLine());

          gs.quitFromGroup(groupId);
        }

        if (menu.equals("check_token")) {
          System.out.println(user.getToken());
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    connector.close();
  }
}
