package com.ngobrol.client.user.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Created by husni on 02/11/16.
 */
public class UserService {
  public void UserService() {

  }

  public void register(String username, String password) {
    JSONObject registerMessage = new JSONObject();
    registerMessage.put("method", "register");
    registerMessage.put("username", username);
    registerMessage.put("password", password);
  }

  public void login(String username, String password) {
    JSONObject loginMessage = new JSONObject();
    loginMessage.put("method", "login");
    loginMessage.put("username", username);
    loginMessage.put("password", password);
  }
}
