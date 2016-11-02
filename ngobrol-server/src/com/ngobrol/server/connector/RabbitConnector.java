package com.ngobrol.server.connector;

import com.ngobrol.server.group.Group;
import com.ngobrol.server.group.dao.GroupDao;
import com.ngobrol.server.membership.Membership;
import com.ngobrol.server.membership.dao.MembershipDao;
import com.ngobrol.server.user.User;
import com.ngobrol.server.user.dao.UserDao;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.List;

/**
 * Created by luqmanarifin on 01/11/16.
 */
public class RabbitConnector {

  private static final String host = "localhost";
  private static final String prefix = "ngobrol_";

  private static ConnectionFactory factory = null;
  private static Connection connection = null;
  protected static Channel channel = null;

  private static GroupDao groupDao = new GroupDao();
  private static UserDao userDao = new UserDao();
  private static MembershipDao membershipDao = new MembershipDao();

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
      List<Group> groups = groupDao.getGroups();
      for (Group group : groups) {
        createGroupExchange(group.getId());
      }
      List<User> users = userDao.getUsers();
      for (User user : users) {
        createUserQueue(user.getUsername());
      }
      List<Membership> memberships = membershipDao.getMemberships();
      for (Membership membership : memberships) {
        bind(membership.getUsername(), membership.getGroupId());
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
    System.out.printf("Pesan untuk " + groupId + ": " + message);
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

  public void createQueue(String queueName) {
    try {
      channel.queueDeclare(queueName, true, false, false, null);
      System.out.println("Queue " + queueName + " berhasil dibuat.");
    } catch (IOException e) {
      System.out.println("Queue " + queueName + " sudah ada.");
    }
  }

  public void createUserQueue(String username) {
    createQueue(prefix + username);
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
