package com.rq.rvlibrary;

import android.view.View;

public interface OnItemClickListener<DATA> {
    void onItemClick(BaseViewHolder holder, DATA data, View view, int position);
}