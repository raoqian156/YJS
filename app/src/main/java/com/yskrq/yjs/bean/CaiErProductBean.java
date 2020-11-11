package com.yskrq.yjs.bean;

import android.content.Context;

import com.yskrq.common.AppInfo;
import com.yskrq.net_library.BaseBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CaiErProductBean extends BaseBean {

  private List<ValueBean> value;

  public List<ValueBean> getValue() {
    return value;
  }

  public void setValue(List<ValueBean> value) {
    this.value = value;
  }

  public static class ValueBean implements Serializable {
    /**
     * typecode : 00
     * itemno : 105
     * price : 288.00
     * name : 疏经油压
     */

    private String typecode;
    private String itemno;
    private String price;
    private String name;

    int num = 0;
    boolean isSelect = false;

    public void setNum(int num) {
      this.num = num;
    }

    public void setSelect(boolean select) {
      isSelect = select;
    }

    public int getNum() {
      return num;
    }

    public boolean isSelect() {
      return isSelect;
    }

    public String getTypecode() {
      return typecode;
    }

    public void setTypecode(String typecode) {
      this.typecode = typecode;
    }

    public String getItemno() {
      return itemno;
    }

    public void setItemno(String itemno) {
      this.itemno = itemno;
    }

    public String getPrice() {
      return price;
    }

    public void setPrice(String price) {
      this.price = price;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public void showSizeAdd() {
      num++;
    }

    public void showSizeCut() {
      if (num > 0) {
        num--;
      }
    }

    public void clear() {
      num = 0;
      isSelect = false;
    }

    public double getAllPrice() {
      try {
        return getNum() * Double.parseDouble(price);
      } catch (Exception e) {
        return 0;
      }
    }

    public static List<TransBean> getUploadXhl(List<ValueBean> sel, Context context,
                                               String account) {
      List<TransBean> res = new ArrayList<>();
      if (sel != null && sel.size() > 0) {
        for (ValueBean valueBean : sel) {
          res.add(new TransBean(valueBean, AppInfo.getTechNum(context), AppInfo
              .getTechSex(context)));
        }
      }
      return res;

    }


    public static class TransBean {
      private String itemno;
      private String itemcount;
      private String itemname;
      private String Technician;
      private String TechnicianSex;
      private String price;

      public TransBean(ValueBean valueBean, String techNum, String techSex) {
        this.Technician = techNum;
        this.TechnicianSex = techSex;
        this.itemno = valueBean.getItemno();
        this.itemcount = valueBean.getNum() + "";
        this.price = valueBean.getPrice();
        this.itemname=valueBean.getName();
      }
    }
  }
}
