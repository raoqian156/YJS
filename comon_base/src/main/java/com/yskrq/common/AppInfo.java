package com.yskrq.common;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yskrq.common.bean.LoginBean;
import com.yskrq.common.bean.TecColorBean;
import com.yskrq.common.util.LOG;
import com.yskrq.common.util.SPUtil;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.WIFI_SERVICE;


public class AppInfo {
  public static LoginBean loginBean;

  public static void saveLoginInfo(Context context, LoginBean bean) {
    loginBean = bean;

    SPUtil.saveString(context, "userid", bean.getUserid());
    SPUtil.saveString(context, "groupid", bean.getGroupid());
    SPUtil.saveString(context, "shopsid", bean.getShopsid());
    SPUtil.saveString(context, "username", bean.getUsername());
    SPUtil.saveString(context, "apptoken", bean.getApptoken());
  }

  public static String getGroupId(Context context) {
    return SPUtil.getString(context, "groupid");
  }

  public static String getShopsid(Context context) {
    return SPUtil.getString(context, "shopsid");
  }

  public static String getUserid(Context context) {
    return SPUtil.getString(context, "userid");
  }

  public static String getToken(Context context) {
    return SPUtil.getString(context, "apptoken");
  }

  public static String getName(Context context) {
    if (loginBean != null) {
      return loginBean.getUsername();
    }
    return SPUtil.getString(context, "username");
  }

  public static String getProfitCenter(Context context) {
    return SPUtil.getString(context, "profitCenter");
  }

  public static String getWorkDate(Context c) {
    return SPUtil.getString(c, "mWorkDate");
  }

  public static void setWorkDate(Context context, String comment) {
    SPUtil.saveString(context, "mWorkDate", comment);
  }

  public static void setProfitCenter(Context context, String code) {
    LOG.e("AppInfo", "setProfitCenter<" + code + ">");
    SPUtil.saveString(context, "profitCenter", code);
  }

  private final static String TAG_COMPUTER_NAME = "computerName";
  private final static String TAG_SALE_DATE = "sale.date";
  private final static String TAG_AUTO_LOGIN = "auto.login";
  private final static String TAG_SAVE_TECH = "sale.tech_num";
  private final static String TAG_SAVE_TYPE = "sale.tech_type";
  private final static String TAG_SAVE_SEX = "sale.tech.sex";
  private final static String TAG_RUNNING = "home.running.target";


  public static String getPhoneName(Context context) {
    return SPUtil.getString(context, TAG_COMPUTER_NAME);
  }

  public static String getSaleDate(Context context) {
    return SPUtil.getString(context, TAG_SALE_DATE);
  }

  public static void setAutoLogin(Context context, boolean isChecked) {
    LOG.e("AppInfo", "setAutoLogin.85:" + isChecked);
    SPUtil.saveInt(context, TAG_AUTO_LOGIN, isChecked ? 1 : 0);
  }


  private final static String VOICE_TYPE = "home.setVoiceType";

  //  1-自动控制 0-未打开
  public static void setVoiceType(Context context, int type) {
    SPUtil.saveInt(context, VOICE_TYPE, type);
  }

  public static boolean isAutoLogin(Context context) {
    LOG.e("AppInfo", "setAutoLogin.86:" + (SPUtil.getInt(context, TAG_AUTO_LOGIN) == 1));
    return SPUtil.getInt(context, TAG_AUTO_LOGIN) == 1;
  }

  public static void setTechNum(Context context, String date) {
    SPUtil.saveString(context, TAG_SAVE_TECH, date);
  }

  public static String getTechNum(Context context) {
    return SPUtil.getString(context, TAG_SAVE_TECH);
  }

  public static void setTechType(Context context, String date) {
    SPUtil.saveString(context, TAG_SAVE_TYPE, date);
  }

  public static String getTechType(Context context) {
    return SPUtil.getString(context, TAG_SAVE_TYPE);
  }

  public static void setTechSex(Context context, String date) {
    SPUtil.saveString(context, TAG_SAVE_SEX, date);
  }


  public static String getTechSex(Context context) {
    return SPUtil.getString(context, TAG_SAVE_SEX);
  }

  public static void saveRunningTargetTime(Context context, long l) {
    LOG.e("AppInfo", "saveRunningTargetTime.114:" + l);
    SPUtil.saveLong(context, TAG_RUNNING, l + System.currentTimeMillis());
  }

  public static long getRunningLeftTime(Context context) {
    return SPUtil.getLong(context, TAG_RUNNING) - System.currentTimeMillis();
  }

  public static void setCuiZHongMinutes(Context context, String data) {
    try {
      SPUtil.saveInt(context, "RUSH.TIME", Integer.parseInt(data));
    } catch (Exception e) {

    }
  }

  public static int getCuiZHongMinutes(Context context) {
    return SPUtil.init(context).getInt("RUSH.TIME", 5);
  }

  public static void setWait(Context context, String expendtime) {//通知需要展示的时间
    SPUtil.saveString(context, "Wait.TIME", expendtime);
  }

  public static String getWait(Context context) {
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
    SPUtil.saveString(context, "tech.colors", save);
  }

  public static void setWaitType(Context context, int tag) {//通知需要展示的状态
    LOG.showUserWhere("setWaitType -> " + tag);
    SPUtil.saveInt(context, "Wait.TYPE", tag);
  }

  public static int getWaitType(Context context) {
    return SPUtil.init(context).getInt("Wait.TYPE", 0);
  }

  public static void clearNotify(Context context) {
    SPUtil.saveString(context, "Wait.TIME", "");
    SPUtil.saveLong(context, TAG_RUNNING, 0L);
  }

  public static void setClockWifi(Context context, String data) {
    SPUtil.saveString(context, "Sign.wifi", data);
  }

  public static void setSignLat(Context context, String data) {
    SPUtil.saveString(context, "Sign.lat", data);
  }

  public static void setSignLon(Context context, String data) {
    SPUtil.saveString(context, "Sign.lon", data);
  }

  public static String getWifiName(Context context) {
    WifiManager wifiManager = (WifiManager) context.getSystemService(WIFI_SERVICE);
    WifiInfo wifiInfo = wifiManager.getConnectionInfo();
    Log.d("wifiInfo", wifiInfo.toString());
    Log.d("SSID", wifiInfo.getSSID());
    if (wifiInfo.getSSID() == null) return "";
    return wifiInfo.getSSID().replaceAll("\"", "");
  }

  public static boolean canSign(Context context, double latitude, double longitude) {
    String wifiName = SPUtil.getString(context, "Sign.wifi");
    boolean isWifiOk = false;
    if (TextUtils.isEmpty(wifiName)) {
      isWifiOk = true;
    } else {
      String[] wifis = wifiName.split(",");
      String curWifi = getWifiName(context);
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
    String lat = SPUtil.getString(context, "Sign.lat");
    String lon = SPUtil.getString(context, "Sign.lon");
    boolean locationOK = false;
    if ("0".equals(lat) || "0".equals(lon)) {
      locationOK = true;
    } else {
      locationOK = isLocationOk(lat, lon, latitude, longitude);
    }
    LOG.e("AppInfo", "canSign.wifiName:" + wifiName + ",lat:" + lat + ",lon:" + lon);
    LOG.e("AppInfo", "canSign.get.wifiName:" + getWifiName(context) + ",lat:" + latitude + ",lon:" + longitude);
    LOG.e("AppInfo", "canSign.isWifiOk:" + isWifiOk + ",locationOK:" + locationOK);
    return isWifiOk && locationOK;
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
    SPUtil.saveString(context, "groupid", "");
    SPUtil.saveString(context, "shopsid", "");
    SPUtil.saveString(context, "username", "");
    SPUtil.saveString(context, "apptoken", "");
    SPUtil.saveString(context, "userid", "");
    setTechNum(context, "");
    setProfitCenter(context, "");
    setWorkDate(context, "");
    setAutoLogin(context, false);
  }

}
