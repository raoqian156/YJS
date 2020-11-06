package com.yskrq.yjs.ui;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.DatePicker;

import com.rq.rvlibrary.BaseAdapter;
import com.rq.rvlibrary.BaseViewHolder;
import com.rq.rvlibrary.EasyViewHolder;
import com.rq.rvlibrary.RecyclerUtil;
import com.yskrq.common.BaseActivity;
import com.yskrq.common.OnClick;
import com.yskrq.common.widget.DialogHelper;
import com.yskrq.net_library.BaseBean;
import com.yskrq.net_library.RequestType;
import com.yskrq.yjs.R;
import com.yskrq.yjs.bean.OrderBean;
import com.yskrq.yjs.net.HttpManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static com.yskrq.yjs.net.Constants.TransCode.GetRelaxReservation;

public class MyOrderActivity extends BaseActivity implements View.OnClickListener {

  public static void start(Context context) {
    Intent intent = new Intent(context, MyOrderActivity.class);
    context.startActivity(intent);
  }

  @Override
  protected int layoutId() {
    return R.layout.act_my_order;
  }

  BaseAdapter mAdapter;

  @Override
  protected void initView() {
    initTitle("我的预约");
    mAdapter = new BaseAdapter(this, R.layout.item_order, EasyViewHolder.class) {
      @Override
      public void onBindEasyHolder(BaseViewHolder holder, int position, Object data) {
        super.onBindEasyHolder(holder, position, data);
        //        holder.getItemView(R.id.parentPanel).setBackgroundColor();
        OrderBean.ValueBean bean = (OrderBean.ValueBean) data;
        holder.setItemText(R.id.tv_1, bean.getAccount() + "\t\t" + bean.getHoteldate());
        holder.setItemText(R.id.tv_2, "房间号:" + bean.getFacilityno() + "\t\t项目名称:" + bean
            .getItemname() + "\t\t人数:" + bean.getCovers());
        holder.setItemText(R.id.tv_3, "客人姓名:" + bean.getResername() + "\t\t客人电话:" + bean
            .getMobilephone() + "\t\t来源:" + bean.getBlltypename());
        holder.setItemText(R.id.tv_4, "预约时间:" + bean.getCreatedate() + "\t\t备注:" + bean
            .getCommentX());
      }
    };
    new RecyclerUtil(mAdapter).set2View((RecyclerView) findViewById(R.id.recycler));
    HttpManager.GetRelaxReservation(this, false, null);
  }

  @Override
  public <T extends BaseBean> void onResponseSucceed(@NonNull RequestType type, @NonNull T data) {
    super.onResponseSucceed(type, data);
    if (type.is(GetRelaxReservation)) {
      OrderBean bean = (OrderBean) data;
      mAdapter.setData(bean.getValue());
    }
  }

  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

  @OnClick({R.id.ck_history, R.id.ck_cancel, R.id.tv_start_time, R.id.tv_end_time, R.id.btn_search})
  public void onClick(final View v) {
    if (v.getId() == R.id.ck_history || v.getId() == R.id.ck_cancel) {
      v.setSelected(!v.isSelected());
      if (findViewById(R.id.ck_history).isSelected() || findViewById(R.id.ck_cancel).isSelected()) {
        Calendar calendar = Calendar.getInstance();
        setString2View(R.id.tv_start_time, sdf.format(calendar.getTimeInMillis()));
        setString2View(R.id.tv_end_time, sdf.format(calendar.getTimeInMillis()));
        findViewById(R.id.ll_time).setVisibility(View.VISIBLE);
      } else {
        findViewById(R.id.ll_time).setVisibility(View.GONE);
      }
    } else if (v.getId() == R.id.tv_start_time || v.getId() == R.id.tv_end_time) {
      DialogHelper.showTimeDialog(getContext(), new DialogHelper.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
          String time = year + "-" + (month + 1) + "-" + dayOfMonth;
          setString2View(v.getId(), time);
        }
      });
    } else if (v.getId() == R.id.btn_search) {
      toSeekData();
    }
  }

  private void toSeekData() {
    boolean isCancel = findViewById(R.id.ck_cancel).isSelected();
    String key = getInput(R.id.et_search);
    if (findViewById(R.id.ll_time).isShown()) {
      String start = getInput(R.id.tv_start_time);
      String end = getInput(R.id.tv_end_time);
      HttpManager.GetRelaxReservation(this, isCancel, key, start, end);
    } else {
      String toDay = sdf.format(System.currentTimeMillis());
      HttpManager.GetRelaxReservation(this, isCancel, key, toDay, toDay);
    }
  }

}
