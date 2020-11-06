package com.yskrq.yjs.bean;

import com.yskrq.net_library.BaseBean;

import java.util.List;

public class ListParamBean extends BaseBean {
  private List<ValueBean> value;

  public List<ValueBean> getValue() {
    return value;
  }

  public void setValue(List<ValueBean> value) {
    this.value = value;
  }

  public static class ValueBean {
    /**
     * section : RelaxGuest
     * entry : bUseCloudTech
     * data : 1
     * helpinfo : 1启用，0不启用
     * label : 门店是否启用云技师功能
     */

    private String section;
    private String entry;
    private String data;
    private String helpinfo;
    private String label;

    public String getSection() {
      return section;
    }

    public void setSection(String section) {
      this.section = section;
    }

    public String getEntry() {
      return entry;
    }

    public void setEntry(String entry) {
      this.entry = entry;
    }

    public String getData() {
      return data;
    }

    public void setData(String data) {
      this.data = data;
    }

    public String getHelpinfo() {
      return helpinfo;
    }

    public void setHelpinfo(String helpinfo) {
      this.helpinfo = helpinfo;
    }

    public String getLabel() {
      return label;
    }

    public void setLabel(String label) {
      this.label = label;
    }
  }
}
