package com.yskrq.yjs;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.tencent.bugly.crashreport.CrashReport;
import com.yskrq.common.BASE;
import com.yskrq.yjs.util.ImageLoadUtil;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class BaseApplication extends Application implements Application.ActivityLifecycleCallbacks {

  @Override
  public void onCreate() {
    super.onCreate();
    BASE.setMainClass(MainActivity.class, true);
    //    CrashHandler.getInstance().init(this);
    CrashReport.initCrashReport(getApplicationContext(), "0fb8de5348", false);
    registerActivityLifecycleCallbacks(this);
    RunningHelper.getInstance().start(this);
  }

  @Override
  public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
    ImageLoadUtil.setActivity(activity);
  }

  @Override
  public void onActivityStarted(@NonNull Activity activity) {

  }

  @Override
  public void onActivityResumed(@NonNull Activity activity) {
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
