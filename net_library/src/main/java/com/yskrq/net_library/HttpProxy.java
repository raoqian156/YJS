package com.yskrq.net_library;

import android.content.Context;
import android.os.Build;

import com.google.gson.Gson;
import com.yskrq.net_library.okhttp.SPUtil;
import com.yskrq.net_library.url_conn.HttpSender;

import java.io.File;
import java.util.HashMap;


/**
 * path 不带域名和端口号
 * 1."_"下划线开头
 * 带包使用登录几口返回的域名
 * {@link SPUtil#saveString(Context, String, String)} key >>"HttpParamUtil.change.url"
 * 2.其他开头  使用 >> "https://hotel16.yskvip.com:9092" 作为端口和域名
 */
public class HttpProxy {

  public static final String TAG_CHANGE_URL = "HttpParamUtil.change.url";
  private static boolean useOkHttp = false;

  /**
   * 返回数据不直接调动界面，使用内部逻辑，不切换线程
   * 不会调用 {@link BaseView#onResponseSucceed(RequestType, BaseBean)}
   * 、{@link BaseView#onResponseError(RequestType, BaseBean)}
   * 只会回调 {@link HttpInnerListener#onString(String)}
   * 、{@link HttpInnerListener#onEmptyResponse()}
   */
  public static void inner(HttpInnerListener httpInnerListener, Context context, String path,
                           HashMap<String, String> param) {
    if (path.startsWith("http")) {
    } else {
      path = "https://" + SPUtil.getString(context, "HttpParamUtil.change.url") + ":9092/" + path;
    }
    HttpSender.post(path, param, httpInnerListener);
  }

  public static void inner(HttpInnerListener httpInnerListener, BaseView view, String path,
                           HashMap<String, String> param) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
      useOkHttp = false;
    }
    //    if (useOkHttp) {
    //      HttpParamUtil.okHttp(httpInnerListener, view, path, param);
    //      return;
    //    }
    inner(httpInnerListener, view.getContext(), path, param);
  }


  public static <T extends BaseBean> void bean(BaseView view, String path,
                                               HashMap<String, String> param, Class<T> clazz) {
    bean(view, path, "9092", param, clazz);
  }

  public static <T extends BaseBean> void bean(BaseView view, String path, String port,
                                               HashMap<String, String> param, Class<T> clazz) {

    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
      useOkHttp = false;
    }
    //    if (useOkHttp) {
    //      HttpParamUtil.bean(view, path, param, clazz);
    //    } else {
    if (path.startsWith("http")) {
    } else {
      path = "https://" + SPUtil
          .getString(view.getContext(), TAG_CHANGE_URL) + ":" + port + "/" + path;
    }
    HttpSender.post(view, path, param, clazz);
  }

  public static <T extends BaseBean> void upload(final BaseView view, String path, String port,
                                                 String filePath, HashMap<String, String> param,
                                                 final Class<T> clazz) {
    if (path.startsWith("http")) {
    } else {
      path = "https://" + SPUtil
          .getString(view.getContext(), TAG_CHANGE_URL) + ":" + port + "/" + path;
    }
    //
    //    HashMap<String, File> fileHashMap = new HashMap<>();
    //    fileHashMap.put("image", new File(filePath));
    //    HttpSender.upload(path, fileHashMap);

    final String tag = path.substring(path.lastIndexOf("/") + 1);
    HttpSender.upload(path, new File(filePath), param, new HttpInnerListener() {
      @Override
      public void onString(String json) {
        BaseBean base = new Gson().fromJson(json, clazz);
        if (base != null && base.isOk()) {
          view.onResponseSucceed(new RequestType(tag), base);
        } else if (base != null) {
          view.onResponseError(new RequestType(tag), base);
        } else {
          view.onConnectError(new RequestType(tag));
        }
      }

      @Override
      public void onEmptyResponse() {
        view.onConnectError(new RequestType(tag));
      }
    });
  }

  //  }
}
