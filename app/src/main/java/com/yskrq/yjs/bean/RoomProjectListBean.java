package com.yskrq.yjs.bean;

import android.content.Context;
import android.text.TextUtils;

import com.yskrq.common.AppInfo;
import com.yskrq.net_library.BaseBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RoomProjectListBean extends BaseBean {

  private List<ValueBean> value;

  public List<ValueBean> getValue() {
    return value;
  }

  public void setValue(List<ValueBean> value) {
    this.value = value;
  }

  public static class ValueBean implements Serializable {
    /**
     * GroupId : J00908
     * Shopsid : 226691
     * TypeCode : 00
     * Name : 保健足疗
     * ItemNo : 100
     * Printer : null
     * ShortKey : bjzl
     * CanDiscount : null
     * IsAvailible : null
     * IsPrinter : null
     * IsPdaUse : null
     * IsNotSend : null
     * DishesType : null
     * IsDownLoad : null
     * Unit : 01
     * Price : 100.00
     * DepotDepart : null
     * Price4 : null
     * sum : 0
     * Technician :
     * TechnicianSex :
     * TechnicianSell :
     * TechnicianCall :
     * ButtonName : 点钟
     * ButtonColor : blue
     * ButtonSellName : 选钟
     * ButtonSellColor : blue
     * ButtonCallName : CALL钟
     * ButtonCallColor : blue
     * IsShow : show
     * iscalctime : True
     * canmodifyname : null
     * canmodifyprice : null
     * canfete : null
     * itemtype : null
     * biswxshow : null
     */

    private String GroupId;
    private String Shopsid;
    private String TypeCode;
    private String Name;
    private String ItemNo;
    private Object Printer;
    private String ShortKey;
    private Object CanDiscount;
    private Object IsAvailible;
    private Object IsPrinter;
    private Object IsPdaUse;
    private Object IsNotSend;
    private Object DishesType;
    private Object IsDownLoad;
    private String Unit;
    private String Price;
    private Object DepotDepart;
    private Object Price4;
    private int sum;
    private String Technician;
    private String TechnicianSex;
    private String TechnicianSell;
    private String TechnicianCall;
    private String ButtonName;
    private String ButtonColor;
    private String ButtonSellName;
    private String ButtonSellColor;
    private String ButtonCallName;
    private String ButtonCallColor;
    private String IsShow;
    private String iscalctime;
    private Object canmodifyname;
    private Object canmodifyprice;
    private Object canfete;
    private Object itemtype;
    private Object biswxshow;

    private int showNum = 0;
    private List select1 = new ArrayList();
    private List select2 = new ArrayList();

    public int getShowNum() {
      if (!TextUtils.isEmpty(Technician + TechnicianSell)) {
        int num = 0;
        if (!TextUtils.isEmpty(Technician)) {
          num += Technician.split(",").length;
        }
        if (!TextUtils.isEmpty(TechnicianSell)) {
          num += TechnicianSell.split(",").length;
        }
        return num;
      }

      return showNum;
    }

    public void showSizeAdd() {
      this.showNum = getShowNum() + 1;
    }

    public void showSizeCut() {
      this.showNum = Math.max(getShowNum() - 1, 0);
    }

    public List getSelect1() {
      return select1;
    }

    public void setSelect1(List select1) {
      this.select1 = select1;
    }

    public List getSelect2() {
      return select2;
    }

    public void setSelect2(List select2) {
      this.select2 = select2;
    }

    public String getGroupId() {
      return GroupId;
    }

    public void setGroupId(String GroupId) {
      this.GroupId = GroupId;
    }

    public String getShopsid() {
      return Shopsid;
    }

    public void setShopsid(String Shopsid) {
      this.Shopsid = Shopsid;
    }

    public String getTypeCode() {
      return TypeCode;
    }

    public void setTypeCode(String TypeCode) {
      this.TypeCode = TypeCode;
    }

    public String getName() {
      return Name;
    }

    public void setName(String Name) {
      this.Name = Name;
    }

    public String getItemNo() {
      return ItemNo;
    }

    public void setItemNo(String ItemNo) {
      this.ItemNo = ItemNo;
    }

    public Object getPrinter() {
      return Printer;
    }

    public void setPrinter(Object Printer) {
      this.Printer = Printer;
    }

    public String getShortKey() {
      return ShortKey;
    }

    public void setShortKey(String ShortKey) {
      this.ShortKey = ShortKey;
    }

    public Object getCanDiscount() {
      return CanDiscount;
    }

    public void setCanDiscount(Object CanDiscount) {
      this.CanDiscount = CanDiscount;
    }

    public Object getIsAvailible() {
      return IsAvailible;
    }

    public void setIsAvailible(Object IsAvailible) {
      this.IsAvailible = IsAvailible;
    }

    public Object getIsPrinter() {
      return IsPrinter;
    }

    public void setIsPrinter(Object IsPrinter) {
      this.IsPrinter = IsPrinter;
    }

    public Object getIsPdaUse() {
      return IsPdaUse;
    }

    public void setIsPdaUse(Object IsPdaUse) {
      this.IsPdaUse = IsPdaUse;
    }

    public Object getIsNotSend() {
      return IsNotSend;
    }

    public void setIsNotSend(Object IsNotSend) {
      this.IsNotSend = IsNotSend;
    }

    public Object getDishesType() {
      return DishesType;
    }

    public void setDishesType(Object DishesType) {
      this.DishesType = DishesType;
    }

    public Object getIsDownLoad() {
      return IsDownLoad;
    }

    public void setIsDownLoad(Object IsDownLoad) {
      this.IsDownLoad = IsDownLoad;
    }

    public String getUnit() {
      return Unit;
    }

    public void setUnit(String Unit) {
      this.Unit = Unit;
    }

    public String getPrice() {
      return Price;
    }

    public void setPrice(String Price) {
      this.Price = Price;
    }

    public Object getDepotDepart() {
      return DepotDepart;
    }

    public void setDepotDepart(Object DepotDepart) {
      this.DepotDepart = DepotDepart;
    }

    public Object getPrice4() {
      return Price4;
    }

    public void setPrice4(Object Price4) {
      this.Price4 = Price4;
    }

    public int getSum() {
      return sum;
    }

    public void setSum(int sum) {
      this.sum = sum;
    }

    public String getTechnician() {
      return Technician;
    }

    public void setTechnician(String Technician) {
      this.Technician = Technician;
    }

    public String getTechnicianSex() {
      return TechnicianSex;
    }

    public void setTechnicianSex(String TechnicianSex) {
      this.TechnicianSex = TechnicianSex;
    }

    public String getTechnicianSell() {
      return TechnicianSell;
    }

    public void setTechnicianSell(String TechnicianSell) {
      this.TechnicianSell = TechnicianSell;
    }

    public String getTechnicianCall() {
      return TechnicianCall;
    }

    public void setTechnicianCall(String TechnicianCall) {
      this.TechnicianCall = TechnicianCall;
    }

    public String getButtonName() {
      return ButtonName;
    }

    public void setButtonName(String ButtonName) {
      this.ButtonName = ButtonName;
    }

    public String getButtonColor() {
      return ButtonColor;
    }

    public void setButtonColor(String ButtonColor) {
      this.ButtonColor = ButtonColor;
    }

    public String getButtonSellName() {
      return ButtonSellName;
    }

    public void setButtonSellName(String ButtonSellName) {
      this.ButtonSellName = ButtonSellName;
    }

    public String getButtonSellColor() {
      return ButtonSellColor;
    }

    public void setButtonSellColor(String ButtonSellColor) {
      this.ButtonSellColor = ButtonSellColor;
    }

    public String getButtonCallName() {
      return ButtonCallName;
    }

    public void setButtonCallName(String ButtonCallName) {
      this.ButtonCallName = ButtonCallName;
    }

    public String getButtonCallColor() {
      return ButtonCallColor;
    }

    public void setButtonCallColor(String ButtonCallColor) {
      this.ButtonCallColor = ButtonCallColor;
    }

    public String getIsShow() {
      return IsShow;
    }

    public void setIsShow(String IsShow) {
      this.IsShow = IsShow;
    }

    public String getIscalctime() {
      return iscalctime;
    }

    public void setIscalctime(String iscalctime) {
      this.iscalctime = iscalctime;
    }

    public Object getCanmodifyname() {
      return canmodifyname;
    }

    public void setCanmodifyname(Object canmodifyname) {
      this.canmodifyname = canmodifyname;
    }

    public Object getCanmodifyprice() {
      return canmodifyprice;
    }

    public void setCanmodifyprice(Object canmodifyprice) {
      this.canmodifyprice = canmodifyprice;
    }

    public Object getCanfete() {
      return canfete;
    }

    public void setCanfete(Object canfete) {
      this.canfete = canfete;
    }

    public Object getItemtype() {
      return itemtype;
    }

    public void setItemtype(Object itemtype) {
      this.itemtype = itemtype;
    }

    public Object getBiswxshow() {
      return biswxshow;
    }

    public void setBiswxshow(Object biswxshow) {
      this.biswxshow = biswxshow;
    }

    public boolean isSelect() {
      return getShowNum() > 0;
    }

    public double getAllPrice() {
      try {
        return getShowNum() * Double.parseDouble(Price);
      } catch (Exception e) {
        return 0;
      }
    }

    public static List<TransBean> getUploadXhl(List<ValueBean> beans, Context context,
                                               String account) {
      List<TransBean> res = new ArrayList<>();
      String groupId = AppInfo.getGroupId();
      String shopId = AppInfo.getShopsid();
      if (beans != null && beans.size() > 0) {
        for (ValueBean item : beans) {
          res.add(new TransBean(item, groupId, shopId, account));
        }
      }
      return res;
    }

    public void clear() {
      this.showNum=0;
      this.Technician="";
      this.TechnicianSell="";
    }

    public static class TransBean {
      private String ShopsID;
      private String groupid;
      private String ID;//账号
      private String bhao;//itemN
      private String name;//
      private String danj;
      private String sl;
      private String lb;
      private String zfyq;
      private String Technician;//点钟技师号 逗号,隔开
      private String TechnicianSell;//选钟技师号  逗号,隔开
      private String TechnicianSex;//
      private boolean RelaxAccount;// true Technician or TechnicianSell  存在   false - 都不在
      private String Countmoney;//danj * sl    or    （Technician+TechnicianSell）*danjia
      private String ItemCount;//danj or （Technician+TechnicianSell）.size()

      public TransBean(ValueBean item, String groupid, String shopsID, String ac) {
        this.ShopsID = shopsID;
        this.groupid = groupid;
        this.ID = ac;
        this.bhao = item.getItemNo();
        this.name = item.getName();
        this.danj = item.getPrice();
        this.sl = item.getShowNum() + "";
        this.lb = item.getItemtype() + "";
        this.zfyq = "";
        this.Technician = item.getTechnician();
        this.TechnicianSell = item.getTechnicianSell();
        this.TechnicianSex = "";
        this.ItemCount = item.getShowNum() + "";
        if (TextUtils.isEmpty(item.getTechnician() + item.getTechnicianSell())) {
          this.RelaxAccount = false;
        } else {
          this.RelaxAccount = true;
        }
      }

    }
  }
}
