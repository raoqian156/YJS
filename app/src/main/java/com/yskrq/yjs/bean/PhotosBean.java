package com.yskrq.yjs.bean;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yskrq.net_library.BaseBean;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PhotosBean extends BaseBean {

  /**
   * error : 0
   * Value : [{"shopsid":226691,"TechNo":"666","OrderId":7,"Image":"/Content/Images/000202/Tech226691/666/20201028104540710797New.jpg"},{"shopsid":226691,"TechNo":"666","OrderId":6,"Image":"/Content/Images/000202/Tech226691/666/20201028104525954102New.jpg"},{"shopsid":226691,"TechNo":"666","OrderId":5,"Image":"/Content/Images/000202/Tech226691/666/20201028104517783768New.jpg"},{"shopsid":226691,"TechNo":"666","OrderId":4,"Image":"/Content/Images/000202/Tech226691/666/20201028104507346700New.jpg"},{"shopsid":226691,"TechNo":"666","OrderId":3,"Image":"/Content/Images/000202/Tech226691/666/20201028104459046341New.jpg"},{"shopsid":226691,"TechNo":"666","OrderId":2,"Image":"/Content/Images/000202/Tech226691/666/20201028104448992514New.jpg"},{"shopsid":226691,"TechNo":"666","OrderId":1,"Image":"/Content/Images/000202/Tech226691/666/20201028104353615243New.jpg"}]
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

  public static class ValueBean {

    /**
     * shopsid : 226691
     * TechNo : 666
     * OrderId : 7
     * Image : /Content/Images/000202/Tech226691/666/20201028104540710797New.jpg
     */

    private int shopsid;
    private String TechNo;
    private int OrderId;
    private String Image;

    public int getShopsid() {
      return shopsid;
    }

    public void setShopsid(int shopsid) {
      this.shopsid = shopsid;
    }

    public String getTechNo() {
      return TechNo;
    }

    public void setTechNo(String TechNo) {
      this.TechNo = TechNo;
    }

    public int getOrderId() {
      return OrderId;
    }

    public void setOrderId(int OrderId) {
      this.OrderId = OrderId;
    }

    public String getImage() {
      return Image;
    }

    public void setImage(String Image) {
      this.Image = Image;
    }
  }
}
