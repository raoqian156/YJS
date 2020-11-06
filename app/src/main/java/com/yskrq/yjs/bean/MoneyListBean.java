package com.yskrq.yjs.bean;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yskrq.net_library.BaseBean;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MoneyListBean extends BaseBean {
  /**
   * error : 0
   * Value : [{"GroupId":1,"GroupName":"营业汇总","xsid":1,"transcode":"200 ","TranName":"实收收入","amount":"现金                ","OpId":"YSK226691","FolioNo":6,"occurTime":"2020-10-31 10:33:37","paidAmount":"100.00","type":5,"UserName":"16服务器测试账号"},{"GroupId":1,"GroupName":"营业汇总","xsid":1,"transcode":"200 ","TranName":"实收收入","amount":"现金                ","OpId":"YSK226691","FolioNo":7,"occurTime":"2020-10-31 10:35:41","paidAmount":"50.00","type":5,"UserName":"16服务器测试账号"}]
   */

  private String Value;
  public List<ValueBean> getValues() {
    if (TextUtils.isEmpty(Value)) return null;
    try {
      Type type = new TypeToken<List<ValueBean>>() {}.getType();
      List<ValueBean> list = new Gson().fromJson(Value, type);
      return list;
    } catch (Exception e) {
      return new ArrayList<>();
    }
  }
  public static class ValueBean{

    /**
     * GroupId : 1
     * GroupName : 营业汇总
     * xsid : 1
     * transcode : 200 
     * TranName : 实收收入
     * amount : 现金                
     * OpId : YSK226691
     * FolioNo : 6
     * occurTime : 2020-10-31 10:33:37
     * paidAmount : 100.00
     * type : 5
     * UserName : 16服务器测试账号
     */

    private int GroupId;
    private String GroupName;
    private int xsid;
    private String transcode;
    private String TranName;
    private String amount;
    private String OpId;
    private int FolioNo;
    private String occurTime;
    private String paidAmount;
    private int type;
    private String UserName;

    public int getGroupId() {
      return GroupId;
    }

    public void setGroupId(int GroupId) {
      this.GroupId = GroupId;
    }

    public String getGroupName() {
      return GroupName;
    }

    public void setGroupName(String GroupName) {
      this.GroupName = GroupName;
    }

    public int getXsid() {
      return xsid;
    }

    public void setXsid(int xsid) {
      this.xsid = xsid;
    }

    public String getTranscode() {
      return transcode;
    }

    public void setTranscode(String transcode) {
      this.transcode = transcode;
    }

    public String getTranName() {
      return TranName;
    }

    public void setTranName(String TranName) {
      this.TranName = TranName;
    }

    public String getAmount() {
      return amount;
    }

    public void setAmount(String amount) {
      this.amount = amount;
    }

    public String getOpId() {
      return OpId;
    }

    public void setOpId(String OpId) {
      this.OpId = OpId;
    }

    public int getFolioNo() {
      return FolioNo;
    }

    public void setFolioNo(int FolioNo) {
      this.FolioNo = FolioNo;
    }

    public String getOccurTime() {
      return occurTime;
    }

    public void setOccurTime(String occurTime) {
      this.occurTime = occurTime;
    }

    public String getPaidAmount() {
      return paidAmount;
    }

    public void setPaidAmount(String paidAmount) {
      this.paidAmount = paidAmount;
    }

    public int getType() {
      return type;
    }

    public void setType(int type) {
      this.type = type;
    }

    public String getUserName() {
      return UserName;
    }

    public void setUserName(String UserName) {
      this.UserName = UserName;
    }
  }
}
