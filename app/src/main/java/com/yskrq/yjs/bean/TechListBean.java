package com.yskrq.yjs.bean;

import com.yskrq.net_library.BaseBean;

import java.util.List;

public class TechListBean extends BaseBean {

  private List<ValueBean> value;

  public List<ValueBean> getValue() {
    return value;
  }

  public void setValue(List<ValueBean> value) {
    this.value = value;
  }

  public static class ValueBean {
    /**
     * brandno : 0000
     * description : 00
     * sexid : 0
     * imageid1 :
     * imageid2 : null
     * istatus : null
     * status : 未休息
     * profitcenter : 000202
     * ijobstatus : null
     * jobstatus : 在上班中(侯钟)
     * destinestatus :
     * destineno :
     * chambercode : 0000
     * facilitynoname :
     * technician :
     * groupid :
     * itemno :
     * itemname :
     * brandnotype : 0000
     * expendtimeid1 :
     * relaxclocktype : null
     * relaxclockname :
     * keepwatch :
     * stimes :
     * holidayType : null
     * sbrandnotype : 00000000
     * waitclock : null
     * shiftname : null
     */

    private String brandno;
    private String description;
    private String sexid;
    private String imageid1;
    private Object imageid2;
    private Object istatus;
    private String status;
    private String profitcenter;
    private Object ijobstatus;
    private String jobstatus;
    private String destinestatus;
    private String destineno;
    private String chambercode;
    private String facilitynoname;
    private String technician;
    private String groupid;
    private String itemno;
    private String itemname;
    private String brandnotype;
    private String expendtimeid1;
    private Object relaxclocktype;
    private String relaxclockname;
    private String keepwatch;
    private String stimes;
    private Object holidayType;
    private String sbrandnotype;
    private Object waitclock;
    private Object shiftname;
    private boolean isSelect;

    public boolean isSelect() {
      return isSelect;
    }

    public void setSelect(boolean select) {
      isSelect = select;
    }

    public String getBrandno() {
      return brandno;
    }

    public void setBrandno(String brandno) {
      this.brandno = brandno;
    }

    public String getDescription() {
      return description;
    }

    public void setDescription(String description) {
      this.description = description;
    }

    public String getSexid() {
      return sexid;
    }

    public void setSexid(String sexid) {
      this.sexid = sexid;
    }

    public String getImageid1() {
      return imageid1;
    }

    public void setImageid1(String imageid1) {
      this.imageid1 = imageid1;
    }

    public Object getImageid2() {
      return imageid2;
    }

    public void setImageid2(Object imageid2) {
      this.imageid2 = imageid2;
    }

    public Object getIstatus() {
      return istatus;
    }

    public void setIstatus(Object istatus) {
      this.istatus = istatus;
    }

    public String getStatus() {
      return status;
    }

    public void setStatus(String status) {
      this.status = status;
    }

    public String getProfitcenter() {
      return profitcenter;
    }

    public void setProfitcenter(String profitcenter) {
      this.profitcenter = profitcenter;
    }

    public Object getIjobstatus() {
      return ijobstatus;
    }

    public void setIjobstatus(Object ijobstatus) {
      this.ijobstatus = ijobstatus;
    }

    public String getJobstatus() {
      return jobstatus;
    }

    public void setJobstatus(String jobstatus) {
      this.jobstatus = jobstatus;
    }

    public String getDestinestatus() {
      return destinestatus;
    }

    public void setDestinestatus(String destinestatus) {
      this.destinestatus = destinestatus;
    }

    public String getDestineno() {
      return destineno;
    }

    public void setDestineno(String destineno) {
      this.destineno = destineno;
    }

    public String getChambercode() {
      return chambercode;
    }

    public void setChambercode(String chambercode) {
      this.chambercode = chambercode;
    }

    public String getFacilitynoname() {
      return facilitynoname;
    }

    public void setFacilitynoname(String facilitynoname) {
      this.facilitynoname = facilitynoname;
    }

    public String getTechnician() {
      return technician;
    }

    public void setTechnician(String technician) {
      this.technician = technician;
    }

    public String getGroupid() {
      return groupid;
    }

    public void setGroupid(String groupid) {
      this.groupid = groupid;
    }

    public String getItemno() {
      return itemno;
    }

    public void setItemno(String itemno) {
      this.itemno = itemno;
    }

    public String getItemname() {
      return itemname;
    }

    public void setItemname(String itemname) {
      this.itemname = itemname;
    }

    public String getBrandnotype() {
      return brandnotype;
    }

    public void setBrandnotype(String brandnotype) {
      this.brandnotype = brandnotype;
    }

    public String getExpendtimeid1() {
      return expendtimeid1;
    }

    public void setExpendtimeid1(String expendtimeid1) {
      this.expendtimeid1 = expendtimeid1;
    }

    public Object getRelaxclocktype() {
      return relaxclocktype;
    }

    public void setRelaxclocktype(Object relaxclocktype) {
      this.relaxclocktype = relaxclocktype;
    }

    public String getRelaxclockname() {
      return relaxclockname;
    }

    public void setRelaxclockname(String relaxclockname) {
      this.relaxclockname = relaxclockname;
    }

    public String getKeepwatch() {
      return keepwatch;
    }

    public void setKeepwatch(String keepwatch) {
      this.keepwatch = keepwatch;
    }

    public String getStimes() {
      return stimes;
    }

    public void setStimes(String stimes) {
      this.stimes = stimes;
    }

    public Object getHolidayType() {
      return holidayType;
    }

    public void setHolidayType(Object holidayType) {
      this.holidayType = holidayType;
    }

    public String getSbrandnotype() {
      return sbrandnotype;
    }

    public void setSbrandnotype(String sbrandnotype) {
      this.sbrandnotype = sbrandnotype;
    }

    public Object getWaitclock() {
      return waitclock;
    }

    public void setWaitclock(Object waitclock) {
      this.waitclock = waitclock;
    }

    public Object getShiftname() {
      return shiftname;
    }

    public void setShiftname(Object shiftname) {
      this.shiftname = shiftname;
    }
  }
}
