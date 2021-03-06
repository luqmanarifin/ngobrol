package com.ngobrol.server.group.dao;

import com.ngobrol.server.connector.DatabaseConnector;
import com.ngobrol.server.group.Group;
import com.ngobrol.server.user.User;
import com.sun.corba.se.spi.orbutil.fsm.Guard;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

  public List<Group> getGroups() {
    String query = String.format("SELECT * FROM `group`;");
    ResultSet rs = executeQuery(query);
    List<Group> groups = new ArrayList<>();

    try {
      while (rs.next()) {
        Group group = new Group(rs.getLong("id"), rs.getString("name"), rs.getString("admin_username"));
        groups.add(group);
      }
      rs.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return groups;
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
