package com.yskrq.yjs.util;

import android.content.Context;
import android.text.TextUtils;

import com.yskrq.common.AppInfo;
import com.yskrq.common.bean.RelaxListBean;
import com.yskrq.common.okhttp.HttpManagerBase;
import com.yskrq.common.util.LOG;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import static com.yskrq.common.AppInfo.getCuiZHongMinutes;
import static com.yskrq.yjs.keep.KeepAliveService.NOTIFICATION_ID;

public class TechStatusManager {

  private TechStatusManager() {
  }

  private final static TechStatusManager mRunningHelper = Holder.mHolde;

  public static TechStatusManager getInstance() {
    return mRunningHelper;
  }

  Context mContext;

  public void setContext(Context context) {
    this.mContext = context;
  }

  public long getRunningLeftTime() {//获取剩余时间
    return stopTime - System.currentTimeMillis();
  }

  public void addOnTechStatusListener(OnTechStatusListener listener) {
    mOnTechStatusListeners.add(listener);
  }

  public boolean removeOnTechStatusListener(OnTechStatusListener listener) {
    return mOnTechStatusListeners.remove(listener);
  }

  public void brandOut() {
    this.dataBase = null;
    runningTargetTime = 0;
    stopTime = 0;
    waitType = -1;
    notifyShowTime = "";
    techStatus = 0;
    for (OnTechStatusListener mOnTechStatusListener : mOnTechStatusListeners) {
      mOnTechStatusListener.onStatus(null, waitType, runningTargetTime, notifyShowTime);
    }
    for (OnDataChangeListener onDataChangeListener : mOnDataChangeListener) {
      onDataChangeListener.refuseList(null);
    }
  }

  public boolean willClose() {
    boolean res = getRunningLeftTime() < getCuiZHongMinutes(mContext) * 60_000;
    LOG.e("AppInfo", "willClose. getRunningLeftTime():" + getRunningLeftTime());
    LOG.e("AppInfo", "willClose.getCuiZHongMinutes(mContext):" + getCuiZHongMinutes(mContext));
    LOG.e("AppInfo", "willClose.170:" + res);
    return res;
  }

  private static class Holder {
    static TechStatusManager mHolde = new TechStatusManager();
  }

  private List<OnTechStatusListener> mOnTechStatusListeners = new ArrayList<>();

  public interface OnTechStatusListener {
    /**
     * @param first
     * @param status            0 - 原始状态
     *                          1 - 上钟状态
     *                          2 - 可下钟
     * @param runningTargetTime
     * @param notifyShowTime
     */
    void onStatus(RelaxListBean.ValueBean first, int status, long runningTargetTime,
                  String notifyShowTime);
  }

  int waitType = -1;
  String notifyShowTime;
  long runningTargetTime;
  int techStatus;//技师状态 0-候钟状态

  public interface OnDataChangeListener {
    void refuseList(RelaxListBean bean);
  }

  static List<OnDataChangeListener> mOnDataChangeListener = new ArrayList<>();

  public void addDataChangeListener(OnDataChangeListener listener) {
    mOnDataChangeListener.add(listener);
  }

  public boolean removeDataChangeListener(OnDataChangeListener listener) {
    return mOnDataChangeListener.remove(listener);
  }

  RelaxListBean.ValueBean dataBase;

  public RelaxListBean.ValueBean getValueBean() {
    return dataBase;
  }

  private void refuseFirst(RelaxListBean.ValueBean first) {
    boolean isChange = false;
    if (first != null) {
      this.dataBase = first;
      int tag = first.getShowStatus();
      int sId = -1;
      waitType = tag;
      long newTime = 0;
      String showTime = null;
      if (tag == 2) {
        try {
          sId = Integer.parseInt(first.getSid());
          newTime = sId * 1000;
        } catch (Exception e) {
        }
      } else if (tag == 1) {
        newTime = 0;
        showTime = first.getExpendtime();
      } else {
        showTime = "";
      }
      if (!TextUtils.equals(notifyShowTime, showTime) || runningTargetTime != newTime) {
        isChange = true;
      }
      notifyShowTime = showTime;
      runningTargetTime = newTime;
    } else {
      if (waitType != 0) {
        isChange = true;
      }
      brandOut();
    }
    stopTime = runningTargetTime + System.currentTimeMillis();
    HttpManagerBase.senError("极光" + AppInfo.getTechNum(), "播报来源:TechStatusManager");
    LOG.e("TechStatusManager", "GetRelaxServerList.播报来源.onString");
    SpeakManager.isRead(mContext, first, "TechStatusManager");
    notifyUser(waitType, runningTargetTime, notifyShowTime);
    if (isChange) {
      for (OnTechStatusListener mOnTechStatusListener : mOnTechStatusListeners) {
        mOnTechStatusListener.onStatus(first, waitType, runningTargetTime, notifyShowTime);
      }
    }
  }

  public void refuseFirstFromJpush(RelaxListBean.ValueBean bean) {
    LOG.e("TechStatusManager", "refuseFirstFromJpush.showTag:");
    refuseFirst(bean);
  }

  public void refuseFromService(RelaxListBean bean) {
    RelaxListBean.ValueBean firstBean = null;
    if (bean != null && bean.getValue() != null && bean.getValue().size() > 0) {
      for (OnDataChangeListener onDataChangeListener : mOnDataChangeListener) {
        onDataChangeListener.refuseList(bean);
      }
      firstBean = bean.getValue().get(0);
    }
    LOG.e("TechStatusManager", "refuseFromService.showTag:");
    refuseFirst(firstBean);
  }

  private long stopTime = 0;//停止时间

  public int getWaitType() {
    return waitType;
  }

  private void notifyUser(int tag, long runningTargetTime, String show) {
    NoticeUtil.clearNotify(mContext);
    String title, con;
    int priorityLevel = 0;
    if (mContext == null || TextUtils.isEmpty(AppInfo.getTechNum())) {
      title = "登录失效";
      con = "请重新登录";
    } else if (tag == 0) {
      return;
    } else if (tag == 1) {
      priorityLevel = 2;
      title = "您有新任务";
      con = "已等待" + show + "分钟";
    } else if (tag == 2) {
      priorityLevel = 1;
      title = "下钟剩余";
      long time = runningTargetTime;
      if (time < 0) {
        title = "下钟时间到";
        con = "00:00";
      } else if (time < 60 * 1000) {
        con = "<一分钟";
      } else {
        time += 1000;
        SimpleDateFormat simpleDateFormat;
        simpleDateFormat = new SimpleDateFormat("HH:mm");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        con = simpleDateFormat.format(time);
      }
    } else {
      title = AppInfo.getTechNum() + " 号技师";
      con = "暂无新任务...";
    }
    NoticeUtil.sentNotice(mContext, NOTIFICATION_ID, title, con, priorityLevel, tag > 0);
  }
}