package com.yskrq.yjs.jpush;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
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
import java.util.TimeZone;

import cn.jpush.android.api.CmdMessage;
import cn.jpush.android.api.CustomMessage;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.JPushMessage;
import cn.jpush.android.api.NotificationMessage;
import cn.jpush.android.service.JPushMessageReceiver;

public class PushMessageReceiver extends JPushMessageReceiver {
  private static final String TAG = "PushMessageReceiver";

  @Override
  public void onMessage(Context context, CustomMessage customMessage) {
    Log.e(TAG, "[onMessage] " + customMessage);
    processCustomMessage(context, customMessage);
  }

  /**
   * 调用时间 点击  ---> 1
   */
  @Override
  public void onNotifyMessageOpened(Context context, NotificationMessage message) {
    Log.e(TAG, "[onNotifyMessageOpened] " + message);
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
    Log.e(TAG, "[onMultiActionClicked] 用户点击了通知栏按钮");
    String nActionExtra = intent.getExtras()
                                .getString(JPushInterface.EXTRA_NOTIFICATION_ACTION_EXTRA);

    //开发者根据不同 Action 携带的 extra 字段来分配不同的动作。
    if (nActionExtra == null) {
      Log.d(TAG, "ACTION_NOTIFICATION_CLICK_ACTION nActionExtra is null");
      return;
    }
    if (nActionExtra.equals("my_extra1")) {
      Log.e(TAG, "[onMultiActionClicked] 用户点击通知栏按钮一");
    } else if (nActionExtra.equals("my_extra2")) {
      Log.e(TAG, "[onMultiActionClicked] 用户点击通知栏按钮二");
    } else if (nActionExtra.equals("my_extra3")) {
      Log.e(TAG, "[onMultiActionClicked] 用户点击通知栏按钮三");
    } else {
      Log.e(TAG, "[onMultiActionClicked] 用户点击通知栏按钮未定义");
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
    LOG.e(TAG, "getNotification.19:" + notificationMessage.toString());
    RelaxListBean.ValueBean bean = GsonUtil
        .getBean(notificationMessage.notificationExtras, RelaxListBean.ValueBean.class);
    if (bean == null) {
      return null;
    }
    NoticeUtil.clearNotify(context);
//    JPushInterface.clearAllNotifications(context);
    YJSNotifyManager.change(bean.getGroupid(), bean.getSid(), bean.getExpendtime());
    int tag = YJSNotifyManager.getShowStatus(bean.getGroupid());
    if (tag == -1) {
      YJSNotifyManager.resetPushInfo();
      return null;
    }
    SpeakManager.isRead(context, bean);
    String title, con;
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
    LOG.e("PushMessageReceiver", "getNotification.142:" + con + ">>" + title);
    return NoticeUtil.getNotification(context, title, con);
  }

  /**
   * 调用时间 新推送到达   -->  6
   */
  @Override
  public void onNotifyMessageArrived(Context context, NotificationMessage message) {
    Log.e(TAG, "[onNotifyMessageArrived] " + message);
    if (message.notificationBuilderId == 1) {
      Speaker.speakOut(context, message.notificationContent);
    }
  }

  @Override
  public void onNotifyMessageDismiss(Context context, NotificationMessage message) {
    Log.e(TAG, "[onNotifyMessageDismiss] " + message);
  }

  @Override
  public void onRegister(Context context, String registrationId) {
    Log.e(TAG, "[onRegister] " + registrationId);
  }

  /**
   * 调用时间 打开应用   -->  2
   */
  @Override
  public void onConnected(Context context, boolean isConnected) {
    Log.e(TAG, "[onConnected] " + isConnected);
  }

  /**
   * 调用时间 打开应用   -->  3
   */
  @Override
  public void onCommandResult(Context context, CmdMessage cmdMessage) {
    Log.e(TAG, "[onCommandResult] " + cmdMessage);
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
    //    if (MainActivity.isForeground) {
    //      String message = customMessage.message;
    //      String extras = customMessage.extra;
    //      Intent msgIntent = new Intent(MainActivity.MESSAGE_RECEIVED_ACTION);
    //      msgIntent.putExtra(MainActivity.KEY_MESSAGE, message);
    //      if (!ExampleUtil.isEmpty(extras)) {
    //        try {
    //          JSONObject extraJson = new JSONObject(extras);
    //          if (extraJson.length() > 0) {
    //            msgIntent.putExtra(MainActivity.KEY_EXTRAS, extras);
    //          }
    //        } catch (JSONException e) {
    //
    //        }
    //
    //      }
    //      LocalBroadcastManager.getInstance(context).sendBroadcast(msgIntent);
    //    }
  }

  /**
   * 调用时间 打开应用   -->  1
   */
  @Override
  public void onNotificationSettingsCheck(Context context, boolean isOn, int source) {
    super.onNotificationSettingsCheck(context, isOn, source);
    LOG.e("PushMessageReceiver", "onNotificationSettingsCheck.203:" + isOn);
  }
}
