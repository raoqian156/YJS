package com.yskrq.common.bean;

import com.yskrq.common.util.LOG;
import com.yskrq.net_library.BaseBean;

import java.io.Serializable;
import java.util.List;

public class RelaxListBean extends BaseBean {
  private List<ValueBean> value;

  public List<ValueBean> getValue() {
    return value;
  }

  public void setValue(List<ValueBean> value) {
    this.value = value;
  }

  public static class ValueBean implements Serializable {
    /**
     * account : L000003568
     * seqnum : 1
     * indexnumber : 6302
     * facilityno : 8889
     * facilityname : 测试二号
     * itemno : 100
     * itemname : 保健足疗
     * itemprice : 100.0000
     * unit : 01
     * relaxclocktype : 1
     * relaxclockname : 点钟
     * occurtime : 2020/10/26 9:02:16
     * expendtime : 51
     * groupid : 9002
     * sid : 2309
     * stime : 00:38:28
     * countdowns : 0
     * rsuhtimes : 0
     * uploadstatus : 1
     */

    //      account:L000026282         |    新任务    |   催钟    |  到钟  |
    //      account:L000026282         |     ***     |    ***    |  ***  |
    //      expendtime:0              |              |           |       |
    //      groupid:9002              |   9002       |    9001   |  9001 |
    //      sid:5392                  |              |   0~300   |  <= 0 |
    //      countdowns:0              |              |    < 1    |       |
    //      rsuhtimes:0               |              |           |  < 2  |
    //      uploadstatus:0            |     0        |           |       |
    //      stime:01:29:52                |
    //      seqnum:1                       |
    //      indexnumber:8567               |
    //      facilityno:8892                |
    //      facilityname:c1                |
    //      itemno:100                     |
    //      itemname:保健足疗12345678       |
    //      itemprice:100.0000             |
    //      unit:01                        |
    //      relaxclocktype:1               |
    //      relaxclockname:点钟            |
    //      occurtime:2020/12/28 16:30:47  |

    private String account;
    private String seqnum;
    private String indexnumber;
    private String facilityno;
    private String facilityname;
    private String itemno;
    private String itemname;
    private String itemprice;
    private String unit;
    private String relaxclocktype;
    private String relaxclockname;
    private String occurtime;
    private String expendtime;
    private String groupid;
    private String sid;
    private String stime;
    private String countdowns;
    private String rsuhtimes;
    private String uploadstatus;

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

    public String getIndexnumber() {
      return indexnumber;
    }

    public void setIndexnumber(String indexnumber) {
      this.indexnumber = indexnumber;
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

    public String getItemprice() {
      return itemprice;
    }

    public void setItemprice(String itemprice) {
      this.itemprice = itemprice;
    }

    public String getUnit() {
      return unit;
    }

    public void setUnit(String unit) {
      this.unit = unit;
    }

    public String getRelaxclocktype() {
      return relaxclocktype;
    }

    public void setRelaxclocktype(String relaxclocktype) {
      this.relaxclocktype = relaxclocktype;
    }

    public String getRelaxclockname() {
      return relaxclockname;
    }

    public void setRelaxclockname(String relaxclockname) {
      this.relaxclockname = relaxclockname;
    }

    public String getOccurtime() {
      return occurtime;
    }

    public void setOccurtime(String occurtime) {
      this.occurtime = occurtime;
    }

    public String getExpendtime() {
      return expendtime;
    }

    public void setExpendtime(String expendtime) {
      this.expendtime = expendtime;
    }

    public String getGroupid() {
      return groupid;
    }

    public int getShowStatus() {// 0-不能打卡  1-代打卡   2-已打卡 3-已下钟
      int tag = 0;
      if ("9000".equals(getGroupid())) {//未安排 - 不能打卡
        tag = 0;
      } else if ("9001".equals(getGroupid())) {//已打卡
        tag = 2;
      } else if ("9002".equals(getGroupid())) {//待打卡
        tag = 1;
      } else if ("9003".equals(getGroupid())) {//3-已下钟
        tag = 3;
      }
      return tag;
    }


    public void setGroupid(String groupid) {
      this.groupid = groupid;
    }

    public String getSid() {
      return sid;
    }

    public void setSid(String sid) {
      this.sid = sid;
    }

    public String getStime() {
      return stime;
    }

    public void setStime(String stime) {
      this.stime = stime;
    }

    public int getCountdownInt() {
      try {
        return Integer.parseInt(countdowns);
      } catch (Exception e) {
        return 0;
      }
    }

    public String getCountdowns() {
      return countdowns;
    }

    public void setCountdowns(String countdowns) {
      this.countdowns = countdowns;
    }

    public String getRsuhtimes() {
      return rsuhtimes;
    }

    public void setRsuhtimes(String rsuhtimes) {
      this.rsuhtimes = rsuhtimes;
    }

    public String getUploadstatus() {
      return uploadstatus;
    }

    public void setUploadstatus(String uploadstatus) {
      this.uploadstatus = uploadstatus;
    }

    public boolean cuizhong(int minutes) {
      boolean isCui = false;
      try {
        isCui = Integer.parseInt(countdowns) < 1 && Integer.parseInt(sid) > 0 && Integer
            .parseInt(sid) <= minutes * 60 && getShowStatus() == 2;
      } catch (Exception e) {
      }
      LOG.e("RelaxListBean", isCui + ".cuizhong.259:" + minutes);
      return isCui;
    }

    public boolean daozhong() {
      boolean isDao = false;
      try {
        isDao = Integer.parseInt(rsuhtimes) < 2 && Integer
            .parseInt(sid) <= 0 && getShowStatus() == 2;
      } catch (Exception e) {
      }
      LOG.e("RelaxListBean", isDao + ".cuizhong.277:");
      return isDao;
    }

    public boolean hasNewTask() {
      return "0".equals(getUploadstatus()) && getShowStatus() == 1;
    }

    public boolean needCloseVoice() {
      return "9001".equals(getGroupid());
    }

    @Override
    public String toString() {
      return "ValueBean{" + "account='" + account + '\'' + ", seqnum='" + seqnum + '\'' + ", facilityno='" + facilityno + '\'' + ", relaxclocktype='" + relaxclocktype + '\'' + ", relaxclockname='" + relaxclockname + '\'' + ", occurtime='" + occurtime + '\'' + ", expendtime='" + expendtime + '\'' + ", groupid='" + groupid + '\'' + ", sid='" + sid + '\'' + ", stime='" + stime + '\'' + ", countdowns='" + countdowns + '\'' + ", rsuhtimes='" + rsuhtimes + '\'' + '}';
    }
  }
}
