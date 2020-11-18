package com.yskrq.yjs.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.rq.rvlibrary.BaseAdapter;
import com.rq.rvlibrary.BaseViewHolder;
import com.rq.rvlibrary.EasyViewHolder;
import com.rq.rvlibrary.OnItemClickListener;
import com.rq.rvlibrary.RecyclerUtil;
import com.yskrq.common.BaseActivity;
import com.yskrq.common.OnClick;
import com.yskrq.common.util.LOG;
import com.yskrq.common.widget.DialogHelper;
import com.yskrq.net_library.BaseBean;
import com.yskrq.net_library.RequestType;
import com.yskrq.yjs.R;
import com.yskrq.yjs.bean.RoomProjectListBean;
import com.yskrq.yjs.bean.RoomProjectTypeBean;
import com.yskrq.yjs.net.HttpManager;
import com.yskrq.yjs.widget.PopUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.yskrq.yjs.net.Constants.TransCode.SelectRelaxInvtType;
import static com.yskrq.yjs.net.Constants.TransCode.selectgoods;
import static com.yskrq.yjs.ui.OrderListActivity.RESULT_QUIT;
import static com.yskrq.yjs.widget.PopUtil.ACTION_CHANGE;
import static com.yskrq.yjs.widget.PopUtil.ACTION_CLEAR;
import static com.yskrq.yjs.widget.PopUtil.ACTION_COMMINT;

public class RoomProjectActivity extends BaseActivity implements OnItemClickListener,
                                                                 View.OnClickListener {

  public static void start(Context context, String account, String facilityno) {
    Intent intent = new Intent(context, RoomProjectActivity.class);
    intent.putExtra("account", account);
    intent.putExtra("facilityno", facilityno);
    context.startActivity(intent);
  }

  @Override
  protected int layoutId() {
    return R.layout.act_room_project;
  }

  BaseAdapter leftAdapter, rightAdapter;
  String account, facilityno;
  boolean isNew = false;

  @Override
  protected void initView() {
    account = getIntent().getStringExtra("account");
    facilityno = getIntent().getStringExtra("facilityno");
    isNew = getIntent().getBooleanExtra("isNew", false);
    initTitle(facilityno + ":选择项目");
    leftAdapter = new BaseAdapter(this, R.layout.item_room_left, EasyViewHolder.class) {
      @Override
      public void onBindEasyHolder(BaseViewHolder holder, int position, Object data) {
        super.onBindEasyHolder(holder, position, data);
        RoomProjectTypeBean.RelaxInvtTypePCViewValueBean bean = (RoomProjectTypeBean.RelaxInvtTypePCViewValueBean) data;
        TextView tv = (TextView) holder.getItemView(R.id.tv_name);
        LOG.e("RoomProjectActivity", position + ":" + bean.getDescription());
        tv.setText(bean.getDescription());
        int num = getLeftNum(bean.getTypeCode());
        if (num > 0) {
          holder.getItemView(R.id.tv_num).setVisibility(View.VISIBLE);
          holder.setItemText(R.id.tv_num, num + "");
        } else {
          holder.getItemView(R.id.tv_num).setVisibility(View.GONE);
        }
        try {
          int select = (int) holder.getPassNullable(0);
          holder.itemView.setSelected(position == select);
          tv.setSelected(position == select);
        } catch (Exception e) {

        }
      }
    };
    leftAdapter.addOnItemClickListener(this);
    new RecyclerUtil(leftAdapter).set2View((RecyclerView) findViewById(R.id.recycler_left));
    rightAdapter = new BaseAdapter(this, R.layout.item_room_right, EasyViewHolder.class) {
      @Override
      public void onBindEasyHolder(BaseViewHolder holder, int position, Object data) {
        RoomProjectListBean.ValueBean bean = (RoomProjectListBean.ValueBean) data;
        boolean isTypeFirst = position == 0 || !TextUtils
            .equals(bean.getTypeCode(), ((RoomProjectListBean.ValueBean) rightAdapter
                .getDataItem(position - 1)).getTypeCode());
        TextView head = (TextView) holder.getItemView(R.id.tv_type_head);
        head.setText(getTypeName(bean.getTypeCode()));
        head.setVisibility(isTypeFirst ? View.VISIBLE : View.GONE);
        holder.setItemText(R.id.tv_name, bean.getName());
        holder.setItemText(R.id.tv_price, bean.getPrice());
        holder.setItemText(R.id.tv_num, bean.getShowNum() + "");
        if ("True".equals(bean.getIscalctime())) {
          holder.getItemView(R.id.tv_name_1).setVisibility(View.VISIBLE);
          holder.getItemView(R.id.tv_name_2).setVisibility(View.VISIBLE);
          holder.getItemView(R.id.tv_name_1).setSelected(!TextUtils
              .isEmpty(((RoomProjectListBean.ValueBean) bean).getTechnician()));
          holder.setItemText(R.id.tv_name_1, bean.getButtonName());
          holder.getItemView(R.id.tv_name_2).setSelected(!TextUtils
              .isEmpty(((RoomProjectListBean.ValueBean) bean).getTechnicianSell()));
          holder.setItemText(R.id.tv_name_2, bean.getButtonSellName());
          holder.setItemText(R.id.tv_tech_1, bean.getTechnician() + (TextUtils
              .isEmpty(bean.getTechnician()) ? "" : "号技师"));
          holder.setItemText(R.id.tv_tech_2, bean.getTechnicianSell() + (TextUtils
              .isEmpty(bean.getTechnicianSell()) ? "" : "号技师"));
          if (!TextUtils.isEmpty(bean.getTechnician() + bean.getTechnicianSell())) {
            holder.getItemView(R.id.ll_num).setVisibility(View.GONE);
          } else {
            holder.getItemView(R.id.ll_num).setVisibility(View.VISIBLE);
          }
        } else {
          holder.getItemView(R.id.tv_name_1).setVisibility(View.GONE);
          holder.getItemView(R.id.tv_name_2).setVisibility(View.GONE);
          holder.getItemView(R.id.ll_num).setVisibility(View.VISIBLE);
        }
      }
    };
    rightAdapter
        .addOnItemClickListener(this, R.id.tv_name_1, R.id.tv_name_2, R.id.btn_cut, R.id.btn_add);
    new RecyclerUtil(rightAdapter).set2View((RecyclerView) findViewById(R.id.recycler_right));

    HttpManager.SelectRelaxInvtType(this);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
      ((RecyclerView) findViewById(R.id.recycler_right))
          .setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
              super.onScrolled(recyclerView, dx, dy);
              LOG.e("RoomProjectActivity", "onScrolled.117:" + dy);
              LinearLayoutManager llm = (LinearLayoutManager) recyclerView.getLayoutManager();
              int firstType = llm.findLastVisibleItemPosition();
              if (dy < 0) {
                firstType = llm.findFirstVisibleItemPosition();
              }
              toShow(firstType);
            }
          });
    rightAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
      @Override
      public void onChanged() {
        super.onChanged();
        List<RoomProjectListBean.ValueBean> lists = rightAdapter.getData();
        select.clear();
        for (RoomProjectListBean.ValueBean item : lists) {
          if (item.isSelect() || item.getShowNum() > 0) {
            select.add(item);
          }
        }
        refuseView();
      }
    });
    rightAdapter.setOnAttachedToBottomListener(new BaseAdapter.OnAttachedToBottomListener() {
      @Override
      public void onAttachedToTop() {
        LOG.e("RoomProjectActivity", "onAttachedToTop");
        leftAdapter.setPassData(0);
        leftAdapter.notifyDataSetChanged();
      }

      @Override
      public void onAttachedToBottom(int position) {
        LOG.e("RoomProjectActivity", "onAttachedToBottom.139:" + position);
      }
    });
    rightAdapter.setDisplay(new BaseAdapter.DisplayOption() {
      @Override
      public boolean show(Object data, Object rule, int position) {
        RoomProjectListBean.ValueBean bean = (RoomProjectListBean.ValueBean) data;
        LOG.e("RoomProjectActivity", "show.181:" + bean.getName() + "   <" + ((String) rule) + ">");
        return bean.getName().contains((String) rule);
      }
    });
  }

  private int getLeftNum(String typeCode) {
    if (jishu.containsKey(typeCode)) {
      return jishu.get(typeCode);
    }
    return 0;
  }

  Map<String, Integer> jishu = new HashMap<>();//选中物品数目

  private void refuseView() {
    jishu.clear();
    int sum = 0;
    for (RoomProjectListBean.ValueBean item : select) {
      if (jishu.containsKey(item.getTypeCode())) {
        int newNum = jishu.get(item.getTypeCode()) + item.getShowNum();
        jishu.put(item.getTypeCode(), newNum);
      } else {
        jishu.put(item.getTypeCode(), item.getShowNum());
      }
      sum += item.getShowNum();
    }

    setString2View(R.id.tv_shop_car_num, sum + "");
    findViewById(R.id.tv_shop_car_num).setVisibility(sum > 0 ? View.VISIBLE : View.GONE);
    leftAdapter.notifyDataSetChanged();
  }

  List<RoomProjectListBean.ValueBean> select = new ArrayList<>();
  long clickLeftTime = 0;

  //滑动右侧列表到相应位置 todo 显示规则与 toShow 不对称
  private void toSlidingRight(RoomProjectTypeBean.RelaxInvtTypePCViewValueBean o) {
    clickLeftTime = System.currentTimeMillis();
    List<RoomProjectListBean.ValueBean> right = rightAdapter.getData();
    int toSeePosition = 0;
    for (int i = 0; i < right.size(); i++) {
      if (TextUtils.equals(right.get(i).getTypeCode(), o.getTypeCode())) {
        toSeePosition = i;
        break;
      }
    }
    RecyclerView rv = findViewById(R.id.recycler_right);
    LinearLayoutManager llm = (LinearLayoutManager) rv.getLayoutManager();
    int first = llm.findFirstVisibleItemPosition();
    int last = llm.findLastVisibleItemPosition();
    int showSize = last - first + 1;//可见条数
    LOG.e("RoomProjectActivity", "toSlidingRight.showSize:" + showSize);
    LOG.e("RoomProjectActivity", "toSlidingRight.toSeePosition:" + toSeePosition);
    if (first > toSeePosition) {
      rv.scrollToPosition(toSeePosition);
    } else {
      rv.scrollToPosition(Math.min(toSeePosition + showSize - 1, rightAdapter.getDataSize() - 1));
    }
  }

  //滑动左侧侧列表到相应位置 todo 显示规则与 toSlidingRight 不对称
  private void toShow(int firstType) {
    if (System.currentTimeMillis() - clickLeftTime < 400) {
      LOG.e("RoomProjectActivity", "toShow.245:");
      return;
    }
    RoomProjectListBean.ValueBean dataItem = ((RoomProjectListBean.ValueBean) rightAdapter
        .getDataItem(firstType));
    if (dataItem == null || dataItem.getTypeCode() == null) return;
    String typeCode = dataItem.getTypeCode();
    int selectPosition = 0;
    List<RoomProjectTypeBean.RelaxInvtTypePCViewValueBean> left = leftAdapter.getData();
    for (int i = 0; i < left.size(); i++) {
      if (typeCode.equals(left.get(i).getTypeCode())) {
        selectPosition = i;
      }
    }
    leftAdapter.setPassData(selectPosition);
    leftAdapter.notifyDataSetChanged();
  }

  private String getTypeName(String typeCode) {
    if (typeCode == null) return "";
    if (typeArr.size() > 0) {
      for (RoomProjectTypeBean.RelaxInvtTypePCViewValueBean item : typeArr) {
        if (typeCode.equals(item.getTypeCode())) {
          return item.getDescription();
        }
      }
    }
    return typeCode;
  }

  List<RoomProjectTypeBean.RelaxInvtTypePCViewValueBean> typeArr = new ArrayList<>();

  @Override
  public <T extends BaseBean> void onResponseSucceed(@NonNull RequestType type, @NonNull T data) {
    super.onResponseSucceed(type, data);
    if (type.is(SelectRelaxInvtType)) {
      RoomProjectTypeBean bean = (RoomProjectTypeBean) data;
      leftAdapter.setData(bean.getRelaxInvtTypePCView_value());
      typeArr.clear();
      typeArr.addAll(bean.getRelaxInvtTypePCView_value());
      HttpManager.selectgoods(this);
    } else if (type.is(selectgoods)) {
      RoomProjectListBean bean = (RoomProjectListBean) data;
      rightAdapter.setData(bean.getValue());
    }
  }

  @Override
  public void onItemClick(BaseViewHolder holder, Object o, View view, int position) {
    if (o instanceof RoomProjectTypeBean.RelaxInvtTypePCViewValueBean) {//点击了左侧列表
      leftAdapter.setPassData(position);
      leftAdapter.notifyDataSetChanged();
      toSlidingRight((RoomProjectTypeBean.RelaxInvtTypePCViewValueBean) o);
    } else if (o instanceof RoomProjectListBean.ValueBean) {
      RoomProjectListBean.ValueBean bean = (RoomProjectListBean.ValueBean) o;
      if (view.getId() == R.id.btn_add) {
        bean.showSizeAdd();
      } else if (view.getId() == R.id.btn_cut) {
        bean.showSizeCut();
      } else if (view.getId() == R.id.tv_name_1) {
        if (TextUtils.isEmpty(bean.getTechnician())) {
          dealBean = bean;
          isTech = true;
          SelectTechWindowActivity.start(this, bean.getItemNo(), REQUEST_CODE_TECH);
        } else {
          bean.setTechnician("");
        }
      } else if (view.getId() == R.id.tv_name_2) {
        if (TextUtils.isEmpty(bean.getTechnicianSell())) {
          isTech = false;
          dealBean = bean;
          SelectTechWindowActivity.start(this, bean.getItemNo(), REQUEST_CODE_TECH);
        } else {
          bean.setTechnicianSell("");
        }
      }
      LOG.e("RoomProjectActivity", "onItemClick.126:");
      rightAdapter.notifyDataSetChanged();
    }
  }

  RoomProjectListBean.ValueBean dealBean;
  boolean isTech = true;//点钟弹窗
  public final static int REQUEST_CODE_TECH = 0x0010;

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    LOG.e("RoomProjectActivity", "onActivityResult.296:" + resultCode);
    if (requestCode == REQUEST_CODE_TECH && resultCode == Activity.RESULT_OK) {
      String sel = SelectTechWindowActivity.getSelectNumsFromIntent(data);
      LOG.e("RoomProjectActivity", "onActivityResult.304:" + sel);
      if (isTech) {
        dealBean.setTechnician(sel);
      } else {
        dealBean.setTechnicianSell(sel);
      }
      rightAdapter.notifyDataSetChanged();
    } else if (resultCode == RESULT_QUIT) {
      finish();
    }
  }

  @Override
  public void finish() {
    if (isNew) {
      DialogHelper.showRemind(this, "是否放弃本次开台", new DialogHelper.DialogConfirmListener() {
        @Override
        public void onSure() {
          RoomProjectActivity.super.finish();
        }

        @Override
        public void onCancel() {

        }
      });
      return;
    }
    OrderDetailActivity.start(this, account, facilityno);
    super.finish();
  }

  @OnClick({R.id.btn_car, R.id.btn_commit, R.id.btn_search})
  public void onClick(View v) {
    if (v.getId() == R.id.btn_car) {
      if (select == null || select.size() == 0) {
        toast("购物车是空的");
        return;
      }
      PopUtil
          .showCarPop(this, select, new PopUtil.OnPopWindowSelectListener<RoomProjectListBean.ValueBean>() {
            @Override
            public void onItemClick(int position, RoomProjectListBean.ValueBean data,
                                    View clickFrom) {
              if (position == ACTION_CHANGE) {
                rightAdapter.notifyDataSetChanged();
              } else if (position == ACTION_CLEAR) {
                for (RoomProjectListBean.ValueBean item : select) {
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
      List<RoomProjectListBean.ValueBean> select = new ArrayList<>();
      for (RoomProjectListBean.ValueBean item : ((List<RoomProjectListBean.ValueBean>) rightAdapter
          .getData())) {
        if (item.getShowNum() > 0) {
          select.add(item);
        }
      }
      if (select.size() == 0) {
        toast("未选择任何项目/物品");
        return;
      }
      OrderListActivity.start(this, account, select, facilityno);
    } else if (v.getId() == R.id.btn_search) {
      String input = getInput(R.id.et_search);
      LOG.e("RoomProjectActivity", "onClick.386:" + input);
      rightAdapter.display(input);
    }
  }
}
