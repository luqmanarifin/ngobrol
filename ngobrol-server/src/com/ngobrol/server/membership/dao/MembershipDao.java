package com.ngobrol.server.membership.dao;

import com.ngobrol.server.connector.DatabaseConnector;
import com.ngobrol.server.membership.Membership;
import com.ngobrol.server.user.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by luqmanarifin on 01/11/16.
 */
public class MembershipDao extends DatabaseConnector {

  public MembershipDao() {
  }

  public void addToGroup(User user, long groupId) {
    String query = String.format("INSERT INTO membership(group_id, username) VALUES(%d, '%s');",
      (int) groupId, user.getUsername());
    executeUpdate(query);
  }

  public void deleteFromGroup(User user, long groupId) {
    String query = String.format("DELETE FROM membership WHERE group_id=%d AND username='%s';",
      (int) groupId, user.getUsername());
    executeUpdate(query);
  }

  public List<Membership> getMemberships() {
    String query = String.format("SELECT * FROM `membership`;");
    ResultSet rs = executeQuery(query);

    List<Membership> memberships = new ArrayList<>();
    try {
      while (rs.next()) {
        Membership membership = new Membership(rs.getLong("id"), rs.getLong("group_id"), rs.getString("username"));
        memberships.add(membership);
      }
      rs.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return memberships;
  }

  public List<Long> getMembership(User user) {
    String query = String.format("SELECT * FROM `membership` WHERE username='%s';", user.getUsername());
    ResultSet rs = executeQuery(query);
    List<Long> groupIds = new ArrayList<>();
    try {
      while (rs.next()) {
        groupIds.add(rs.getLong("group_id"));
      }
      rs.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return groupIds;
  }

  public boolean isMember(User user, long groupId) {
    String query = String.format("SELECT COUNT(*) as cnt FROM `membership` WHERE username='%s' AND group_id=%d;",
      user.getUsername(), groupId);
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

}
