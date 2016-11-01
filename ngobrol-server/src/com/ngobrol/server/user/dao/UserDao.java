package com.ngobrol.server.user.dao;

import com.ngobrol.server.connector.DatabaseConnector;
import com.ngobrol.server.user.User;

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



}
