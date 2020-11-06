package com.yskrq.yjs.util;

import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import com.yskrq.common.util.LOG;
import com.yskrq.common.util.SPUtil;

public class PhoneUtil {
  /**
   * @return 是否需要打开免打扰权限
   */
  public static boolean needPermission(Context context) {
    NotificationManager notificationManager = (NotificationManager) context
        .getSystemService(Context.NOTIFICATION_SERVICE);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && notificationManager != null && !notificationManager
        .isNotificationPolicyAccessGranted()) {
      return true;
    }
    return false;
  }

  public static boolean toOpen(Context context) {
    NotificationManager notificationManager = (NotificationManager) context
        .getSystemService(Context.NOTIFICATION_SERVICE);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && notificationManager != null && !notificationManager
        .isNotificationPolicyAccessGranted()) {
      Intent intent = new Intent(android.provider.Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
      context.startActivity(intent);
      return true;
    }
    return false;
  }

  public static boolean closeVoice(Context context) {//关闭声音   result >> false-静音失败 true-静音成功
    if (SPUtil.getInt(context, "voice.status") == 1) {
      return true;
    }
    NotificationManager notificationManager = (NotificationManager) context
        .getSystemService(Context.NOTIFICATION_SERVICE);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && !notificationManager
        .isNotificationPolicyAccessGranted()) {
      return false;
    }
    AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
    if (audioManager != null) {
      audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
      audioManager.getStreamVolume(AudioManager.STREAM_RING);
      SPUtil.saveInt(context, "voice.status", 1);
      LOG.e("SpeakManager", "closeVoice.29:+静音成功");
      return true;
    }
    return false;
  }


  public static boolean openVoice(Context context) {//打开声音   result >> false-打开声音失败 true-打开声音成功
//    if (SPUtil.getInt(context, "voice.status") == 2) {
//      LOG.e("PhoneUtil", "openVoice.66:");
//      return true;
//    }
    LOG.e("PhoneUtil", "openVoice.69:");
    NotificationManager notificationManager = (NotificationManager) context
        .getSystemService(Context.NOTIFICATION_SERVICE);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && !notificationManager
        .isNotificationPolicyAccessGranted()) {
      LOG.e("PhoneUtil", "openVoice.74:");
      return false;
    }
    AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
    if (audioManager != null) {
      LOG.e("PhoneUtil", "openVoice.79:");
      audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
      audioManager.getStreamVolume(AudioManager.STREAM_RING);
      LOG.e("SpeakManager", "openVoice.48:取消静音");
      SPUtil.saveInt(context, "voice.status", 2);
      return true;
    }
    LOG.e("PhoneUtil", "openVoice.86:");
    return false;
  }

  public static void toStartInterface(Context context) {
    Intent intent = new Intent();
    try {
      intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      Log.e("HLQ_Struggle", "******************当前手机型号为：" + getMobileType());
      ComponentName componentName = null;
      if (getMobileType().equals("Xiaomi")) { // 红米Note4测试通过
        componentName = new ComponentName("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity");
      } else if (getMobileType().equals("Letv")) { // 乐视2测试通过
        intent.setAction("com.letv.android.permissionautoboot");
      } else if (getMobileType().equals("samsung")) { // 三星Note5测试通过
        componentName = new ComponentName("com.samsung.android.sm_cn", "com.samsung.android.sm.ui.ram.AutoRunActivity");
      } else if (getMobileType().equals("HUAWEI")) { // 华为测试通过
        componentName = ComponentName
            .unflattenFromString("com.huawei.systemmanager/.startupmgr.ui.StartupNormalAppListActivity");//跳自启动管理
        //SettingOverlayView.show(context);
      } else if (getMobileType().equals("vivo")) { // VIVO测试通过
        componentName = ComponentName
            .unflattenFromString("com.iqoo.secure/.safeguard.PurviewTabActivity");
      } else if (getMobileType().equals("Meizu")) { //万恶的魅族
        // 通过测试，发现魅族是真恶心，也是够了，之前版本还能查看到关于设置自启动这一界面，系统更新之后，完全找不到了，心里默默Fuck！
        // 针对魅族，我们只能通过魅族内置手机管家去设置自启动，所以我在这里直接跳转到魅族内置手机管家界面，具体结果请看图
        componentName = ComponentName
            .unflattenFromString("com.meizu.safe/.permission.PermissionMainActivity");
      } else if (getMobileType().equals("OPPO")) { // OPPO R8205测试通过
        componentName = ComponentName
            .unflattenFromString("com.oppo.safe/.permission.startup.StartupAppListActivity");
      } else if (getMobileType().equals("ulong")) { // 360手机 未测试
        componentName = new ComponentName("com.yulong.android.coolsafe", ".ui.activity.autorun.AutoRunListActivity");
      } else {
        // 以上只是市面上主流机型，由于公司你懂的，所以很不容易才凑齐以上设备
        // 针对于其他设备，我们只能调整当前系统app查看详情界面
        // 在此根据用户手机当前版本跳转系统设置界面
        if (Build.VERSION.SDK_INT >= 9) {
          intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
          intent.setData(Uri.fromParts("package", context.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
          intent.setAction(Intent.ACTION_VIEW);
          intent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
          intent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
        }
      }
      intent.setComponent(componentName);
      context.startActivity(intent);
    } catch (Exception e) {//抛出异常就直接打开设置页面
      intent = new Intent(Settings.ACTION_SETTINGS);
      context.startActivity(intent);
    }
  }

  private static String getMobileType() {
    return Build.MANUFACTURER;
  }

}
