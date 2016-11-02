package com.ngobrol.server.user.socket;

import com.ngobrol.server.connector.RabbitConnector;
import com.ngobrol.server.group.Group;
import com.ngobrol.server.user.User;
import org.json.simple.JSONObject;

/**
 * Created by luqmanarifin on 01/11/16.
 */
public class UserSocket extends RabbitConnector {

  public UserSocket() {
  }

  public void registerUser(String queueName, String status, String description) {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("method", "register_reply");
    jsonObject.put("status", status);
    jsonObject.put("description", description);
    sendToQueue(queueName, jsonObject.toJSONString());
  }

  public void loginUser(String queueName, String status, String description, String token) {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("method", "login_reply");
    jsonObject.put("status", status);
    jsonObject.put("description", description);
    jsonObject.put("token", token);
    sendToQueue(queueName, jsonObject.toJSONString());
  }

  public void sendToClient(User user, String status, String description) {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("method", "send_client_reply");
    jsonObject.put("status", status);
    jsonObject.put("description", description);
    sendToClient(user.getUsername(), jsonObject.toJSONString());
  }

  public void sendToGroup(User user, String status, String description) {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("method", "send_group_reply");
    jsonObject.put("status", status);
    jsonObject.put("description", description);
    sendToClient(user.getUsername(), jsonObject.toJSONString());
  }

  public void sendMessageToClient(User sender, User destination, String message) {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("method", "send_client");
    jsonObject.put("username_from", sender.getUsername());
    jsonObject.put("message", message);
    sendToClient(destination.getUsername(), jsonObject.toJSONString());
  }

  public void sendMessageToGroup(User sender, Group destination, String message) {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("method", "send_group");
    jsonObject.put("username_from", sender.getUsername());
    jsonObject.put("group_id_to", destination.getId());
    jsonObject.put("group_name_to", destination.getName());
    jsonObject.put("message", message);
    sendToGroup(destination.getId(), jsonObject.toJSONString());
  }

}
