package com.ngobrol.server.group.service;

import com.ngobrol.server.common.Validator;
import com.ngobrol.server.group.Group;
import com.ngobrol.server.group.dao.GroupDao;
import com.ngobrol.server.group.socket.GroupSocket;
import com.ngobrol.server.membership.dao.MembershipDao;
import com.ngobrol.server.user.User;

/**
 * Created by luqmanarifin on 01/11/16.
 */
public class GroupService {

  private Validator validator;
  private GroupDao groupDao;
  private MembershipDao membershipDao;
  private GroupSocket groupSocket;

  public GroupService() {
    validator = new Validator();
    groupDao = new GroupDao();
    membershipDao = new MembershipDao();
    groupSocket = new GroupSocket();
  }

  public void createGroup(User user, String groupName) {
    if (!validator.validateToken(user)) {
      groupSocket.createGroup(user, "error", "Token invalid.");
      return;
    }
    groupDao.addGroup(user, groupName);
    Group group = groupDao.getLastGroup(user, groupName);
    membershipDao.addToGroup(user, group.getId());
    groupSocket.bind(user.getUsername(), group.getId());

    // bikin exchange baru untuk group yg udah dibuat
    groupSocket.createGroupExchange(group.getId());

    groupSocket.createGroup(user, "ok", "Group berhasil ditambahkan.");
  }

}
