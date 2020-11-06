package com.yskrq.net_library;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BaseBean {// 统一网络回调基本数据

  public static boolean isJustShowUI = false;//无网络无后台，仅展示界面

  private String error;
  private String Error;
  private String comment;
  private String Comment;
  private String sessionId;
  private String all;

  public BaseBean() {
  }

  public BaseBean(String respCode, String respMsg) {
    this.error = respCode;
    this.comment = respMsg;
  }

  /**
   * 针对 如
   * {"error":0,"Comment":"获取技师ID成功","Value":"[{\"Description\":\"666技师\"}]"}
   * 返回数据取值
   * key -> Description   只能取数组第一个数据
   * return 666技师
   *
   * @param key 取值关键字
   *
   * @return 数组第一个数据 关键字对应值
   */
  public String getStrInList(String key) {
    if (key != null && all != null) {
      if (all.contains(key)) {
        //        Log.e("BaseBean", "getStrInList.38《" + all + "》");
        String realKey = "\\\"" + key + "\\\":\\\"";
        //        Log.e("BaseBean", "getStrInList.realKey:" + realKey);
        String left = all.substring(all.indexOf(realKey) + key.length() + 7);
        //        Log.e("BaseBean", "getStrInList.39:" + left);
        try {
          return left.substring(0, left.indexOf("\\\""));
        } catch (Exception e) {
          if (left.length() > 4) {
            return left.substring(0, left.length() - 1);
          }
        }
      }
    }
    return "";
  }

  public static String getStrInJson(String key, String all) {
    if (key != null && all != null) {
      if (all.contains(key)) {
        all = all.replaceAll("\"", "");
        try {
          int start = all.indexOf(key) + key.length() + 1;
          Log.e("BaseBean", "getStrInJson.73:"+start);
          String remain = all.substring(start);
          Log.e("BaseBean", "getStrInJson.75:"+remain);
          int end = start + (remain.indexOf(",") > 0 ? remain.indexOf(",") : remain.indexOf("}"));
          Log.e("BaseBean", "getStrInJson.77:"+end);
          return all.substring(start, end);
        } catch (Exception e) {
          return "";
        }
      }
    }
    return "";
  }

  public final void setAll(String all) {
    this.all = all;
  }

  public boolean isOk() {//接口逻辑通畅判断标准
    return "0".equals(getRespCode());
  }

  public String getRespCode() {
    if (!TextUtils.isEmpty(Error)) return Error;
    return error;
  }

  public void setRespCode(String respCode) {
    this.error = respCode;
  }

  public String getRespMsg() {
    if (!TextUtils.isEmpty(comment)) return comment;
    return Comment;
  }

  public void setRespMsg(String respMsg) {
    this.comment = respMsg;
  }

  public String getSessionId() {
    return sessionId;
  }

  public <T> T get(Class clazz, String... key) {
    if (isJustShowUI) {
      if (clazz.isAssignableFrom(String.class)) {
        return (T) ("debug" + System.currentTimeMillis());
      }
      if (clazz.isAssignableFrom(Integer.class)) {
        return (T) ((Integer) new Random().nextInt());
      }
      if (clazz.isAssignableFrom(Boolean.class)) {
        return (T) ((Boolean) (new Random().nextInt() % 2 == 0));
      }
      if (clazz.isAssignableFrom(Long.class)) {
        return (T) ((Long) Long.parseLong(System.currentTimeMillis() + ""));
      }
      if (clazz.isAssignableFrom(Double.class)) {
        return (T) ((Double) Double.parseDouble(System.currentTimeMillis() + ""));
      }
    }
    JSONObject json;
    try {
      json = new JSONObject(all);
      if (key.length == 1) {
        return getByClass(clazz, json, key[0]);
      } else if (key.length > 1) {
        for (int i = 0; i < key.length; i++) {
          if (i == key.length - 1) {
            return getByClass(clazz, json, key[i]);
          }
          json = json.getJSONObject(key[i]);
        }
      }
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return (T) new Object();
  }

  public static <T> T getByClass(Class clazz, JSONObject json, String key) {
    if (clazz.isAssignableFrom(String.class)) {
      return (T) json.optString(key);
    }
    if (clazz.isAssignableFrom(String.class)) {
      return (T) json.optString(key);
    }
    if (clazz.isAssignableFrom(Integer.class)) {
      return (T) ((Integer) json.optInt(key, 0));
    }
    if (clazz.isAssignableFrom(Boolean.class)) {
      return (T) ((Boolean) json.optBoolean(key, false));
    }
    if (clazz.isAssignableFrom(Long.class)) {
      return (T) ((Long) json.optLong(key, 0L));
    }
    if (clazz.isAssignableFrom(Double.class)) {
      return (T) ((Double) json.optDouble(key, 0));
    }
    return (T) new Object();
  }

  //    public <T> List<T> getList(String con) {
  //        if (TextUtils.isEmpty(con)) return new ArrayList<>();
  //        Type type = new TypeToken<List<T>>() {}.getType();
  //        List<T> list = new Gson().fromJson(con, type);
  //        return list;
  //    }

  public <T> List<T> getList(String... key) {
    Type type = new TypeToken<ArrayList<T>>() {}.getType();

    Log.e("BaseBean", "WorkerListController.type:" + type);
    Log.e("BaseBean", "WorkerListController.type:" + type.getClass());
    Log.e("BaseBean", "WorkerListController.type:" + type.getClass().getSimpleName());
    JSONObject json;
    try {
      json = new JSONObject(all);
      if (key.length == 1) {
        List<T> lists = new Gson().fromJson(json.optString(key[0]), type);
        return lists;
      } else if (key.length > 1) {
        for (int i = 0; i < key.length; i++) {
          if (i == key.length - 1) {
            List<T> lists = new Gson().fromJson(json.optString(key[i]), type);
            return lists;
          }
          json = json.getJSONObject(key[i]);
        }
      }
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return new ArrayList();
  }

  public String getAll() {
    return all;
  }
}
