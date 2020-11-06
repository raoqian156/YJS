package com.yskrq.yjs.bean;

import com.yskrq.net_library.BaseBean;

import java.util.List;

public class RoomProjectTypeBean extends BaseBean {

  /**
   * Account : null
   * No : null
   * IsShowTeaCar : 0
   * ProfitCenter_value : []
   * FixingType_value : []
   * FixingView_value : []
   * RelaxInvtTypePCView_value : [{"ProfitCenter":"000202","TypeCode":"00","Description":"足浴","BaseType":"00","BaseName":"食品","ChamberExpendType":"","GroupId":"J00908","ShopsId":"226691","SortNumber":0},{"ProfitCenter":"000202","TypeCode":"06","Description":"鲜榨果汁","BaseType":"00","BaseName":"食品","ChamberExpendType":"","GroupId":"J00908","ShopsId":"226691","SortNumber":0},{"ProfitCenter":"000202","TypeCode":"10","Description":"香烟","BaseType":"01","BaseName":"酒水","ChamberExpendType":"","GroupId":"J00908","ShopsId":"226691","SortNumber":0},{"ProfitCenter":"000202  ","TypeCode":"88","Description":"外地门票","BaseType":"99","BaseName":"门票","ChamberExpendType":"","GroupId":"J00908","ShopsId":"226691","SortNumber":0},{"ProfitCenter":"000202  ","TypeCode":"99","Description":"本地门票","BaseType":"99","BaseName":"门票","ChamberExpendType":"","GroupId":"J00908","ShopsId":"226691","SortNumber":0}]
   */

  private Object Account;
  private Object No;
  private int IsShowTeaCar;
  private List<?> ProfitCenter_value;
  private List<?> FixingType_value;
  private List<?> FixingView_value;
  private List<RelaxInvtTypePCViewValueBean> RelaxInvtTypePCView_value;

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

  public List<?> getFixingView_value() {
    return FixingView_value;
  }

  public void setFixingView_value(List<?> FixingView_value) {
    this.FixingView_value = FixingView_value;
  }

  public List<RelaxInvtTypePCViewValueBean> getRelaxInvtTypePCView_value() {
    return RelaxInvtTypePCView_value;
  }

  public void setRelaxInvtTypePCView_value(
      List<RelaxInvtTypePCViewValueBean> RelaxInvtTypePCView_value) {
    this.RelaxInvtTypePCView_value = RelaxInvtTypePCView_value;
  }

  public static class RelaxInvtTypePCViewValueBean {
    /**
     * ProfitCenter : 000202
     * TypeCode : 00
     * Description : 足浴
     * BaseType : 00
     * BaseName : 食品
     * ChamberExpendType :
     * GroupId : J00908
     * ShopsId : 226691
     * SortNumber : 0
     */

    private String ProfitCenter;
    private String TypeCode;
    private String Description;
    private String BaseType;
    private String BaseName;
    private String ChamberExpendType;
    private String GroupId;
    private String ShopsId;
    private int SortNumber;

    public String getProfitCenter() {
      return ProfitCenter;
    }

    public void setProfitCenter(String ProfitCenter) {
      this.ProfitCenter = ProfitCenter;
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

    public String getBaseType() {
      return BaseType;
    }

    public void setBaseType(String BaseType) {
      this.BaseType = BaseType;
    }

    public String getBaseName() {
      return BaseName;
    }

    public void setBaseName(String BaseName) {
      this.BaseName = BaseName;
    }

    public String getChamberExpendType() {
      return ChamberExpendType;
    }

    public void setChamberExpendType(String ChamberExpendType) {
      this.ChamberExpendType = ChamberExpendType;
    }

    public String getGroupId() {
      return GroupId;
    }

    public void setGroupId(String GroupId) {
      this.GroupId = GroupId;
    }

    public String getShopsId() {
      return ShopsId;
    }

    public void setShopsId(String ShopsId) {
      this.ShopsId = ShopsId;
    }

    public int getSortNumber() {
      return SortNumber;
    }

    public void setSortNumber(int SortNumber) {
      this.SortNumber = SortNumber;
    }
  }
}
