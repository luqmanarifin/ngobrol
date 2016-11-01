package com.ngobrol.server.connector;

import java.sql.*;

/**
 * Created by luqmanarifin on 01/11/16.
 */

public class DatabaseConnector {

  // JDBC driver name and database URL
  static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
  static final String DB_URL = "jdbc:mysql://localhost/ngobrol";

  //  Database credentials
  static final String USER = "ngobrol";
  static final String PASS = "password";

  private static Connection conn = null;
  private static Statement stmt = null;

  public DatabaseConnector() {
    if (stmt == null) {
      initiate();
    }
  }

  private void initiate() {
    try {
      // Register JDBC driver
      Class.forName("com.mysql.jdbc.Driver");

      // Get connection
      if (conn == null) {
        conn = DriverManager.getConnection(DB_URL, USER, PASS);
      }

      // Get statement
      if (stmt == null) {
        stmt = conn.createStatement();
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void executeUpdate(String query) {
    System.out.println(query);
    try {
      stmt.executeUpdate(query);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public ResultSet executeQuery(String query) {
    try {
      return stmt.executeQuery(query);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

}