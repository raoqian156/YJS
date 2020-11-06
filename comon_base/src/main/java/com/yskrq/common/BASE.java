package com.yskrq.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.io.Serializable;

public class BASE {
  private static Class<?> clazz;
  public static boolean needTechNum;

  public static <T extends Activity> void setMainClass(Class<T> mainClass, boolean need) {
    needTechNum = need;
    clazz = mainClass;
  }

  public static void toMain(Context context, Serializable... pass) {
    Intent intent = new Intent(context, clazz);
    if (pass != null && pass.length > 0) {
      for (int i = 0; i < pass.length; i++) {
        intent.putExtra("pass" + i, pass[i]);
      }
    }
    context.startActivity(intent);
  }
}
