package com.yskrq.net_library.okhttp;



/**
 * backToast 简单接口提示内容<接口成功与失败的提示语  固定位置> 0-成功提示语，1-失败提示语
 */
public class HttpParamUtil {

//
//  public static <T extends BaseBean> void okHttp(HttpInnerListener listener, final BaseView view,
//                                                 String type, @NonNull Map<String, String> maps) {
//    RequestType requestType = new RequestType(type, maps);
//    String host = null;
//    if (!type.startsWith("http")) {
//      host = SPUtil.getString(view.getContext(), TAG_CHANGE_URL);
//    }
//    if (type != null && type.contains("/")) {
//      String[] p = type.split("/");
//      if (host != null) {
//        dealCommonRequest(listener, RetrofitUtils.getApiService(view.getContext())
//                                                 .post(host, p[0], p[1], NET_PARAM_HELPER
//                                                     .getParam(maps)), view, requestType, null);
//      } else dealCommonRequest(listener, RetrofitUtils.getApiService(view.getContext())
//                                                      .post(p[0], p[1], NET_PARAM_HELPER
//                                                          .getParam(maps)), view, requestType, null);
//    }
//  }
//
//  public static <T extends BaseBean> void bean(final BaseView view, String type,
//                                               @NonNull Map<String, String> maps, Class<T> clazz) {
//    if (isJustShowUI) {
//      final BaseBean bean = (BaseBean) DebugDataCreateHelper.instans().createData2(0, clazz);
//      if (bean == null) {
//        LOG.e("HttpManager", "bean--> null");
//      } else {
//        LOG.bean("HttpManager", bean.toString());
//      }
//      Handler handler = new Handler(Looper.getMainLooper());
//      final String finalType = type;
//      handler.postDelayed(new Runnable() {
//        @Override
//        public void run() {
//          view.onResponseSucceed(new RequestType(finalType), bean);
//        }
//      }, 1000);
//      return;
//    }
//    view.showLoading();
//    RequestType requestType = new RequestType(type, maps);
//    String host = null;
//    if (!type.startsWith("http")) {
//      host = SPUtil.getString(view.getContext(), TAG_CHANGE_URL);
//    }
//    if (type.contains("/")) {
//      String[] p = type.split("/");
//      if (host != null) {
//        dealCommonRequest(RetrofitUtils.getApiService(view.getContext())
//                                       .post(host, p[0], p[1], NET_PARAM_HELPER
//                                           .getParam(maps)), view, requestType, clazz);
//      } else dealCommonRequest(RetrofitUtils.getApiService(view.getContext())
//                                            .post(p[0], p[1], NET_PARAM_HELPER
//                                                .getParam(maps)), view, requestType, clazz);
//    }
//
//    //        if (type != null && type.contains("/")) {
//    //            String[] p = type.split("/");
//    //            dealCommonRequest(RetrofitUtils.getApiService(view.getContext())
//    //                                           .post(p[0], p[1], NET_PARAM_HELPER
//    //                                                   .getParam(maps)), view, requestType, clazz, backToast);
//    //        }
//  }
//
//  private static <T extends BaseBean> void dealCommonRequest(Observable<ResponseBody> post,
//                                                             final BaseView view,
//                                                             final RequestType type,
//                                                             Class<T> clazz) {
//    dealCommonRequest(null, post, view, type, clazz);
//  }
//
//  private static <T extends BaseBean> void dealCommonRequest(final HttpInnerListener listener,
//                                                             Observable<ResponseBody> post,
//                                                             final BaseView view,
//                                                             final RequestType type,
//                                                             Class<T> clazz) {
//    post.compose(RxUtil.rxSchedulerHelper(view.<ResponseBody>bindToLifecycle()))
//        .subscribe(new MvcObserver<T>(listener == null ? clazz : null, type) {
//
//          @Override
//          public void onSuccess(T result) {
//            LOG.e("HttpParamUtil", "onSuccess.MvcObserver:");
//            view.dismissLoading();
//            String token = result.get(String.class, "T");
//            if (!TextUtils.isEmpty(token)) {
//              SPUtil.saveString(view.getContext(), "T", token);
//            }
//            if (result.isOk()) {//逻辑成功
//              view.onResponseSucceed(type, result);
//            } else {//逻辑失败
//              view.onResponseError(type, result);
//            }
//          }
//
//          @Override
//          protected void onCommonError(String respCode, String respMsg) {
//            LOG.e("HttpParamUtil", "onCommonError.MvcObserver:");
//            view.dismissLoading();
//            view.onConnectError(type.with(respCode, respMsg));
//          }
//
//          @Override
//          protected void onString(String s) {
//            super.onString(s);
//            LOG.e("HttpParamUtil", "onString.MvcObserver:");
//            if (listener != null) listener.onString(s);
//          }
//        });
//  }
//
//  //    public static void commonError(BaseView view, RequestType type) {
//  //        if (type.isNetError() && BASE.getNetErrorToast() > 0) {
//  //            ToastUtil.show(view.getContextActivity(), BASE.getNetErrorToast());
//  //            return;
//  //        }
//  //        ToastUtil.show(view.getContextActivity(), type.getErrorMsg());
//  //        view.dismissLoading();
//  //    }
}
