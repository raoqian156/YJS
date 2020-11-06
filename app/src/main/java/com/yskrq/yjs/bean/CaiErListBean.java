package com.yskrq.yjs.bean;

import com.yskrq.net_library.BaseBean;

import java.util.List;

public class CaiErListBean extends BaseBean {
  private List<FixServiceItemListViewValueBean> Fix_ServiceItemListView_value;

  public List<FixServiceItemListViewValueBean> getValue() {
    return Fix_ServiceItemListView_value;
  }

  public void setFix_ServiceItemListView_value(
      List<FixServiceItemListViewValueBean> Fix_ServiceItemListView_value) {
    this.Fix_ServiceItemListView_value = Fix_ServiceItemListView_value;
  }

  public static class FixServiceItemListViewValueBean {
    /**
     * sid : 1050
     * facilityno : 8889
     * facilityname : 测试二号
     * account : L000003579
     * seqnum : 1
     * itemno : 000
     * name : 播报测试
     * expendtime : 11316分
     */

    private String sid;
    private String facilityno;
    private String facilityname;
    private String account;
    private String seqnum;
    private String itemno;
    private String name;
    private String expendtime;

    public String getSid() {
      return sid;
    }

    public void setSid(String sid) {
      this.sid = sid;
    }

    public String getFacilityno() {
      return facilityno;
    }

    public void setFacilityno(String facilityno) {
      this.facilityno = facilityno;
    }

    public String getFacilityname() {
      return facilityname;
    }

    public void setFacilityname(String facilityname) {
      this.facilityname = facilityname;
    }

    public String getAccount() {
      return account;
    }

    public void setAccount(String account) {
      this.account = account;
    }

    public String getSeqnum() {
      return seqnum;
    }

    public void setSeqnum(String seqnum) {
      this.seqnum = seqnum;
    }

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

    public String getExpendtime() {
      return expendtime;
    }

    public void setExpendtime(String expendtime) {
      this.expendtime = expendtime;
    }
  }
}
