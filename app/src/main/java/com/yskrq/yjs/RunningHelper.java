package com.yskrq.yjs;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.CountDownTimer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RunningHelper {
  private static RunningHelper mRunningHelper = new RunningHelper();

  public static RunningHelper getInstance() {
    return mRunningHelper;
  }

  Context mContext;
  IntentFilter filter;

  private RunningHelper() {
    this.filter = new IntentFilter();
    this.filter.addAction(Intent.ACTION_TIME_TICK);
    this.filter.addAction(Intent.ACTION_TIME_CHANGED);
  }

  public void start(Application application) {
    mContext = application;
    lastHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    application.registerReceiver(broadcastReceiver, filter);
    refuseTime();
  }

  private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

    @Override
    public void onReceive(Context context, Intent intent) {
      if (intent.ACTION_TIME_TICK.equals(intent.getAction())) {
        refuseTime();
      } else if (intent.ACTION_TIME_CHANGED.equals(intent.getAction())) {
        refuseTime();
      }
    }
  };

  CountDownTimer timer;
  int lastHour = 0;

  private void refuseTime() {
    if (timer == null) {
      timer = new CountDownTimer(60 * 1000, 998) {
        @Override
        public void onTick(long l) {
          if (Calendar.getInstance().get(Calendar.HOUR_OF_DAY) != lastHour) {
            lastHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
            onHour();
          }
          onSecond();
          if (Calendar.getInstance().get(Calendar.SECOND) % 15 == 0) {
            on15Second();
          }
        }

        @Override
        public void onFinish() {
        }
      };
    } else {
      timer.cancel();
    }
    timer.start();
  }

  private void onHour() {
    for (OnSecondTickListener item : mOnSecondTickListeners) {
      item.onHour();
    }
  }

  private void on15Second() {
    for (OnSecondTickListener item : mOnSecondTickListeners) {
      item.on15Second();
    }
  }

  private void onSecond() {
    for (OnSecondTickListener item : mOnSecondTickListeners) {
      item.onSecond();
    }
  }


  public interface OnSecondTickListener {
    void on15Second();

    void onSecond();

    void onHour();
  }

  List<OnSecondTickListener> mOnSecondTickListeners = new ArrayList<>();

  public void register(OnSecondTickListener listener) {
    if (listener != null && !mOnSecondTickListeners.contains(listener)) {
      mOnSecondTickListeners.add(listener);
    }
  }

  public boolean remove(OnSecondTickListener listener) {
    return mOnSecondTickListeners.remove(listener);
  }
}
