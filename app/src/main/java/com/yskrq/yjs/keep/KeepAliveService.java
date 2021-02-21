package com.yskrq.yjs.keep;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.yskrq.common.AppInfo;
import com.yskrq.common.bean.RelaxListBean;
import com.yskrq.common.okhttp.HttpManagerBase;
import com.yskrq.common.util.LOG;
import com.yskrq.net_library.HttpInnerListener;
import com.yskrq.yjs.R;
import com.yskrq.yjs.net.HttpManager;
import com.yskrq.yjs.util.NoticeUtil;
import com.yskrq.yjs.util.SpeakManager;
import com.yskrq.yjs.util.YJSNotifyManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import static com.yskrq.yjs.util.SpeakManager.openVoice;


/**
 * 播放无声音乐，来保持进程包活
 */
//播放无声音乐，来保持进程保活
public class KeepAliveService extends Service  {

  public static final int READ_WAY = 0;//0-直接打开  1-锁屏才打开
  public static final int NOTIFICATION_ID = 12345;
  public volatile static boolean stop = true;
  public static int REFUSE_TIME_DURATION = 10;//秒

  private boolean isScreenON = true;//控制暂停
  private MediaPlayer mediaPlayer;
  //锁屏广播监听
  private ScreenStateReceiver screenStateReceiver;

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    if (KeepAliveService.stop) {
      return START_NOT_STICKY;
    }
    Log.e("KeepAliveService", "---KeepAliveService 启动---> " + Integer.toHexString(hashCode()));
    //注册锁屏广播
    registerScreenStateReceiver();
    //初始化播放器
    initMediaPlayer();
    //开启前台Service
    startForeground(this);
    //start HideNotifactionService
    startHideNotificationService();

    //绑定守护进程
    bindRemoteService();
    return START_STICKY;
  }

  private void bindRemoteService() {
    Log.e("KeepAliveService", KeepAliveService.this.toString() + ":" + KeepAliveService.stop);
    if (KeepAliveService.stop) {
      return;
    }
    boolean isRun = ServiceUtils
        .isServiceRunning(getApplicationContext(), RemoteService.class.getName());
    Log.e("KeepAliveService", isRun + ".bindRemoteService:" + RemoteService.class.getName());
    try {
      Intent intent = new Intent(this, RemoteService.class);
      bindService(intent, connection, Context.BIND_ABOVE_CLIENT);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void startHideNotificationService() {
    try {
      //            if(Build.VERSION.SDK_INT < 25){
      startService(new Intent(this, HideNotificationService.class));
      //            }
    } catch (Exception e) {
    }
  }

  @Override
  public void onCreate() {
    super.onCreate();
    if (stop) {
      quitSelf();
      return;
    }
    start();
  }

  private void quitSelf() {
    Log.e("KeepAliveService", "quitSelf.131:");

    if (connection != null) {
      try {
        unbindService(connection);
      } catch (Exception e) {

      }
    }
    if (screenStateReceiver != null) {
      Log.e("KeepAliveService", "quitSelf.136:");
      try {
        unregisterReceiver(screenStateReceiver);
      } catch (Exception e) {

      }
    }
    stopSelf();
  }

  private void start() {
    new Thread(new Runnable() {
      @Override
      public void run() {
        while (!stop) {
          try {
            if (stop) {
              return;
            }
            int delayTime = (int) (AppInfo
                .getRunningLeftTime(KeepAliveService.this) / 1000 % REFUSE_TIME_DURATION);
            if (delayTime > 0) {
              if (delayTime * 2 > REFUSE_TIME_DURATION) {
                 onRead();
              }
              Thread.sleep(delayTime * 1000L);
            }
             onRead();
            Thread.sleep(REFUSE_TIME_DURATION * 1000L);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }
    }).start();
  }

  public interface RefuseListener {

    void onRefuse(RelaxListBean bean);
  }

  static List<RefuseListener> mRefuseListeners = new ArrayList<>();

  public static void addRefuseListener(RefuseListener listener) {
    if (listener != null && mRefuseListeners != null && !mRefuseListeners
        .contains(mRefuseListeners)) {
      mRefuseListeners.add(listener);
    }
  }

  public static void removeRefuseListener(RefuseListener listener) {
    if (mRefuseListeners != null) mRefuseListeners.remove(listener);
  }


  private void onRead() {
    onRunning(this);
  }

  static Handler mainHandler = new Handler(Looper.getMainLooper());
  static long workDateCheckTime = 0;

  private static void onRunning(final Context context) {
    if (System.currentTimeMillis() - workDateCheckTime > 1000 * 60 * 30) {
      workDateCheckTime = System.currentTimeMillis();
      HttpManager.justRefuseSaleDate(context);
    }
    final Map<String, String> param = new HashMap<>();
    param.put("groupid", AppInfo.getGroupId());
    param.put("brandno", AppInfo.getTechNum());
    param.put("profitcenter", AppInfo.getProfitCenter());
    param.put("hoteldate", AppInfo.getWorkDate());
    LOG.e("KeepAliveService", "onRunning.GetRelaxServerList:");
    HttpManager.GetRelaxServerList(context, new HttpInnerListener() {
      @Override
      public void onString(String json) {
        final RelaxListBean bean = new Gson().fromJson(json, RelaxListBean.class);
        KeepAliveService.notify(context, bean);
        if (bean != null && bean.isOk() && bean.getValue() != null && bean.getValue().size() > 0) {
          RelaxListBean.ValueBean first = bean.getValue().get(0);
          HttpManagerBase.senError("极光" + AppInfo.getTechNum(), "播报来源:KeepAliveService");
          LOG.e("KeepAliveService", "GetRelaxServerList.播报来源.onString" );
          SpeakManager.isRead(context, first);
        } else {
          openVoice(context);
          AppInfo.clearNotify(context);
        }
        notifyUser(context);
      }

      @Override
      public void onEmptyResponse() {
        notifyUser(context);
      }

    });
  }

  private static void notify(Context context, final RelaxListBean bean) {
    if (bean != null && bean.getValue() != null && bean.getValue().size() > 0) {
      RelaxListBean.ValueBean first = bean.getValue().get(0);
      int tag = first.getShowStatus();
      int sId = -1;
      String expendTime = "";
      AppInfo.setWaitType(context, tag);
      if (tag == 2) {
        try {
          sId = Integer.parseInt(first.getSid());
        } catch (Exception e) {
        }
      } else if (tag == 1) {
        expendTime = first.getExpendtime();
      }
      YJSNotifyManager.change(tag, sId, expendTime);
    } else {
      AppInfo.setWaitType(context, 0);
      AppInfo.setWait(context, "");
      AppInfo.saveRunningTargetTime(context, 0);
    }
    mainHandler.post(new Runnable() {
      @Override
      public void run() {
        if (mRefuseListeners.size() > 0) {
          for (RefuseListener listener : mRefuseListeners) {
            listener.onRefuse(bean);
          }
        } else {

        }
      }
    });
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
    NoticeUtil.sentNotice(context, NOTIFICATION_ID, title, con, priorityLevel, tag > 0);
  }

  //将keepAliveBinder交给RemoteService
  public IBinder onBind(Intent intent) {
    return keepAliveBinder;
  }

  private GuardAidl.Stub keepAliveBinder = new GuardAidl.Stub() {
    @Override
    public void notifyAlive() throws RemoteException {
      Log.i(null, "Hello RemoteService!");
    }
  };

  private ServiceConnection connection = new ServiceConnection() {

    @Override
    public void onServiceDisconnected(ComponentName name) {
      if (stop) {
        return;
      }
      Intent remoteService = new Intent(KeepAliveService.this, RemoteService.class);
      KeepAliveService.this.startService(remoteService);

      Intent intent = new Intent(KeepAliveService.this, RemoteService.class);
      //将KeepAliveService和RemoteService进行绑定
      KeepAliveService.this.bindService(intent, connection, Context.BIND_ABOVE_CLIENT);
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
      try {
        //与RemoteService绑定成功
        GuardAidl remoteBinder = GuardAidl.Stub.asInterface(service);
        remoteBinder.notifyAlive();
      } catch (RemoteException e) {
        e.printStackTrace();
      }
    }
  };

  public static void startForeground(Service service) {
    notifyUser(service);
  }

  private void registerScreenStateReceiver() {
    screenStateReceiver = new ScreenStateReceiver();
    screenStateReceiver.addListener(new ScreenStateReceiver.ScreenStateListener() {
      @Override
      public void screenOn() {
        isScreenON = true;
        Log.e("ScreenStateReceiver", "---音乐关闭---");
        pause();
      }

      @Override
      public void screenOff() {
        isScreenON = false;
        Log.e("ScreenStateReceiver", "---音乐开启---");
        play();
      }
    });
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
    intentFilter.addAction(Intent.ACTION_SCREEN_ON);
    registerReceiver(screenStateReceiver, intentFilter);
  }

  private void initMediaPlayer() {
    if (mediaPlayer == null) {
      mediaPlayer = MediaPlayer.create(this, R.raw.novioce);
      mediaPlayer.setVolume(0f, 0f);
      mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
          if (isScreenON) {
            return;
          }
          //循环播放
          play();
        }
      });
    }
    //防止Service启动的时候屏幕处于解锁状态
    if (!DeviceUtils.isScreenOn(this)) {
      play();
    }
  }

  private void play() {
    if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
      mediaPlayer.start();
    }
  }

  private void pause() {
    if (mediaPlayer != null && mediaPlayer.isPlaying()) {
      mediaPlayer.pause();
    }
  }
}