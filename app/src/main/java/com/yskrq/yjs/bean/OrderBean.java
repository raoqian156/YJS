package com.yskrq.yjs.bean;

import com.google.gson.annotations.SerializedName;
import com.yskrq.net_library.BaseBean;

import java.util.List;

public class OrderBean extends BaseBean {


  private List<ValueBean> value;

  public List<ValueBean> getValue() {
    return value;
  }

  public void setValue(List<ValueBean> value) {
    this.value = value;
  }

  public static class ValueBean {
    /**
     * account : B000000959
     * seqnum : 1
     * userid :
     * username :
     * opid : YSK226691
     * blltype : S
     * blltypename : 前台预约
     * createdate : 2020/10/21 15:19:59
     * status : C
     * cashiertype :
     * mobilephone :
     * resername :
     * comment : 夜审系统取消
     * covers :
     * debitamount :
     * relaxcode : 666
     * relaxname : 666
     * itemno :
     * facilityno :
     * itemname :
     * arrivaltime : 15:19
     * profitcenter : 000202
     * hoteldate : 2020-10-21
     * canceldate :
     * cancelopid :
     */

    private String account;
    private String seqnum;
    private String userid;
    private String username;
    private String opid;
    private String blltype;
    private String blltypename;
    private String createdate;
    private String status;
    private String cashiertype;
    private String mobilephone;
    private String resername;
    @SerializedName("comment")
    private String commentX;
    private String covers;
    private String debitamount;
    private String relaxcode;
    private String relaxname;
    private String itemno;
    private String facilityno;
    private String itemname;
    private String arrivaltime;
    private String profitcenter;
    private String hoteldate;
    private String canceldate;
    private String cancelopid;

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

    public String getUserid() {
      return userid;
    }

    public void setUserid(String userid) {
      this.userid = userid;
    }

    public String getUsername() {
      return username;
    }

    public void setUsername(String username) {
      this.username = username;
    }

    public String getOpid() {
      return opid;
    }

    public void setOpid(String opid) {
      this.opid = opid;
    }

    public String getBlltype() {
      return blltype;
    }

    public void setBlltype(String blltype) {
      this.blltype = blltype;
    }

    public String getBlltypename() {
      return blltypename;
    }

    public void setBlltypename(String blltypename) {
      this.blltypename = blltypename;
    }

    public String getCreatedate() {
      return createdate;
    }

    public void setCreatedate(String createdate) {
      this.createdate = createdate;
    }

    public String getStatus() {
      return status;
    }

    public void setStatus(String status) {
      this.status = status;
    }

    public String getCashiertype() {
      return cashiertype;
    }

    public void setCashiertype(String cashiertype) {
      this.cashiertype = cashiertype;
    }

    public String getMobilephone() {
      return mobilephone;
    }

    public void setMobilephone(String mobilephone) {
      this.mobilephone = mobilephone;
    }

    public String getResername() {
      return resername;
    }

    public void setResername(String resername) {
      this.resername = resername;
    }

    public String getCommentX() {
      return commentX;
    }

    public void setCommentX(String commentX) {
      this.commentX = commentX;
    }

    public String getCovers() {
      return covers;
    }

    public void setCovers(String covers) {
      this.covers = covers;
    }

    public String getDebitamount() {
      return debitamount;
    }

    public void setDebitamount(String debitamount) {
      this.debitamount = debitamount;
    }

    public String getRelaxcode() {
      return relaxcode;
    }

    public void setRelaxcode(String relaxcode) {
      this.relaxcode = relaxcode;
    }

    public String getRelaxname() {
      return relaxname;
    }

    public void setRelaxname(String relaxname) {
      this.relaxname = relaxname;
    }

    public String getItemno() {
      return itemno;
    }

    public void setItemno(String itemno) {
      this.itemno = itemno;
    }

    public String getFacilityno() {
      return facilityno;
    }

    public void setFacilityno(String facilityno) {
      this.facilityno = facilityno;
    }

    public String getItemname() {
      return itemname;
    }

    public void setItemname(String itemname) {
      this.itemname = itemname;
    }

    public String getArrivaltime() {
      return arrivaltime;
    }

    public void setArrivaltime(String arrivaltime) {
      this.arrivaltime = arrivaltime;
    }

    public String getProfitcenter() {
      return profitcenter;
    }

    public void setProfitcenter(String profitcenter) {
      this.profitcenter = profitcenter;
    }

    public String getHoteldate() {
      return hoteldate;
    }

    public void setHoteldate(String hoteldate) {
      this.hoteldate = hoteldate;
    }

    public String getCanceldate() {
      return canceldate;
    }

    public void setCanceldate(String canceldate) {
      this.canceldate = canceldate;
    }

    public String getCancelopid() {
      return cancelopid;
    }

    public void setCancelopid(String cancelopid) {
      this.cancelopid = cancelopid;
    }
  }
}
