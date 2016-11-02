package com.ngobrol.client.chat.service;

import com.ngobrol.client.connector.RabbitConnector;
import com.ngobrol.client.user.User;
import org.json.simple.JSONObject;

/**
 * Created by husni on 02/11/16.
 */
public class ChatService {
  RabbitConnector connector;

  public ChatService(RabbitConnector connector) {
    this.connector = connector;
  }

  public void sendChat(User user, String usernameTo, String message) {
    JSONObject sendChatMessage = new JSONObject();
    sendChatMessage.put("method", "send_client");
    sendChatMessage.put("username_to", usernameTo);
    sendChatMessage.put("username_from", user.getUsername());
    sendChatMessage.put("message", message);
    sendChatMessage.put("token", user.getToken());

    connector.sendMessageToServer(sendChatMessage.toJSONString());
  }
}
