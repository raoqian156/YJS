package com.yskrq.common.bean;

import com.yskrq.net_library.BaseBean;

import java.util.List;

public class LoginBean extends BaseBean {
    /**
     * serverip : hotel17.yskvip.com
     * userid : YSK226725
     * username : 云上客产品测试
     * apptoken : 12F54685952BD578A93AC850B14EF8F9
     * shopsid :
     * groupid : null
     * IsAdministrator : 1
     * User_value : []
     * Value : null
     * Danshu : null
     * YingYee : null
     * Renshu : null
     * UserRight_value : []
     */

    private String serverip;
    private String userid;
    private String username;
    private String apptoken;
    private String shopsid;
    private String groupid;
    private String IsAdministrator;
    private Object Danshu;
    private Object YingYee;
    private Object Renshu;
    private List<?> User_value;
    private List<?> UserRight_value;

    public String getServerip() {
        return serverip;
    }

    public void setServerip(String serverip) {
        this.serverip = serverip;
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

    public String getApptoken() {
        return apptoken;
    }

    public void setApptoken(String apptoken) {
        this.apptoken = apptoken;
    }

    public String getShopsid() {
        return shopsid;
    }

    public void setShopsid(String shopsid) {
        this.shopsid = shopsid;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public String getIsAdministrator() {
        return IsAdministrator;
    }

    public void setIsAdministrator(String IsAdministrator) {
        this.IsAdministrator = IsAdministrator;
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

    public List<?> getUser_value() {
        return User_value;
    }

    public void setUser_value(List<?> User_value) {
        this.User_value = User_value;
    }

    public List<?> getUserRight_value() {
        return UserRight_value;
    }

    public void setUserRight_value(List<?> UserRight_value) {
        this.UserRight_value = UserRight_value;
    }
}
