package com.yskrq.yjs.keep;

import android.content.Intent;
import android.util.Log;

import com.yskrq.common.BASE;
import com.yskrq.common.util.LOG;


public class KeepManager {

  public static void stopAliveRun() {
    if (!KeepAliveService.stop) {
      Log.e("KeepManager", "stopAliveRun.18:");
      KeepAliveService.stop = true;
      Intent bindIntent = new Intent(BASE.getCxt(), KeepAliveService.class);
      bindIntent.putExtra("stop", true);
      BASE.getCxt().stopService(bindIntent);
    } else {
      LOG.e("KeepManager", "stopAliveRun.19:");
    }
  }

  public static void startAliveRun() {
    if (KeepAliveService.stop) {
      LOG.e("KeepManager", "startAliveRun.26:");
      Intent bindIntent = new Intent(BASE.getCxt(), KeepAliveService.class);
      KeepAliveService.stop = false;
      BASE.getCxt().startService(bindIntent);
    } else {
      LOG.e("KeepManager", "startAliveRun.30:");
    }
  }
}
