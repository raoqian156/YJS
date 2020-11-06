package com.yskrq.yjs.ui;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.yskrq.common.BaseActivity;
import com.yskrq.common.OnClick;
import com.yskrq.net_library.BaseBean;
import com.yskrq.net_library.RequestType;
import com.yskrq.yjs.R;
import com.yskrq.yjs.net.HttpManager;

import androidx.annotation.NonNull;

import static com.yskrq.yjs.net.Constants.TransCode.UpdatePwd;

public class ModifyPassActivity extends BaseActivity implements View.OnClickListener {

  public static final int RESULT_LOGIN_OUT = 0x000111;

  public static void start(Activity context) {
    Intent intent = new Intent(context, ModifyPassActivity.class);
    context.startActivityForResult(intent, 0x001001);
  }

  @Override
  protected int layoutId() {
    return R.layout.act_modifu_pass;
  }

  @Override
  protected void initView() {
    initTitle("修改密码");
  }

  @OnClick({R.id.btn_commit})
  public void onClick(View v) {
    String old = getInput(R.id.et_old);
    String pass1 = getInput(R.id.et_new_1);
    String pass2 = getInput(R.id.et_new_2);
    if (TextUtils.isEmpty(old) || TextUtils.isEmpty(pass1) || TextUtils.isEmpty(pass2)) {
      toast("请输入密码");
      return;
    }
    if (!TextUtils.equals(pass1, pass2)) {
      toast("两次密码不一致，请确认");
      setString2View(R.id.et_new_1, "");
      setString2View(R.id.et_new_2, "");
      return;
    }
    HttpManager.UpdatePwd(this, pass1);
    HttpManager.UpdatePwd2(this, pass1);
  }

  @Override
  public <T extends BaseBean> void onResponseSucceed(@NonNull RequestType type, @NonNull T data) {
    super.onResponseSucceed(type, data);
    if (type.is(UpdatePwd)) {
      toast(data.getRespMsg());
      setResult(RESULT_LOGIN_OUT);
      finish();
    }
  }
}
