package com.yskrq.common;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yskrq.common.bean.LoginBean;
import com.yskrq.common.bean.TecColorBean;
import com.yskrq.common.util.AppUtils;
import com.yskrq.common.util.LOG;
import com.yskrq.common.util.SPUtil;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class AppInfo {
  public static LoginBean loginBean;

  public static void saveLoginInfo(LoginBean bean) {
    loginBean = bean;

    SPUtil.saveString(BASE.getCxt(), "userid", bean.getUserid());
    SPUtil.saveString(BASE.getCxt(), "groupid", bean.getGroupid());
    SPUtil.saveString(BASE.getCxt(), "shopsid", bean.getShopsid());
    SPUtil.saveString(BASE.getCxt(), "username", bean.getUsername());
    SPUtil.saveString(BASE.getCxt(), "apptoken", bean.getApptoken());
  }

  public static String getGroupId() {
    return SPUtil.getString(BASE.getCxt(), "groupid");
  }

  public static boolean isDebugUser() {
    String url = SPUtil.getString(BASE.getCxt(), "HttpParamUtil.change.url");
    if (!TextUtils.isEmpty(url)) {
      return url.startsWith("hotel16");
    }
    return false;
  }

  public static String getShopsid() {
    return SPUtil.getString(BASE.getCxt(), "shopsid");
  }

  public static String getUserid() {
    return SPUtil.getString(BASE.getCxt(), "userid");
  }

  public static String getToken() {
    return SPUtil.getString(BASE.getCxt(), "apptoken");
  }

  public static String getName() {
    if (loginBean != null) {
      return loginBean.getUsername();
    }
    return SPUtil.getString(BASE.getCxt(), "username");
  }

  public static String getProfitCenter() {
    return SPUtil.getString(BASE.getCxt(), "profitCenter");
  }

  public static String getWorkDate() {
    return SPUtil.getString(BASE.getCxt(), "mWorkDate");
  }

  public static void setWorkDate(String comment) {
    SPUtil.saveString(BASE.getCxt(), "mWorkDate", comment);
  }

  public static void setProfitCenter(Context context, String code) {
    LOG.e("AppInfo", "setProfitCenter<" + code + ">");
    SPUtil.saveString(BASE.getCxt(), "profitCenter", code);
  }

  private final static String TAG_COMPUTER_NAME = "computerName";
  private final static String TAG_SALE_DATE = "sale.date";
  private final static String TAG_AUTO_LOGIN = "auto.login";
  private final static String TAG_SAVE_TECH = "sale.tech_num";
  private final static String TAG_SAVE_TYPE = "sale.tech_type";
  private final static String TAG_SAVE_SEX = "sale.tech.sex";
  private final static String TAG_RUNNING = "home.running.target";


  public static String getPhoneName() {
    return SPUtil.getString(BASE.getCxt(), TAG_COMPUTER_NAME);
  }

  public static String getSaleDate() {
    return SPUtil.getString(BASE.getCxt(), TAG_SALE_DATE);
  }

  public static void setAutoLogin(Context context, boolean isChecked) {
    LOG.e("AppInfo", "setAutoLogin.85:" + isChecked);
    SPUtil.saveInt(BASE.getCxt(), TAG_AUTO_LOGIN, isChecked ? 1 : 0);
  }


  private final static String VOICE_TYPE = "home.setVoiceType";

  //  1-自动控制 0-未打开
  public static void setVoiceType(Context context, int type) {
    SPUtil.saveInt(BASE.getCxt(), VOICE_TYPE, type);
  }

  public static boolean isAutoLogin(Context context) {
    if (context == null) return false;
    LOG.e("AppInfo", "setAutoLogin.86:" + (SPUtil.getInt(BASE.getCxt(), TAG_AUTO_LOGIN) == 1));
    return SPUtil.getInt(BASE.getCxt(), TAG_AUTO_LOGIN) == 1;
  }

  public static boolean skipBattery() {
    return SPUtil.getBoolean(BASE.getCxt(), "skip.check.battery");
  }

  public static void needSkipBattery() {
    SPUtil.setBoolean(BASE.getCxt(), "skip.check.battery", true);
  }

  public static void setTechNum(String date) {
    SPUtil.saveString(BASE.getCxt(), TAG_SAVE_TECH, date);
  }

  public static String getTechNum() {
    return SPUtil.getString(BASE.getCxt(), TAG_SAVE_TECH);
  }

  public static void setTechType(Context context, String date) {
    SPUtil.saveString(BASE.getCxt(), TAG_SAVE_TYPE, date);
  }

  public static String getTechType(Context context) {
    if (context == null) return "getTechType";
    return SPUtil.getString(BASE.getCxt(), TAG_SAVE_TYPE);
  }

  public static void setTechSex(Context context, String date) {
    SPUtil.saveString(BASE.getCxt(), TAG_SAVE_SEX, date);
  }


  public static String getTechSex(Context context) {
    if (context == null) return "getTechSex";
    return SPUtil.getString(BASE.getCxt(), TAG_SAVE_SEX);
  }

  public static void saveRunningTargetTime(Context context, long l) {
    LOG.e("AppInfo", "saveRunningTargetTime.114:" + l);
    SPUtil.saveLong(BASE.getCxt(), TAG_RUNNING, l + System.currentTimeMillis());
  }

  public static long getRunningLeftTime(Context context) {
    return SPUtil.getLong(BASE.getCxt(), TAG_RUNNING) - System.currentTimeMillis();
  }

  public static void setCuiZHongMinutes(Context context, String data) {
    try {
      SPUtil.saveInt(BASE.getCxt(), "RUSH.TIME", Integer.parseInt(data));
    } catch (Exception e) {

    }
  }

  public static int getCuiZHongMinutes(Context context) {
    return SPUtil.init(context).getInt("RUSH.TIME", 5);
  }

  public static void setWait(Context context, String expendtime) {//通知需要展示的时间
    SPUtil.saveString(BASE.getCxt(), "Wait.TIME", expendtime);
  }

  public static String getWait(Context context) {
    if (context == null) return "getWait";
    return SPUtil.init(context).getString("Wait.TIME", "");
  }

  public static List<TecColorBean.ValueBean> getColors(Context context) {
    String save = SPUtil.init(context).getString("tech.colors", "");
    Type type = new TypeToken<ArrayList<TecColorBean.ValueBean>>() {}.getType();
    List<TecColorBean.ValueBean> lists = new Gson().fromJson(save, type);
    LOG.bean("AppInfo", lists);
    return lists;
  }

  public static void saveColor(Context context, List<? extends Serializable> colors) {//通知需要展示的状态
    String save = new Gson().toJson(colors);
    LOG.e("AppInfo", "saveColor.164:" + save);
    SPUtil.saveString(BASE.getCxt(), "tech.colors", save);
  }

  public static void setWaitType(Context context, int tag) {//通知需要展示的状态
    LOG.showUserWhere("setWaitType -> " + tag);
    SPUtil.saveInt(BASE.getCxt(), "Wait.TYPE", tag);
  }

  public static int getWaitType() {
    return SPUtil.init(BASE.getCxt()).getInt("Wait.TYPE", 0);
  }

  public static void clearNotify(Context context) {
    SPUtil.saveString(BASE.getCxt(), "Wait.TIME", "");
    SPUtil.saveLong(BASE.getCxt(), TAG_RUNNING, 0L);
  }

  public static void setClockWifi(Context context, String data) {
    SPUtil.saveString(BASE.getCxt(), "Sign.wifi", data);
  }

  public static void setSignLat(Context context, String data) {
    LOG.e("AppInfo", "setSignLat.221:" + data);
    SPUtil.saveString(BASE.getCxt(), "Sign.lat", data);
  }

  public static void setSignLon(Context context, String data) {
    SPUtil.saveString(BASE.getCxt(), "Sign.lon", data);
  }


  /**
   * @param context
   * @param latitude
   * @param longitude
   *
   * @return 1.wifi名错误   2.打卡范围错误
   */
  public static int canSign(Context context, double latitude, double longitude) {
    if (context == null) return -1;
    String wifiName = SPUtil.getString(BASE.getCxt(), "Sign.wifi");
    String curWifi = AppUtils.getWifiName(context);
    boolean isWifiOk = false;
    if (TextUtils.isEmpty(wifiName)) {
      isWifiOk = true;
    } else {
      String[] wifis = wifiName.split(",");
      LOG.e("AppInfo", "canSign.250:" + wifis.length);
      for (String wifi : wifis) {
        LOG.e("AppInfo", "canSign.item:" + "-" + wifi + "--" + curWifi + "-> " + TextUtils
            .equals(wifi, curWifi));
        if (TextUtils.equals(wifi, curWifi)) {
          isWifiOk = true;
          break;
        }
      }
    }
    if (!isWifiOk) {
      return 1;
    }
    String lat = SPUtil.getString(BASE.getCxt(), "Sign.lat");
    String lon = SPUtil.getString(BASE.getCxt(), "Sign.lon");
    boolean locationOK = false;
    if ("0".equals(lat) || "0".equals(lon)) {
      locationOK = true;
    } else {
      locationOK = isLocationOk(lat, lon, latitude, longitude);
    }
    if (!locationOK) {
      return 2;
    }
    LOG.e("AppInfo", "canSign.wifiName:" + wifiName + ",lat:" + lat + ",lon:" + lon);
    LOG.e("AppInfo", "canSign.user.wifiName:" + curWifi + ",lat:" + latitude + ",lon:" + longitude);
    LOG.e("AppInfo", "canSign.isWifiOk:" + isWifiOk + ",locationOK:" + locationOK);
    return 0;
  }

  public static boolean needWifi() {
    String wifiName = SPUtil.getString(BASE.getCxt(), "Sign.wifi");
    return !TextUtils.isEmpty(wifiName);
  }

  public static boolean needLocation(Context context) {
    if (context == null) return false;
    String lat = SPUtil.getString(BASE.getCxt(), "Sign.lat");
    String lon = SPUtil.getString(BASE.getCxt(), "Sign.lon");
    LOG.e("AppInfo", "needLocation.lat:" + lat + "  lon=" + lon);
    if ("0".equals(lat) || "0".equals(lon) || "".equals(lat) || "".equals(lon)) {
      return false;
    }
    return true;
  }

  private static boolean isLocationOk(String latSave, String lonSave, double latitude,
                                      double longitude) {
    if (TextUtils.isEmpty(latSave) || TextUtils.isEmpty(lonSave)) {
      return true;
    }
    double lat = Double.parseDouble(latSave);
    double lon = Double.parseDouble(lonSave);

    double longitudeok = (lon * Math.PI) / 180.0;
    double longitudeok2 = (longitude * Math.PI) / 180.0;
    double latitudeok = (lat * Math.PI) / 180.0;
    double latitudeok2 = (latitude * Math.PI) / 180.0;
    double a = longitudeok - longitudeok2;
    double b = latitudeok - latitudeok2;
    double Distance = 2 * Math.asin(Math
        .sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(latitudeok) * Math.cos(latitudeok2) * Math
            .pow(Math.sin(b / 2), 2)));
    Distance = Distance * 6378.137;
    Distance = Math.round(Distance * 10000) / 10000;
    LOG.e("AppInfo", "canSign.isLocationOk.286:" + Distance);
    return Distance < 1000;
  }

  public static void loginOut(Context context) {
    loginBean = null;
    SPUtil.saveString(BASE.getCxt(), "groupid", "");
    SPUtil.saveString(BASE.getCxt(), "shopsid", "");
    SPUtil.saveString(BASE.getCxt(), "username", "");
    SPUtil.saveString(BASE.getCxt(), "apptoken", "");
    SPUtil.saveString(BASE.getCxt(), "userid", "");
    setTechNum("");
    setProfitCenter(BASE.getCxt(), "");
    setWorkDate("");
    setAutoLogin(BASE.getCxt(), false);
  }

}
