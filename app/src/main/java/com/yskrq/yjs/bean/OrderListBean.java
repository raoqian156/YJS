package com.yskrq.yjs.bean;

import com.yskrq.net_library.BaseBean;

import java.io.Serializable;
import java.util.List;

public class OrderListBean extends BaseBean {

  private List<ValueBean> value;

  public List<ValueBean> getValue() {
    return value;
  }

  public void setValue(List<ValueBean> value) {
    this.value = value;
  }

  public static class ValueBean implements Serializable {
    /**
     * ShopsId : 226691
     * Account : L000003771
     * SeqNum : 1
     * ProfitCenter : 000202
     * WaiterID : YSK226691
     * ItemNo : 100
     * ItemName : 保健足疗
     * Price : 100.0000
     * ItemCount : 1.00
     * Status : 1
     * Modification : null
     * FacilityNo : 8889
     * Remark : null
     * OpId : YSK226691
     * Technician : 003
     * GroupId : 9002
     * MastSeqNum : null
     * ItemType : 0000
     * Hoteldate : 2020-10-30 16:00:00Z
     * PrintStatus : 0
     * Unit : 01
     * CostPosFactureItem : 0000
     * RelaxClockType : 1
     * RemainderRemark : null
     * RelaxSex : 0
     * RelaxAccount : L000003771
     * IndexNumber : 6649
     * ExpendTimeId : 46
     * WaitBeginTimeDepart : 09:40:10
     * Shift : null
     * RelaxGroupId : null
     * RelaxPosId : null
     * ServiceRate : null
     * DiscountRate : null
     */

    private String ShopsId;
    private String Account;
    private int SeqNum;
    private String ProfitCenter;
    private String WaiterID;
    private String ItemNo;
    private String ItemName;
    private String Price;
    private String ItemCount;
    private String Status;
    private Object Modification;
    private String FacilityNo;
    private Object Remark;
    private String OpId;
    private String Technician;
    private String GroupId;
    private Object MastSeqNum;
    private String ItemType;
    private String Hoteldate;
    private String PrintStatus;
    private String Unit;
    private String CostPosFactureItem;
    private String RelaxClockType;
    private Object RemainderRemark;
    private String RelaxSex;
    private String RelaxAccount;
    private int IndexNumber;
    private String ExpendTimeId;
    private String WaitBeginTimeDepart;
    private Object Shift;
    private Object RelaxGroupId;
    private Object RelaxPosId;
    private Object ServiceRate;
    private Object DiscountRate;

    public double getAllPrice() {
      try {
        return (Double.parseDouble(ItemCount) * Double.parseDouble(Price));
      } catch (Exception e) {
        return 0;
      }
    }

    public String getShopsId() {
      return ShopsId;
    }

    public void setShopsId(String ShopsId) {
      this.ShopsId = ShopsId;
    }

    public String getAccount() {
      return Account;
    }

    public void setAccount(String Account) {
      this.Account = Account;
    }

    public int getSeqNum() {
      return SeqNum;
    }

    public void setSeqNum(int SeqNum) {
      this.SeqNum = SeqNum;
    }

    public String getProfitCenter() {
      return ProfitCenter;
    }

    public void setProfitCenter(String ProfitCenter) {
      this.ProfitCenter = ProfitCenter;
    }

    public String getWaiterID() {
      return WaiterID;
    }

    public void setWaiterID(String WaiterID) {
      this.WaiterID = WaiterID;
    }

    public String getItemNo() {
      return ItemNo;
    }

    public void setItemNo(String ItemNo) {
      this.ItemNo = ItemNo;
    }

    public String getItemName() {
      return ItemName;
    }

    public void setItemName(String ItemName) {
      this.ItemName = ItemName;
    }

    public String getPrice() {
      return Price;
    }

    public void setPrice(String Price) {
      this.Price = Price;
    }

    public String getItemCount() {
      return ItemCount;
    }

    public void setItemCount(String ItemCount) {
      this.ItemCount = ItemCount;
    }

    public String getStatus() {
      return Status;
    }

    public void setStatus(String Status) {
      this.Status = Status;
    }

    public Object getModification() {
      return Modification;
    }

    public void setModification(Object Modification) {
      this.Modification = Modification;
    }

    public String getFacilityNo() {
      return FacilityNo;
    }

    public void setFacilityNo(String FacilityNo) {
      this.FacilityNo = FacilityNo;
    }

    public Object getRemark() {
      return Remark;
    }

    public void setRemark(Object Remark) {
      this.Remark = Remark;
    }

    public String getOpId() {
      return OpId;
    }

    public void setOpId(String OpId) {
      this.OpId = OpId;
    }

    public String getTechnician() {
      return Technician;
    }

    public void setTechnician(String Technician) {
      this.Technician = Technician;
    }

    public String getGroupId() {
      return GroupId;
    }

    public void setGroupId(String GroupId) {
      this.GroupId = GroupId;
    }

    public Object getMastSeqNum() {
      return MastSeqNum;
    }

    public void setMastSeqNum(Object MastSeqNum) {
      this.MastSeqNum = MastSeqNum;
    }

    public String getItemType() {
      return ItemType;
    }

    public void setItemType(String ItemType) {
      this.ItemType = ItemType;
    }

    public String getHoteldate() {
      return Hoteldate;
    }

    public void setHoteldate(String Hoteldate) {
      this.Hoteldate = Hoteldate;
    }

    public String getPrintStatus() {
      return PrintStatus;
    }

    public void setPrintStatus(String PrintStatus) {
      this.PrintStatus = PrintStatus;
    }

    public String getUnit() {
      return Unit;
    }

    public void setUnit(String Unit) {
      this.Unit = Unit;
    }

    public String getCostPosFactureItem() {
      return CostPosFactureItem;
    }

    public void setCostPosFactureItem(String CostPosFactureItem) {
      this.CostPosFactureItem = CostPosFactureItem;
    }

    public String getRelaxClockType() {
      return RelaxClockType;
    }

    public void setRelaxClockType(String RelaxClockType) {
      this.RelaxClockType = RelaxClockType;
    }

    public Object getRemainderRemark() {
      return RemainderRemark;
    }

    public void setRemainderRemark(Object RemainderRemark) {
      this.RemainderRemark = RemainderRemark;
    }

    public String getRelaxSex() {
      return RelaxSex;
    }

    public void setRelaxSex(String RelaxSex) {
      this.RelaxSex = RelaxSex;
    }

    public String getRelaxAccount() {
      return RelaxAccount;
    }

    public void setRelaxAccount(String RelaxAccount) {
      this.RelaxAccount = RelaxAccount;
    }

    public int getIndexNumber() {
      return IndexNumber;
    }

    public void setIndexNumber(int IndexNumber) {
      this.IndexNumber = IndexNumber;
    }

    public String getExpendTimeId() {
      return ExpendTimeId;
    }

    public void setExpendTimeId(String ExpendTimeId) {
      this.ExpendTimeId = ExpendTimeId;
    }

    public String getWaitBeginTimeDepart() {
      return WaitBeginTimeDepart;
    }

    public void setWaitBeginTimeDepart(String WaitBeginTimeDepart) {
      this.WaitBeginTimeDepart = WaitBeginTimeDepart;
    }

    public Object getShift() {
      return Shift;
    }

    public void setShift(Object Shift) {
      this.Shift = Shift;
    }

    public Object getRelaxGroupId() {
      return RelaxGroupId;
    }

    public void setRelaxGroupId(Object RelaxGroupId) {
      this.RelaxGroupId = RelaxGroupId;
    }

    public Object getRelaxPosId() {
      return RelaxPosId;
    }

    public void setRelaxPosId(Object RelaxPosId) {
      this.RelaxPosId = RelaxPosId;
    }

    public Object getServiceRate() {
      return ServiceRate;
    }

    public void setServiceRate(Object ServiceRate) {
      this.ServiceRate = ServiceRate;
    }

    public Object getDiscountRate() {
      return DiscountRate;
    }

    public void setDiscountRate(Object DiscountRate) {
      this.DiscountRate = DiscountRate;
    }

    public String getExpendShow() {
      if (ExpendTimeId == null) return "";
      return "\n余 " + ExpendTimeId + " 分钟";
    }

    public String getShowZhong() {
      return "0".equals(getRelaxClockType()) ? "轮钟"
          : "1".equals(getRelaxClockType()) ? "轮钟"
          : "2".equals(getRelaxClockType()) ? "选种"
          : "5" .equals(getRelaxClockType()) ? "CALL钟"
          : "7".equals(getRelaxClockType()) ? "买钟" : "";
    }
  }
}
