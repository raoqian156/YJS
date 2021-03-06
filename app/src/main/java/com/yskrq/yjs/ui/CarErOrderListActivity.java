package com.yskrq.yjs.ui;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.rq.rvlibrary.BaseAdapter;
import com.rq.rvlibrary.BaseViewHolder;
import com.rq.rvlibrary.EasyViewHolder;
import com.rq.rvlibrary.OnItemClickListener;
import com.rq.rvlibrary.RecyclerUtil;
import com.yskrq.common.BaseActivity;
import com.yskrq.common.OnClick;
import com.yskrq.common.util.LOG;
import com.yskrq.net_library.BaseBean;
import com.yskrq.net_library.RequestType;
import com.yskrq.yjs.R;
import com.yskrq.yjs.bean.CaiErProductBean;
import com.yskrq.yjs.bean.OrderListBean;
import com.yskrq.yjs.net.HttpManager;
import com.yskrq.yjs.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static com.yskrq.yjs.net.Constants.TransCode.AddBillitemBigRelax;
import static com.yskrq.yjs.net.Constants.TransCode.selectddan;

public class CarErOrderListActivity extends BaseActivity implements View.OnClickListener,
                                                                    OnItemClickListener {

  public static void start(Activity context, String account, List<CaiErProductBean.ValueBean> array,
                           String facilityno) {
    ArrayList pass = new ArrayList();
    if (array != null) pass.addAll(array);
    Intent intent = new Intent(context, CarErOrderListActivity.class);
    intent.putExtra("account", account);
    intent.putExtra("facilityno", facilityno);
    intent.putExtra("array", pass);
    context.startActivityForResult(intent, 12);
  }

  @Override
  protected int layoutId() {
    return R.layout.act_order_list;
  }

  BaseAdapter topAdapter, bottomAdapter;
  String account, facilityno;
  List<CaiErProductBean.ValueBean> selects = new ArrayList<>();

  @Override
  protected void initView() {
    selects
        .addAll((ArrayList<CaiErProductBean.ValueBean>) getIntent().getSerializableExtra("array"));
    account = getIntent().getStringExtra("account");
    facilityno = getIntent().getStringExtra("facilityno");
    initTitle(facilityno + ":订单明细");
    HttpManager.selectddan(this, account);
    topAdapter = new BaseAdapter(this, R.layout.item_order_list_editable, EasyViewHolder.class) {
      @Override
      public void onBindEasyHolder(BaseViewHolder holder, int position, Object o) {
        super.onBindEasyHolder(holder, position, o);
        CaiErProductBean.ValueBean bean = (CaiErProductBean.ValueBean) o;
        holder.setItemText(R.id.tv_name, bean.getName());
        holder.setItemText(R.id.tv_price, "￥" + bean.getPrice());
        holder.setItemText(R.id.tv_money, "￥" + bean.getAllPrice());
        holder.getItemView(R.id.btn_cut).setVisibility(View.VISIBLE);
        holder.getItemView(R.id.btn_add).setVisibility(View.VISIBLE);
      }
    };
    topAdapter.addOnItemClickListener(this, R.id.btn_add, R.id.btn_cut);

    bottomAdapter = new BaseAdapter(getContext(), R.layout.item_order_list_show, EasyViewHolder.class) {
      @Override
      public void onBindEasyHolder(BaseViewHolder holder, int position, Object o) {
        super.onBindEasyHolder(holder, position, o);
        OrderListBean.ValueBean bean = (OrderListBean.ValueBean) o;
        holder.setItemText(R.id.tv_name, bean.getItemName());
        holder.setItemText(R.id.tv_price, "￥" + bean.getPrice());
        holder.setItemText(R.id.tv_num, bean.getItemCount());
        holder.setItemText(R.id.tv_money, "￥" + bean.getAllPrice());
      }
    };
    new RecyclerUtil(topAdapter).set2View((RecyclerView) findViewById(R.id.recycler_add));
    new RecyclerUtil(bottomAdapter).set2View((RecyclerView) findViewById(R.id.recycler));
    topAdapter.setData(selects);
    topAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
      @Override
      public void onChanged() {
        super.onChanged();
        refusePrice();
      }
    });
    refusePrice();
  }


  private void refusePrice() {
    double all = 0;
    List<CaiErProductBean.ValueBean> copy = BaseAdapter.deepCopy(topAdapter.getData());
    int remove = -1;
    int num = 0;
    for (int i = 0; i < copy.size(); i++) {
      CaiErProductBean.ValueBean item = copy.get(i);
      if (item.getNum() > 0) {
        all = StringUtil.doubleAdd(all, item.getAllPrice());
        num += item.getNum();
      } else {
        remove = i;
      }
    }
    topAdapter.removeItem(remove);
    String show = "共" + num + "项,合计￥" + all;
    setString2View(R.id.tv_all_pay_show, show);
    setString2View(R.id.tv_all_price, "￥" + +all);
  }

  @Override
  public <T extends BaseBean> void onResponseSucceed(@NonNull RequestType type, @NonNull T data) {
    if (type.is(selectddan)) {
      OrderListBean bean = (OrderListBean) data;
      if (bean.getValue() == null || bean.getValue().size() == 0) {
        findViewById(R.id.ll_history_head).setVisibility(View.GONE);
        findViewById(R.id.line_history).setVisibility(View.GONE);
      }
      bottomAdapter.setData(bean.getValue());
      refusePrice();
    } else if (type.is(AddBillitemBigRelax)) {
      toast(data.getRespMsg());
      setResult(RESULT_QUIT);
      finish();
    }
  }

  @Override
  public <T extends BaseBean> void onResponseError(@NonNull RequestType type, @NonNull T data) {
    if (type.is(selectddan) && "10".equals(data.getRespCode())) {
      findViewById(R.id.ll_history_head).setVisibility(View.GONE);
      findViewById(R.id.line_history).setVisibility(View.GONE);
      return;
    }
    super.onResponseError(type, data);
  }

  @Override
  public void onConnectError(@NonNull RequestType type) {
    super.onConnectError(type);
    LOG.e("OrderListActivity", "onConnectError.141:" + type.getErrorMsg());
  }

  public static final int RESULT_QUIT = 0x123;

  @OnClick({R.id.btn_commit})
  public void onClick(View v) {
    if (v.getId() == R.id.btn_commit) {
      List<CaiErProductBean.ValueBean> sel = topAdapter.getData();
      if (sel == null || sel.size() == 0) {
        toast("未添加任何项目/商品");
        return;
      }
      LOG.bean("CarErOrderListActivity", sel);
      HttpManager.AddBillitemBigRelax(this, account, sel, facilityno);
    }
  }

  @Override
  public void onItemClick(BaseViewHolder holder, Object o, View view, int position) {
    CaiErProductBean.ValueBean bean = (CaiErProductBean.ValueBean) o;
    if (view.getId() == R.id.btn_cut) {
      bean.showSizeCut();
      topAdapter.notifyDataSetChanged();
    } else if (view.getId() == R.id.btn_add) {
      bean.showSizeAdd();
      topAdapter.notifyDataSetChanged();
    }
  }
}
