package com.yskrq.yjs.jpush;

import android.app.Notification;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.yskrq.common.AppInfo;
import com.yskrq.common.bean.RelaxListBean;
import com.yskrq.common.okhttp.HttpManagerBase;
import com.yskrq.common.util.GsonUtil;
import com.yskrq.common.util.LOG;
import com.yskrq.yjs.Speaker;
import com.yskrq.yjs.util.SpeakManager;
import com.yskrq.yjs.util.TechStatusManager;
import com.yskrq.yjs.util.YJSNotifyManager;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.NotificationMessage;

//import com.yskrq.yjs.jpush.NotificationGetter;

public class JpushHelper implements NotificationGetter {
  static Handler mainHandler = new Handler(Looper.getMainLooper());
  private static boolean connected = false;

  static List<OnPushListener> mOnPushListeners = new ArrayList<>();

  public interface OnPushListener {
    void onConnected(boolean connected);

    void onPush(RelaxListBean.ValueBean bean);
  }

  public static void addOnPushListener(OnPushListener listener) {
    if (listener != null && mOnPushListeners != null && !mOnPushListeners.contains(listener)) {
      mOnPushListeners.add(listener);
    }
    if (listener != null) listener.onConnected(connected);
  }

  public static void removeOnPushListener(OnPushListener listener) {
    if (mOnPushListeners != null) mOnPushListeners.remove(listener);
  }

  @Override
  public Notification getNotification(Context context, NotificationMessage notificationMessage) {
    LOG.e("JpushHelper", "getNotification.19:" + notificationMessage.toString());
    RelaxListBean.ValueBean bean = GsonUtil
        .getBean(notificationMessage.notificationExtras, RelaxListBean.ValueBean.class);
    ////    String title, con;
    ////    boolean hasRead = false;
    ////    if (bean == null) {
    ////      LOG.e("JpushHelper", "getNotification.101:");
    ////      title = AppInfo.getTechNum() + " 号技师";
    ////      con = "暂无新任务...";
    ////    } else {
    ////      for (OnPushListener push : mOnPushListeners) {
    ////        push.onPush(bean);
    ////      }
    ////      NoticeUtil.clearNotify(context);
    ////      JPushInterface.clearAllNotifications(context);
    ////      YJSNotifyManager.change(bean.getGroupid(), bean.getSid(), bean.getExpendtime());
    ////      int tag = YJSNotifyManager.getShowStatus(bean.getGroupid());
    ////
    ////      if (tag == -1) {
    ////        YJSNotifyManager.resetPushInfo();
    ////        title = AppInfo.getTechNum() + " 号技师";
    ////        con = "暂无新任务...";
    ////      } else {
    ////        int leftTime = 0;
    ////        try {
    ////          leftTime = Integer.parseInt(bean.getExpendtime());
    ////        } catch (Exception e) {
    ////        }
    ////        if (context == null || TextUtils.isEmpty(AppInfo.getTechNum())) {
    ////          title = "登录失效";
    ////          con = "请重新登录";
    ////        } else if (tag == 0) {
    ////          title = AppInfo.getTechNum() + " 号技师";
    ////          SimpleDateFormat simpleDateFormat;
    ////          simpleDateFormat = new SimpleDateFormat("HH:mm");
    ////          con = simpleDateFormat.format(System.currentTimeMillis()) + " 暂无新任务...";
    ////        } else if (tag == 1) {
    ////          title = "您有新任务";
    ////          con = "已等待" + leftTime + "分钟";
    ////        } else if (tag == 2) {
    ////          title = "下钟剩余";
    ////          long time = leftTime * 1000 + System.currentTimeMillis();
    ////          if (time < 0) {
    ////            title = "下钟时间到";
    ////            con = "00:00";
    ////          } else {
    ////            time += 1000;
    ////            SimpleDateFormat simpleDateFormat;
    ////            simpleDateFormat = new SimpleDateFormat("HH:mm");
    ////            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    ////            con = simpleDateFormat.format(time);
    ////          }
    ////        } else {
    ////          title = AppInfo.getTechNum() + " 号技师";
    ////          con = "暂无新任务...";
    ////        }
    ////      }
    ////    }
    //    LOG.bean("JpushHelper.2", bean);
    //    LOG.e("JpushHelper", "getNotification.142:" + con + ">>" + title);
    //    return NoticeUtil.getNotification(context, title, con, hasRead);
    return null;
  }

  @Override
  public void onNotifyMessageArrived(final Context context, NotificationMessage message) {
    LOG.bean("JpushHelper.1", message);
    HttpManagerBase.senError("极光" + AppInfo.getTechNum(), "onNotifyMessageArrived:" + message);
    int i = 0;
    if ("1".equals(message.notificationChannelId)) {
      LOG.e("JpushHelper", "onNotifyMessageArrived.createNotificationChannel.124:");
      i = 1;
    } else if ("2".equals(message.notificationChannelId)) {
      LOG.e("JpushHelper", "onNotifyMessageArrived.createNotificationChannel.127:");
      i = 2;
    } else if ("3".equals(message.notificationChannelId)) {
      LOG.e("JpushHelper", "onNotifyMessageArrived.createNotificationChannel.130:");
      i = 3;
    }
    if (message.notificationBuilderId == 1) {
      HttpManagerBase.senError("极光" + AppInfo
          .getTechNum(), "onNotifyMessageArrived.播报:" + message.notificationContent);
      Speaker.speakOut(context, message.notificationContent);
    } else if (message.notificationBuilderId == 2 || message.notificationBuilderId == 3) {
      SpeakManager.removeReadHistory();
    }
    final RelaxListBean.ValueBean bean = GsonUtil
        .getBean(message.notificationExtras, RelaxListBean.ValueBean.class);
    if (bean == null) {
      LOG.e("JpushHelper", "onNotifyMessageArrived.101:");
    } else {
      HttpManagerBase.senError("极光" + AppInfo.getTechNum(), "播报来源:JpushHelper");
      LOG.e("JpushHelper", "onNotifyMessageArrived.播报来源.isRead");
      SpeakManager.isRead(context, bean,"JpushHelper");
      YJSNotifyManager.change(bean.getGroupid(), bean.getSid(), bean.getExpendtime());
      int tag = YJSNotifyManager.getShowStatus(bean.getGroupid());
      if (tag == -1) {
        YJSNotifyManager.resetPushInfo();
      }
      mainHandler.post(new Runnable() {
        @Override
        public void run() {
          for (OnPushListener push : mOnPushListeners) {
            push.onPush(bean);
          }
        }
      });
      TechStatusManager.getInstance().refuseFirstFromJpush(bean);
      //      notifyUser(context, i);
    }
  }

  @Override
  public void onConnected(Context context, boolean isConnected) {
    JpushHelper.connected = isConnected;
    if (mOnPushListeners != null && mOnPushListeners.size() > 0) {
      for (OnPushListener push : mOnPushListeners) {
        push.onConnected(isConnected);
      }
    }
  }

  private static void notifyUser(Context context, int... i) {
    //    LOG.e("KeepAliveService", "notifyUser.247:");
    //    String title, con;
    //    int tag = TechStatusManager.getInstance().getWaitType();
    //    int priorityLevel = 0;
    //    if (context == null || TextUtils.isEmpty(AppInfo.getTechNum())) {
    //      title = "登录失效";
    //      con = "请重新登录";
    //    } else if (tag == 0) {
    //      title = AppInfo.getTechNum() + " 号技师";
    //      SimpleDateFormat simpleDateFormat;
    //      simpleDateFormat = new SimpleDateFormat("HH:mm");
    //      con = simpleDateFormat.format(System.currentTimeMillis()) + " 暂无新任务...";
    //    } else if (tag == 1) {
    //      priorityLevel = 2;
    //      title = "您有新任务";
    //      con = "已等待" + TechStatusManager.getInstance().getWait() + "分钟";
    //    } else if (tag == 2) {
    //      priorityLevel = 1;
    //      title = "下钟剩余";
    //      long time = TechStatusManager.getInstance().getRunningLeftTime();
    //      if (time < 0) {
    //        title = "下钟时间到";
    //        con = "00:00";
    //      } else {
    //        time += 1000;
    //        SimpleDateFormat simpleDateFormat;
    //        simpleDateFormat = new SimpleDateFormat("HH:mm");
    //        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    //        con = simpleDateFormat.format(time);
    //      }
    //    } else {
    //      title = AppInfo.getTechNum() + " 号技师";
    //      con = "暂无新任务...";
    //    }
    //    JPushInterface.clearAllNotifications(context);
    //    LOG.e("JpushHelper", "notifyUser.createNotificationChannel:" + i[0]);
    //    NoticeUtil.sentNotice(context, NOTIFICATION_ID, title, con, priorityLevel, tag > 0, i);
  }
}
