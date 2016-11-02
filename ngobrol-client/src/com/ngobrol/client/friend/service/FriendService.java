package com.ngobrol.client.friend.service;

import com.ngobrol.client.connector.RabbitConnector;
import com.ngobrol.client.user.User;
import org.json.simple.JSONObject;

/**
 * Created by husni on 02/11/16.
 */
public class FriendService {
  private RabbitConnector connector;
  private User user;

  public void setUser(User user) {
    this.user = user;
  }

  public FriendService(RabbitConnector connector) {
    this.connector = connector;

  }

  public void addFriend(String username) {
    JSONObject addFriendMessage = new JSONObject();
    addFriendMessage.put("method", "add_friend");
    addFriendMessage.put("username_from", user.getUsername());
    addFriendMessage.put("username_to", username);
    addFriendMessage.put("token", user.getToken());

    connector.sendMessageToServer(addFriendMessage.toJSONString());
  }

  public void getFriend() {
    JSONObject getFriendMessage = new JSONObject();
    getFriendMessage.put("method", "get_friend");
    getFriendMessage.put("username", user.getUsername());
    getFriendMessage.put("token", user.getToken());

    connector.sendMessageToServer(getFriendMessage.toJSONString());
  }
}
