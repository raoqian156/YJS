package com.yskrq.net_library.okhttp;


import com.yskrq.net_library.BaseBean;

/**
 * 专用于 MVC 架构的观察者
 * 用以和MyObserver 区别开，避免修改造成不可预知的连锁影响
 * Observer 观察者桌一件事情，将正确数据转化出来然后传递给调用层
 */
public abstract class MvcObserver<T extends BaseBean> {//implements Observer<ResponseBody> {
//    private Class<?> clazz;
//    private RequestType httpCode;//该参数不能为空，为 接口标识编码
//
//    public MvcObserver(Class<T> clazz, RequestType type) {//没使用MVP架构
//        this.clazz = clazz;
//        this.httpCode = type;
//    }
//
//    @Override
//    public void onSubscribe(Disposable d) {
//    }
//
//    @Override
//    public void onNext(ResponseBody responseBody) {
//        if (clazz != null) {//非MVP写法
//            String s = null;
//            try {
//                s = responseBody.string();
//                dealBean(s);
//            } catch (Exception e) {
//                e.printStackTrace();
//                onCommonError("-1", "数据解析异常，请确认网络连接正常后重试");
//            }
//        } else {
//            String s = null;
//            try {
//                s = responseBody.string();
//                onString(s);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    protected void onString(String s) {
//    }
//
//    private void dealBean(String s) {
//        T bean = (T) new Gson().fromJson(s, clazz);
//        if (bean != null && bean instanceof BaseBean) {
//            // TODO: 2020/9/21 更新SessionId
//            //            if (!TextUtils.equals(BASE.getSessionId(), bean.getSessionId())) {
//            //                BASE.setSessionId(bean.getSessionId());
//            //            }
//            bean.setAll(s);
//            onSuccess(bean);
//        } else {
//            LOG.e("MvcObserver", "后台返回数据异常");
//            LOG.bean("MvcObserver", s);
//        }
//    }
//
//    protected void onCommonError(String respCode, String respMsg) {
//        //        ToastUtil.show(respMsg);
//    }
//
//    @Override
//    public void onError(Throwable e) {
//        if (e instanceof UnknownHostException) {
//            onCommonError("-1", "请求异常，请稍后重试");
//        } else if (e instanceof ConnectException) {
//            onCommonError("-2", "请求异常，请稍后重试");
//        } else if (e instanceof NullPointerException) {
//            onCommonError("-3", "服务器异常，请稍后重试");
//        } else if (clazz != null) {
//            onCommonError("-4", "请求异常，请稍后重试");
//        } else if (e instanceof NullPointerException) {
//        }
//        e.printStackTrace();
//        Exception eIn = new Exception("主动上抛.onError：接口数据 解析异常 : " + e.getMessage());
//        //        CrashReport.postCatchedException(eIn);
//    }
//
//    @Override
//    public void onComplete() {
//    }
//
//    /**
//     * 接口通畅 不判断逻辑是否成功
//     */
//    public abstract void onSuccess(T t);
}
