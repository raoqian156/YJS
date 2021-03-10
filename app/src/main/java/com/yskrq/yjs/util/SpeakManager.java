package com.yskrq.yjs.util;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;

import com.yskrq.common.AppInfo;
import com.yskrq.common.BASE;
import com.yskrq.common.bean.RelaxListBean;
import com.yskrq.common.okhttp.HttpManagerBase;
import com.yskrq.common.util.LOG;
import com.yskrq.common.util.SPUtil;
import com.yskrq.yjs.Speaker;
import com.yskrq.yjs.net.HttpManager;

import java.text.SimpleDateFormat;
import java.util.HashMap;

public class SpeakManager {
  private static String getReadBrand(Context context) {
    String s = AppInfo.getTechNum();
    if (TextUtils.isEmpty(s)) return "";
    return s.replaceAll("", " ");
  }


  static HashMap<String, Long> readTimeTag = new HashMap<>();

  public static int isRead(final Context context, final RelaxListBean.ValueBean first,
                           String... tag) {
    if (first == null) {
      return 0;
    }
    if (tag != null && tag.length > 0 && !TextUtils.isEmpty(tag[0])) {
      LOG.e("SpeakManager", "isRead.showTag:" + tag[0]);
    }
    final String rcvTime = new SimpleDateFormat("HH:mm:ss").format(System.currentTimeMillis());
    if (first.hasNewTask()) {
      LOG.showUserWhere("SpeakManager"+rcvTime);
      String key = first.getAccount() + "1";
      if (readTimeTag.containsKey(key)) {
        long time = readTimeTag.get(key);
        if (System.currentTimeMillis() - time < 10000) {
          LOG.e("SpeakManager", "isRead.播报.72:"+rcvTime);
          HttpManagerBase.senError("极光" + AppInfo.getTechNum(), "isRead.72"+rcvTime);
          return 0;
        }
        readTimeTag.put(key, System.currentTimeMillis());
      }
      if (!PhoneUtil.openVoice(context)) {
        return 0;
      }
      LOG.e("SpeakManager", context.toString() + ".isRead.播报.78:您有新的任务"+rcvTime);
      HttpManagerBase.senError("极光" + AppInfo.getTechNum(), "isRead.78:您有新的任务"+rcvTime);
      Speaker.speakOut(context, "您有新的任务", new Speaker.OnSpeakListener() {
        @Override
        public void onFinish(int status) {
          if (TextToSpeech.SUCCESS == status) {
            LOG.e("SpeakManager", context.toString() + ".isRead.播报成功:您有新的任务"+rcvTime);
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
      if (!PhoneUtil.openVoice(context)) {
        return 0;
      }
      LOG.e("SpeakManager", "isRead.播报.79:"+rcvTime);
      String key = first.getAccount() + "2";
      if (readTimeTag.containsKey(key)) {
        long time = readTimeTag.get(key);
        if (System.currentTimeMillis() - time < 10000) {
          HttpManagerBase.senError("极光" + AppInfo.getTechNum(), "isRead.96"+rcvTime);
          LOG.e("SpeakManager", "isRead.播报.96:"+rcvTime);
          return 0;
        }
        readTimeTag.put(key, System.currentTimeMillis());
      }
      final String brand = getReadBrand(context) + "号技师";
      final String spearOut = brand + "还有 " + AppInfo.getCuiZHongMinutes(context) + " 分钟下钟";
      LOG.e("SpeakManager", "isRead.播报.100:" + spearOut);
      HttpManagerBase.senError("极光" + AppInfo.getTechNum(), "isRead.100:" + spearOut);
      Speaker.speakOutTwice(context, spearOut, true, new Speaker.OnSpeakListener() {
        @Override
        public void onFinish(int status) {
          if (TextToSpeech.SUCCESS == status) {
            LOG.e("SpeakManager", "isRead.播报成功:5分钟下钟"+rcvTime);
            HttpManager
                .hasSend(context, first.getAccount(), first.getSeqnum(), first.getFacilityno(), 0);
          }
        }
      });
      return 2;
    } else if (first.daozhong()) {
      String key = first.getAccount() + "3";
      if (readTimeTag.containsKey(key)) {
        long time = readTimeTag.get(key);
        if (System.currentTimeMillis() - time < 10000) {
          LOG.e("SpeakManager", "isRead.播报.127:"+rcvTime);
          HttpManagerBase.senError("极光" + AppInfo.getTechNum(), "isRead.127"+rcvTime);
          //          14:42:45.230 21556-21797/com.yskrq.yjs E/PushMessageReceiver: [onNotifyMessageArrived] NotificationMessage{notificationId=516817775, msgId='20266313553991304', appkey='447f2da0d311500893b0445d', notificationContent='5555技师还有5分钟到钟', notificationAlertType=-1, notificationTitle='云技师', notificationSmallIcon='', notificationLargeIcon='', notificationExtras='{"account":"L000026373","countdowns":"0","expendtime":"1","groupid":"9001","rsuhtimes":"0","sid":"252","uploadstatus":"0"}', notificationStyle=0, notificationBuilderId=0, notificationBigText='', notificationBigPicPath='', notificationInbox='', notificationPriority=0, notificationCategory='', developerArg0='', platform=0, notificationChannelId='', displayForeground='', notificationType=0', inAppMsgType=1', inAppMsgShowType=2', inAppMsgShowPos=0', inAppMsgTitle=, inAppMsgContentBody=}
          //          14:42:30.315 21556-21797/com.yskrq.yjs E/PushMessageReceiver: [onNotifyMessageArrived] NotificationMessage{notificationId=464847687, msgId='2251914957360196', appkey='447f2da0d311500893b0445d', notificationContent='5555技师还有5分钟到钟', notificationAlertType=-1, notificationTitle='云技师', notificationSmallIcon='', notificationLargeIcon='', notificationExtras='{"account":"L000026373","countdowns":"0","expendtime":"1","groupid":"9001","rsuhtimes":"0","sid":"267","uploadstatus":"0"}', notificationStyle=0, notificationBuilderId=0, notificationBigText='', notificationBigPicPath='', notificationInbox='', notificationPriority=0, notificationCategory='', developerArg0='', platform=0, notificationChannelId='', displayForeground='', notificationType=0', inAppMsgType=1', inAppMsgShowType=2', inAppMsgShowPos=0', inAppMsgTitle=, inAppMsgContentBody=}
          return 0;
        }
        readTimeTag.put(key, System.currentTimeMillis());
      }
      if (!PhoneUtil.openVoice(context)) {
        return 0;
      }
      String brand = getReadBrand(context) + "号技师";
      final String spearOut = brand + "已到钟";
      LOG.e("SpeakManager", "isRead.播报.99:" + spearOut);
      HttpManagerBase.senError("极光" + AppInfo.getTechNum(), "isRead.99:" + spearOut);
      Speaker.speakOutTwice(context, spearOut, true, new Speaker.OnSpeakListener() {
        @Override
        public void onFinish(int status) {
          LOG.e("SpeakManager", "onFinish.98:" + status);
          if (TextToSpeech.SUCCESS == status) {
            LOG.e("SpeakManager", "isRead.播报成功:已到钟"+rcvTime);
            HttpManager
                .hasSend(context, first.getAccount(), first.getSeqnum(), first.getFacilityno(), 1);
          }
        }
      });
      LOG.e("SpeakManager", "isRead.播报.122:"+rcvTime);
      return 3;
    }
    PhoneUtil.closeVoice(context);
    HttpManagerBase.senError("极光" + AppInfo.getTechNum(), "isRead.160");
    return 0;
  }

  public static void removeReadHistory() {
    SPUtil.init(BASE.getCxt()).edit().remove("read.tag").apply();
  }

}
