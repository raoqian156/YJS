package com.yskrq.yjs.bean;

import com.yskrq.net_library.BaseBean;

import java.util.List;

public class ShouKaBean extends BaseBean {
  private List<ValueBean> value;

  public List<ValueBean> getValue() {
    return value;
  }

  public void setValue(List<ValueBean> value) {
    this.value = value;
  }

  public static class ValueBean {
    /**
     * TransCode : 619
     * TransName : 集团应收
     * CreditCZ : 38.00
     */

    private String TransCode;
    private String TransName;
    private String CreditCZ;

    public String getTransCode() {
      return TransCode;
    }

    public void setTransCode(String TransCode) {
      this.TransCode = TransCode;
    }

    public String getTransName() {
      return TransName;
    }

    public void setTransName(String TransName) {
      this.TransName = TransName;
    }

    public String getCreditCZ() {
      return CreditCZ;
    }

    public double getNum(){
      try {
        return Double.parseDouble(CreditCZ);
      }catch (Exception e){
        return 0;
      }
    }

    public void setCreditCZ(String CreditCZ) {
      this.CreditCZ = CreditCZ;
    }
  }
}
