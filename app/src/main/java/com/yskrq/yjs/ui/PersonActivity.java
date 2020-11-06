package com.yskrq.yjs.ui;

import android.content.Context;
import android.content.Intent;

import com.yskrq.common.AppInfo;
import com.yskrq.common.BaseActivity;
import com.yskrq.net_library.BaseBean;
import com.yskrq.net_library.RequestType;
import com.yskrq.yjs.R;
import com.yskrq.yjs.net.HttpManager;

import androidx.annotation.NonNull;

public class PersonActivity extends BaseActivity {

  public static void start(Context context) {
    Intent intent = new Intent(context, PersonActivity.class);
    context.startActivity(intent);
  }

  @Override
  protected int layoutId() {
    return R.layout.act_person;
  }

  @Override
  protected void initView() {
    initTitle("个人信息");
    setString2View( R.id.tv_no,AppInfo.getUserid(this));
    setString2View( R.id.tv_name,AppInfo.getTechNum(this));
    setString2View( R.id.tv_group,AppInfo.getGroupId(this));
    setString2View( R.id.tv_shop,AppInfo.getShopsid(this));
    HttpManager.checkTechNo(this);
  }

  @Override
  public <T extends BaseBean> void onResponseSucceed(@NonNull RequestType type, @NonNull T data) {
    super.onResponseSucceed(type, data);
    setString2View( R.id.tv_name,data.getStrInList("Description"));
  }
}
