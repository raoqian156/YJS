package com.yskrq.common.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

public class UUID {

  public static final String TAG_COMPUTER_UUID = "computer.uuid";

  /**
   * 获得设备硬件标识
   *
   * @param context 上下文
   *
   * @return 设备硬件标识
   * java.lang.SecurityException Neither user 10194 nor current process has android.permission.CHANGE_WIFI_STATE
   * <p>
   * <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
   * <uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />
   * <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
   * <uses-permission android:name="android.permission.CHANGE_WIFI_MULTICAST_STATE" />
   * <uses-permission android:name="android.permission.INTERNET" />
   * <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
   * ————————————————
   * 版权声明：本文为CSDN博主「lifeidrid」的原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接及本声明。
   * 原文链接：https://blog.csdn.net/u010055819/article/details/87066721
   */
  public static String getDeviceId(Context context) {
    String save = SPUtil.getString(context, TAG_COMPUTER_UUID);
    if (!TextUtils.isEmpty(save) && !save.equals("D41D8CD98F00B204E9800998ECF8427E")) {
      LOG.e("UUID", "getDeviceId.38:" + save);
      return save;
    }
// 9X   2BEF9E9915156EF91C105857AD2F2DB3
    //获得设备默认IMEI（>=6.0 需要ReadPhoneState权限）
    String imei;
    try {
      imei = getIMEI(context);
    } catch (Exception e) {
      imei = "";
    }
    StringBuilder sbDeviceId = new StringBuilder();
    //        获得AndroidId（无需权限）
    String androidid = getAndroidId(context);
    //获得设备序列号（无需权限）
    String serial = getSERIAL();
    //获得硬件uuid（根据硬件相关属性，生成uuid）（无需权限）
    String uuid = getDeviceUUID().replace("-", "");
    String mac = getMacAddress(context);
    //追加imei
    if (imei != null && imei.length() > 0) {
      sbDeviceId.append("|");
      sbDeviceId.append(imei);
    } else {
      sbDeviceId.append("|");
      sbDeviceId.append("imei");
    }
    //追加androidid
    if (androidid != null && androidid.length() > 0) {
      sbDeviceId.append("|");
      sbDeviceId.append(androidid);
    } else {
      sbDeviceId.append("|");
      sbDeviceId.append("androidid");
    }
    //追加serial
    if (serial != null && serial.length() > 0) {
      sbDeviceId.append("|");
      sbDeviceId.append(serial);
    } else {
      sbDeviceId.append("|");
      sbDeviceId.append("serial");
    }
    //追加硬件uuid
    if (uuid != null && uuid.length() > 0) {
      sbDeviceId.append("|");
      sbDeviceId.append(uuid);
    } else {
      sbDeviceId.append("|");
      sbDeviceId.append("uuid");
    }
    sbDeviceId.append("|" + mac);
    String res = sbDeviceId.toString();
    LOG.e("UUID", "getDeviceId.86:" + res);
    res = MD5Util.getMD5String(res);
    SPUtil.saveString(context, TAG_COMPUTER_UUID, res);
    return res;
  }
  //如果以上硬件标识数据均无法获得 可能性不大
  //则DeviceId默认使用系统随机数，这样保证DeviceId不为空

  private static String getMacAddress(Context context) {
    String macAddress = null;
    try {
      WifiManager wifiManager = (WifiManager) context.getApplicationContext()
                                                     .getSystemService(Context.WIFI_SERVICE);
      WifiInfo info = (null == wifiManager ? null : wifiManager.getConnectionInfo());

      if (!wifiManager.isWifiEnabled()) {
        //必须先打开，才能获取到MAC地址
        wifiManager.setWifiEnabled(true);
        wifiManager.setWifiEnabled(false);
      }
      if (null != info) {
        macAddress = info.getMacAddress();
      }
    } catch (Exception e) {
      macAddress = "unknow";
    }
    return macAddress;
  }

  //需要获得READ_PHONE_STATE权限，>=6.0，默认返回null
  @SuppressLint("HardwareIds")
  //    @RequiresPermission(android.Manifest.permission.READ_PRIVILEGED_PHONE_STATE)
  private static String getIMEI(Context context) {
    try {
      TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
      return tm.getDeviceId();
    } catch (RuntimeException ex) {
      return "";
    }
  }

  /**
   * 获得设备的AndroidId
   *
   * @param context 上下文
   *
   * @return 设备的AndroidId
   */
  private static String getAndroidId(Context context) {
    try {
      return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return "";
  }

  /**
   * 获得设备序列号（如：WTK7N16923005607）, 个别设备无法获取
   *
   * @return 设备序列号
   */
  private static String getSERIAL() {
    try {
      return Build.SERIAL;
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return "";
  }

  /**
   * 获得设备硬件uuid
   * 使用硬件信息，计算出一个随机数
   *
   * @return 设备硬件uuid
   */
  private static String getDeviceUUID() {
    try {
      String dev = "10182" + Build.BOARD.length() % 10 + Build.BRAND.length() % 10 + Build.DEVICE
          .length() % 10 + Build.HARDWARE.length() % 10 + Build.ID.length() % 10 + Build.MODEL
          .length() % 10 + Build.PRODUCT.length() % 10 + Build.SERIAL.length() % 10;
      return dev;
    } catch (Exception ex) {
      ex.printStackTrace();
      return "";
    }
  }
}
