package com.ngobrol.server.membership.service;

import com.ngobrol.server.common.Validator;
import com.ngobrol.server.group.Group;
import com.ngobrol.server.group.dao.GroupDao;
import com.ngobrol.server.membership.dao.MembershipDao;
import com.ngobrol.server.membership.socket.MembershipSocket;
import com.ngobrol.server.user.User;
import com.ngobrol.server.user.dao.UserDao;
import com.ngobrol.server.user.socket.UserSocket;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luqmanarifin on 01/11/16.
 */
public class MembershipService {

  Validator validator;
  MembershipDao membershipDao;
  MembershipSocket membershipSocket;
  GroupDao groupDao;
  UserDao userDao;
  UserSocket userSocket;

  public MembershipService() {
    validator = new Validator();
    membershipDao = new MembershipDao();
    membershipSocket = new MembershipSocket();
    groupDao = new GroupDao();
    userDao = new UserDao();
    userSocket = new UserSocket();
  }

  public void getGroups(User user) {
    if (!validator.validateToken(user)) {
      membershipSocket.getGroups(user, "error", "Token invalid.", new ArrayList<>());
      return;
    }
    List<Long> groupIds = membershipDao.getMembership(user);
    List<Group> groups = new ArrayList<>();
    for (Long groupId : groupIds) {
      groups.add(groupDao.getGroup(groupId));
    }
    membershipSocket.getGroups(user, "ok", "Group berhasil diambil", groups);
  }

  public void addMemberToGroup(User adder, User toAdd, long groupId) {
    if (!validator.validateToken(adder)) {
      membershipSocket.addMemberToGroup(adder, "error", "Invalid token.");
      return;
    }
    if (!groupDao.isGroupExists(groupId)) {
      membershipSocket.addMemberToGroup(adder, "error", "Group tidak ditemukan.");
      return;
    }
    Group group = groupDao.getGroup(groupId);
    if (!group.getAdminUsername().equals(adder.getUsername())) {
      membershipSocket.addMemberToGroup(adder, "error", "Hanya admin yang boleh menambahkan anggota grup ini.");
      return;
    }
    if (!userDao.isUserExists(toAdd)) {
      membershipSocket.addMemberToGroup(adder, "error", "Username tidak ada.");
      return;
    }
    if (membershipDao.isMember(toAdd, groupId)) {
      membershipSocket.addMemberToGroup(adder, "error", toAdd.getUsername() + " sudah merupakan anggota grup.");
      return;
    }
    membershipDao.addToGroup(toAdd, groupId);

    // bind queue user toAdd dengan exchange groupId
    membershipSocket.bind(toAdd.getUsername(), groupId);

    // notif all member of group bahwa ada org masuk
    userSocket.sendMessageToGroup(new User("admin", "", ""), group, toAdd.getUsername() + " telah masuk ke dalam grup.");

    membershipSocket.addMemberToGroup(adder, "ok", toAdd.getUsername() + " berhasil ditambahkan.");
  }

  public void quitFromGroup(User user, long groupId) {
    if (!validator.validateToken(user)) {
      membershipSocket.quitFromGroup(user, "error", "Invalid token.");
      return;
    }
    if (!groupDao.isGroupExists(groupId)) {
      membershipSocket.quitFromGroup(user, "error", "Group tidak ditemukan.");
      return;
    }
    if (!membershipDao.isMember(user, groupId)) {
      membershipSocket.quitFromGroup(user, "error", user.getUsername() + " bukan member grup. Ngapain keluar?");
      return;
    }
    membershipDao.deleteFromGroup(user, groupId);

    // unbind queue user dengan exchange groupId
    membershipSocket.unbind(user.getUsername(), groupId);

    Group group = groupDao.getGroup(groupId);
    // notif all member of group bahwa ada org keluar
    userSocket.sendMessageToGroup(new User("admin", "", ""), group, user.getUsername() + " telah keluar dari grup.");

    membershipSocket.quitFromGroup(user, "ok", user.getUsername() + " berhasil keluar grup");
  }

}
