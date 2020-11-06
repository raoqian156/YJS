package com.yskrq.yjs.keep;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

import static com.yskrq.yjs.keep.KeepAliveService.NOTIFICATION_ID;


public class HideForegroundService extends Service {
    private Handler handler;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startForeground();
        if (handler == null){
            handler = new Handler();
        }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                stopForeground(true);
                stopSelf();
            }
        }, 2000);
        return START_NOT_STICKY;
    }


    private void startForeground() {
        if (KeepLive.foregroundNotification != null) {
            Intent intent = new Intent(getApplicationContext(), NotificationClickReceiver.class);
            intent.setAction(NotificationClickReceiver.CLICK_NOTIFICATION);
            Notification notification = NotificationUtils.createNotification(this, KeepLive.foregroundNotification.getTitle(), KeepLive.foregroundNotification.getDescription(), KeepLive.foregroundNotification.getIconRes(), intent);
            startForeground(NOTIFICATION_ID, notification);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
