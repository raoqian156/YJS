package com.yskrq.jpush;

import android.app.Notification;
import android.content.Context;

import cn.jpush.android.api.NotificationMessage;

public interface NotificationGetter {
  Notification getNotification(Context context, NotificationMessage notificationMessage);

  void onNotifyMessageArrived(Context context, NotificationMessage message);

  void onConnected(Context context, boolean isConnected);
}
