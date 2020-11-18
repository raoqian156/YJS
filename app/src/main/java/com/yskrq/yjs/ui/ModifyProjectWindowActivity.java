package com.yskrq.yjs.ui;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.yskrq.common.BaseActivity;
import com.yskrq.common.OnClick;
import com.yskrq.net_library.BaseBean;
import com.yskrq.net_library.RequestType;
import com.yskrq.yjs.R;
import com.yskrq.yjs.bean.RelaxListBean;
import com.yskrq.yjs.bean.TechProjectBean;
import com.yskrq.yjs.net.HttpManager;
import com.yskrq.yjs.widget.PopUtil;

import java.util.List;

import androidx.annotation.NonNull;

import static com.yskrq.yjs.net.Constants.TransCode.RelaxTechChangeItem;
import static com.yskrq.yjs.net.Constants.TransCode.TechAddPro;
import static com.yskrq.yjs.net.Constants.TransCode.getIscalctime;


public class ModifyProjectWindowActivity extends BaseActivity implements View.OnClickListener {


  public static void start(Activity activity, RelaxListBean.ValueBean first) {
    if (first == null) {
      Toast.makeText(activity, "未发现上点的项目，暂不可更改项目", Toast.LENGTH_LONG).show();
      return;
    }
    Intent intent = new Intent(activity, ModifyProjectWindowActivity.class);
    intent.putExtra("project", first);
    activity.startActivity(intent);
  }

  @Override
  protected int layoutId() {
    return R.layout.pop_modify_project;
  }

  RelaxListBean.ValueBean project;

  @Override
  protected void initView() {
    findViewById(R.id.btn_cancel).setOnClickListener(this);
    project = (RelaxListBean.ValueBean) getIntent().getSerializableExtra("project");
    setString2View(R.id.btn_name, project.getItemname());
    setString2View(R.id.tv_type, project.getRelaxclockname());
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
      }
    } else if (type.is(RelaxTechChangeItem)) {
      toast(data.getRespMsg());
      finish();
    }
  }

  @OnClick({R.id.btn_name, R.id.btn_cut, R.id.btn_add, R.id.btn_cancel, R.id.btn_sure})
  public void onClick(View v) {
    if (v.getId() == R.id.btn_name) {
      PopUtil.showPopChoice(this, selects, v, new PopUtil.OnChoiceListener() {
        @Override
        public void onChoice(View viewClick, Object select, int position) {
          selected = (TechProjectBean.ValueBean) select;
          setString2View(R.id.btn_name, selected.getName());
        }
      });
    } else if (v.getId() == R.id.btn_cancel) {
      finish();
    } else if (v.getId() == R.id.btn_sure) {
      if (selected == null) {
        finish();
        return;
      }
      HttpManager.RelaxTechChangeItem(this, project, selected);
    }
  }
}
