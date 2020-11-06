package com.yskrq.yjs.bean;

import com.yskrq.net_library.BaseBean;

import java.util.List;

public class KaiDanBean extends BaseBean {
  private List<ValueBean> value;

  public List<ValueBean> getValue() {
    return value;
  }

  public void setValue(List<ValueBean> value) {
    this.value = value;
  }

  public static class ValueBean {
    /**
     * itemname : Timi一次性菜品
     * itemcount : 2.00
     * RoyaltyMoney : 0.00
     */

    private String itemname;
    private String itemcount;
    private String RoyaltyMoney;

    public String getItemname() {
      return itemname;
    }

    public void setItemname(String itemname) {
      this.itemname = itemname;
    }

    public String getItemcount() {
      return itemcount;
    }

    public void setItemcount(String itemcount) {
      this.itemcount = itemcount;
    }

    public String getRoyaltyMoney() {
      return RoyaltyMoney;
    }

    public void setRoyaltyMoney(String RoyaltyMoney) {
      this.RoyaltyMoney = RoyaltyMoney;
    }

    public double getNum() {
      try {
        return Double.parseDouble(itemcount);
      } catch (Exception e) {
        return 0;
      }
    }

    public double getMoney() {
      try {
        return Double.parseDouble(RoyaltyMoney);
      } catch (Exception e) {
        return 0;
      }
    }
  }
}
