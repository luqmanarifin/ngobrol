package com.ngobrol.server.membership.socket;

import com.ngobrol.server.connector.RabbitConnector;
import com.ngobrol.server.group.Group;
import com.ngobrol.server.user.User;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luqmanarifin on 01/11/16.
 */
public class MembershipSocket extends RabbitConnector {

  public void getGroups(User user, String status, String description, List<Group> groups) {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("method", "get_group_reply");
    jsonObject.put("status", status);
    jsonObject.put("description", description);
    List<JSONObject> groupJson = new ArrayList<>();
    for (Group group : groups) {
      JSONObject jsonObject1 = new JSONObject();
      jsonObject1.put("group_id", group.getId());
      jsonObject1.put("group_name", group.getName());
      groupJson.add(jsonObject1);
    }
    jsonObject.put("groups", groupJson);
    sendToClient(user.getUsername(), jsonObject.toJSONString());
  }

  public void addMemberToGroup(User user, String status, String description) {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("method", "add_member_to_group_reply");
    jsonObject.put("status", status);
    jsonObject.put("description", description);
    sendToClient(user.getUsername(), jsonObject.toJSONString());
  }

  public void quitFromGroup(User user, String status, String description) {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("method", "quit_from_group_reply");
    jsonObject.put("status", status);
    jsonObject.put("description", description);
    sendToClient(user.getUsername(), jsonObject.toJSONString());
  }

}
