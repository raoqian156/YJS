package com.yskrq.yjs.ui;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.yskrq.common.BaseActivity;
import com.yskrq.common.OnClick;
import com.yskrq.yjs.R;

public class PersonalAchievementActivity extends BaseActivity implements View.OnClickListener {

  public static void start(Context context) {
    Intent intent = new Intent(context, PersonalAchievementActivity.class);
    context.startActivity(intent);
  }

  @Override
  protected int layoutId() {
    return R.layout.act_achievement;
  }

  @Override
  protected void initView() {
    initTitle("个人业绩");
  }

  @OnClick({R.id.btn_1, R.id.btn_2, R.id.btn_3})
  public void onClick(View v) {
    if (v.getId() == R.id.btn_1) {
      StatisticsActivity.start(this, 1);
    }else if(v.getId()==R.id.btn_2){
      StatisticsActivity.start(this, 2);
    }else if(v.getId()==R.id.btn_3){
      StatisticsActivity.start(this, 3);
    }
  }
}
