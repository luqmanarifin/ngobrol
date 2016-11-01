package com.ngobrol.server;

import com.ngobrol.server.user.User;
import com.ngobrol.server.user.dao.UserDao;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Main {

  public static void main(String[] args) {
    // write your code here

    List<String> tes = new ArrayList<>();
    tes.add("asu");
    tes.add("kentang");

    JSONObject jsonObject = new JSONObject();
    jsonObject.put("method", "add_friend_reply");
    jsonObject.put("status", "asu");
    jsonObject.put("description", tes);
    System.out.println(jsonObject.toJSONString());
    //sendToClient(user.getUsername(), jsonObject.toJSONString());
  }
}
