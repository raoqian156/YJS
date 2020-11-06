package com.yskrq.yjs.bean;

import com.yskrq.net_library.BaseBean;

import java.util.List;

public class HavedRoomBean extends BaseBean {

  private List<ValueBean> value;

  public List<ValueBean> getValue() {
    return value;
  }

  public void setValue(List<ValueBean> value) {
    this.value = value;
  }

  public static class ValueBean {
    /**
     * facilityno : 8889
     * description : 测试二号
     * account : L000003579
     */

    private String facilityno;
    private String description;
    private String account;

    public String getFacilityno() {
      return facilityno;
    }

    public void setFacilityno(String facilityno) {
      this.facilityno = facilityno;
    }

    public String getDescription() {
      return description;
    }

    public void setDescription(String description) {
      this.description = description;
    }

    public String getAccount() {
      return account;
    }

    public void setAccount(String account) {
      this.account = account;
    }
  }
}
