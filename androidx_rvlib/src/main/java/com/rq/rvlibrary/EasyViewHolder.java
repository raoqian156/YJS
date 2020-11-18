package com.rq.rvlibrary;

import android.view.View;

/**
 * 不校验LayoutId
 * 复写 {@link BaseAdapter#onBindEasyHolder(BaseViewHolder, int, Object)}}
 */
public class EasyViewHolder<DATA> extends BaseViewHolder<DATA> {
    public EasyViewHolder(View itemView) {
        super(itemView);
    }
}
