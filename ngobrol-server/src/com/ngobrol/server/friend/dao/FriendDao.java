package com.ngobrol.server.friend.dao;

import com.ngobrol.server.connector.DatabaseConnector;
import com.ngobrol.server.user.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by luqmanarifin on 01/11/16.
 */
public class FriendDao extends DatabaseConnector {

  public FriendDao() {
  }

  public boolean isFriend(User a, User b) {
    String query = String.format("SELECT * FROM friend WHERE username='%s' AND friend_username='%s';",
      a.getUsername(), b.getUsername());

    ResultSet rs = executeQuery(query);
    try {
      boolean ada = rs.next();
      rs.close();
      return ada;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  public void addFriend(User a, User b) {
    String query = String.format("INSERT INTO friend(username, friend_username) VALUES('%s', '%s');",
      a.getUsername(), b.getUsername());
    executeUpdate(query);
  }

  public List<User> getFriends(User user) {
    String query = String.format("SELECT * FROM friend WHERE username='%s';", user.getUsername());
    ResultSet rs = executeQuery(query);

    List<User> friends = new ArrayList<>();
    try {
      while (rs.next()) {
        friends.add(new User(rs.getString("username"), rs.getString("password"), rs.getString("token")));
      }
      rs.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return friends;
  }
}
