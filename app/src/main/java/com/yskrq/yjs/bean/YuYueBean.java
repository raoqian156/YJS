package com.yskrq.yjs.bean;

import com.yskrq.net_library.BaseBean;

public class YuYueBean extends BaseBean {

  /**
   * error : 0
   * Value : 技师003 正在房间:8889上钟,预计还需:7分钟下钟。
   */

  private String Value;

  public String getValue() {
    return Value;
  }

  public void setValue(String Value) {
    this.Value = Value;
  }
}
