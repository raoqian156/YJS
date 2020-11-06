package com.yskrq.net_library.url_conn;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.yskrq.net_library.BuildConfig;

class LOG {

  public static boolean showDebug() {
    return BuildConfig.SHOW_LOG;
  }

  public static void e(String tag, String con) {
    if (showDebug()) {
      Log.e(tag, con);
    }
  }

  public static void bean(String tag, Object obj, String... url) {
    if (showDebug()) {
      try {
        bean(tag, new Gson().toJson(obj).trim(), url);
      } catch (OutOfMemoryError error) {
        if (obj != null) {
          Log.e("LOG", "bean: = " + obj.toString());
        } else {
          Log.e("LOG", "bean: = null");
        }
      } catch (Exception e) {
        Log.w("LOG", "bean: 输出错误");
      }
    }
  }

  public static void bean(String tag, String log, String... url) {
    if (!showDebug()) return;
    String lineOut = "";
    if (url != null && url.length > 0) {
      lineOut = "<" + url[0] + ">";
    } else {
      lineOut = tag;
    }
    Log.w("BEAN." + tag, "------------------------------" + lineOut + "------------------------------");
    Log.w("BEAN." + tag, "=====>" + log);
    String outPut = log.replaceAll(":\\{", ":,{").replaceAll(":\\[\\{", ":[,{")
                       .replaceAll("\\}", "},").replaceAll("\\]", "],").replaceAll("\\\\\"", "");
    String[] outs = outPut.split(",");

    String SPACE = "";
    int lineNumLength = String.valueOf(outs.length).length();
    for (int i = 0; i < outs.length; i++) {
      String showLine = frontCompWithZore(i, lineNumLength);
      if (outs[i].contains("}") || outs[i].contains("]")) {
        if (!TextUtils.isEmpty(SPACE)) {
          SPACE = SPACE.substring(0, SPACE.lastIndexOf("\t"));
        }
      }
        outs[i]=outs[i].replaceAll("\"","");
      Log.w("BEAN." + tag, "    --" + showLine + "->" + SPACE + outs[i]);
      if (outs[i].startsWith("{") || outs[i].contains("[")) {
        SPACE = SPACE + "\t";
      }
    }
    Log.w("BEAN." + tag, "------------------------------" + lineOut + ".end------------------------------");
  }

  /**
   * 0 指前面补充零
   * formatLength 字符总长度为 formatLength
   * d 代表为正数。
   */
  static String frontCompWithZore(int sourceDate, int formatLength) {
    String newString = String.format("%0" + formatLength + "d", sourceDate);
    return newString;
  }

}
