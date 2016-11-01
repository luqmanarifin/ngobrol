package com.ngobrol.server.user.dao;

import com.ngobrol.server.connector.DatabaseConnector;
import com.ngobrol.server.user.User;

import java.sql.ResultSet;
import java.sql.SQLException;

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
    String query = String.format("SELECT * FROM user WHERE username='%s';", user.getUsername());
    ResultSet rs = executeQuery(query);
    boolean ada = false;
    try {
      ada = rs.next();
      rs.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return ada;
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


}
