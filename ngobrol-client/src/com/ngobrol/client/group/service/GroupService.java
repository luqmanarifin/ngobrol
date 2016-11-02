package com.ngobrol.client.group.service;

import com.ngobrol.client.connector.RabbitConnector;
import com.ngobrol.client.user.User;
import org.json.simple.JSONObject;

/**
 * Created by husni on 02/11/16.
 */
public class GroupService {
  RabbitConnector connector;

  public GroupService(RabbitConnector connector) {
    this.connector = connector;
  }

  public void createGroup(User user, String groupName) {
    JSONObject createGroupMessage = new JSONObject();
    createGroupMessage.put("method", "create_group");
    createGroupMessage.put("username", user.getUsername());
    createGroupMessage.put("token", user.getToken());

    connector.sendMessageToServer(createGroupMessage.toJSONString());
  }

  public void quitFromGroup(User user, int groupId) {
    JSONObject quitFromGroupMessage = new JSONObject();
    quitFromGroupMessage.put("method", "quit_from_group");
    quitFromGroupMessage.put("username", user.getUsername());
    quitFromGroupMessage.put("group_id", groupId);
    quitFromGroupMessage.put("token", user.getToken());

    connector.sendMessageToServer(quitFromGroupMessage.toJSONString());
  }

  public void sendChatToGroup(User user, int groupId, String message) {
    JSONObject sendToGroupMessage = new JSONObject();
    sendToGroupMessage.put("method", "send_group");
    sendToGroupMessage.put("username_from", user.getUsername());
    sendToGroupMessage.put("group_id_to", groupId);
    sendToGroupMessage.put("message", message);
    sendToGroupMessage.put("token", user.getToken());

    connector.sendMessageToServer(sendToGroupMessage.toJSONString());
  }
}
