package com.yskrq.yjs;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import com.tencent.bugly.crashreport.CrashReport;
import com.yskrq.common.BASE;
import com.yskrq.common.okhttp.HttpManagerBase;
import com.yskrq.jpush.JPushUtil;
import com.yskrq.net_library.url_conn.HttpSender;
import com.yskrq.yjs.jpush.JpushHelper;
import com.yskrq.yjs.util.ImageLoadUtil;
import com.yskrq.yjs.util.status.NetWorkMonitorManager;

import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class BaseApplication extends Application implements Application.ActivityLifecycleCallbacks {

  public static Context ctx;
  public static boolean isLogin = false;

  @Override
  public void onCreate() {
    super.onCreate();
    BaseApplication.ctx = this;
    BASE.setContext(this);
    BASE.setMainClass(MainActivity.class, true);
    JPushUtil.init(this, true);
    JPushUtil.setNotificationGetter(new JpushHelper());
    //    CrashHandler.getInstance().init(this);
    CrashReport.initCrashReport(getApplicationContext(), "0fb8de5348", false);
    registerActivityLifecycleCallbacks(this);
    RunningHelper.getInstance().start(this);
    NetWorkMonitorManager.getInstance().init(this);
    HttpSender.addNetErrorListener(new HttpSender.NetErrorListener() {
      @Override
      public void onError(String url, Map<String, String> params) {
        StringBuffer msg = new StringBuffer();
        msg.append(url + "\n");
        msg.append(HttpSender.getRequestData(params).toString() + "\n");
        HttpManagerBase.senError("YJS_EMPTY_E1", msg.toString());
      }

      @Override
      public void onTransError(String url, Map<String, String> params) {
        StringBuffer msg = new StringBuffer();
        msg.append(url + "\n");
        msg.append(HttpSender.getRequestData(params).toString() + "\n");
        HttpManagerBase.senError("YJS_EMPTY_E2", msg.toString());
      }

      @Override
      public void onLogicError(String url, Map<String, String> params) {
        StringBuffer msg = new StringBuffer();
        msg.append(url + "\n");
        msg.append(HttpSender.getRequestData(params).toString() + "\n");
        HttpManagerBase.senError("YJS_EMPTY_E3", msg.toString());
      }
    });
    //    initCloudChannel(this);
  }

  //  private static final String TAG = "BaseApplication";
  //
  //  private void initCloudChannel(Context applicationContext) {
  //    PushServiceFactory.init(applicationContext);
  //    CloudPushService pushService = PushServiceFactory.getCloudPushService();
  //    pushService.register(applicationContext, new CommonCallback() {
  //      @Override
  //      public void onSuccess(String response) {
  //        Log.d(TAG, "init cloudchannel success");
  //      }
  //
  //      @Override
  //      public void onFailed(String errorCode, String errorMessage) {
  //        Log.d(TAG, "init cloudchannel failed -- errorcode:" + errorCode + " -- errorMessage:" + errorMessage);
  //      }
  //    });
  //  }

  @Override
  public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
    ImageLoadUtil.setActivity(activity);
  }

  @Override
  public void onActivityStarted(@NonNull Activity activity) {

  }

  @Override
  public void onActivityResumed(@NonNull Activity activity) {
    BASE.setContext(activity);
    ImageLoadUtil.setActivity(activity);
  }

  @Override
  public void onActivityPaused(@NonNull Activity activity) {

  }

  @Override
  public void onActivityStopped(@NonNull Activity activity) {

  }

  @Override
  public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

  }

  @Override
  public void onActivityDestroyed(@NonNull Activity activity) {

  }
}
