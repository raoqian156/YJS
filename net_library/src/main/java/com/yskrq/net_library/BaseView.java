package com.yskrq.net_library;

public interface BaseView extends OnHttpListener {//统一网络回调

    void handleFailResponse(BaseBean baseBean);  //统一处理响应失败

    void showLoading(String... show);

    void dismissLoading();

//    <T> LifecycleTransformer<T> bindToLifecycle();   //为了让接口可以调用 RxFragmentActivity的bindToLifecycle()方法

}
