package com.ngobrol.server.user.dao;

import com.ngobrol.server.connector.DatabaseConnector;
import com.ngobrol.server.user.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by luqmanarifin on 01/11/16.
 */
public class UserDao extends DatabaseConnector {

  public UserDao() {}

  public void addUser(User user) {
    String query = "INSERT INTO user(username, password)" +
      " VALUES ('" + user.getUsername() + "', '" + user.getPassword() + "');";
    executeUpdate(query);
  }

  public boolean isUserExists(User user) {
    String query = String.format("SELECT COUNT(*) AS cnt FROM user WHERE username='%s';", user.getUsername());
    ResultSet rs = executeQuery(query);
    int ada = 0;
    try {
      if (rs.next()) {
        ada = rs.getInt("cnt");
      }
      rs.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return ada > 0;
  }

  public List<User> getUsers() {
    String query = String.format("SELECT * FROM user;");
    ResultSet rs = executeQuery(query);

    List<User> users = new ArrayList<>();
    try {
      while (rs.next()) {
        User user = new User(rs.getString("username"), rs.getString("password"), rs.getString("token"));
        users.add(user);
      }
      rs.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return users;
  }

  public User getByUsername(String username) {
    String query = String.format("SELECT * FROM user WHERE username='%s';", username);
    ResultSet rs = executeQuery(query);
    try {
      if (rs.next()) {
        return new User(rs.getString("username"), rs.getString("password"), rs.getString("token"));
      }
      rs.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return new User("", "", "");
  }

  public void editToken(String username, String token) {
    String query = String.format("UPDATE user SET token='%s' WHERE username='%s';", token, username);
    executeUpdate(query);
  }


}
