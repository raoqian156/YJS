package com.yskrq.yjs.bean;

import com.yskrq.net_library.BaseBean;

import java.util.List;

public class RoomProjectBean extends BaseBean {

  private List<ValueBean> value;

  public List<ValueBean> getValue() {
    return value;
  }

  public void setValue(List<ValueBean> value) {
    this.value = value;
  }

  public static class ValueBean {
    /**
     * itemno : 000
     * name : 播报测试
     */

    private String itemno;
    private String name;

    public String getItemno() {
      return itemno;
    }

    public void setItemno(String itemno) {
      this.itemno = itemno;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }
  }
}
