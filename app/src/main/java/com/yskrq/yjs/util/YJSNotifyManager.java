package com.yskrq.yjs.util;

import android.content.SharedPreferences;

import com.yskrq.common.BASE;
import com.yskrq.common.util.SPUtil;

public class YJSNotifyManager {

  public static void resetPushInfo() {
    SharedPreferences.Editor edit = SPUtil.init(BASE.getCxt()).edit();
    edit.remove("Wait.TYPE");
    edit.remove("Wait.TIME");
    edit.remove("home.running.target");
    edit.apply();
  }

  public static void change(String groupid, String sIdStr, String expendTime) {
    int tag = getShowStatus(groupid);
//    AppInfo.setWaitType(BASE.getCxt(), tag);
//    if (tag == 2) {
//      int sId = Integer.parseInt(sIdStr);
//      AppInfo.saveRunningTargetTime(BASE.getCxt(), sId * 1000);
//    } else if (tag == 1) {
//      AppInfo.setWait(BASE.getCxt(), expendTime);
//      AppInfo.saveRunningTargetTime(BASE.getCxt(), 0);
//    } else {
//      AppInfo.setWait(BASE.getCxt(), "");
//    }
  }

  public static int getShowStatus(String groupId) {// 0-不能打卡  1-代打卡   2-已打卡 3-已下钟
    int tag = -1;
    if ("9000".equals(groupId)) {//未安排 - 不能打卡
      tag = 0;
    } else if ("9001".equals(groupId)) {//已打卡
      tag = 2;
    } else if ("9002".equals(groupId)) {//待打卡
      tag = 1;
    } else if ("9003".equals(groupId)) {//3-已下钟
      tag = 3;
    }
    return tag;
  }

  public static void change(int tag, int sId, String expendTime) {
//    AppInfo.setWaitType(BASE.getCxt(), tag);
//    if (tag == 2) {
//      AppInfo.saveRunningTargetTime(BASE.getCxt(), sId * 1000);
//    } else if (tag == 1) {
//      AppInfo.setWait(BASE.getCxt(), expendTime);
//      AppInfo.saveRunningTargetTime(BASE.getCxt(), 0);
//    } else {
//      AppInfo.setWait(BASE.getCxt(), "");
//    }
  }

}
