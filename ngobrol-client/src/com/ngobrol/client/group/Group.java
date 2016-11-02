package com.ngobrol.client.group;


public class Group {

  private long id;
  private String name;
  private String adminUsername;

  public Group(long id, String name, String adminUsername) {
    this.id = id;
    this.name = name;
    this.adminUsername = adminUsername;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAdminUsername() {
    return adminUsername;
  }

  public void setAdminUsername(String adminUsername) {
    this.adminUsername = adminUsername;
  }
}