package com.ngobrol.server.common;

import com.ngobrol.server.user.User;
import com.ngobrol.server.user.dao.UserDao;

/**
 * Created by luqmanarifin on 02/11/16.
 */
public class Validator {

  private UserDao userDao;

  public Validator() {
    userDao = new UserDao();
  }

  // return true if token valid, false;
  public boolean validateToken(User user) {
    User correctUser = userDao.getByUsername(user.getUsername());
    return correctUser.getToken().equals(user.getToken());
  }
}
