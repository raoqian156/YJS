package com.yskrq.yjs.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.yskrq.yjs.MainActivity;
import com.yskrq.yjs.R;
import com.yskrq.yjs.keep.NotificationClickReceiver;
import com.yskrq.yjs.keep.NotificationUtils;

import static android.content.Context.NOTIFICATION_SERVICE;

public class NoticeUtil {
  public static void sentNotice(Context context, int id, String title, String con) {
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
}
