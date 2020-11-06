package com.yskrq.yjs.bean;

import com.yskrq.net_library.BaseBean;

import java.io.Serializable;
import java.util.List;

public class RoomBean extends BaseBean {

  /**
   * Account : null
   * No : null
   * IsShowTeaCar : 1
   * ProfitCenter_value : []
   * FixingType_value : []
   * FixingView_value : [{"UsedByProfitCenter":"000202","TypeDescription":"大厅","ShopsId":"226691","FacilityNo":"0004","Status":"1","Description":"0004","Account":"","TypeCode":"0001","CleanId":"1","MostPerson":"8","IsNeedShow":"0","IsNotUse":"False","ResourceType":"","StatusNo":"","LogFlag":"","billopentime":"","billopentimes":"","covers":"","keyno":"","techlist":"","techlasttime":"","calctype":"","balance":""},{"UsedByProfitCenter":"000202","TypeDescription":"大厅","ShopsId":"226691","FacilityNo":"6666","Status":"1","Description":"6666","Account":"","TypeCode":"0001","CleanId":"0","MostPerson":"2","IsNeedShow":"0","IsNotUse":"False","ResourceType":"","StatusNo":"","LogFlag":"","billopentime":"","billopentimes":"","covers":"","keyno":"","techlist":"","techlasttime":"","calctype":"","balance":""}]
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

  public List<FixingViewValueBean> getFixingView_value() {
    return FixingView_value;
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
     * keyno :
     * techlist :
     * techlasttime :
     * calctype :
     * balance :
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
    private String keyno;
    private String techlist;
    private String techlasttime;
    private String calctype;
    private String balance;

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

    public String getKeyno() {
      return keyno;
    }

    public void setKeyno(String keyno) {
      this.keyno = keyno;
    }

    public String getTechlist() {
      return techlist;
    }

    public void setTechlist(String techlist) {
      this.techlist = techlist;
    }

    public String getTechlasttime() {
      return techlasttime;
    }

    public void setTechlasttime(String techlasttime) {
      this.techlasttime = techlasttime;
    }

    public String getCalctype() {
      return calctype;
    }

    public void setCalctype(String calctype) {
      this.calctype = calctype;
    }

    public String getBalance() {
      return balance;
    }

    public void setBalance(String balance) {
      this.balance = balance;
    }
  }
}
