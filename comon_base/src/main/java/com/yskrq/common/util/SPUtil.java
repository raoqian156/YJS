package com.yskrq.common.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

/**
 * 小数据手机本地存储
 */

public class SPUtil {
  public static SharedPreferences init(Context context) {
    if (context == null) {
      return null;
    }
    SharedPreferences sp = context.getSharedPreferences("user", Context.MODE_PRIVATE);
    return sp;
  }

  /**
   * 添加数据
   *
   * @param key
   * @param value
   */
  public static void saveString(Context context, String key, String value) {
    if (context == null) return;
    SharedPreferences.Editor edit = init(context).edit();
    edit.putString(key, value);
    edit.apply();
  }

  public static void saveLong(Context context, String key, long value) {
    if (context == null) return;
    SharedPreferences.Editor edit = init(context).edit();
    edit.putLong(key, value);
    edit.apply();
  }

  public static void saveInt(Context context, String key, int value) {
    if (context == null) return;
    SharedPreferences.Editor edit = init(context).edit();
    edit.putInt(key, value);
    edit.apply();
  }

  public static int getInt(Context context, String key) {
    if (context == null) return -1;
    return init(context).getInt(key, -1);
  }

  public static void setString(Context context, String key, String value) {
    if (context == null) return;
    SharedPreferences.Editor edit = init(context).edit();
    edit.putString(key, value);
    edit.apply();
  }

  public static String getString(Context context, String key) {
    if (context == null) return "";
    return init(context).getString(key, "");
  }

  public static void setBoolean(Context context, String key, Boolean b) {
    if (context == null) return;
    SharedPreferences.Editor edit = init(context).edit();
    edit.putBoolean(key, b);
    edit.apply();
  }

  public static boolean getBoolean(Context context, String key) {
    if (context == null) return false;
    return init(context).getBoolean(key, false);
  }

  public static void setLong(Context context, String key, Long b) {
    if (context == null) return;
    SharedPreferences.Editor edit = init(context).edit();
    edit.putLong(key, b);
    edit.apply();
  }

  public static Long getLong(Context context, String key) {
    if (context == null) return 0L;
    return init(context).getLong(key, 0L);
  }

  /**
   * 获取数据
   *
   * @param key
   */
  public static String getSharePreferencesData(Context context, String key) {
    if (context == null) return "";
    return init(context).getString(key, "");

  }

  /**
   * 删除数据
   *
   * @param key
   */
  public static void deleteSharePreferencesData(Context context, String key) {
    if (context == null) return;
    SharedPreferences.Editor edit = init(context).edit();
    edit.putString(key, "");
    edit.apply();
  }

  public static void deleteInt(Context context, String key) {
    if (context == null) return;
    SharedPreferences.Editor edit = init(context).edit();
    edit.putInt(key, -1);
    edit.apply();
  }

  /**
   * 是否存在数据
   *
   * @param context
   * @param key
   *
   * @return
   */
  public static boolean isExistData(Context context, String key) {
    if (context == null) return false;
    if (!TextUtils.isEmpty(getSharePreferencesData(context, key))) {
      return true;
    } else {
      return false;
    }
  }

}
