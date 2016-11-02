package com.ngobrol.client.common;

import com.ngobrol.client.group.Group;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by husni on 02/11/16.
 */
public class Parser {
  private static JSONParser jsonParser = new JSONParser();

  public static String getMethod(String json) {
    try {
      JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
      return (String) jsonObject.get("method");
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return "";
  }

  public static String getStatus(String json) {
    try {
      JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
      return (String) jsonObject.get("status");
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return "";
  }

  public static String getDescription(String json) {
    try {
      JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
      return (String) jsonObject.get("description");
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return "";
  }

  public static String getUsername(String json) {
    try {
      JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
      return (String) jsonObject.get("username");
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return "";
  }

  public static String getPassword(String json) {
    try {
      JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
      return (String) jsonObject.get("password");
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return "";
  }

  public static String getQueueName(String json) {
    try {
      JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
      return (String) jsonObject.get("queue_name");
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return "";
  }

  public static String getUsernameFrom(String json) {
    try {
      JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
      return (String) jsonObject.get("username_from");
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return "";
  }

  public static List<String> getFriends(String json) {
    try {
      JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
      JSONArray groupJsons = (JSONArray) jsonObject.get("usernames");
      List<String> friends= new ArrayList<>();

      for (int i = 0; i < groupJsons.size(); i++) {
        String friend = (String) groupJsons.get(i);
        friends.add(friend);
      }
      return friends;
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return new ArrayList<>();
  }

  public static String getUsernameTo(String json) {
    try {
      JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
      return (String) jsonObject.get("username_to");
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return "";
  }

  public static String getToken(String json) {
    try {
      JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
      return (String) jsonObject.get("token");
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return "";
  }

  public static String getGroupName(String json) {
    try {
      JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
      return (String) jsonObject.get("group_name");
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return "";
  }

  public static String getGroupNameTo(String json) {
    try {
      JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
      return (String) jsonObject.get("group_name_to");
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return "";
  }

  public static String getUsernameAdder(String json) {
    try {
      JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
      return (String) jsonObject.get("username_adder");
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return "";
  }

  public static List<Group> getGroups(String json) {
    try {
      JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
      JSONArray groupJsons = (JSONArray) jsonObject.get("groups");
      List<Group> groups = new ArrayList<>();

      for (int i = 0; i < groupJsons.size(); i++) {
        JSONObject groupJson = (JSONObject) groupJsons.get(i);
        Group group = new Group((long) groupJson.get("group_id"), (String) groupJson.get("group_name"), "");
        groups.add(group);
      }
      return groups;
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return new ArrayList<>();
  }

  public static String getUsernameToAdd(String json) {
    try {
      JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
      return (String) jsonObject.get("username_to_add");
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return "";
  }

  public static long getGroupId(String json) {
    try {
      JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
      return Long.parseLong((String) jsonObject.get("group_id"));
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return 0;
  }

  public static String getMessage(String json) {
    try {
      JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
      return (String) jsonObject.get("message");
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return "";
  }

  public static long getGroupIdTo(String json) {
    try {
      JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
      return Long.parseLong((String) jsonObject.get("group_id_to"));
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return 0;
  }
}
