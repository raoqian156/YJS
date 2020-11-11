package com.yskrq.yjs.ui;

import android.content.Context;
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
import com.yskrq.yjs.net.HttpManager;
import com.yskrq.yjs.widget.PopUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import static com.yskrq.yjs.net.Constants.TransCode.selectgoodsbigrelax;
import static com.yskrq.yjs.ui.CarErOrderListActivity.RESULT_QUIT;
import static com.yskrq.yjs.widget.PopUtil.ACTION_CHANGE;
import static com.yskrq.yjs.widget.PopUtil.ACTION_CLEAR;
import static com.yskrq.yjs.widget.PopUtil.ACTION_COMMINT;

public class CarErProjectActivity extends BaseActivity implements OnItemClickListener,
                                                                  View.OnClickListener {

  public static void start(Context context, String account, String facilityno) {
    Intent intent = new Intent(context, CarErProjectActivity.class);
    intent.putExtra("account", account);
    intent.putExtra("facilityno", facilityno);
    context.startActivity(intent);
  }

  @Override
  protected int layoutId() {
    return R.layout.act_room_caier;
  }

  BaseAdapter rightAdapter;
  String account, facilityno;

  @Override
  protected void initView() {
    account = getIntent().getStringExtra("account");
    facilityno = getIntent().getStringExtra("facilityno");
    initTitle(facilityno + ":选择项目");
    rightAdapter = new BaseAdapter(this, R.layout.item_caier_product, EasyViewHolder.class) {
      @Override
      public void onBindEasyHolder(BaseViewHolder holder, int position, Object data) {
        CaiErProductBean.ValueBean bean = (CaiErProductBean.ValueBean) data;
        holder.setItemText(R.id.tv_name, bean.getName());
        holder.setItemText(R.id.tv_price, bean.getPrice());
        holder.setItemText(R.id.tv_num, bean.getNum() + "");
      }
    };
    rightAdapter.addOnItemClickListener(this, R.id.btn_cut, R.id.btn_add);
    new RecyclerUtil(rightAdapter).set2View((RecyclerView) findViewById(R.id.recycler_right));

    HttpManager.SelectRelaxInvtType(this);
    rightAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
      @Override
      public void onChanged() {
        super.onChanged();
        List<CaiErProductBean.ValueBean> lists = rightAdapter.getData();
        select.clear();
        for (CaiErProductBean.ValueBean item : lists) {
          if (item.isSelect() || item.getNum() > 0) {
            select.add(item);
          }
        }
        refuseCar();
      }
    });
    rightAdapter.setDisplay(new BaseAdapter.DisplayOption() {
      @Override
      public boolean show(Object data, Object rule, int position) {
        CaiErProductBean.ValueBean bean = (CaiErProductBean.ValueBean) data;
        LOG.e("RoomProjectActivity", "show.181:" + bean.getName() + "   <" + ((String) rule) + ">");
        return bean.getName().contains((String) rule);
      }
    });
    HttpManager.selectgoodsbigrelax(this);
  }

  Map<String, Integer> jishu = new HashMap<>();//选中物品数目

  private void refuseCar() {
    int sum = 0;
    for (CaiErProductBean.ValueBean item : select) {
      if (jishu.containsKey(item.getTypecode())) {
        int newNum = jishu.get(item.getTypecode()) + item.getNum();
        jishu.put(item.getTypecode(), newNum);
      } else {
        jishu.put(item.getTypecode(), item.getNum());
      }
      sum += item.getNum();
    }
    setString2View(R.id.tv_shop_car_num, sum + "");
    findViewById(R.id.tv_shop_car_num).setVisibility(sum > 0 ? View.VISIBLE : View.GONE);
  }

  List<CaiErProductBean.ValueBean> select = new ArrayList<>();

  @Override
  public <T extends BaseBean> void onResponseSucceed(@NonNull RequestType type, @NonNull T data) {
    super.onResponseSucceed(type, data);
    if (type.is(selectgoodsbigrelax)) {
      CaiErProductBean bean = (CaiErProductBean) data;
      rightAdapter.setData(bean.getValue());
    }
  }

  @Override
  public void onItemClick(BaseViewHolder holder, Object o, View view, int position) {
    if (o instanceof CaiErProductBean.ValueBean) {
      CaiErProductBean.ValueBean bean = (CaiErProductBean.ValueBean) o;
      if (view.getId() == R.id.btn_add) {
        bean.showSizeAdd();
      } else if (view.getId() == R.id.btn_cut) {
        bean.showSizeCut();
      }
      rightAdapter.notifyDataSetChanged();
    }
  }


  @OnClick({R.id.btn_car, R.id.btn_commit, R.id.btn_search})
  public void onClick(View v) {
    if (v.getId() == R.id.btn_car) {
      if (select == null || select.size() == 0) {
        toast("购物车是空的");
        return;
      }
      PopUtil
          .showCarPop(this, select, new PopUtil.OnPopWindowSelectListener<CaiErProductBean.ValueBean>() {
            @Override
            public void onItemClick(int position, CaiErProductBean.ValueBean data, View clickFrom) {
              if (position == ACTION_CHANGE) {
                rightAdapter.notifyDataSetChanged();
              } else if (position == ACTION_CLEAR) {
                for (CaiErProductBean.ValueBean item : select) {
                  item.clear();
                }
                select.clear();
                rightAdapter.notifyDataSetChanged();
              } else if (position == ACTION_COMMINT) {
                rightAdapter.notifyDataSetChanged();
                onClick(findViewById(R.id.btn_commit));
              }
            }
          });
    } else if (v.getId() == R.id.btn_commit) {
      List<CaiErProductBean.ValueBean> select = new ArrayList<>();
      for (CaiErProductBean.ValueBean item : ((List<CaiErProductBean.ValueBean>) rightAdapter
          .getData())) {
        if (item.getNum() > 0) {
          select.add(item);
        }
      }
      if (select.size() == 0) {
        toast("未选择任何项目/物品");
        return;
      }
      CarErOrderListActivity.start(this, account, select, facilityno);
    } else if (v.getId() == R.id.btn_search) {
      String input = getInput(R.id.et_search);
      LOG.e("RoomProjectActivity", "onClick.386:" + input);
      rightAdapter.display(input);
    }
  }
  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
     if (resultCode == RESULT_QUIT) {
      finish();
    }
  }
}
