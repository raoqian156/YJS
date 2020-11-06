package com.yskrq.yjs.bean;

import com.yskrq.net_library.BaseBean;

import java.util.List;

public class QuerryBean extends BaseBean {
    /**
     * error : 0
     * comment : 获取成功
     * value : [{"account":"L000000506","seqnum":"1","indexnumber":"1797","facilityno":"Z666","facilityname":"Z666","itemno":"101","itemname":"白沙漫步","itemprice":"188.0000","unit":"45","relaxclocktype":"1","relaxclockname":"点钟","occurtime":"2020/8/6 9:41:36","expendtime":"3","groupid":"9002","sid":"4008","stime":"01:06:48","countdowns":"0","rsuhtimes":"0","uploadstatus":"0"},{"account":"L000000507","seqnum":"1","indexnumber":"1798","facilityno":"V13","facilityname":"V13","itemno":"102","itemname":"云海境地","itemprice":"22.0000","unit":"26","relaxclocktype":"1","relaxclockname":"点钟","occurtime":"2020/8/6 9:44:49","expendtime":"0","groupid":"9000","sid":"","stime":"","countdowns":"0","rsuhtimes":"0","uploadstatus":"0"}]
     */

    private List<ValueBean> value;

    public List<ValueBean> getValue() {
        return value;
    }

    public void setValue(List<ValueBean> value) {
        this.value = value;
    }

    public static class ValueBean {
        /**
         * account : L000000506
         * seqnum : 1
         * indexnumber : 1797
         * facilityno : Z666
         * facilityname : Z666
         * itemno : 101
         * itemname : 白沙漫步
         * itemprice : 188.0000
         * unit : 45
         * relaxclocktype : 1
         * relaxclockname : 点钟
         * occurtime : 2020/8/6 9:41:36
         * expendtime : 3
         * groupid : 9002
         * sid : 4008
         * stime : 01:06:48
         * countdowns : 0
         * rsuhtimes : 0
         * uploadstatus : 0
         */

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

        @Override
        public String toString() {
            return "ValueBean{" +
                    "account='" + account + '\'' +
                    ", seqnum='" + seqnum + '\'' +
                    ", indexnumber='" + indexnumber + '\'' +
                    ", facilityno='" + facilityno + '\'' +
                    ", facilityname='" + facilityname + '\'' +
                    ", itemno='" + itemno + '\'' +
                    ", itemname='" + itemname + '\'' +
                    ", itemprice='" + itemprice + '\'' +
                    ", unit='" + unit + '\'' +
                    ", relaxclocktype='" + relaxclocktype + '\'' +
                    ", relaxclockname='" + relaxclockname + '\'' +
                    ", occurtime='" + occurtime + '\'' +
                    ", expendtime='" + expendtime + '\'' +
                    ", groupid='" + groupid + '\'' +
                    ", sid='" + sid + '\'' +
                    ", stime='" + stime + '\'' +
                    ", countdowns='" + countdowns + '\'' +
                    ", rsuhtimes='" + rsuhtimes + '\'' +
                    ", uploadstatus='" + uploadstatus + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "QuerryBean{" +
                "value=" + ((value != null && value.size() > 0) ? value.get(0).toString() : "") +
                '}';
    }
}
