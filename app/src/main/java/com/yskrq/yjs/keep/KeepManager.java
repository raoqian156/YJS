package com.yskrq.yjs.keep;

import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class KeepManager {

  public static void stopAliveRun(Context context) {
    //        KeepAliveService.setJsonObject(context, null);
    Log.e("KeepManager", "stopAliveRun.18:");
    KeepAliveService.stop = true;
    Intent bindIntent = new Intent(context, KeepAliveService.class);
    bindIntent.putExtra("stop", true);
    context.stopService(bindIntent);
  }

  public static void startAliveRun(Context context) {
    Intent bindIntent = new Intent(context, KeepAliveService.class);
    KeepAliveService.stop = false;
    context.startService(bindIntent);
  }
}
