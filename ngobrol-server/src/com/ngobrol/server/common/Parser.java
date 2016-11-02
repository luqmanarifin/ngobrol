package com.ngobrol.server.common;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Created by luqmanarifin on 02/11/16.
 */
public class Parser {

  private static JSONParser jsonParser = new JSONParser();

  public Parser() {
  }

  public String getMethod(String json) {
    try {
      JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
      return (String) jsonObject.get("method");
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return "";
  }

  public String getUsername(String json) {
    try {
      JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
      return (String) jsonObject.get("username");
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return "";
  }

  public String getPassword(String json) {
    try {
      JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
      return (String) jsonObject.get("password");
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return "";
  }

  public String getQueueName(String json) {
    try {
      JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
      return (String) jsonObject.get("queue_name");
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return "";
  }

  public String getUsernameFrom(String json) {
    try {
      JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
      return (String) jsonObject.get("username_from");
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return "";
  }

  public String getUsernameTo(String json) {
    try {
      JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
      return (String) jsonObject.get("username_to");
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return "";
  }

  public String getToken(String json) {
    try {
      JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
      return (String) jsonObject.get("token");
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return "";
  }

  public String getGroupName(String json) {
    try {
      JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
      return (String) jsonObject.get("group_name");
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return "";
  }

  public String getUsernameAdder(String json) {
    try {
      JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
      return (String) jsonObject.get("username_adder");
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return "";
  }

  public String getUsernameToAdd(String json) {
    try {
      JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
      return (String) jsonObject.get("username_to_add");
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return "";
  }

  public long getGroupId(String json) {
    try {
      JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
      return Long.parseLong((String) jsonObject.get("group_id"));
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return 0;
  }

  public String getMessage(String json) {
    try {
      JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
      return (String) jsonObject.get("message");
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return "";
  }

  public long getGroupIdTo(String json) {
    try {
      JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
      return Long.parseLong((String) jsonObject.get("group_id_to"));
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return 0;
  }

  

}
