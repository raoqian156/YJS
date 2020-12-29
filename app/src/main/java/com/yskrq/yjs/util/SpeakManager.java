package com.yskrq.yjs.util;

import android.app.NotificationManager;
import android.content.Context;
import android.media.AudioManager;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;

import com.yskrq.common.AppInfo;
import com.yskrq.common.BASE;
import com.yskrq.common.util.LOG;
import com.yskrq.common.util.SPUtil;
import com.yskrq.yjs.Speaker;
import com.yskrq.yjs.bean.RelaxListBean;
import com.yskrq.yjs.net.HttpManager;

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
      LOG.e("SpeakManager", "openVoice.48:打开声音失败");
    } else {
      AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
      if (audioManager != null) {
        audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        audioManager.getStreamVolume(AudioManager.STREAM_RING);
        LOG.e("SpeakManager", "openVoice.48:取消静音");
      }
    }
  }

  public static int isRead(final Context context, final RelaxListBean.ValueBean first) {
    if (first == null) {
      openVoice(context);
      return 0;
    }
    if (first.hasNewTask()) {
      //      openVoice(context);
      //      Speaker.speakOut(context, "您有新的任务", new Speaker.OnSpeakListener() {
      //        @Override
      //        public void onFinish(int status) {
      //          if (TextToSpeech.SUCCESS == status) {
      //            HttpManager.sendNewTaskSuccess(context, first.getAccount(), first.getSeqnum(), first
      //                .getFacilityno());
      //          }
      //        }
      //      });
      //      HttpManager.sendNewTaskSuccess(context, first.getAccount(), first.getSeqnum(), first
      //          .getFacilityno());
      return 1;
    } else if (first.cuizhong(AppInfo.getCuiZHongMinutes(context))) {
      openVoice(context);
      final String brand = getReadBrand(context) + "号技师";
      final String spearOut = brand + "还有 " + AppInfo.getCuiZHongMinutes(context) + " 分钟下钟";
      if (hasRead(first.getAccount() + "_2")) {
        return 0;
      }
      Speaker.speakOut(context, spearOut, new Speaker.OnSpeakListener() {
        @Override
        public void onFinish(int status) {
          LOG.e("SpeakManager", "onFinish.83:" + status);
          if (TextToSpeech.SUCCESS == status) {
            HttpManager
                .hasSend(context, first.getAccount(), first.getSeqnum(), first.getFacilityno(), 0);
            Speaker.speakOut(context, spearOut, null);
          }
        }
      });
      return 2;
    } else if (first.daozhong()) {
      openVoice(context);
      if (hasRead(first.getAccount() + "_3")) {
        return 0;
      }
      String brand = getReadBrand(context) + "号技师";
      final String spearOut = brand + "已到钟";
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
      return 3;
    }
    return 0;
  }

  private static boolean hasRead(String s) {
    String read = SPUtil.getString(BASE.getCxt(), "read.tag");
    if (TextUtils.equals(read, s)) {
      return true;
    }
    SPUtil.saveString(BASE.getCxt(), "read.tag", s);
    return false;
  }

}
