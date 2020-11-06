package com.yskrq.yjs.bean;

import com.rq.rvlibrary.DRViewHolder;
import com.yskrq.net_library.BaseBean;

import java.io.Serializable;
import java.util.List;

public class StaticsListBean extends BaseBean {

  /**
   * total : 2
   * Value : [{"hotel_date":null,"item_name":"保健足疗","relax_clock_type":null,"item_count_qty":5,"item_price_sum":0,"FacilityNo":null,"WaitBeginTime":null,"children":[{"hotel_date":null,"item_name":"点钟","relax_clock_type":null,"item_count_qty":1,"item_price_sum":0,"FacilityNo":"8897      ","WaitBeginTime":"2020/10/26 10:48:00","children":null},{"hotel_date":null,"item_name":"点钟","relax_clock_type":null,"item_count_qty":1,"item_price_sum":0,"FacilityNo":"8898      ","WaitBeginTime":"2020/10/26 15:14:20","children":null},{"hotel_date":null,"item_name":"点钟","relax_clock_type":null,"item_count_qty":2,"item_price_sum":0,"FacilityNo":"8898      ","WaitBeginTime":"2020/10/26 16:49:53","children":null},{"hotel_date":null,"item_name":"加半钟","relax_clock_type":null,"item_count_qty":1,"item_price_sum":0,"FacilityNo":"8898      ","WaitBeginTime":"2020/10/26 16:49:53","children":null}]},{"hotel_date":null,"item_name":"瑜伽泰式","relax_clock_type":null,"item_count_qty":2,"item_price_sum":0,"FacilityNo":null,"WaitBeginTime":null,"children":[{"hotel_date":null,"item_name":"点钟","relax_clock_type":null,"item_count_qty":2,"item_price_sum":0,"FacilityNo":"8897      ","WaitBeginTime":"2020/10/26 14:44:57","children":null}]}]
   * error : 0
   */

  private int total;
  private List<ValueBean> Value;

  public int getTotal() {
    return total;
  }

  public void setTotal(int total) {
    this.total = total;
  }

  public List<ValueBean> getValue() {
    return Value;
  }

  public void setValue(List<ValueBean> Value) {
    this.Value = Value;
  }

  public static class ValueBean implements DRViewHolder.DRParent, Serializable {
    /**
     * hotel_date : null
     * item_name : 保健足疗
     * relax_clock_type : null
     * item_count_qty : 5
     * item_price_sum : 0
     * FacilityNo : null
     * WaitBeginTime : null
     * children : [{"hotel_date":null,"item_name":"点钟","relax_clock_type":null,"item_count_qty":1,"item_price_sum":0,"FacilityNo":"8897      ","WaitBeginTime":"2020/10/26 10:48:00","children":null},{"hotel_date":null,"item_name":"点钟","relax_clock_type":null,"item_count_qty":1,"item_price_sum":0,"FacilityNo":"8898      ","WaitBeginTime":"2020/10/26 15:14:20","children":null},{"hotel_date":null,"item_name":"点钟","relax_clock_type":null,"item_count_qty":2,"item_price_sum":0,"FacilityNo":"8898      ","WaitBeginTime":"2020/10/26 16:49:53","children":null},{"hotel_date":null,"item_name":"加半钟","relax_clock_type":null,"item_count_qty":1,"item_price_sum":0,"FacilityNo":"8898      ","WaitBeginTime":"2020/10/26 16:49:53","children":null}]
     */

    private Object hotel_date;
    private String item_name;
    private Object relax_clock_type;
    private int item_count_qty;
    private int item_price_sum;
    private Object FacilityNo;
    private Object WaitBeginTime;
    private List<ChildrenBean> children;

    public Object getHotel_date() {
      return hotel_date;
    }

    public void setHotel_date(Object hotel_date) {
      this.hotel_date = hotel_date;
    }

    public String getItem_name() {
      return item_name;
    }

    public void setItem_name(String item_name) {
      this.item_name = item_name;
    }

    public Object getRelax_clock_type() {
      return relax_clock_type;
    }

    public void setRelax_clock_type(Object relax_clock_type) {
      this.relax_clock_type = relax_clock_type;
    }

    public int getItem_count_qty() {
      return item_count_qty;
    }

    public void setItem_count_qty(int item_count_qty) {
      this.item_count_qty = item_count_qty;
    }

    public int getItem_price_sum() {
      return item_price_sum;
    }

    public void setItem_price_sum(int item_price_sum) {
      this.item_price_sum = item_price_sum;
    }

    public Object getFacilityNo() {
      return FacilityNo;
    }

    public void setFacilityNo(Object FacilityNo) {
      this.FacilityNo = FacilityNo;
    }

    public Object getWaitBeginTime() {
      return WaitBeginTime;
    }

    public void setWaitBeginTime(Object WaitBeginTime) {
      this.WaitBeginTime = WaitBeginTime;
    }

    public List<ChildrenBean> getChildren() {
      return children;
    }

    public void setChildren(List<ChildrenBean> children) {
      this.children = children;
    }

    boolean isOpen = false;

    @Override
    public boolean isOpen() {
      return isOpen;
    }

    public void changeOpen() {
      this.isOpen = !isOpen();
    }

    public String getNum() {
      if (children != null) {
        int sum = 0;
        for (ChildrenBean child : children) {
          sum += child.getItem_count_qty();
        }
        return sum + "";
      }
      return "";
    }

    public String getAll() {
      if (children != null) {
        int sum = 0;
        for (ChildrenBean child : children) {
          sum +=  child.getItem_price_sum();
        }
        return sum + "";
      }
      return "";
    }

    public static class ChildrenBean implements Serializable {
      /**
       * hotel_date : null
       * item_name : 点钟
       * relax_clock_type : null
       * item_count_qty : 1
       * item_price_sum : 0
       * FacilityNo : 8897
       * WaitBeginTime : 2020/10/26 10:48:00
       * children : null
       */

      private Object hotel_date;
      private String item_name;
      private Object relax_clock_type;
      private int item_count_qty;
      private int item_price_sum;
      private String FacilityNo;
      private String WaitBeginTime;
      private Object children;

      public Object getHotel_date() {
        return hotel_date;
      }

      public void setHotel_date(Object hotel_date) {
        this.hotel_date = hotel_date;
      }

      public String getItem_name() {
        return item_name;
      }

      public void setItem_name(String item_name) {
        this.item_name = item_name;
      }

      public Object getRelax_clock_type() {
        return relax_clock_type;
      }

      public void setRelax_clock_type(Object relax_clock_type) {
        this.relax_clock_type = relax_clock_type;
      }

      public int getItem_count_qty() {
        return item_count_qty;
      }

      public void setItem_count_qty(int item_count_qty) {
        this.item_count_qty = item_count_qty;
      }

      public int getItem_price_sum() {
        return item_price_sum;
      }

      public void setItem_price_sum(int item_price_sum) {
        this.item_price_sum = item_price_sum;
      }

      public String getFacilityNo() {
        return FacilityNo;
      }

      public void setFacilityNo(String FacilityNo) {
        this.FacilityNo = FacilityNo;
      }

      public String getWaitBeginTime() {
        return WaitBeginTime;
      }

      public void setWaitBeginTime(String WaitBeginTime) {
        this.WaitBeginTime = WaitBeginTime;
      }

      public Object getChildren() {
        return children;
      }

      public void setChildren(Object children) {
        this.children = children;
      }
    }
  }
}
