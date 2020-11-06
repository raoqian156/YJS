package com.yskrq.net_library.okhttp;


/**
 * @author
 * @date 2018/8/3
 * @description 网络请求工具类
 */
public class RetrofitUtils {
//
//    public static ApiService apiService;
//
//    public static ApiService getApiService(Context context) {
//        if (apiService == null) {
//            synchronized (RetrofitUtils.class) {
//                apiService = initRetrofitConfig(context);
//            }
//        }
//        return apiService;
//    }
//
//    private static String headCookie;
//
//    private static ApiService initRetrofitConfig(final Context context) {
//        ClearableCookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context));
//
//        Interceptor errorInterceptor = new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) {
//                Request.Builder builder = chain.request().newBuilder();
////                if (!TextUtils.isEmpty(headCookie)) {
////                    builder.addHeader("set-cookie", headCookie);
////                    LOG.e("RetrofitUtils", "LINE:JSESSIONID " + headCookie);
////                } else {
////                    LOG.e("RetrofitUtils", "LINE:JSESSIONID EMPTY");
////                }
//
//                Response mResponse = null;
//                try {
//                    mResponse = chain.proceed(builder.build());
//                    String head = mResponse.header("set-cookie");
//                    LOG.e("RetrofitUtils", "head:" + head);
////                    if (!TextUtils.isEmpty(head)) {
////                        RetrofitUtils.headCookie = head;
////                    }
//                } catch (Exception e) {
//                    if (e instanceof UnknownHostException) {
////                        ToastUtil.show("Net Error");
//                    } else if (e instanceof ConnectException) {
////                        ToastUtil.show("Unknow Error");
//                    }
////                    CrashReport.postCatchedException(e);
//                }
//                return mResponse;
//            }
//        };
//        //配置超时时间以及拦截器
//        OkHttpClient.Builder builder = new OkHttpClient().newBuilder().connectTimeout(30, TimeUnit.SECONDS)
//                .cookieJar(cookieJar)
//                .readTimeout(30, TimeUnit.SECONDS)
//                .addInterceptor(new LogInterceptor())
//                .addInterceptor(errorInterceptor);
//        LOG.e("RetrofitUtils", "initRetrofitConfig.77:");
//        final OkHttpClient build = builder.build();
//
//        //配置Retrofit信息
//        Retrofit retrofit = new Retrofit.Builder()
//                .client(build)
//                .baseUrl("https://hotel16.yskvip.com")
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .build();
//        ApiService apiService = retrofit.create(ApiService.class);
//        return apiService;
//    }
}
