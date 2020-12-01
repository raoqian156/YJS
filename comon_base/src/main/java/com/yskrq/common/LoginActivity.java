package com.yskrq.common;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.yskrq.common.bean.LoginBean;
import com.yskrq.common.bean.ProfitCenterBean;
import com.yskrq.common.bean.UpdateBean;
import com.yskrq.common.okhttp.HttpManagerBase;
import com.yskrq.common.ui.TableWindowActivity;
import com.yskrq.common.util.AppUtils;
import com.yskrq.common.util.LOG;
import com.yskrq.common.util.SPUtil;
import com.yskrq.common.util.ToastUtil;
import com.yskrq.common.widget.DialogHelper;
import com.yskrq.common.widget.DownloadUtils;
import com.yskrq.net_library.BaseBean;
import com.yskrq.net_library.RequestType;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import ecomm.lib_comm.permission.PermissionUtil;

import static com.yskrq.common.okhttp.Constants_base.TransCode.LOGIN_Luo;
import static com.yskrq.common.ui.TableWindowActivity.RESULT_COMPLETE_SELECT;


public class LoginActivity extends BaseActivity implements View.OnClickListener, TextWatcher,
                                                           PermissionUtil.OnPermissionListener {

  EditText passInput;
  EditText userInput;

  @Override
  protected int layoutId() {
    return R.layout.login_act_login;
  }

  @Override
  protected void initView() {
    passInput = findViewById(R.id.et_pass);
    userInput = findViewById(R.id.et_user);
    if (AppUtils.isTV(this)) {
      findViewById(R.id.iv_logo).requestFocus(1000);
    }
    findViewById(R.id.btn_login).setOnClickListener(this);
    findViewById(R.id.btn_user_clear).setOnClickListener(this);
    findViewById(R.id.btn_pass_show).setOnClickListener(this);

    userInput.addTextChangedListener(this);
    setString2View(R.id.et_user, SPUtil.getString(this, "user"));//226725  226692
    setString2View(R.id.et_pass, SPUtil.getString(this, "pass"));
    ((CheckBox) findViewById(R.id.tv_local_service)).setChecked(AppInfo.isAutoLogin(this));
    if (AppInfo.isAutoLogin(this) && !TextUtils.isEmpty(SPUtil.getString(this, "pass"))) {
      showLoading("自动登陆中...");
      login();
    }
  }

  @Override
  public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

  }

  @Override
  public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

  }

  @Override
  public void afterTextChanged(Editable s) {
    findViewById(R.id.btn_user_clear)
        .setVisibility(s != null && s.toString().length() > 0 ? View.VISIBLE : View.GONE);
  }

  @Override
  public void onClick(View v) {
    if (v.getId() == R.id.btn_login) {
      login();
    } else if (v.getId() == R.id.btn_user_clear) {
      setString2View(R.id.et_user, "");
      v.setVisibility(View.GONE);
    } else if (v.getId() == R.id.btn_pass_show) {
      v.setSelected(!v.isSelected());
      if (v.isSelected()) {
        //如果选中，显示密码
        passInput.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
      } else {
        //否则隐藏密码
        passInput.setTransformationMethod(PasswordTransformationMethod.getInstance());
      }
    }
  }

  private void login() {
    PermissionUtil.selectLocalFile(this, this);
    //        PermissionUtil.openIMEI(this, this);
  }

  @Override
  public <T extends BaseBean> void onResponseSucceed(@NonNull RequestType type, @NonNull T data) {
    super.onResponseSucceed(type, data);
    dismissLoading();
    if (type.is(LOGIN_Luo)) {
      toMain();
      finish();
    }
  }

  @Override
  public <T extends BaseBean> void onResponseError(@NonNull final RequestType type,
                                                   @NonNull T data) {
    LOG.e("LoginActivity", "onResponseError.128:" + type);
    dismissLoading();
    if (type.is(LOGIN_Luo)) {
      if (data instanceof ProfitCenterBean) {
        List<ProfitCenterBean.ProfitCenterValueBean> list = ((ProfitCenterBean) data)
            .getProfitCenter_value();
        if (list.size() == 0) {
          toMain();
          finish();
          return;
        }
        TableWindowActivity.start(this, list);
      } else if (type.getMore() instanceof String) {
        toast((String) type.getMore());
      } else if (data instanceof UpdateBean) {
        updateBean = (UpdateBean) data;
        if (updateBean == null || updateBean.getUpdate() == null) {
          return;
        }
        DialogHelper.showRemind(this, "有新版本，是否更新\n" + updateBean.getUpdate()
                                                                .getSRemark(), new DialogHelper.DialogConfirmListener() {

          @Override
          public void onSure() {
            if (updateBean != null) {
              DownloadUtils mDownloadUtils = new DownloadUtils(LoginActivity.this, updateBean
                  .getUpdate().getUpdateServer(), updateBean.getUpdate().getAPPVersion() + ".apk");
              mDownloadUtils.start();
            }
          }

          @Override
          public void onCancel() {
            if (updateBean != null && updateBean.getUpdate().isBForceUpdating()) {
              ToastUtil.show("请更新到最新版本");
              return;
            }
            LoginBean login = (LoginBean) type.getMore();
            HttpManagerBase.toMain(LoginActivity.this, login);
          }

        });
      } else if (data instanceof BaseBean) {
        ToastUtil.show(data.getRespMsg());
      }
    }
  }

  private void toMain() {
    AppInfo.setAutoLogin(LoginActivity.this, ((CheckBox) findViewById(R.id.tv_local_service))
        .isChecked());
    BASE.toMain(this);
  }

  UpdateBean updateBean;

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == RESULT_COMPLETE_SELECT) {
      toMain();
      finish();
    }
  }

  @Override
  public void onPermissionOk() {
    showLoading("登陆中...");
    String user = userInput.getText().toString();
    String pass = passInput.getText().toString();
    SPUtil.saveString(this, "user", user);//21308
    SPUtil.saveString(this, "pass", pass);
    HttpManagerBase.login(this, user, pass);
    AppUtils.hideInputPan(this, userInput);
    findViewById(R.id.iv_logo).requestFocus(1000);
  }

}