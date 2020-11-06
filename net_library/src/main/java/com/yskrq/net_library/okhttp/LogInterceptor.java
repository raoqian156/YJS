package com.yskrq.net_library.okhttp;

/**
 * Created by raoqian on 2019/6/23.
 */

public class LogInterceptor {//implements Interceptor {
//  @Override
//  public Response intercept(Chain chain) throws IOException, NullPointerException {
//    Request request = chain.request();
//    long startTime = System.currentTimeMillis();
//    Response response = chain.proceed(chain.request());
//    long endTime = System.currentTimeMillis();
//    long duration = endTime - startTime;
//    okhttp3.MediaType mediaType = response.body().contentType();
//    String content = response.body().string();
//    String TAG = "";
//    String url = request.url().toString();
//    try {
//      TAG = TAG + url.substring(url.lastIndexOf("/") + 1);
//    } catch (Exception e) {
//
//    }
//    Log.d(TAG, "\n");
//    Log.d(TAG, "----------------Start.LogInterceptor----------------");
//    Log.e("BEAN." + TAG + ".POST.url", "=============<" + url + ">=============");
//    String method = request.method();
//    HashMap<String, String> param = new HashMap<>();
//    if ("POST".equals(method)) {
//      StringBuilder sb = new StringBuilder();
//      if (request.body() instanceof FormBody) {
//        FormBody body = (FormBody) request.body();
//        //                for (int i = 0; i < body.size(); i++) {
//        //                    param.put(body.)
//        //                }
//        //                if (!TextUtils.isEmpty(AESUtil.getKey())) {
//        //                    String parm = AESUtil.decrypt(body.value(0));
//        //                                    HashMap<String, String> maps = new Gson().fromJson(parm, HashMap.class);
//        //                    if (maps != null && maps.size() > 0) {
//        //                        for (Map.Entry<String, String> entry : maps.entrySet()) {
//        //                            sb.append(entry.getKey() + ":" + entry.getValue() + "<,");
//        //                        }
//        //                    }
//        //                } else {
//
//        for (int i = 0; i < body.size(); i++) {
//          param.put(body.name(i), body.value(i));
//          sb.append(body.name(i) + ":" + body.value(i) + "<,");
//        }
//        //                }
//        int start = Math.max(0, sb.length() - 1);
//        sb.delete(start, sb.length());
//        LOG.bean("BEAN." + TAG + ".POST", sb.toString(), url);
//      }
//
//    }
//    if (BuildConfig.SHOW_LOG) {
//      CrashHandler.getInstance().saveNETResponseInfo(TAG + ".OK_Http", param, content);
//    }
//    LOG.bean(TAG, content);
//    Log.d(TAG, "----------End:" + duration + "毫秒----------");
//    Log.d(TAG, "----------------End.LogInterceptor----------------");
//    return response.newBuilder().body(okhttp3.ResponseBody.create(mediaType, content)).build();
//  }
}