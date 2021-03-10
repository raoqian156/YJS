package com.yskrq.yjs.keep;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import com.yskrq.common.util.LOG;
import com.yskrq.yjs.ui.KeepOnActivity;
import com.yskrq.yjs.util.TechStatusManager;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

public class NotificationUtils extends ContextWrapper {
  private NotificationManager manager;
  private String id;
  private String name;
  private Context context;

  private NotificationUtils(Context context) {
    super(context);
    this.context = context;
    id = context.getPackageName();
    name = context.getPackageName();
  }

  String realId;

  public String getRealId() {
    return realId;
  }

  public void setRealId(String realId) {
    this.realId = realId;
  }

  @RequiresApi(api = Build.VERSION_CODES.O)
  public void createNotificationChannel(int... voice) {
    LOG.e("NotificationUtils", "createNotificationChannel.id:" + id);
    LOG.e("NotificationUtils", "createNotificationChannel.name:" + name);
    Uri voiceUri = null;
    boolean needVibration = KeepOnActivity.isShowToUser()&& TechStatusManager.getInstance().willClose();
    //    if (voice != null && voice.length > 0) {
    //      setRealId(id + voice[0]);
    //      needVibration = false;
    ////      if (voice[0] == 1) {
    ////        voiceUri = Uri.parse("android.resource://com.yskrq.yjs/raw/newtask");
    ////      } else if (voice[0] == 2) {
    ////        voiceUri = Uri.parse("android.resource://com.yskrq.yjs/raw/fiveremind");
    ////      } else if (voice[0] == 3) {
    ////        voiceUri = Uri.parse("android.resource://com.yskrq.yjs/raw/timeover");
    ////      } else {
    ////      }
    //    } else {
    //      setRealId(id);
    //    }
    setRealId(id);
    if (voiceUri != null) {
      LOG.e("NotificationUtils", needVibration + ".createNotificationChannel.voiceUri:" + voiceUri
          .getPath());
    } else {
      LOG.e("NotificationUtils", needVibration + ".createNotificationChannel.voiceUri:null");
    }
    NotificationChannel channel = new NotificationChannel(getRealId(), name, NotificationManager.IMPORTANCE_HIGH);
    channel.enableLights(false);
    channel.enableVibration(needVibration);
    channel.setSound(voiceUri, null);
    getManager().createNotificationChannel(channel);
  }

  private NotificationManager getManager() {
    if (manager == null) {
      manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }
    return manager;
  }

  @RequiresApi(api = Build.VERSION_CODES.O)
  public Notification.Builder getChannelNotification(String title, String content, int icon,
                                                     Intent intent) {
    LOG.e("NotificationUtils", "getChannelNotification.createNotificationChannel:" + getRealId());
    //PendingIntent.FLAG_UPDATE_CURRENT 这个类型才能传值
    PendingIntent pendingIntent = PendingIntent
        .getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    return new Notification.Builder(context, getRealId()).setContentTitle(title)
                                                         .setContentText(content).setSmallIcon(icon)
                                                         .setAutoCancel(true)
                                                         .setContentIntent(pendingIntent);
  }

  public NotificationCompat.Builder getNotification_25(String title, String content, int icon,
                                                       Intent intent) {
    //PendingIntent.FLAG_UPDATE_CURRENT 这个类型才能传值
    PendingIntent pendingIntent = PendingIntent
        .getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    return new NotificationCompat.Builder(context).setContentTitle(title).setContentText(content)
                                                  .setSmallIcon(icon).setAutoCancel(true)
                                                  .setVibrate(new long[]{0})
                                                  .setContentIntent(pendingIntent);
  }

  public static void sendNotification(@NonNull Context context, @NonNull String title,
                                      @NonNull String content, @NonNull int icon,
                                      @NonNull Intent intent) {
    NotificationUtils notificationUtils = new NotificationUtils(context);
    Notification notification = null;
    if (Build.VERSION.SDK_INT >= 26) {
      notificationUtils.createNotificationChannel();
      notification = notificationUtils.getChannelNotification(title, content, icon, intent).build();
    } else {
      notification = notificationUtils.getNotification_25(title, content, icon, intent).build();
    }
    notificationUtils.getManager().notify(new java.util.Random().nextInt(10000), notification);
  }

  public static Notification createNotification(@NonNull Context context, @NonNull String title,
                                                @NonNull String content, @NonNull int icon,
                                                @NonNull Intent intent, int... i) {
    LOG.e("NotificationUtils", "createNotificationChannel.91:");
    NotificationUtils notificationUtils = new NotificationUtils(context);
    Notification notification = null;
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      LOG.e("NotificationUtils", "createNotificationChannel.110:");
      notificationUtils.createNotificationChannel(i);
      notification = notificationUtils.getChannelNotification(title, content, icon, intent).build();
    } else {
      LOG.e("NotificationUtils", "createNotificationChannel.114:");
      notification = notificationUtils.getNotification_25(title, content, icon, intent).build();
    }
    return notification;
  }
}
