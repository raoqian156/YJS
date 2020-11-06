package com.yskrq.yjs.bean;

import android.text.TextUtils;

import com.contrarywind.interfaces.IPickerViewData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yskrq.net_library.BaseBean;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TechProjectBean extends BaseBean {

  /**
   * error : 0
   * Value : [{"iscalctime":true,"ItemNo":"100     ","Price":100.00,"Name":"保健足疗","Unit":"01","UnitTime":90,"UnitName":"份        ","ItemType":"0000"},{"iscalctime":true,"ItemNo":"101     ","Price":120.00,"Name":"尊品足疗","Unit":"01","UnitTime":50,"UnitName":"份        ","ItemType":"0000"},{"iscalctime":true,"ItemNo":"104     ","Price":268.00,"Name":"瑜伽泰式","Unit":"01","UnitTime":0,"UnitName":"份        ","ItemType":"0000"},{"iscalctime":true,"ItemNo":"105     ","Price":288.00,"Name":"疏经油压","Unit":"01","UnitTime":80,"UnitName":"份        ","ItemType":"0000"},{"iscalctime":true,"ItemNo":"106     ","Price":368.00,"Name":"净化身心","Unit":"01","UnitTime":80,"UnitName":"份        ","ItemType":"0000"},{"iscalctime":true,"ItemNo":"107     ","Price":388.00,"Name":"欧亚风情","Unit":"01","UnitTime":90,"UnitName":"份        ","ItemType":"0000"},{"iscalctime":true,"ItemNo":"1234    ","Price":35.00,"Name":"芙蓉王1","Unit":"23","UnitTime":0,"UnitName":"盒        ","ItemType":"0000"},{"iscalctime":true,"ItemNo":"52145   ","Price":135.00,"Name":"帝豪之星","Unit":"01","UnitTime":80,"UnitName":"份        ","ItemType":"0000"},{"iscalctime":true,"ItemNo":"52146   ","Price":66.00,"Name":"和天下1","Unit":"25","UnitTime":90,"UnitName":"包","ItemType":"0000"}]
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

  public static class ValueBean implements IPickerViewData {

    /**
     * iscalctime : true
     * ItemNo : 100
     * Price : 100.0
     * Name : 保健足疗
     * Unit : 01
     * UnitTime : 90
     * UnitName : 份
     * ItemType : 0000
     */

    private boolean iscalctime;
    private String ItemNo;
    private double Price;
    private String Name;
    private String Unit;
    private int UnitTime;
    private String UnitName;
    private String ItemType;

    public boolean isIscalctime() {
      return iscalctime;
    }

    public void setIscalctime(boolean iscalctime) {
      this.iscalctime = iscalctime;
    }

    public String getItemNo() {
      return ItemNo;
    }

    public void setItemNo(String ItemNo) {
      this.ItemNo = ItemNo;
    }

    public double getPrice() {
      return Price;
    }

    public void setPrice(double Price) {
      this.Price = Price;
    }

    public String getName() {
      return Name;
    }

    public void setName(String Name) {
      this.Name = Name;
    }

    public String getUnit() {
      return Unit;
    }

    public void setUnit(String Unit) {
      this.Unit = Unit;
    }

    public int getUnitTime() {
      return UnitTime;
    }

    public void setUnitTime(int UnitTime) {
      this.UnitTime = UnitTime;
    }

    public String getUnitName() {
      return UnitName;
    }

    public void setUnitName(String UnitName) {
      this.UnitName = UnitName;
    }

    public String getItemType() {
      return ItemType;
    }

    public void setItemType(String ItemType) {
      this.ItemType = ItemType;
    }

    @Override
    public String getPickerViewText() {
      return Name;
    }
  }
}
