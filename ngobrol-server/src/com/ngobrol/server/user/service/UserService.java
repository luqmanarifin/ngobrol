package com.ngobrol.server.user.service;

import com.ngobrol.server.common.SessionIdentifierGenerator;
import com.ngobrol.server.common.Validator;
import com.ngobrol.server.friend.dao.FriendDao;
import com.ngobrol.server.group.dao.GroupDao;
import com.ngobrol.server.membership.dao.MembershipDao;
import com.ngobrol.server.user.User;
import com.ngobrol.server.user.dao.UserDao;
import com.ngobrol.server.user.socket.UserSocket;

/**
 * Created by luqmanarifin on 01/11/16.
 */
public class UserService {

  UserDao userDao;
  UserSocket userSocket;
  Validator validator;
  GroupDao groupDao;
  FriendDao friendDao;
  MembershipDao membershipDao;

  public UserService() {
    userDao = new UserDao();
    userSocket = new UserSocket();
    validator = new Validator();
    groupDao = new GroupDao();
    friendDao = new FriendDao();
    membershipDao = new MembershipDao();
  }

  public void registerUser(String queueName, User user) {
    if (userDao.isUserExists(user)) {
      userSocket.registerUser(queueName, "error", "Username telah dipakai. Silakan cari yang lain.");
      return;
    }
    userDao.addUser(user);

    // bikin queue baru
    userSocket.createUserQueue(user.getUsername());

    userSocket.registerUser(queueName, "ok", "Akun berhasil dibuat.");
  }

  public void loginUser(String queueName, User user) {
    userSocket.createUserQueue(user.getUsername());
    if (!userDao.isUserExists(user)) {
      userSocket.loginUser(queueName, "error", "Username tidak ada.", "");
      return;
    }
    User correctUser = userDao.getByUsername(user.getUsername());
    if (!correctUser.getPassword().equals(user.getPassword())) {
      userSocket.loginUser(queueName, "error", "Password salah.", "");
      return;
    }
    String token = SessionIdentifierGenerator.nextSessionId();
    userDao.editToken(user.getUsername(), token);
    userSocket.loginUser(queueName, "ok", "Login berhasil.", token);
  }

  public void sendToClient(User sender, User destination, String message) {
    if (!validator.validateToken(sender)) {
      userSocket.sendToClient(sender, "error", "Token invalid.");
      return;
    }
    if (!userDao.isUserExists(destination)) {
      userSocket.sendToClient(sender, "error", "Destination user not exists.");
      return;
    }
    if (!friendDao.isFriend(sender, destination)) {
      userSocket.sendToClient(sender, "error", "Kamu bukan teman " + destination.getUsername() + ". Jadilah teman dahulu baru ngechat.");
      return;
    }
    userSocket.sendMessageToClient(sender, destination, message);
    userSocket.sendToClient(sender, "ok", "Message berhasil dikirim");
  }

  public void sendToGroup(User sender, long groupId, String message) {
    if (!validator.validateToken(sender)) {
      userSocket.sendToGroup(sender, "error", "Token invalid.");
      return;
    }
    if (!groupDao.isGroupExists(groupId)) {
      userSocket.sendToGroup(sender, "error", "Group tidak ada.");
      return;
    }
    if (!membershipDao.isMember(sender, groupId)) {
      userSocket.sendToGroup(sender, "error", "Kamu bukan anggota grup ini.");
      return;
    }
    userSocket.sendMessageToGroup(sender, groupDao.getGroup(groupId), message);
    userSocket.sendToGroup(sender, "ok", "Message berhasil dikirim");
  }

}
