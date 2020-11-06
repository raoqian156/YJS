package com.yskrq.yjs.ui;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.yskrq.common.BaseActivity;
import com.yskrq.common.OnClick;
import com.yskrq.net_library.BaseBean;
import com.yskrq.net_library.RequestType;
import com.yskrq.yjs.R;
import com.yskrq.yjs.bean.TechProjectBean;
import com.yskrq.yjs.net.HttpManager;
import com.yskrq.yjs.widget.PopUtil;

import java.util.List;

import androidx.annotation.NonNull;

import static com.yskrq.yjs.net.Constants.TransCode.TechAddPro;
import static com.yskrq.yjs.net.Constants.TransCode.getIscalctime;


public class AddProjectWindowActivity extends BaseActivity implements View.OnClickListener {


  public static void start(Activity activity, String indexnumber, String account) {
    Intent intent = new Intent(activity, AddProjectWindowActivity.class);
    intent.putExtra("indexNum", indexnumber);
    intent.putExtra("account", account);
    activity.startActivityForResult(intent, 111);
  }

  @Override
  protected int layoutId() {
    return R.layout.pop_add_project;
  }

  String indexNum, account;

  @Override
  protected void initView() {
    findViewById(R.id.btn_cancel).setOnClickListener(this);
    indexNum = getIntent().getStringExtra("indexNum");
    account = getIntent().getStringExtra("account");
    HttpManager.getIscalctime(this);
  }

  List<TechProjectBean.ValueBean> selects;
  TechProjectBean.ValueBean selected;

  @Override
  public <T extends BaseBean> void onResponseSucceed(@NonNull RequestType type, @NonNull T data) {
    super.onResponseSucceed(type, data);
    if (type.is(TechAddPro)) {
      toast(data.getRespMsg());
      finish();
    } else if (type.is(getIscalctime)) {
      TechProjectBean bean = (TechProjectBean) data;
      selects = bean.getValues();
      if (selects != null && selects.size() > 0) {
        selected = selects.get(0);
        setString2View( R.id.btn_name,selected.getName());
      }
    }
  }

  @OnClick({R.id.btn_name, R.id.btn_cut, R.id.btn_add, R.id.btn_cancel, R.id.btn_sure})
  public void onClick(View v) {
    if (v.getId() == R.id.btn_name) {
      PopUtil.showPopChoice(this, selects, v, new PopUtil.OnChoiceListener() {
        @Override
        public void onChoice(View viewClick, Object select, int position) {
          selected = (TechProjectBean.ValueBean) select;
          setString2View( R.id.btn_name,selected.getName());
        }
      });
    } else if (v.getId() == R.id.btn_cancel) {
      finish();
    } else if (v.getId() == R.id.btn_cut) {
      String nu = getInput(R.id.tv_num);
      int num = Integer.parseInt(nu);
      if (num > 1) {
        setString2View( R.id.tv_num,--num + "");
      }
    } else if (v.getId() == R.id.btn_add) {
      String nu = getInput(R.id.tv_num);
      int num = Integer.parseInt(nu);
      setString2View( R.id.tv_num,++num + "");
    } else if (v.getId() == R.id.btn_sure) {
      //      String relaxaccount, String itemno,
      //          String itemcount, String indexnumber
      HttpManager.TechAddPro(this, account, selected.getItemNo(), getInput(R.id.tv_num), indexNum);
    }
  }
}
