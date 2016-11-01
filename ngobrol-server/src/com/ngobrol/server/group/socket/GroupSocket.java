package com.ngobrol.server.group.socket;

import com.ngobrol.server.connector.RabbitConnector;
import com.ngobrol.server.user.User;
import org.json.simple.JSONObject;

/**
 * Created by luqmanarifin on 01/11/16.
 */
public class GroupSocket extends RabbitConnector {

  public GroupSocket() {
  }

  public void createGroup(User user, String status, String description) {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("method", "create_group_reply");
    jsonObject.put("status", status);
    jsonObject.put("description", description);
    sendToClient(user.getUsername(), jsonObject.toJSONString());
  }

}
