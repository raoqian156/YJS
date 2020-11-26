package com.yskrq.yjs.ui;

import android.content.Intent;
import android.view.View;

import com.yskrq.common.AppInfo;
import com.yskrq.common.BaseFragment;
import com.yskrq.common.LoginActivity;
import com.yskrq.common.OnClick;
import com.yskrq.common.util.AppUtils;
import com.yskrq.yjs.R;
import com.yskrq.yjs.keep.KeepManager;
import com.yskrq.yjs.util.PhoneUtil;

import static com.yskrq.yjs.util.PhoneUtil.toStartInterface;

public class MineFragment extends BaseFragment implements View.OnClickListener {
  @Override
  protected int layoutId() {
    return R.layout.fra_mine;
  }

  @Override
  protected void initView() {
    initTitle("我的");
    setTextView2View(R.id.tv_version, AppUtils.getVersion(getContext()));
  }

  @OnClick({R.id.btn_battery_setting, R.id.btn_voice, R.id.tv_name, R.id.btn_pass, R.id.btn_about, R.id.btn_photo, R.id.btn_bg_setting, R.id.btn_login_out})
  public void onClick(View v) {
    if (v.getId() == R.id.btn_battery_setting) {
      AppUtils.requestIgnoreBatteryOptimizations(getContext());
    } else if (v.getId() == R.id.btn_voice) {
      PhoneUtil.toOpen(getContext());
    } else if (v.getId() == R.id.tv_name) {
      PersonActivity.start(getContext());
    } else if (v.getId() == R.id.btn_pass) {
      ModifyPassActivity.start(getActivity());
    } else if (v.getId() == R.id.btn_about) {
      AboutActivity.start(getContext());
    } else if (v.getId() == R.id.btn_photo) {
      PhotoActivity.start(getContext());
    } else if (v.getId() == R.id.btn_bg_setting) {
      toStartInterface(getContext());
    } else if (v.getId() == R.id.btn_login_out) {
      KeepManager.stopAliveRun(getContext());
      AppInfo.loginOut(getContext());
      Intent intent = new Intent(getActivity(), LoginActivity.class);
      startActivity(intent);
      getActivity().finish();
    }
  }
}
