package com.yskrq.common.bean;

import java.util.List;

public class LoginBean2 {
    /**
     * error : 0
     * comment : 查询成功
     * serverip : null
     * userid : null
     * username : null
     * apptoken : null
     * shopsid : null
     * groupid : null
     * IsAdministrator : null
     * User_value : [{"ShopsID":"226725","ShopsName":"云上客产品测试","GroupId":"J00936"}]
     * Value : null
     * Danshu : null
     * YingYee : null
     * Renshu : null
     * UserRight_value : []
     */

    private String error;
    private String comment;
    private Object serverip;
    private Object userid;
    private Object username;
    private Object apptoken;
    private Object shopsid;
    private Object groupid;
    private Object IsAdministrator;
    private Object Value;
    private Object Danshu;
    private Object YingYee;
    private Object Renshu;
    private List<UserValueBean> User_value;
    private List<?> UserRight_value;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Object getServerip() {
        return serverip;
    }

    public void setServerip(Object serverip) {
        this.serverip = serverip;
    }

    public Object getUserid() {
        return userid;
    }

    public void setUserid(Object userid) {
        this.userid = userid;
    }

    public Object getUsername() {
        return username;
    }

    public void setUsername(Object username) {
        this.username = username;
    }

    public Object getApptoken() {
        return apptoken;
    }

    public void setApptoken(Object apptoken) {
        this.apptoken = apptoken;
    }

    public Object getShopsid() {
        return shopsid;
    }

    public void setShopsid(Object shopsid) {
        this.shopsid = shopsid;
    }

    public Object getGroupid() {
        return groupid;
    }

    public void setGroupid(Object groupid) {
        this.groupid = groupid;
    }

    public Object getIsAdministrator() {
        return IsAdministrator;
    }

    public void setIsAdministrator(Object IsAdministrator) {
        this.IsAdministrator = IsAdministrator;
    }

    public Object getValue() {
        return Value;
    }

    public void setValue(Object Value) {
        this.Value = Value;
    }

    public Object getDanshu() {
        return Danshu;
    }

    public void setDanshu(Object Danshu) {
        this.Danshu = Danshu;
    }

    public Object getYingYee() {
        return YingYee;
    }

    public void setYingYee(Object YingYee) {
        this.YingYee = YingYee;
    }

    public Object getRenshu() {
        return Renshu;
    }

    public void setRenshu(Object Renshu) {
        this.Renshu = Renshu;
    }

    public List<UserValueBean> getUser_value() {
        return User_value;
    }

    public void setUser_value(List<UserValueBean> User_value) {
        this.User_value = User_value;
    }

    public List<?> getUserRight_value() {
        return UserRight_value;
    }

    public void setUserRight_value(List<?> UserRight_value) {
        this.UserRight_value = UserRight_value;
    }

    public static class UserValueBean {
        /**
         * ShopsID : 226725
         * ShopsName : 云上客产品测试
         * GroupId : J00936
         */

        private String ShopsID;
        private String ShopsName;
        private String GroupId;

        public String getShopsID() {
            return ShopsID;
        }

        public void setShopsID(String ShopsID) {
            this.ShopsID = ShopsID;
        }

        public String getShopsName() {
            return ShopsName;
        }

        public void setShopsName(String ShopsName) {
            this.ShopsName = ShopsName;
        }

        public String getGroupId() {
            return GroupId;
        }

        public void setGroupId(String GroupId) {
            this.GroupId = GroupId;
        }
    }
}
