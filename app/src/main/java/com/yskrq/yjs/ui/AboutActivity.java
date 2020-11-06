package com.yskrq.yjs.ui;

import android.content.Context;
import android.content.Intent;

import com.yskrq.common.BaseActivity;
import com.yskrq.yjs.R;

public class AboutActivity extends BaseActivity {
  public static void start(Context context) {
    Intent intent = new Intent(context, AboutActivity.class);
    context.startActivity(intent);
  }

  @Override
  protected int layoutId() {
    return R.layout.act_about;
  }

  @Override
  protected void initView() {
    initTitle("关于云上客");
  }

}
