package com.yskrq.common.util;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.yskrq.net_library.BuildConfig;

public class LOG {

  public static boolean showDebug() {
    return BuildConfig.SHOW_LOG;
  }

  public static void e(String tag, String con) {
    if (showDebug()) {
      Log.e(tag, con);
    }
  }

  public static void d(String tag, String con) {
    if (showDebug()) Log.d(tag, con);
  }

  public static void i(String tag, String con) {
    if (showDebug()) {
      Log.i(tag, con);
    }
  }

  public static void bean(String tag, Object obj, String... url) {
    if (showDebug()) {
      try {
        bean(tag, new Gson().toJson(obj).trim(), url);
      } catch (OutOfMemoryError error) {
        if (obj != null) {
          Log.e("LOG", "post: = " + obj.toString());
        } else {
          Log.e("LOG", "post: = null");
        }
      } catch (Exception e) {
        Log.w("LOG", "post: 输出错误");
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
    if (log == null) {
      return;
    }
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
      Log.w("BEAN." + tag, "    --" + showLine + "->" + SPACE + outs[i].replaceAll("\"", ""));
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

  public static int useLine() {//该方法 所在方法的 调用所在行数
    StackTraceElement[] stacks = new Exception().getStackTrace();
    return stacks[2].getLineNumber();
  }

  public static int thisLine() {//该方法调用所在行数
    StackTraceElement[] stacks = new Exception().getStackTrace();
    return stacks[1].getLineNumber();
  }

  public static void showUserWhere(Object... object) {
    StackTraceElement[] stacks = new Exception().getStackTrace();
    if (stacks != null) {
      try {
        StringBuilder sb = new StringBuilder();
        int index = 4;
        for (int i = 0; i < index; i++) {
          String classname = stacks[index - i].getFileName(); //获取调用者的类名
          String method_name = stacks[index - i].getMethodName(); //获取调用者的方法名
          int line = stacks[index - i].getLineNumber(); //获取调用者的方法名
          if (i > 0) {
            sb.append("==> ");
          }
          sb.append(classname);
          sb.append("[");
          sb.append(method_name);
          sb.append("].");
          sb.append(line);
        }

        String tag = "LOG.showUserWhere";
        if (object != null) {
          if (object[0] instanceof String) {
            tag = (String) object[0];
          } else {
            tag = object[0].getClass().getSimpleName();
          }
        }
        Log.e(tag, sb.toString());
      } catch (Exception e) {
      }
    }
  }

  /**
   * 需要引起程序员注意并且调试时调用这方法
   * 一般是和后台联调错误需要通过沟通解决
   */
  public static void utilLog(String tag) {
    //        if (!showDebug()) return;
    //        String packageName = "com.hz.huarun";
    String con = "";
    StackTraceElement[] stacks = new Exception().getStackTrace();
    StringBuffer stringBuffer = new StringBuffer();
    //        String simpleName = classname.substring(classname.lastIndexOf(".") + 1);
    if (stacks != null) {
      stringBuffer.append("\t【异常起源】>>\t");
      int start = Math.min(stacks.length - 1, 10);
      int recoderIndex = start;

      for (int i = start; i >= 0; i--) {
        String classname = stacks[i].getClassName(); //获取调用者的类名
        String method_name = stacks[i].getMethodName(); //获取调用者的方法名
        int line = stacks[i].getLineNumber(); //获取调用者的方法名
        //                if (recoderIndex == -1 && classname.contains(packageName)) {//项目代码开始
        //                    recoderIndex = i;
        //                }
        if (i <= recoderIndex) {
          if (LOG.class.getName().equals(classname)) {
            continue;
          }
          if (i != recoderIndex) {
            stringBuffer.append(tag + "|\t\t\t\t\t");
          }
          stringBuffer.append(i + "[" + classname + "." + method_name + " LINE " + line + "]  \n");
        }

      }
    }
    con = stringBuffer.toString();
    System.err.print(tag + "|  " + con);
  }
}
