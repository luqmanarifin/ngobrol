package com.ngobrol.client.group.service;

import com.ngobrol.client.connector.RabbitConnector;
import com.ngobrol.client.user.User;
import org.json.simple.JSONObject;

/**
 * Created by husni on 02/11/16.
 */
public class GroupService {
  RabbitConnector connector;

  public void setUser(User user) {
    this.user = user;
  }

  User user;

  public GroupService(RabbitConnector connector) {
    this.connector = connector;
  }

  public void createGroup(String groupName) {
    JSONObject createGroupMessage = new JSONObject();
    createGroupMessage.put("method", "create_group");
    createGroupMessage.put("username", user.getUsername());
    createGroupMessage.put("group_name", groupName);
    createGroupMessage.put("token", user.getToken());

    connector.sendMessageToServer(createGroupMessage.toJSONString());
  }

  public void getGroup() {
    JSONObject getGroupMessage = new JSONObject();
    getGroupMessage.put("method", "get_group");
    getGroupMessage.put("username", user.getUsername());
    getGroupMessage.put("token", user.getToken());

    connector.sendMessageToServer(getGroupMessage.toJSONString());
  }

  public void addMembertoGroup(int groupId, String username) {
    JSONObject addMemberMessage = new JSONObject();
    addMemberMessage.put("method", "add_member_to_group");
    addMemberMessage.put("username_adder", user.getUsername());
    addMemberMessage.put("username_to_add", username);
    addMemberMessage.put("group_id", groupId);
    addMemberMessage.put("token", user.getToken());

    connector.sendMessageToServer(addMemberMessage.toJSONString());
  }

  public void quitFromGroup(int groupId) {
    JSONObject quitFromGroupMessage = new JSONObject();
    quitFromGroupMessage.put("method", "quit_from_group");
    quitFromGroupMessage.put("username", user.getUsername());
    quitFromGroupMessage.put("group_id", groupId);
    quitFromGroupMessage.put("token", user.getToken());

    connector.sendMessageToServer(quitFromGroupMessage.toJSONString());
  }

  public void sendChatToGroup(int groupId, String message) {
    JSONObject sendToGroupMessage = new JSONObject();
    sendToGroupMessage.put("method", "send_group");
    sendToGroupMessage.put("username_from", user.getUsername());
    sendToGroupMessage.put("group_id_to", groupId);
    sendToGroupMessage.put("message", message);
    sendToGroupMessage.put("token", user.getToken());

    connector.sendMessageToServer(sendToGroupMessage.toJSONString());
  }
}
