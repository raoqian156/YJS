package com.yskrq.yjs.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.yskrq.common.util.LOG;
import com.yskrq.yjs.MainActivity;
import com.yskrq.yjs.R;
import com.yskrq.yjs.keep.NotificationClickReceiver;
import com.yskrq.yjs.keep.NotificationUtils;

import cn.jpush.android.api.JPushInterface;

import static android.content.Context.NOTIFICATION_SERVICE;
import static com.yskrq.yjs.keep.KeepAliveService.NOTIFICATION_ID;

public class NoticeUtil {
  public static void sentNotice(Context context, int id, String title, String con) {
    LOG.e("NoticeUtil", "sentNotice.clearAllNotifications:");
    JPushInterface.clearAllNotifications(context);
    Intent intentTarget = new Intent(context, MainActivity.class);
    PendingIntent contentIntent = PendingIntent
        .getActivity(context, 0, intentTarget, PendingIntent.FLAG_CANCEL_CURRENT);

    Intent intent = new Intent(context, NotificationClickReceiver.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    Notification notification = NotificationUtils
        .createNotification(context, title, con, R.mipmap.ic_launcher, intent);
    notification.contentIntent = contentIntent;
    NotificationManager mNManager = (NotificationManager) context
        .getSystemService(NOTIFICATION_SERVICE);
    mNManager.notify(id, notification);
  }

  public static void clearNotify(Context context) {
    NotificationManager mNManager = (NotificationManager) context
        .getSystemService(NOTIFICATION_SERVICE);
    mNManager.cancel(NOTIFICATION_ID);
  }

  public static Notification getNotification(Context context, String title, String con) {
    Intent intentTarget = new Intent(context, MainActivity.class);
    PendingIntent contentIntent = PendingIntent
        .getActivity(context, 0, intentTarget, PendingIntent.FLAG_CANCEL_CURRENT);

    Intent intent = new Intent(context, NotificationClickReceiver.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    Notification notification = NotificationUtils
        .createNotification(context, title, con, R.mipmap.ic_launcher, intent);
    notification.contentIntent = contentIntent;
    return notification;
  }
}
