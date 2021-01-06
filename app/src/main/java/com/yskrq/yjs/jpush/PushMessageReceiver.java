package com.yskrq.yjs.jpush;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import com.yskrq.common.AppInfo;
import com.yskrq.common.util.GsonUtil;
import com.yskrq.common.util.LOG;
import com.yskrq.common.util.ToastUtil;
import com.yskrq.yjs.Speaker;
import com.yskrq.yjs.bean.RelaxListBean;
import com.yskrq.yjs.util.NoticeUtil;
import com.yskrq.yjs.util.SpeakManager;
import com.yskrq.yjs.util.YJSNotifyManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import cn.jpush.android.api.CmdMessage;
import cn.jpush.android.api.CustomMessage;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.JPushMessage;
import cn.jpush.android.api.NotificationMessage;
import cn.jpush.android.service.JPushMessageReceiver;

import static com.yskrq.yjs.keep.KeepAliveService.NOTIFICATION_ID;

public class PushMessageReceiver extends JPushMessageReceiver {

  @Override
  public void onMessage(Context context, CustomMessage customMessage) {
    Log.e("PushMessageReceiver", "[onMessage] " + customMessage);
    processCustomMessage(context, customMessage);
  }

  /**
   * 调用时间 点击  ---> 1
   */
  @Override
  public void onNotifyMessageOpened(Context context, NotificationMessage message) {
    Log.e("PushMessageReceiver", "[onNotifyMessageOpened] " + message);
    try {
      //打开自定义的Activity
      ToastUtil.show("打开页面");
      //            Intent i = new Intent(context, TestActivity.class);
      //            Bundle bundle = new Bundle();
      //            bundle.putString(JPushInterface.EXTRA_NOTIFICATION_TITLE,message.notificationTitle);
      //            bundle.putString(JPushInterface.EXTRA_ALERT,message.notificationContent);
      //            i.putExtras(bundle);
      //            //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      //            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
      //            context.startActivity(i);
    } catch (Throwable throwable) {

    }
  }

  @Override
  public void onMultiActionClicked(Context context, Intent intent) {
    Log.e("PushMessageReceiver", "[onMultiActionClicked] 用户点击了通知栏按钮");
    String nActionExtra = intent.getExtras()
                                .getString(JPushInterface.EXTRA_NOTIFICATION_ACTION_EXTRA);

    //开发者根据不同 Action 携带的 extra 字段来分配不同的动作。
    if (nActionExtra == null) {
      Log.d("PushMessageReceiver", "ACTION_NOTIFICATION_CLICK_ACTION nActionExtra is null");
      return;
    }
    if (nActionExtra.equals("my_extra1")) {
      Log.e("PushMessageReceiver", "[onMultiActionClicked] 用户点击通知栏按钮一");
    } else if (nActionExtra.equals("my_extra2")) {
      Log.e("PushMessageReceiver", "[onMultiActionClicked] 用户点击通知栏按钮二");
    } else if (nActionExtra.equals("my_extra3")) {
      Log.e("PushMessageReceiver", "[onMultiActionClicked] 用户点击通知栏按钮三");
    } else {
      Log.e("PushMessageReceiver", "[onMultiActionClicked] 用户点击通知栏按钮未定义");
    }
  }

  @Override
  public boolean isNeedShowNotification(Context context, NotificationMessage notificationMessage,
                                        String s) {
    LOG.e("PushMessageReceiver", "isNeedShowNotification.79:该方法未调用");
    return super.isNeedShowNotification(context, notificationMessage, s);
  }

  /**
   * 调用时间 新推送到达   -->  5
   */
  @Override
  public Notification getNotification(Context context, NotificationMessage notificationMessage) {
    LOG.e("PushMessageReceiver", "getNotification.19:" + notificationMessage.toString());
    RelaxListBean.ValueBean bean = GsonUtil
        .getBean(notificationMessage.notificationExtras, RelaxListBean.ValueBean.class);
    String title, con;
    if (bean == null) {
      LOG.e("PushMessageReceiver", "getNotification.101:");
      title = AppInfo.getTechNum() + " 号技师";
      con = "暂无新任务...";
    } else {
      for (OnPushListener push : mOnPushListeners) {
        push.onPush(bean);
      }
      NoticeUtil.clearNotify(context);
      JPushInterface.clearAllNotifications(context);
      YJSNotifyManager.change(bean.getGroupid(), bean.getSid(), bean.getExpendtime());
      int tag = YJSNotifyManager.getShowStatus(bean.getGroupid());
      if (tag == -1) {
        YJSNotifyManager.resetPushInfo();
        title = AppInfo.getTechNum() + " 号技师";
        con = "暂无新任务...";
      } else {
        LOG.e("PushMessageReceiver", "getNotification.播报:");
        SpeakManager.isRead(context, bean);
        int leftTime = 0;
        try {
          leftTime = Integer.parseInt(bean.getExpendtime());
        } catch (Exception e) {
        }
        if (context == null || TextUtils.isEmpty(AppInfo.getTechNum())) {
          title = "登录失效";
          con = "请重新登录";
        } else if (tag == 0) {
          title = AppInfo.getTechNum() + " 号技师";
          SimpleDateFormat simpleDateFormat;
          simpleDateFormat = new SimpleDateFormat("HH:mm");
          con = simpleDateFormat.format(System.currentTimeMillis()) + " 暂无新任务...";
        } else if (tag == 1) {
          title = "您有新任务";
          con = "已等待" + leftTime + "分钟";
        } else if (tag == 2) {
          title = "下钟剩余";
          long time = leftTime * 1000 + System.currentTimeMillis();
          if (time < 0) {
            title = "下钟时间到";
            con = "00:00";
          } else {
            time += 1000;
            SimpleDateFormat simpleDateFormat;
            simpleDateFormat = new SimpleDateFormat("HH:mm");
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            con = simpleDateFormat.format(time);
          }
        } else {
          title = AppInfo.getTechNum() + " 号技师";
          con = "暂无新任务...";
        }
      }
    }
    LOG.bean("PushMessageReceiver", bean);
    LOG.e("PushMessageReceiver", "getNotification.142:" + con + ">>" + title);
    return NoticeUtil.getNotification(context, title, con);
  }

  static Handler mainHandler = new Handler(Looper.getMainLooper());

  /**
   * 调用时间 新推送到达   -->  6
   */
  @Override
  public void onNotifyMessageArrived(Context context, NotificationMessage message) {
    Log.e("PushMessageReceiver", "[onNotifyMessageArrived] " + message);
    if (message.notificationBuilderId == 1) {
      Speaker.speakOut(context, message.notificationContent);
    } else if (message.notificationBuilderId == 2 || message.notificationBuilderId == 3) {
      SpeakManager.removeReadHistory();
    }
    final RelaxListBean.ValueBean bean = GsonUtil
        .getBean(message.notificationExtras, RelaxListBean.ValueBean.class);
    if (bean == null) {
      LOG.e("PushMessageReceiver", "onNotifyMessageArrived.101:");
    } else {
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
      notifyUser(context);
    }
  }

  private static void notifyUser(Context context) {
    LOG.e("KeepAliveService", "notifyUser.247:");
    String title, con;
    int tag = AppInfo.getWaitType();
    int priorityLevel = 0;
    if (context == null || TextUtils.isEmpty(AppInfo.getTechNum())) {
      title = "登录失效";
      con = "请重新登录";
    } else if (tag == 0) {
      title = AppInfo.getTechNum() + " 号技师";
      SimpleDateFormat simpleDateFormat;
      simpleDateFormat = new SimpleDateFormat("HH:mm");
      con = simpleDateFormat.format(System.currentTimeMillis()) + " 暂无新任务...";
    } else if (tag == 1) {
      priorityLevel = 2;
      title = "您有新任务";
      con = "已等待" + AppInfo.getWait(context) + "分钟";
    } else if (tag == 2) {
      priorityLevel = 1;
      title = "下钟剩余";
      long time = AppInfo.getRunningLeftTime(context);
      if (time < 0) {
        title = "下钟时间到";
        con = "00:00";
      } else {
        time += 1000;
        SimpleDateFormat simpleDateFormat;
        simpleDateFormat = new SimpleDateFormat("HH:mm");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        con = simpleDateFormat.format(time);
      }
    } else {
      title = AppInfo.getTechNum() + " 号技师";
      con = "暂无新任务...";
    }
    NoticeUtil.sentNotice(context, NOTIFICATION_ID, title, con, priorityLevel);
  }

  @Override
  public void onNotifyMessageDismiss(Context context, NotificationMessage message) {
    Log.e("PushMessageReceiver", "[onNotifyMessageDismiss] " + message);
  }

  @Override
  public void onRegister(Context context, String registrationId) {
    Log.e("PushMessageReceiver", "[onRegister] " + registrationId);
  }

  /**
   * 调用时间 打开应用   -->  2
   */
  @Override
  public void onConnected(Context context, boolean isConnected) {
    Log.e("PushMessageReceiver", "[onConnected] " + isConnected);
  }

  /**
   * 调用时间 打开应用   -->  3
   */
  @Override
  public void onCommandResult(Context context, CmdMessage cmdMessage) {
    Log.e("PushMessageReceiver", "[onCommandResult] " + cmdMessage);
  }

  @Override
  public void onTagOperatorResult(Context context, JPushMessage jPushMessage) {
    LOG.e("PushMessageReceiver", "onTagOperatorResult.97:" + jPushMessage.toString());
    //    TagAliasOperatorHelper.getInstance().onTagOperatorResult(context, jPushMessage);
    super.onTagOperatorResult(context, jPushMessage);
  }

  @Override
  public void onCheckTagOperatorResult(Context context, JPushMessage jPushMessage) {
    LOG.e("PushMessageReceiver", "onCheckTagOperatorResult.97:" + jPushMessage.toString());
    //    TagAliasOperatorHelper.getInstance().onCheckTagOperatorResult(context, jPushMessage);
    super.onCheckTagOperatorResult(context, jPushMessage);
  }

  /**
   * 调用时间 打开应用   -->  4
   */
  @Override
  public void onAliasOperatorResult(Context context, JPushMessage jPushMessage) {
    //    TagAliasOperatorHelper.getInstance().onAliasOperatorResult(context, jPushMessage);
    LOG.e("PushMessageReceiver", "onAliasOperatorResult.97:" + jPushMessage.toString());
    super.onAliasOperatorResult(context, jPushMessage);
  }

  @Override
  public void onMobileNumberOperatorResult(Context context, JPushMessage jPushMessage) {
    //    TagAliasOperatorHelper.getInstance().onMobileNumberOperatorResult(context, jPushMessage);
    LOG.e("PushMessageReceiver", "onMobileNumberOperatorResult.97:" + jPushMessage.toString());
    super.onMobileNumberOperatorResult(context, jPushMessage);
  }

  //send msg to MainActivity
  private void processCustomMessage(Context context, CustomMessage customMessage) {
    LOG.e("PushMessageReceiver", "processCustomMessage.122:" + customMessage.toString());
  }

  /**
   * 调用时间 打开应用   -->  1
   */
  @Override
  public void onNotificationSettingsCheck(Context context, boolean isOn, int source) {
    super.onNotificationSettingsCheck(context, isOn, source);
    LOG.e("PushMessageReceiver", "onNotificationSettingsCheck.203:" + isOn);
  }


  public interface OnPushListener {

    void onPush(RelaxListBean.ValueBean bean);
  }

  static List<OnPushListener> mOnPushListeners = new ArrayList<>();

  public static void addOnPushListener(OnPushListener listener) {
    if (listener != null && mOnPushListeners != null && !mOnPushListeners
        .contains(mOnPushListeners)) {
      mOnPushListeners.add(listener);
    }
  }

  public static void removeOnPushListener(OnPushListener listener) {
    if (mOnPushListeners != null) mOnPushListeners.remove(listener);
  }

}
