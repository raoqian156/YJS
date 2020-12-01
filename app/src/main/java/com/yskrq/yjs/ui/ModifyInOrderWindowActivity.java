package com.yskrq.yjs.ui;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TimePicker;

import com.yskrq.common.BaseActivity;
import com.yskrq.common.OnClick;
import com.yskrq.common.util.LOG;
import com.yskrq.common.util.ToastUtil;
import com.yskrq.common.widget.DialogHelper;
import com.yskrq.net_library.BaseBean;
import com.yskrq.net_library.RequestType;
import com.yskrq.yjs.R;
import com.yskrq.yjs.bean.OrderListBean;
import com.yskrq.yjs.bean.TechProjectBean;
import com.yskrq.yjs.net.HttpManager;
import com.yskrq.yjs.widget.PopUtil;

import java.util.List;

import androidx.annotation.NonNull;

import static com.yskrq.yjs.net.Constants.TransCode.RelaxTechChangeItem;
import static com.yskrq.yjs.net.Constants.TransCode.TechAddPro;
import static com.yskrq.yjs.net.Constants.TransCode.getIscalctime;


public class ModifyInOrderWindowActivity extends BaseActivity implements View.OnClickListener {

  public static void start(final Activity activity, final OrderListBean.ValueBean first) {
    Intent intent = new Intent(activity, ModifyInOrderWindowActivity.class);
    intent.putExtra("project", first);
    activity.startActivityForResult(intent, 0x0110);
  }

  @Override
  protected int layoutId() {
    return R.layout.pop_modify_project_in_order;
  }

  OrderListBean.ValueBean project;
  String oldItemName = "";

  @Override
  protected void initView() {
    findViewById(R.id.btn_cancel).setOnClickListener(this);
    project = (OrderListBean.ValueBean) getIntent().getSerializableExtra("project");
    setString2View(R.id.tv_type, project.getShowZhong());
    setString2View(R.id.btn_name, project.getItemName());
    oldItemName = project.getItemName();
    setString2View(R.id.btn_time, project.getWaitBeginTimeDepart());
  }

  @Override
  protected void onResume() {
    super.onResume();
    HttpManager.getIscalctime(this);
  }

  List<TechProjectBean.ValueBean> selects;
  TechProjectBean.ValueBean selected;
  public static final int TAG_REFUSE_ITEM = 0x00010010;

  @Override
  public <T extends BaseBean> void onResponseSucceed(@NonNull RequestType type, @NonNull T data) {
    super.onResponseSucceed(type, data);
    if (type.is(TechAddPro)) {
      toast(data.getRespMsg());
      setResult(TAG_REFUSE_ITEM);
      finish();
    } else if (type.is(getIscalctime)) {
      TechProjectBean bean = (TechProjectBean) data;
      selects = bean.getValues();
      LOG.e("ModifyInOrderWindowActivity", "onResponseSucceed.selects:"+selects.size());
      if (selects != null && selects.size() > 0) {
        for (TechProjectBean.ValueBean item : selects) {
          if (TextUtils.equals(oldItemName, item.getName())) {
            selected = item;
          }
        }
      }
      if(isWaitToShow){
        isWaitToShow=false;
        onClick(findViewById(R.id.btn_name));
      }
    } else if (type.is(RelaxTechChangeItem)) {
      LOG.e("ModifyInOrderWindowActivity", "onResponseSucceed.86:" + data.getRespMsg());
      toast(data.getRespMsg());
      setResult(TAG_REFUSE_ITEM);
      finish();
    }
  }
  boolean isWaitToShow = false;

  @Override
  public <T extends BaseBean> void onResponseError(@NonNull RequestType type, @NonNull T data) {
    super.onResponseError(type, data);
    if (type.is(getIscalctime)) {
      isWaitToShow = false;
    }
  }

  @Override
  public void onConnectError(@NonNull RequestType type) {
    super.onConnectError(type);
    if (type.is(getIscalctime)) {
      isWaitToShow = false;
    }
  }
  @OnClick({R.id.btn_time, R.id.btn_name, R.id.btn_cut, R.id.btn_add, R.id.btn_cancel, R.id.btn_sure})
  public void onClick(final View v) {
    if (v.getId() == R.id.btn_name) {
      HttpManager
          .checkPermission("POSBillItemUpBrandNo", "项目修改", this, new HttpManager.OnPermissionCheck() {
            @Override
            public void onPermissionOk() {

              LOG.e("ModifyInOrderWindowActivity", "checkPermission.selects:"+selects.size());
              if (selects == null || selects.size() ==0) {
                ToastUtil.show("数据获取中...");
                isWaitToShow=true;
                HttpManager.getIscalctime(ModifyInOrderWindowActivity.this);
                return;
              }
              PopUtil
                  .showPopChoice(ModifyInOrderWindowActivity.this, selects, v, new PopUtil.OnChoiceListener() {
                    @Override
                    public void onChoice(View viewClick, Object select, int position) {
                      selected = (TechProjectBean.ValueBean) select;
                      setString2View(R.id.btn_name, selected.getName());
                    }
                  });
            }

            @Override
            public void onPermissionError(String rea) {

            }
          });

    } else if (v.getId() == R.id.btn_cancel) {
      finish();
    } else if (v.getId() == R.id.btn_sure) {
      HttpManager.RelaxTechChangeItem(this, project, selected);
    } else if (v.getId() == R.id.btn_time) {
      HttpManager
          .checkPermission("RelaxClockEditTime", "修改技师上钟时间", this, new HttpManager.OnPermissionCheck() {
            @Override
            public void onPermissionOk() {
              DialogHelper
                  .showHourTimeDialog(ModifyInOrderWindowActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                      String time = hourOfDay + ":" + minute;
                      setString2View(R.id.btn_time, time);
                    }
                  });
            }

            @Override
            public void onPermissionError(String rea) {

            }
          });
    }
  }

}
