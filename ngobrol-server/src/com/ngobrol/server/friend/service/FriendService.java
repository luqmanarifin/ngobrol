package com.ngobrol.server.friend.service;

import com.ngobrol.server.common.Validator;
import com.ngobrol.server.friend.dao.FriendDao;
import com.ngobrol.server.friend.socket.FriendSocket;
import com.ngobrol.server.user.User;
import com.ngobrol.server.user.dao.UserDao;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by luqmanarifin on 01/11/16.
 */
public class FriendService {

  private FriendDao friendDao;
  private UserDao userDao;
  private Validator validator;
  private FriendSocket friendSocket;

  public FriendService() {
    friendDao = new FriendDao();
    userDao = new UserDao();
    validator = new Validator();
    friendSocket = new FriendSocket();
  }

  public void addFriend(User adder, User toAdd) {
    if (!validator.validateToken(adder)) {
      friendSocket.addFriend(adder, "error", "Token invalid.");
      return;
    }
    if (adder.getUsername().equals(toAdd.getUsername())) {
      friendSocket.addFriend(adder, "error", "Kamu tidak bisa menambah dirimu sendiri sebagai teman.");
      return;
    }
    if (!userDao.isUserExists(adder) || !userDao.isUserExists(toAdd)) {
      friendSocket.addFriend(adder, "error", "Username tidak ada.");
      return;
    }
    friendDao.addFriend(adder, toAdd);
    friendDao.addFriend(toAdd, adder);
    friendSocket.addFriend(adder, "ok", "Teman berhasil ditambahkan.");
  }

  public void getFriends(User user) {
    if (!validator.validateToken(user)) {
      friendSocket.getFriends(user, "error", "Token invalid.", new ArrayList<>());
      return;
    }
    List<User> friends = friendDao.getFriends(user);
    List<String> usernames = friends.stream().map(User::getUsername).collect(Collectors.toList());
    friendSocket.getFriends(user, "ok", "Teman berhasil diambil.", usernames);
  }

}
