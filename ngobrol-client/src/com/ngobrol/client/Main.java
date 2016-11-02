package com.ngobrol.client;

import com.ngobrol.client.connector.RabbitConnector;
import com.ngobrol.client.user.service.UserService;

import java.io.IOException;

/**
 * Created by husni on 02/11/16.
 */
public class Main {
  public static void main(String[] args) {
    RabbitConnector connector = new RabbitConnector("10.5.25.71");
    UserService us = new UserService(connector);
    try {
      us.register("user", "password");
      us.login("user", "password");
    } catch (IOException e) {
      e.printStackTrace();
    }
    // connector.close();
  }
}
