package com.yskrq.net_library;


public interface HttpInnerListener {

    void onString(String json);

    void onEmptyResponse();
}
