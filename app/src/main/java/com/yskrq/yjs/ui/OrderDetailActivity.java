package com.yskrq.yjs.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import com.rq.rvlibrary.BaseAdapter;
import com.rq.rvlibrary.BaseViewHolder;
import com.rq.rvlibrary.EasyViewHolder;
import com.rq.rvlibrary.OnItemClickListener;
import com.rq.rvlibrary.RecyclerUtil;
import com.yskrq.common.AppInfo;
import com.yskrq.common.BaseActivity;
import com.yskrq.common.OnClick;
import com.yskrq.common.util.LOG;
import com.yskrq.common.widget.DialogHelper;
import com.yskrq.net_library.BaseBean;
import com.yskrq.net_library.RequestType;
import com.yskrq.yjs.R;
import com.yskrq.yjs.bean.MoneyListBean;
import com.yskrq.yjs.bean.OrderListBean;
import com.yskrq.yjs.net.HttpManager;
import com.yskrq.yjs.widget.PopUtil;

import java.text.SimpleDateFormat;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static com.yskrq.yjs.net.Constants.TransCode.DelItem;
import static com.yskrq.yjs.net.Constants.TransCode.checkrights;
import static com.yskrq.yjs.net.Constants.TransCode.getPaidMoney;
import static com.yskrq.yjs.net.Constants.TransCode.selectddan;

;

public class OrderDetailActivity extends BaseActivity implements View.OnClickListener,
                                                                 OnItemClickListener,
                                                                 PopUtil.OnPopClickListener {
  public static void start(Context context, String account, String facilityno) {
    Intent intent = new Intent(context, OrderDetailActivity.class);
    intent.putExtra("account", account);
    intent.putExtra("facilityno", facilityno);
    context.startActivity(intent);
  }

  public static void startCanAdd(Context context, String account, String facilityno) {
    Intent intent = new Intent(context, OrderDetailActivity.class);
    intent.putExtra("account", account);
    intent.putExtra("facilityno", facilityno);
    intent.putExtra("canAdd", true);
    context.startActivity(intent);
  }

  String account, facilityno;
  boolean canAdd = false;
  BaseAdapter topAdapter, bottomAdapter;

  @Override
  protected int layoutId() {
    return R.layout.act_order_detail;
  }


  @Override
  protected void initView() {
    setView();
  }

  @Override
  protected void onResume() {
    super.onResume();
    onRefuse();
  }

  private void setView() {
    account = getIntent().getStringExtra("account");
    canAdd = getIntent().getBooleanExtra("canAdd", false);
    facilityno = getIntent().getStringExtra("facilityno");
    setString2View(R.id.tv_order_no, "账单:(" + account + ")");
    if (getIntent().getBooleanExtra("canAdd", false)) {
      findViewById(R.id.btn_add).setVisibility(View.VISIBLE);
    }
    initTitle(facilityno + ":订单明细");
    topAdapter = new BaseAdapter(getContext(), R.layout.item_payed_money, EasyViewHolder.class) {
      @Override
      public void onBindEasyHolder(BaseViewHolder holder, int position, Object o) {
        super.onBindEasyHolder(holder, position, o);
        MoneyListBean.ValueBean bean = (MoneyListBean.ValueBean) o;
        holder.setItemText(R.id.tv_name, bean.getGroupName() + "(" + bean.getAmount() + ")");
        holder.setItemText(R.id.tv_value, "￥" + bean.getPaidAmount());
      }
    };
    bottomAdapter = new BaseAdapter(getContext(), R.layout.item_project, EasyViewHolder.class) {
      @Override
      public void onBindEasyHolder(BaseViewHolder holder, int position, Object o) {
        super.onBindEasyHolder(holder, position, o);
        OrderListBean.ValueBean bean = (OrderListBean.ValueBean) o;
        holder.setItemText(R.id.tv_1, bean.getOpId());
        boolean isTui = false;
        if ("5".equals(bean.getStatus())) {
          try {
            if (Double.parseDouble(bean.getItemCount()) < 0) {
              isTui = true;
            }
          } catch (Exception e) {

          }
        }
        if (isTui) {
          SpannableString con = new SpannableString(bean.getItemName() + "\n" + "(已退)");
          con.setSpan(new ForegroundColorSpan(Color.parseColor("#F37A1D")), bean.getItemName()
                                                                                .length(), bean
              .getItemName().length() + 5, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
          holder.setItemText(R.id.tv_2, con);
        } else {
          holder.setItemText(R.id.tv_2, bean.getItemName());
        }


        holder.setItemText(R.id.tv_3, "￥" + bean.getPrice());
        String status = "9000".equals(bean.getGroupId()) ? "\n(未安排)" : "9001"
            .equals(bean.getGroupId()) ? "\n(已打卡)" : "9002"
            .equals(bean.getGroupId()) ? "\n(待打卡)" : "9003"
            .equals(bean.getGroupId()) ? "\n(已下钟)" : "";

        String show = bean.getTechnician() + status + bean.getExpendShow();
        if ("5".equals(bean.getStatus())) {
          show = bean.getItemCount();
        }
        SpannableString con = new SpannableString(show);
        try {
          con.setSpan(new ForegroundColorSpan(Color.parseColor("#000000")), 0, show
              .indexOf("\n"), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
          con.setSpan(new ForegroundColorSpan(Color.parseColor("#F37A1D")), show
              .lastIndexOf("\n"), show.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        } catch (Exception e) {

        }
        holder.setItemText(R.id.tv_4, con);
        holder.setItemText(R.id.tv_5, "￥" + bean.getAllPrice());
        //        if ("1".equals(bean.getRelaxClockType())) {
        //
        //        }
        if ("0".equals(bean.getRelaxClockType())) {
          holder.setItemText(R.id.btn_dot, "轮");
          holder.getItemView(R.id.btn_dot).setVisibility(View.VISIBLE);
        } else if (!isTui) {
          holder.setItemText(R.id.btn_dot, "点");
          holder.getItemView(R.id.btn_dot).setVisibility(View.VISIBLE);
        } else {
          holder.getItemView(R.id.btn_dot).setVisibility(View.GONE);
        }
      }
    };
    new RecyclerUtil(topAdapter).set2View((RecyclerView) findViewById(R.id.rv_payed));
    new RecyclerUtil(bottomAdapter).set2View((RecyclerView) findViewById(R.id.rv_project));
    bottomAdapter.setDisplay(new BaseAdapter.DisplayOption() {
      @Override
      public boolean show(Object o, Object rule, int position) {
        OrderListBean.ValueBean bean = (OrderListBean.ValueBean) o;
        if (rule.equals(bean.getStatus())) {
          return false;
        }
        return true;
      }
    });
    bottomAdapter.display("5");
    bottomAdapter.addOnItemClickListener(this);
  }

  private void onRefuse() {
    // TODO: 2020-11-03 数据会累加
    HttpManager.selectddan(this, account);
    HttpManager.getPaidMoney(this, account);
    setString2View(R.id.tv_user, AppInfo.getUserid(this));
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
    setString2View(R.id.tv_time, simpleDateFormat.format(System.currentTimeMillis()));
  }

  double allPayed = 0;
  double allMoney = 0;

  @Override
  public <T extends BaseBean> void onResponseSucceed(@NonNull RequestType type, @NonNull T data) {
    super.onResponseSucceed(type, data);
    if (type.is(selectddan)) {
      OrderListBean bean = (OrderListBean) data;
      bottomAdapter.setData(bean.getValue());
      setDanInfo(BaseAdapter.deepCopy(bean.getValue()));
    } else if (type.is(getPaidMoney)) {
      refusePayed((MoneyListBean) data);
    } else if (type.is(checkrights)) {
      if (deleteOrder != null) HttpManager
          .DelItem(OrderDetailActivity.this, deleteOrder.getAccount(), deleteOrder
              .getIndexNumber() + "", deleteOrder.getSeqNum() + "", deleteOrder.getTechnician());
    } else if (type.is(DelItem)) {
      toast(data.getRespMsg());
      finish();
    }
  }

  @Override
  public <T extends BaseBean> void onResponseError(@NonNull RequestType type, @NonNull T data) {
    if (type.is(selectddan) && "10".equals(data.getRespCode())) {
      return;
    }
    super.onResponseError(type, data);
  }

  private <T extends BaseBean> void refusePayed(@NonNull MoneyListBean data) {
    allPayed = 0;
    findViewById(R.id.rv_payed)
        .setVisibility(findViewById(R.id.tv_pay_ed).isSelected() ? View.VISIBLE : View.GONE);
    MoneyListBean bean = data;

    if (bean.getValues() != null && bean.getValues().size() > 0) {
      try {
        for (MoneyListBean.ValueBean item : bean.getValues()) {
          allPayed += Double.parseDouble(item.getPaidAmount());
        }
      } catch (Exception e) {
      }
    }
    setString2View(R.id.tv_pay_ed, "￥" + allPayed);
    LOG.e("OrderDetailActivity", "onResponseSucceed.161:" + bean.getValues().size());
    findViewById(R.id.tv_pay_ed).setEnabled(bean.getValues().size() > 0);
    topAdapter.setData(bean.getValues());
    if (allMoney != 0) {
      setString2View(R.id.tv_need_pay, "￥" + (allMoney - allPayed));
    }
  }

  private void setDanInfo(List<OrderListBean.ValueBean> value) {
    allMoney = 0;
    if (value == null || value.size() == 0) return;
    setString2View(R.id.tv_order_no, "账单:(" + value.get(0).getAccount() + ")");

    try {
      LOG.e("OrderDetailActivity", "setDanInfo.174:");
      for (OrderListBean.ValueBean item : value) {
        allMoney += Double.parseDouble(item.getItemCount()) * Double.parseDouble(item.getPrice());
      }
    } catch (Exception e) {
      LOG.e("OrderDetailActivity", "setDanInfo.179:");
    }
    LOG.e("OrderDetailActivity", "setDanInfo.181:");
    setString2View(R.id.tv_money, "￥" + allMoney);
    setString2View(R.id.tv_need_pay, "￥" + (allMoney - allPayed));
  }

  @OnClick({R.id.btn_add, R.id.iv_ck_over, R.id.tv_pay_ed, R.id.btn_refuse})
  public void onClick(View v) {
    if (v.getId() == R.id.btn_add) {
      if (canAdd) {
        CarErProjectActivity.start(this, account, facilityno);
      } else {
        RoomProjectActivity.start(this, account, facilityno);
      }
      finish();
    } else if (v.getId() == R.id.btn_refuse) {
      onRefuse();
    } else if (v.getId() == R.id.iv_ck_over) {
      v.setSelected(!v.isSelected());
      if (v.isSelected()) {
        bottomAdapter.display(null);
      } else {
        bottomAdapter.display("5");
      }
    } else if (v.getId() == R.id.tv_pay_ed) {
      v.setSelected(!v.isSelected());
      findViewById(R.id.rv_payed).setVisibility(v.isSelected() ? View.VISIBLE : View.GONE);
    }
  }

  @Override
  public void onItemClick(BaseViewHolder holder, Object o, View view, int position) {
    OrderListBean.ValueBean bean = (OrderListBean.ValueBean) o;
    if (!"5".equals(bean.getStatus())) PopUtil.showTuiProject(this, view, bean, this);
  }

  @Override
  public void onPopClick(int position, Object data, View clickFrom) {
    final OrderListBean.ValueBean bean = (OrderListBean.ValueBean) data;
    if (position == 0) {//修改项目
      ModifyInOrderWindowActivity.start(this, bean);
    } else if (position == 2) {
      if ("9000".equals(bean.getGroupId()) || "9001".equals(bean.getGroupId()) || "9002"
          .equals(bean.getGroupId())) {
        DialogHelper.showRemind(this, "确定推掉该技师吗?", new DialogHelper.DialogConfirmListener() {
          @Override
          public void onSure() {

          }

          @Override
          public void onCancel() {
            HttpManager.CancelTec(OrderDetailActivity.this, bean.getAccount());
          }
        });
      }
    } else if (position == 1) {
      if ("9001".equals(bean.getGroupId()) || "9002".equals(bean.getGroupId())) {
        DialogHelper.showRemind(this, "确定推掉该项目吗?", new DialogHelper.DialogConfirmListener() {
          @Override
          public void onSure() {
            deleteOrder = bean;
            HttpManager.checkrights(OrderDetailActivity.this);
          }

          @Override
          public void onCancel() {
            deleteOrder = null;
          }
        });
      }
    }
  }

  OrderListBean.ValueBean deleteOrder = null;
}
