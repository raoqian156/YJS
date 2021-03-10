package com.yskrq.yjs.jpush;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.yskrq.common.util.LOG;

import cn.jpush.android.api.CmdMessage;
import cn.jpush.android.api.CustomMessage;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.JPushMessage;
import cn.jpush.android.api.NotificationMessage;
import cn.jpush.android.service.JPushMessageReceiver;

public class PushMessageReceiver extends JPushMessageReceiver {

  static NotificationGetter mNotificationGetter;


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
    if (mNotificationGetter != null) {
      return mNotificationGetter.getNotification(context, notificationMessage);
    }
    return null;
  }

  /**
   * 调用时间 新推送到达   -->  6
   */
  @Override
  public void onNotifyMessageArrived(Context context, NotificationMessage message) {
    if (mNotificationGetter != null) {
      mNotificationGetter.onNotifyMessageArrived(context, message);
    }
  }


  @Override
  public void onNotifyMessageDismiss(Context context, NotificationMessage message) {
    Log.e("PushMessageReceiver", "[onNotifyMessageDismiss] " + message);
  }

  @Override
  public void onRegister(Context context, String registrationId) {
    Log.e("PushMessageReceiver", "[onRegister] " + registrationId);
  }

  private static boolean connected = false;

  /**
   * 调用时间 打开应用   -->  2
   */
  @Override
  public void onConnected(Context context, boolean isConnected) {
    if (mNotificationGetter != null) {
      mNotificationGetter.onConnected(context, isConnected);
    }
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
}
