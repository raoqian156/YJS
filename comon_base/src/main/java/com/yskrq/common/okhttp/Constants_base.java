package com.yskrq.common.okhttp;


public class Constants_base {
    //"http://19a744739c.iok.la:29524"; "http://www.hnhaozhen.com" :
    //    public static final String url = isDebug() ? "http://19a744739c.iok.la:29524" : "http://qddyzyym.jbtzl.com";

    /*************网络请求************/
    public static class TransCode {

        // 登录
        public static final String LOGIN_Luo = "https://hotel16.yskvip.com:9092/User/Login";
        public static final String UPDATE_CHECK_Liu = "User/getLatestVersion";
        public static final String SELECT_LOGIN_Liu = "User/SelectLogin";
        public static final String SELECT_FOOT_Liu = "CA_Restaurant/selectFootBath";
        public static final String wirtelog = "https://hotel16.yskvip.com:9092/RM_Others/wirtelog";
        public static final String getTechNo = "TPC_AddItem/getTechNo";

        public static final String GET_SALE_DATE = "CA_Restaurant/SelectHotelDate";
        public static final String GET_TEC_COLOR_STATUS = "RM_Others/ShowBrandStatusColor";
    }
}
