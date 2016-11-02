package com.ngobrol.server.membership;

/**
 * Created by luqmanarifin on 02/11/16.
 */
public class Membership {

  private long id;
  private long groupId;
  private String username;

  public Membership(long id, long groupId, String username) {
    this.id = id;
    this.groupId = groupId;
    this.username = username;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getGroupId() {
    return groupId;
  }

  public void setGroupId(long groupId) {
    this.groupId = groupId;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}
