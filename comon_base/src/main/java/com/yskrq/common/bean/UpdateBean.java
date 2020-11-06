package com.yskrq.common.bean;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yskrq.net_library.BaseBean;

import java.lang.reflect.Type;
import java.util.List;

public class UpdateBean extends BaseBean {

    /**
     * Value : [{"APPVersion":"1.0","UpdateServer":"https://yskvipup1.oss-cn-shenzhen.aliyuncs.com/APPDOWN/%E7%BE%8E%E9%A3%9F%E5%B9%BF%E5%9C%BA.apk","APPDescription":"美食广场","sRemark":"\u003cdiv\u003e升级测试\u003c/div\u003e","bForceUpdating":false}]
     */


    public ValueBean getUpdate() {
        if (TextUtils.isEmpty(Value)) return null;
        try {
            Type type = new TypeToken<List<ValueBean>>() {}.getType();
            List<ValueBean> list = new Gson().fromJson(Value, type);
            return list.get(0);
        } catch (Exception e) {
            return null;
        }
    }
    private String Value;

    public static class ValueBean {

        /**
         * APPVersion : 1.0
         * UpdateServer : https://yskvipup1.oss-cn-shenzhen.aliyuncs.com/APPDOWN/%E7%BE%8E%E9%A3%9F%E5%B9%BF%E5%9C%BA.apk
         * APPDescription : 美食广场
         * sRemark : <div>升级测试</div>
         * bForceUpdating : false
         */

        private String APPVersion;
        private String UpdateServer;
        private String APPDescription;
        private String sRemark="";
        private boolean bForceUpdating;

        public String getAPPVersion() {
            return APPVersion;
        }

        public void setAPPVersion(String APPVersion) {
            this.APPVersion = APPVersion;
        }

        public String getUpdateServer() {
            return UpdateServer;
        }

        public void setUpdateServer(String UpdateServer) {
            this.UpdateServer = UpdateServer;
        }

        public String getAPPDescription() {
            return APPDescription;
        }

        public void setAPPDescription(String APPDescription) {
            this.APPDescription = APPDescription;
        }

        public String getSRemark() {
            return sRemark;
        }

        public void setSRemark(String sRemark) {
            this.sRemark = sRemark;
        }

        public boolean isBForceUpdating() {
            return bForceUpdating;
        }

        public void setBForceUpdating(boolean bForceUpdating) {
            this.bForceUpdating = bForceUpdating;
        }
    }
}
