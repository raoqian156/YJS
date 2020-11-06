package com.yskrq.net_library;

import android.content.Context;

import androidx.annotation.NonNull;

public interface OnHttpListener {

    Context getContext();

    // 逻辑正确
    <T extends BaseBean> void onResponseSucceed(@NonNull RequestType type,
                                                @NonNull T data);//使用 Bean文件 作为回调 的 成功回调 code = 00000

    // 逻辑错误
    <T extends BaseBean> void onResponseError(@NonNull RequestType type, @NonNull T data);

    //连接异常
    void onConnectError(@NonNull RequestType type);
}
