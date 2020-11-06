package com.yskrq.yjs.util;

//import android.app.Notification;
//import android.app.NotificationManager;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Build;
//import android.text.TextUtils;
//
//import com.fanjun.keeplive.KeepLive;
//import com.fanjun.keeplive.config.ForegroundNotification;
//import com.fanjun.keeplive.config.ForegroundNotificationClickListener;
//import com.fanjun.keeplive.config.KeepLiveService;
//import com.fanjun.keeplive.config.NotificationUtils;
//import com.fanjun.keeplive.receiver.NotificationClickReceiver;
//import com.fanjun.keeplive.service.JobHandlerService;
//import com.fanjun.keeplive.service.LocalService;
//import com.fanjun.keeplive.service.RemoteService;
//import com.google.gson.Gson;
//import com.yskrq.common.AppInfo;
//import com.yskrq.common.util.LOG;
//import com.yskrq.net_library.HttpInnerListener;
//import com.yskrq.yjs.R;
//import com.yskrq.yjs.bean.RelaxListBean;
//import com.yskrq.yjs.net.HttpManager;
//
//import java.text.SimpleDateFormat;
//import java.util.TimeZone;
//
//import static android.content.Context.NOTIFICATION_SERVICE;
//import static com.fanjun.keeplive.KeepLive.NOTIFICATION_ID;

public class KeepUtil {
//  public static void keep(final Context context) {
//    KeepLive.stop = false;
//    //定义前台服务的默认样式。即标题、描述和图标
//    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
//    ForegroundNotification foregroundNotification = new ForegroundNotification("云技师", "数据连接中..." + simpleDateFormat
//        .format(System.currentTimeMillis()), R.mipmap.ic_launcher,
//        //定义前台服务的通知点击事件
//        new ForegroundNotificationClickListener() {
//
//          @Override
//          public void foregroundNotificationClick(Context context, Intent intent) {
//          }
//        });
//    //启动保活服务
//    KeepLive.startWork(context, KeepLive.RunMode.ENERGY, foregroundNotification,
//        //你需要保活的服务，如socket连接、定时任务等，建议不用匿名内部类的方式在这里写
//        new KeepLiveService() {
//          /**
//           * 运行中
//           * 由于服务可能会多次自动启动，该方法可能重复调用
//           */
//          @Override
//          public void onWorking(final Context context1) {
//            LOG.e("KeepUtil", "onWorking.KeepLive:");
//            new Thread(new Runnable() {
//              @Override
//              public void run() {
//                while (!KeepLive.stop) {
//                  notifyUser(context1);
//                  LOG.e("KeepUtil", "run.62:");
//                  //                  onRunning(context1);
//                  try {
//                    Thread.sleep(5000);
//                  } catch (InterruptedException e) {
//                    e.printStackTrace();
//                  }
//                }
//              }
//            }).start();
//          }
//
//          /**
//           * 服务终止
//           * 由于服务可能会被多次终止，该方法可能重复调用，需同onWorking配套使用，如注册和注销broadcast
//           */
//          @Override
//          public void onStop() {
//            LOG.e("KeepUtil", "onStop.KeepLive:");
//          }
//        });
//  }
//
//  private static void onRunning(final Context context) {
//    HttpManager.GetRelaxServerList(context, new HttpInnerListener() {
//      @Override
//      public void onString(String json) {
//        RelaxListBean bean = new Gson().fromJson(json, RelaxListBean.class);
//        if (bean != null && bean.isOk() && bean.getValue() != null && bean.getValue().size() > 0) {
//          SpeakManager.isRead(context, bean.getValue().get(0));
//        }
//      }
//
//      @Override
//      public void onEmptyResponse() {
//
//      }
//    });
//    //    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//    //    CrashHandler.getInstance().saveNETInfo("onWorking", "保活测试 -> " + simpleDateFormat
//    //        .format(System.currentTimeMillis()));
//    //    HttpManager.senError(context, AppInfo.getUserid(context) + ".保活测试->" + AppInfo
//    //        .getTechNum(context) + ":" + System.currentTimeMillis(), new HttpInnerListener() {
//    //      @Override
//    //      public void onString(String json) {
//    //
//    //      }
//    //
//    //      @Override
//    //      public void onEmptyResponse() {
//    //        CrashHandler.getInstance().saveNETInfo("onWorking", "接口异常");
//    //      }
//    //    });
//  }
//
//  private static void notifyUser(Context context) {
//    String title = "下钟剩余", con = "0 分钟";
//    long time = AppInfo.getRunningLeftTime(context);
//    if (time > 0) {
//      time += 1000;
//      SimpleDateFormat simpleDateFormat;
//      simpleDateFormat = new SimpleDateFormat("HH:mm");
//      simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
//      con = simpleDateFormat.format(time);
//    } else if (!TextUtils.isEmpty(AppInfo.getWait(context))) {
//      title = "您有新任务";
//      con = "已等待" + AppInfo.getWait(context) + "分钟";
//    } else {
//      title = AppInfo.getTechNum(context) + " 号技师";
//      con = "暂无新任务...";
//    }
//    Intent intent = new Intent(context, NotificationClickReceiver.class);
//    Notification notification = NotificationUtils
//        .createNotification(context, title, con, R.mipmap.ic_launcher, intent);
//    NotificationManager mNManager = (NotificationManager) context
//        .getSystemService(NOTIFICATION_SERVICE);
//    mNManager.notify(NOTIFICATION_ID, notification);
//  }
//
//  public static void stop(Context context) {
//    KeepLive.foregroundNotification = null;
//    KeepLive.keepLiveService = null;
//    KeepLive.runMode = null;
//    KeepLive.stop = true;
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//      Intent intent = new Intent(context, JobHandlerService.class);
//      context.stopService(intent);
//      Intent localIntent = new Intent(context, LocalService.class);
//      Intent guardIntent = new Intent(context, RemoteService.class);
//      context.stopService(localIntent);
//      context.stopService(guardIntent);
//    } else {
//      Intent localIntent = new Intent(context, LocalService.class);
//      Intent guardIntent = new Intent(context, RemoteService.class);
//      context.stopService(localIntent);
//      context.stopService(guardIntent);
//    }
//    NotificationManager mNManager = (NotificationManager) context
//        .getSystemService(NOTIFICATION_SERVICE);
//    mNManager.cancel(NOTIFICATION_ID);
//    LOG.e("KeepUtil", "stop.NOTIFICATION_ID:");
//  }
}
