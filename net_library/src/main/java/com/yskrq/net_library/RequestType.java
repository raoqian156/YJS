package com.yskrq.net_library;

import android.text.TextUtils;
import android.util.Log;

import java.util.Map;


public class RequestType {
  private String type;
  private int pageNum = 0;
  public static final String PAGE_NUM_TAG = "pageNo";
  private int respCode;
  private String respMsg;
  private Object more;

  public Object getMore() {
    return more;
  }


  public int getRespCode() {
    return respCode;
  }

  public String getErrorMsg() {
    return respMsg;
  }

  public RequestType(String type) {
    this.type = type;
  }

  public RequestType more(Object more) {
    this.more = more;
    return this;
  }

  public RequestType(String type, Map<String, String> param) {
    this.type = type;
    try {
      this.pageNum = Integer.parseInt(param.get(PAGE_NUM_TAG));
    } catch (Exception e) {

    }
  }

  public boolean is(String tag) {
    Log.e("RequestType", tag + ".is.48:" + type);

    if (TextUtils.equals(tag, type)) {
      Log.e("RequestType", "is.52:");
      return true;
    }
    if (tag != null) {
      tag = tag.substring(1 + tag.lastIndexOf("/"));
    }
    Log.e("RequestType", "<"+tag+".is.58:"+type+">");
    return TextUtils.equals(tag, type);
  }

  public int getPageNum() {
    return pageNum;
  }

  public boolean isNetError() {
    return respCode < 0;
  }

  public RequestType with(String respCode, String msg) {
    this.respMsg = msg;
    try {
      this.respCode = Integer.parseInt(respCode);
    } catch (Exception e) {
      this.respCode = 0;
      e.printStackTrace();
    }
    return this;
  }

  public boolean is(int errorCode) {
    return respCode == errorCode;
  }

  @Override
  public String toString() {
    return "RequestType{" + "type='" + type + '\'' + ", pageNum=" + pageNum + ", respCode=" + respCode + ", respMsg='" + respMsg + '\'' + ", more=" + more + '}';
  }
}
