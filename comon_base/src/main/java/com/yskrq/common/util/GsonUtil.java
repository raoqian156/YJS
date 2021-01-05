package com.yskrq.common.util;

import com.google.gson.Gson;

import java.lang.reflect.Type;

public class GsonUtil {

  public static <T> T getBean(String json, Class<T> clazz) {
    try {
      return (T) new Gson().fromJson(json, clazz);
    } catch (Exception e) {
      LOG.e("GsonUtil", "getBean.13:");
      return null;
    }
  }
//  Type type = new TypeToken<List<Remark>>() {}.getType();
//  ArrayList<Remark> list = GsonUtil.getBean(Value, type);
  public static <T> T getBean(String json, Type clazz) {
    try {
      return (T) new Gson().fromJson(json, clazz);
    } catch (Exception e) {
      return null;
    }
  }

  public static String getString(Object object) {
    if (object == null) return "null";
    return new Gson().toJson(object);
  }

}
