package com.yskrq.yjs.bean;

import com.yskrq.net_library.BaseBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RoomListBean extends BaseBean {

  /**
   * Account : null
   * No : null
   * IsShowTeaCar : 1
   * ProfitCenter_value : []
   * FixingType_value : []
   * FixingView_value : [{"UsedByProfitCenter":"000202","TypeDescription":"大厅","ShopsId":"226691","FacilityNo":"0004","Status":"1","Description":"0004","Account":"","TypeCode":"0001","CleanId":"1","MostPerson":"8","IsNeedShow":"0","IsNotUse":"False","ResourceType":"","StatusNo":"","LogFlag":"","billopentime":"","billopentimes":"","covers":"","keyno":null,"techlist":null,"techlasttime":null,"calctype":null,"balance":null}]
   */

  private Object Account;
  private Object No;
  private int IsShowTeaCar;
  private List<?> ProfitCenter_value;
  private List<?> FixingType_value;
  private List<FixingViewValueBean> FixingView_value;

  public Object getAccount() {
    return Account;
  }

  public void setAccount(Object Account) {
    this.Account = Account;
  }

  public Object getNo() {
    return No;
  }

  public void setNo(Object No) {
    this.No = No;
  }

  public int getIsShowTeaCar() {
    return IsShowTeaCar;
  }

  public void setIsShowTeaCar(int IsShowTeaCar) {
    this.IsShowTeaCar = IsShowTeaCar;
  }

  public List<?> getProfitCenter_value() {
    return ProfitCenter_value;
  }

  public void setProfitCenter_value(List<?> ProfitCenter_value) {
    this.ProfitCenter_value = ProfitCenter_value;
  }

  public List<?> getFixingType_value() {
    return FixingType_value;
  }

  public void setFixingType_value(List<?> FixingType_value) {
    this.FixingType_value = FixingType_value;
  }

  public ArrayList<FixingViewValueBean> getFixingView_value() {
    ArrayList<FixingViewValueBean>serArr=new ArrayList<>();
    serArr.addAll(FixingView_value);
    return serArr;
  }

  public void setFixingView_value(List<FixingViewValueBean> FixingView_value) {
    this.FixingView_value = FixingView_value;
  }

  public static class FixingViewValueBean implements Serializable {
    /**
     * UsedByProfitCenter : 000202
     * TypeDescription : 大厅
     * ShopsId : 226691
     * FacilityNo : 0004
     * Status : 1
     * Description : 0004
     * Account :
     * TypeCode : 0001
     * CleanId : 1
     * MostPerson : 8
     * IsNeedShow : 0
     * IsNotUse : False
     * ResourceType :
     * StatusNo :
     * LogFlag :
     * billopentime :
     * billopentimes :
     * covers :
     * keyno : null
     * techlist : null
     * techlasttime : null
     * calctype : null
     * balance : null
     */

    private String UsedByProfitCenter;
    private String TypeDescription;
    private String ShopsId;
    private String FacilityNo;
    private String Status;
    private String Description;
    private String Account;
    private String TypeCode;
    private String CleanId;
    private String MostPerson;
    private String IsNeedShow;
    private String IsNotUse;
    private String ResourceType;
    private String StatusNo;
    private String LogFlag;
    private String billopentime;
    private String billopentimes;
    private String covers;
    private Object keyno;
    private Object techlist;
    private Object techlasttime;
    private Object calctype;
    private Object balance;

    public String getUsedByProfitCenter() {
      return UsedByProfitCenter;
    }

    public void setUsedByProfitCenter(String UsedByProfitCenter) {
      this.UsedByProfitCenter = UsedByProfitCenter;
    }

    public String getTypeDescription() {
      return TypeDescription;
    }

    public void setTypeDescription(String TypeDescription) {
      this.TypeDescription = TypeDescription;
    }

    public String getShopsId() {
      return ShopsId;
    }

    public void setShopsId(String ShopsId) {
      this.ShopsId = ShopsId;
    }

    public String getFacilityNo() {
      return FacilityNo;
    }

    public void setFacilityNo(String FacilityNo) {
      this.FacilityNo = FacilityNo;
    }

    public String getStatus() {
      return Status;
    }

    public void setStatus(String Status) {
      this.Status = Status;
    }

    public String getDescription() {
      return Description;
    }

    public void setDescription(String Description) {
      this.Description = Description;
    }

    public String getAccount() {
      return Account;
    }

    public void setAccount(String Account) {
      this.Account = Account;
    }

    public String getTypeCode() {
      return TypeCode;
    }

    public void setTypeCode(String TypeCode) {
      this.TypeCode = TypeCode;
    }

    public String getCleanId() {
      return CleanId;
    }

    public void setCleanId(String CleanId) {
      this.CleanId = CleanId;
    }

    public String getMostPerson() {
      return MostPerson;
    }

    public void setMostPerson(String MostPerson) {
      this.MostPerson = MostPerson;
    }

    public String getIsNeedShow() {
      return IsNeedShow;
    }

    public void setIsNeedShow(String IsNeedShow) {
      this.IsNeedShow = IsNeedShow;
    }

    public String getIsNotUse() {
      return IsNotUse;
    }

    public void setIsNotUse(String IsNotUse) {
      this.IsNotUse = IsNotUse;
    }

    public String getResourceType() {
      return ResourceType;
    }

    public void setResourceType(String ResourceType) {
      this.ResourceType = ResourceType;
    }

    public String getStatusNo() {
      return StatusNo;
    }

    public void setStatusNo(String StatusNo) {
      this.StatusNo = StatusNo;
    }

    public String getLogFlag() {
      return LogFlag;
    }

    public void setLogFlag(String LogFlag) {
      this.LogFlag = LogFlag;
    }

    public String getBillopentime() {
      return billopentime;
    }

    public void setBillopentime(String billopentime) {
      this.billopentime = billopentime;
    }

    public String getBillopentimes() {
      return billopentimes;
    }

    public void setBillopentimes(String billopentimes) {
      this.billopentimes = billopentimes;
    }

    public String getCovers() {
      return covers;
    }

    public void setCovers(String covers) {
      this.covers = covers;
    }

    public Object getKeyno() {
      return keyno;
    }

    public void setKeyno(Object keyno) {
      this.keyno = keyno;
    }

    public Object getTechlist() {
      return techlist;
    }

    public void setTechlist(Object techlist) {
      this.techlist = techlist;
    }

    public Object getTechlasttime() {
      return techlasttime;
    }

    public void setTechlasttime(Object techlasttime) {
      this.techlasttime = techlasttime;
    }

    public Object getCalctype() {
      return calctype;
    }

    public void setCalctype(Object calctype) {
      this.calctype = calctype;
    }

    public Object getBalance() {
      return balance;
    }

    public void setBalance(Object balance) {
      this.balance = balance;
    }
  }
}
