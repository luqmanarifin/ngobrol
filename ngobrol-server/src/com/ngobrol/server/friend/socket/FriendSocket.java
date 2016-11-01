package com.ngobrol.server.friend.socket;

import com.ngobrol.server.connector.RabbitConnector;
import com.ngobrol.server.user.User;
import org.json.simple.JSONObject;

import java.util.List;

/**
 * Created by luqmanarifin on 01/11/16.
 */
public class FriendSocket extends RabbitConnector {

  public FriendSocket() {
  }

  public void addFriend(User user, String status, String description) {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("method", "add_friend_reply");
    jsonObject.put("status", status);
    jsonObject.put("description", description);
    sendToClient(user.getUsername(), jsonObject.toJSONString());
  }

  public void getFriends(User user, String status, String description, List<String> usernames) {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("method", "get_friend_reply");
    jsonObject.put("status", status);
    jsonObject.put("description", description);
    jsonObject.put("usernames", usernames);
    sendToClient(user.getUsername(), jsonObject.toJSONString());
  }
}
