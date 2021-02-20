package com.yskrq.yjs.util;

import android.app.NotificationManager;
import android.content.Context;
import android.media.AudioManager;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;

import com.yskrq.common.AppInfo;
import com.yskrq.common.BASE;
import com.yskrq.common.okhttp.HttpManagerBase;
import com.yskrq.common.util.LOG;
import com.yskrq.common.util.SPUtil;
import com.yskrq.yjs.Speaker;
import com.yskrq.common.bean.RelaxListBean;
import com.yskrq.yjs.net.HttpManager;

import java.util.HashMap;

public class SpeakManager {
  private static String getReadBrand(Context context) {
    String s = AppInfo.getTechNum();
    if (TextUtils.isEmpty(s)) return "";
    return s.replaceAll("", " ");
  }

  public static void closeVoice(Context context) {//关闭声音   result >> false-静音失败 true-静音成功
    NotificationManager notificationManager = (NotificationManager) context
        .getSystemService(Context.NOTIFICATION_SERVICE);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && !notificationManager
        .isNotificationPolicyAccessGranted()) {
      LOG.e("SpeakManager", "closeVoice.29:+静音失败");
    } else {
      AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
      if (audioManager != null) {
        audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
        audioManager.getStreamVolume(AudioManager.STREAM_RING);
        LOG.e("SpeakManager", "closeVoice.29:+静音成功");
      }
    }
  }


  public static void openVoice(Context context) {//打开声音   result >> false-打开声音失败 true-打开声音成功
    NotificationManager notificationManager = (NotificationManager) context
        .getSystemService(Context.NOTIFICATION_SERVICE);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && !notificationManager
        .isNotificationPolicyAccessGranted()) {
    } else {
      AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
      if (audioManager != null) {
        audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        audioManager.getStreamVolume(AudioManager.STREAM_RING);
        LOG.e("SpeakManager", "openVoice.48:取消静音");
      }
    }
  }

  static HashMap<String, Long> readTimeTag = new HashMap<>();

  public static int isRead(final Context context, final RelaxListBean.ValueBean first) {
    if (first == null) {
      openVoice(context);
      HttpManagerBase.senError("极光"+AppInfo.getTechNum(), "isRead.61");
      LOG.e("SpeakManager", "isRead.播报.61:");
      return 0;
    }
    if (first.hasNewTask()) {
      LOG.e("SpeakManager", "isRead.播报.64:");
      String key = first.getAccount() + "1";
      if (readTimeTag.containsKey(key)) {
        long time = readTimeTag.get(key);
        if (System.currentTimeMillis() - time < 10000) {
          LOG.e("SpeakManager", "isRead.播报.72:");
          HttpManagerBase.senError("极光"+AppInfo.getTechNum(), "isRead.72");
          return 0;
        }
        readTimeTag.put(key, System.currentTimeMillis());
      }
      openVoice(context);
      LOG.e("SpeakManager", "isRead.播报.78:您有新的任务");
      HttpManagerBase.senError("极光"+AppInfo.getTechNum(), "isRead.78:您有新的任务");
      Speaker.speakOut(context, "您有新的任务", new Speaker.OnSpeakListener() {
        @Override
        public void onFinish(int status) {
          if (TextToSpeech.SUCCESS == status) {
            new Thread(new Runnable() {
              @Override
              public void run() {
                HttpManager.sendNewTaskSuccess(context, first.getAccount(), first.getSeqnum(), first
                    .getFacilityno());
              }
            }).start();
          }
        }
      });
      return 1;
    } else if (first.cuizhong(AppInfo.getCuiZHongMinutes(context))) {
      LOG.e("SpeakManager", "isRead.播报.79:");
      String key = first.getAccount() + "2";
      if (readTimeTag.containsKey(key)) {
        long time = readTimeTag.get(key);
        if (System.currentTimeMillis() - time < 10000) {
          HttpManagerBase.senError("极光"+AppInfo.getTechNum(), "isRead.96");
          LOG.e("SpeakManager", "isRead.播报.96:");
          return 0;
        }
        readTimeTag.put(key, System.currentTimeMillis());
      }
      openVoice(context);
      final String brand = getReadBrand(context) + "号技师";
      final String spearOut = brand + "还有 " + AppInfo.getCuiZHongMinutes(context) + " 分钟下钟";
      LOG.e("SpeakManager", "isRead.播报.100:" + spearOut);
      HttpManagerBase.senError("极光"+AppInfo.getTechNum(), "isRead.100:" + spearOut);
      Speaker.speakOut(context, spearOut, new Speaker.OnSpeakListener() {
        @Override
        public void onFinish(int status) {
          LOG.e("SpeakManager", "onFinish.83:" + status);
          if (TextToSpeech.SUCCESS == status) {
            HttpManager
                .hasSend(context, first.getAccount(), first.getSeqnum(), first.getFacilityno(), 0);
            Speaker.speakOut(context, spearOut);
          }
        }
      });
      return 2;
    } else if (first.daozhong()) {
      String key = first.getAccount() + "3";
      if (readTimeTag.containsKey(key)) {
        long time = readTimeTag.get(key);
        if (System.currentTimeMillis() - time < 10000) {
          LOG.e("SpeakManager", "isRead.播报.127:");
          HttpManagerBase.senError("极光"+AppInfo.getTechNum(), "isRead.127");
          //          14:42:45.230 21556-21797/com.yskrq.yjs E/PushMessageReceiver: [onNotifyMessageArrived] NotificationMessage{notificationId=516817775, msgId='20266313553991304', appkey='447f2da0d311500893b0445d', notificationContent='5555技师还有5分钟到钟', notificationAlertType=-1, notificationTitle='云技师', notificationSmallIcon='', notificationLargeIcon='', notificationExtras='{"account":"L000026373","countdowns":"0","expendtime":"1","groupid":"9001","rsuhtimes":"0","sid":"252","uploadstatus":"0"}', notificationStyle=0, notificationBuilderId=0, notificationBigText='', notificationBigPicPath='', notificationInbox='', notificationPriority=0, notificationCategory='', developerArg0='', platform=0, notificationChannelId='', displayForeground='', notificationType=0', inAppMsgType=1', inAppMsgShowType=2', inAppMsgShowPos=0', inAppMsgTitle=, inAppMsgContentBody=}
          //          14:42:30.315 21556-21797/com.yskrq.yjs E/PushMessageReceiver: [onNotifyMessageArrived] NotificationMessage{notificationId=464847687, msgId='2251914957360196', appkey='447f2da0d311500893b0445d', notificationContent='5555技师还有5分钟到钟', notificationAlertType=-1, notificationTitle='云技师', notificationSmallIcon='', notificationLargeIcon='', notificationExtras='{"account":"L000026373","countdowns":"0","expendtime":"1","groupid":"9001","rsuhtimes":"0","sid":"267","uploadstatus":"0"}', notificationStyle=0, notificationBuilderId=0, notificationBigText='', notificationBigPicPath='', notificationInbox='', notificationPriority=0, notificationCategory='', developerArg0='', platform=0, notificationChannelId='', displayForeground='', notificationType=0', inAppMsgType=1', inAppMsgShowType=2', inAppMsgShowPos=0', inAppMsgTitle=, inAppMsgContentBody=}
          return 0;
        }
        readTimeTag.put(key, System.currentTimeMillis());
      }
      openVoice(context);
      String brand = getReadBrand(context) + "号技师";
      final String spearOut = brand + "已到钟";
      LOG.e("SpeakManager", "isRead.播报.99:" + spearOut);
      HttpManagerBase.senError("极光"+AppInfo.getTechNum(), "isRead.99:" + spearOut);
      Speaker.speakOut(context, spearOut, new Speaker.OnSpeakListener() {
        @Override
        public void onFinish(int status) {
          LOG.e("SpeakManager", "onFinish.98:" + status);
          if (TextToSpeech.SUCCESS == status) {
            HttpManager
                .hasSend(context, first.getAccount(), first.getSeqnum(), first.getFacilityno(), 1);
            Speaker.speakOut(context, spearOut, null);
          }
        }
      });
      LOG.e("SpeakManager", "isRead.播报.122:");
      return 3;
    }
    HttpManagerBase.senError("极光"+AppInfo.getTechNum(), "isRead.160" );
    return 0;
  }

  public static void removeReadHistory() {
    SPUtil.init(BASE.getCxt()).edit().remove("read.tag").apply();
  }

}
