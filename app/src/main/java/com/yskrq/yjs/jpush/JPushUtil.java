package com.yskrq.yjs.jpush;

import android.content.Context;

import cn.jpush.android.api.JPushInterface;

public class JPushUtil {

  public static void init(Context context, boolean debug) {
    JPushInterface.setDebugMode(debug);
    JPushInterface.init(context);
  }

  public static void setNotificationGetter(NotificationGetter getter) {
    PushMessageReceiver.mNotificationGetter = getter;
  }
}
