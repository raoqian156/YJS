package com.yskrq.yjs.bean;

import com.yskrq.net_library.BaseBean;

import java.util.List;

public class RoomLeftBean extends BaseBean {
  /**
   * Account : null
   * No : null
   * IsShowTeaCar : 0
   * ProfitCenter_value : []
   * FixingType_value : [{"ShopsId":"226691","TypeCode":"0001","Description":"大厅","UsedByProfitCenter":"000202","IsLimited":"False","UsePeriodDef":"False","CtrlCode":"1","IsFillTime":"False","IsDownLoad":"0"}]
   * FixingView_value : []
   * RelaxInvtTypePCView_value : []
   */

  private Object Account;
  private Object No;
  private int IsShowTeaCar;
  private List<?> ProfitCenter_value;
  private List<FixingTypeValueBean> FixingType_value;
  private List<?> FixingView_value;
  private List<?> RelaxInvtTypePCView_value;

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

  public List<FixingTypeValueBean> getFixingType_value() {
    return FixingType_value;
  }

  public void setFixingType_value(List<FixingTypeValueBean> FixingType_value) {
    this.FixingType_value = FixingType_value;
  }

  public List<?> getFixingView_value() {
    return FixingView_value;
  }

  public void setFixingView_value(List<?> FixingView_value) {
    this.FixingView_value = FixingView_value;
  }

  public List<?> getRelaxInvtTypePCView_value() {
    return RelaxInvtTypePCView_value;
  }

  public void setRelaxInvtTypePCView_value(List<?> RelaxInvtTypePCView_value) {
    this.RelaxInvtTypePCView_value = RelaxInvtTypePCView_value;
  }

  public static class FixingTypeValueBean {
    /**
     * ShopsId : 226691
     * TypeCode : 0001
     * Description : 大厅
     * UsedByProfitCenter : 000202
     * IsLimited : False
     * UsePeriodDef : False
     * CtrlCode : 1
     * IsFillTime : False
     * IsDownLoad : 0
     */

    private String ShopsId;
    private String TypeCode;
    private String Description;
    private String UsedByProfitCenter;
    private String IsLimited;
    private String UsePeriodDef;
    private String CtrlCode;
    private String IsFillTime;
    private String IsDownLoad;

    public String getShopsId() {
      return ShopsId;
    }

    public void setShopsId(String ShopsId) {
      this.ShopsId = ShopsId;
    }

    public String getTypeCode() {
      return TypeCode;
    }

    public void setTypeCode(String TypeCode) {
      this.TypeCode = TypeCode;
    }

    public String getDescription() {
      return Description;
    }

    public void setDescription(String Description) {
      this.Description = Description;
    }

    public String getUsedByProfitCenter() {
      return UsedByProfitCenter;
    }

    public void setUsedByProfitCenter(String UsedByProfitCenter) {
      this.UsedByProfitCenter = UsedByProfitCenter;
    }

    public String getIsLimited() {
      return IsLimited;
    }

    public void setIsLimited(String IsLimited) {
      this.IsLimited = IsLimited;
    }

    public String getUsePeriodDef() {
      return UsePeriodDef;
    }

    public void setUsePeriodDef(String UsePeriodDef) {
      this.UsePeriodDef = UsePeriodDef;
    }

    public String getCtrlCode() {
      return CtrlCode;
    }

    public void setCtrlCode(String CtrlCode) {
      this.CtrlCode = CtrlCode;
    }

    public String getIsFillTime() {
      return IsFillTime;
    }

    public void setIsFillTime(String IsFillTime) {
      this.IsFillTime = IsFillTime;
    }

    public String getIsDownLoad() {
      return IsDownLoad;
    }

    public void setIsDownLoad(String IsDownLoad) {
      this.IsDownLoad = IsDownLoad;
    }
  }
}
