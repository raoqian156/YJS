package com.yskrq.yjs.ui;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.DatePicker;

import com.rq.rvlibrary.BaseAdapter;
import com.rq.rvlibrary.BaseViewHolder;
import com.rq.rvlibrary.EasyViewHolder;
import com.rq.rvlibrary.OnItemClickListener;
import com.rq.rvlibrary.RecyclerUtil;
import com.yskrq.common.BaseActivity;
import com.yskrq.common.OnClick;
import com.yskrq.common.widget.DialogHelper;
import com.yskrq.net_library.BaseBean;
import com.yskrq.net_library.RequestType;
import com.yskrq.yjs.R;
import com.yskrq.yjs.bean.KaiDanBean;
import com.yskrq.yjs.bean.ShouKaBean;
import com.yskrq.yjs.bean.StaticsListBean;
import com.yskrq.yjs.net.HttpManager;
import com.yskrq.yjs.util.StringUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static com.yskrq.yjs.net.Constants.TransCode.BillingSaled;
import static com.yskrq.yjs.net.Constants.TransCode.GetData;
import static com.yskrq.yjs.net.Constants.TransCode.TimeCardSaled;

public class StatisticsActivity extends BaseActivity implements View.OnClickListener {

  public static void start(Context context, int type) {
    Intent intent = new Intent(context, StatisticsActivity.class);
    intent.putExtra("type", type);
    context.startActivity(intent);
  }

  int type = 0;
  BaseAdapter mAdapter;

  @Override
  protected int layoutId() {
    type = getIntent().getIntExtra("type", 0);
    return R.layout.act_page;
  }

  @Override
  protected void initView() {
    if (type == 1) {//我的钟数
      initTitle("我的钟数");
      initTip("名称", "数量", "总提成");
      mAdapter = new BaseAdapter(this, R.layout.item_statis_out, DRStatiscHolder.class);
      Calendar calendar = Calendar.getInstance();
      calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 1);
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      HttpManager.GetData(this, "1", sdf.format(calendar.getTimeInMillis()), sdf
          .format(calendar.getTimeInMillis()));
    } else if (type == 2) {//我的售卡
      initTip("支付方式", "金额");
      initTitle("我的售卡");
      mAdapter = new BaseAdapter(this, R.layout.item_statis_shouka, EasyViewHolder.class) {
        @Override
        public void onBindEasyHolder(BaseViewHolder holder, int position, Object o) {
          super.onBindEasyHolder(holder, position, o);
          ShouKaBean.ValueBean bean = (ShouKaBean.ValueBean) o;
          holder.setItemText(R.id.tv_name, bean.getTransName());
          holder.setItemText(R.id.tv_num, bean.getCreditCZ());
        }
      };
      setString2View(R.id.tv_left, "合计");
    } else if (type == 3) {//开单业绩
      initTitle("开单业绩");
      initTip("项目名称", "数量", "提成金额");
      setString2View(R.id.tv_left, "合计");
      mAdapter = new BaseAdapter(this, R.layout.item_statis_kaidan, EasyViewHolder.class) {
        @Override
        public void onBindEasyHolder(BaseViewHolder holder, int position, Object o) {
          super.onBindEasyHolder(holder, position, o);
          KaiDanBean.ValueBean bean = (KaiDanBean.ValueBean) o;
          holder.setItemText(R.id.tv_name, bean.getItemname());
          holder.setItemText(R.id.tv_num, bean.getItemcount());
          holder.setItemText(R.id.tv_price, bean.getRoyaltyMoney());
        }
      };
    }
    selectShow(R.id.btn_tab_1);
    new RecyclerUtil(mAdapter).set2View((RecyclerView) findViewById(R.id.recycler));

    mAdapter.addOnItemClickListener(new OnItemClickListener() {
      @Override
      public void onItemClick(BaseViewHolder holder, Object o, View view, int position) {
        if (o instanceof StaticsListBean.ValueBean) {
          StaticsListBean.ValueBean bean = (StaticsListBean.ValueBean) o;
          bean.changeOpen();
          mAdapter.notifyDataSetChanged();
          return;
        }
      }
    }, R.id.btn_expand);

    mAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
      @Override
      public void onChanged() {
        super.onChanged();
        if (type == 1) {
          int lun = 0;
          int dian = 0;
          int sun = 0;
          int ticheng = 0;

          for (Object datum : mAdapter.getData()) {
            StaticsListBean.ValueBean bean = (StaticsListBean.ValueBean) datum;
            for (StaticsListBean.ValueBean.ChildrenBean child : bean.getChildren()) {
              if ("轮钟".equals(child.getItem_name())) {
                lun += child.getItem_count_qty();
              } else if ("点钟".equals(child.getItem_name())) {
                dian += child.getItem_count_qty();
              }
              sun += child.getItem_count_qty();
              ticheng += child.getItem_price_sum();
            }
          }
          StringBuffer left = new StringBuffer();
          if (lun > 0) {
            left.append("轮钟数:" + lun);
          }
          if (dian > 0) {
            if (left.length() > 0) {
              left.append("\n");
            }
            left.append("点钟数:" + dian);
          }
          setString2View(R.id.tv_left, left.toString());
          setString2View(R.id.tv_center, "合计:" + sun);
          setString2View(R.id.tv_right, "" + ticheng);
        } else if (type == 2) {
          double sun = 0;
          for (Object datum : mAdapter.getData()) {
            ShouKaBean.ValueBean bean = (ShouKaBean.ValueBean) datum;
            sun = StringUtil.doubleAdd(sun, bean.getNum());
          }
          setString2View(R.id.tv_right, "" + sun);
        } else if (type == 3) {
          double cen = 0;
          double sun = 0;
          for (Object datum : mAdapter.getData()) {
            KaiDanBean.ValueBean bean = (KaiDanBean.ValueBean) datum;
            cen = StringUtil.doubleAdd(cen, bean.getNum());
            sun = StringUtil.doubleAdd(sun, bean.getMoney());
          }
          setString2View(R.id.tv_center, "" + cen);
          setString2View(R.id.tv_right, "" + sun);
        }
      }
    });
  }

  private void initTip(String... con) {
    setString2View(R.id.tip_1, con[0]);
    setString2View(R.id.tip_2, con[1]);
    findViewById(R.id.tip_3).setVisibility(con.length > 2 ? View.VISIBLE : View.GONE);
    if (con.length > 2) {
      setString2View(R.id.tip_3, con[2]);
    }
  }

  @Override
  public <T extends BaseBean> void onResponseSucceed(@NonNull RequestType type, @NonNull T data) {
    if (type.is(GetData)) {
      StaticsListBean bean = (StaticsListBean) data;
      if (bean.getValue() == null || bean.getValue().size() == 0) {
        toast("暂无数据");
        mAdapter.setData(null);
        return;
      }
      mAdapter.setData(bean.getValue());
    }
  }

  @Override
  public <T extends BaseBean> void onResponseError(@NonNull RequestType type, @NonNull T data) {
    if (type.is(TimeCardSaled) && "1".equals(data.getRespCode())) {
      ShouKaBean bean = (ShouKaBean) data;
      mAdapter.setData(bean.getValue());
    } else if (type.is(BillingSaled) && "1".equals(data.getRespCode())) {
      KaiDanBean bean = (KaiDanBean) data;
      mAdapter.setData(bean.getValue());
    } else {
      super.onResponseError(type, data);
      mAdapter.setData(null);
    }
  }

  @OnClick({R.id.btn_tab_1, R.id.btn_tab_2, R.id.btn_tab_3, R.id.tv_start_time_select, R.id.tv_time_select})
  public void onClick(final View v) {
    if (v.getId() == R.id.btn_tab_1 || v.getId() == R.id.btn_tab_2 || v.getId() == R.id.btn_tab_3) {
      selectShow(v.getId());
    }
    if (findViewById(R.id.btn_tab_3).isSelected()) {
      if (v.getId() == R.id.tv_start_time_select || v.getId() == R.id.tv_time_select) {
        DialogHelper.showTimeDialog(getContext(), new DialogHelper.OnDateSetListener() {
          @Override
          public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            String time = year + "-" + (month + 1) + "-" + dayOfMonth;
            setString2View(v.getId(), time);
            toSeekData();
          }
        });
      }
    }
  }

  private void toSeekData() {
    String from = getInput(R.id.tv_start_time_select), today = getInput(R.id.tv_time_select);
    if (type == 1) {
      HttpManager.GetData(this, "3", from, today);
    } else if (type == 2) {
      HttpManager.TimeCardSaled(this, from, today);
    } else if (type == 3) {
      HttpManager.BillingSaled(this, from, today);
    }
  }

  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINESE);

  private void selectShow(int id) {
    findViewById(R.id.btn_tab_1).setSelected(id == R.id.btn_tab_1);
    findViewById(R.id.btn_tab_2).setSelected(id == R.id.btn_tab_2);
    findViewById(R.id.btn_tab_3).setSelected(id == R.id.btn_tab_3);
    findViewById(R.id.time_tip).setVisibility(id == R.id.btn_tab_3 ? View.VISIBLE : View.GONE);
    findViewById(R.id.time_center_tip)
        .setVisibility(id == R.id.btn_tab_3 ? View.VISIBLE : View.GONE);
    findViewById(R.id.tv_start_time_select)
        .setVisibility(id == R.id.btn_tab_3 ? View.VISIBLE : View.GONE);

    Calendar now = Calendar.getInstance();
    String from = "", today = "";
    if (id == R.id.btn_tab_1) {
      now.set(Calendar.DAY_OF_MONTH, now.get(Calendar.DAY_OF_MONTH) - 1);
      setString2View(R.id.tv_time_select, sdf.format(now.getTimeInMillis()));
    } else if (id == R.id.btn_tab_2) {
      setString2View(R.id.tv_time_select, sdf.format(now.getTimeInMillis()));
    } else {
      today = sdf.format(now.getTimeInMillis());
      setString2View(R.id.tv_time_select, sdf.format(now.getTimeInMillis()));
      now.set(Calendar.DAY_OF_MONTH, 1);
      from = sdf.format(now.getTimeInMillis());
      setString2View(R.id.tv_start_time_select, sdf.format(now.getTimeInMillis()));
    }
    if (type == 1) {
      if (id == R.id.btn_tab_1) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        HttpManager.GetData(this, "1", sdf.format(calendar.getTimeInMillis()), sdf
            .format(calendar.getTimeInMillis()));
      } else if (id == R.id.btn_tab_2) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        HttpManager.GetData(this, "2", sdf.format(calendar.getTimeInMillis()), sdf
            .format(calendar.getTimeInMillis()));
      } else if (id == R.id.btn_tab_3) {
        HttpManager.GetData(this, "3", from, today);
      }
    } else if (type == 2) {
      if (id == R.id.btn_tab_1) {
        HttpManager.CardSaled(this, false);
      } else if (id == R.id.btn_tab_2) {
        HttpManager.CardSaled(this, true);
      } else if (id == R.id.btn_tab_3) {
        HttpManager.TimeCardSaled(this, from, today);
      }
    } else if (type == 3) {
      if (id == R.id.btn_tab_1) {
        HttpManager.BillingSaled(this, sdf.format(now.getTimeInMillis()));
      } else if (id == R.id.btn_tab_2) {
        HttpManager.BillingSaled(this, sdf.format(now.getTimeInMillis()));
      } else if (id == R.id.btn_tab_3) {
        HttpManager.BillingSaled(this, from, today);
      }

    }
  }

}
