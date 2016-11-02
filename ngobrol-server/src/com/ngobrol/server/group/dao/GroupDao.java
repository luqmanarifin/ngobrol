package com.ngobrol.server.group.dao;

import com.ngobrol.server.connector.DatabaseConnector;
import com.ngobrol.server.group.Group;
import com.ngobrol.server.user.User;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by luqmanarifin on 01/11/16.
 */
public class GroupDao extends DatabaseConnector {

  public GroupDao() {
  }

  public void addGroup(User user, String groupName) {
    String query = String.format("INSERT INTO `group`(name, admin_username) VALUES ('%s', '%s');",
      groupName, user.getUsername());
    executeUpdate(query);
  }

  public Group getLastGroup(User admin, String groupName) {
    String query = String.format("SELECT * FROM `group` WHERE name='%s' AND admin_username='%s' ORDER BY id DESC;",
      groupName, admin.getUsername());
    ResultSet rs = executeQuery(query);
    try {
      if (rs.next()) {
        return new Group(rs.getLong("id"), rs.getString("name"), rs.getString("admin_username"));
      }
      rs.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return new Group(0, "", "");
  }

  public Group getGroup(long groupId) {
    String query = String.format("SELECT * FROM `group` WHERE id=%d;", groupId);
    ResultSet rs = executeQuery(query);

    try {
      if (rs.next()) {
        return new Group(rs.getLong("id"), rs.getString("name"), rs.getString("admin_username"));
      }
      rs.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return new Group(0, "", "");
  }

  public boolean isGroupExists(long groupId) {
    String query = String.format("SELECT COUNT(*) as cnt FROM `group` WHERE id=%d", groupId);
    ResultSet rs = executeQuery(query);

    int ada = 0;
    try {
      if (rs.next()) {
        ada = rs.getInt("cnt");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return ada > 0;
  }

}
