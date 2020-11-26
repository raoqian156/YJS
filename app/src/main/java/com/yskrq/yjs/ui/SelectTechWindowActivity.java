package com.yskrq.yjs.ui;

import android.app.Activity;
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
import com.yskrq.yjs.bean.DianTypeBean;
import com.yskrq.yjs.bean.TechListBean;
import com.yskrq.yjs.bean.YuYueBean;
import com.yskrq.yjs.net.HttpManager;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.yskrq.yjs.net.Constants.TransCode.CheckTechDestineStatus;
import static com.yskrq.yjs.net.Constants.TransCode.GetRelaxCdTechFac;
import static com.yskrq.yjs.net.Constants.TransCode.SelectType;
import static com.yskrq.yjs.net.Constants.TransCode.getWorkingTech;


public class SelectTechWindowActivity extends BaseActivity implements View.OnClickListener,
                                                                      OnItemClickListener {
  public static void start(final Activity context, final String itemno, final int requestCode) {
    HttpManager
        .checkPermission("RelaxOrderClock", "点钟", context, new HttpManager.OnPermissionCheck() {
          @Override
          public void onPermissionOk() {

            Intent intent = new Intent(context, SelectTechWindowActivity.class);
            intent.putExtra("itemno", itemno);
            context.startActivityForResult(intent, requestCode);
          }

          @Override
          public void onPermissionError(String rea) {

          }
        });
  }

  @Override
  protected int layoutId() {
    return R.layout.pop_tech_select;
  }

  BaseAdapter leftAdapter, rightAdapter;
  String itemno;

  @Override
  protected void initView() {
    initTitle("选择项目");
    initRecyclerView();
    itemno = getIntent().getStringExtra("itemno");
    HttpManager.SelectType(SelectTechWindowActivity.this);
    HttpManager.getWorkingTech(SelectTechWindowActivity.this, "", itemno);
  }

  @Override
  public <T extends BaseBean> void onResponseSucceed(@NonNull RequestType type, @NonNull T data) {
    super.onResponseSucceed(type, data);
    if (type.is(SelectType)) {
      DianTypeBean bean = (DianTypeBean) data;
      leftAdapter.setData(bean.getType_Value());
    } else if (type.is(getWorkingTech)) {
      TechListBean bean = (TechListBean) data;
      rightAdapter.setData(bean.getValue());
    } else if (type.is(CheckTechDestineStatus)) {
      LOG.e("SelectTechWindowActivity", "CheckTechDestineStatus.76:" + data.getRespMsg());
      if ("1".equals(data.getRespMsg())) {
        toast("该技师有预约");
      } else {
        HttpManager.GetRelaxCdTechFac(this, searchBrandNo);
      }
    } else if (type.is(GetRelaxCdTechFac)) {
      YuYueBean bean = (YuYueBean) data;
      if (!TextUtils.isEmpty(bean.getValue())) {
        DialogHelper.showRemind(this, bean.getValue(), null);
      }
    }
  }

  private void initRecyclerView() {
    leftAdapter = new BaseAdapter(this, R.layout.item_tech_left, EasyViewHolder.class) {
      @Override
      public void onBindEasyHolder(BaseViewHolder holder, int position, Object data) {
        super.onBindEasyHolder(holder, position, data);
        DianTypeBean.TypeValueBean bean = (DianTypeBean.TypeValueBean) data;
        TextView tv = (TextView) holder.getItemView(R.id.tv_name);
        LOG.e("SelectTechWindowActivity", position + ":" + bean.getDescription());
        tv.setText(bean.getDescription());
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
    rightAdapter = new BaseAdapter(this, R.layout.item_work_tech, EasyViewHolder.class) {
      @Override
      public void onBindEasyHolder(BaseViewHolder holder, int position, Object bean) {
        TechListBean.ValueBean item = (TechListBean.ValueBean) bean;
        boolean isTypeFirst = position == 0 || !TextUtils
            .equals(item.getBrandnotype(), ((TechListBean.ValueBean) rightAdapter
                .getDataItem(position - 1)).getBrandnotype());
        TextView head = (TextView) holder.getItemView(R.id.tv_type_head);
        head.setText(getTypeName(item.getBrandnotype()));
        head.setVisibility(isTypeFirst ? View.VISIBLE : View.GONE);
        holder.setItemText(R.id.tv_name, item.getChambercode());
        holder.setItemText(R.id.btn_sex, "0".equals(item.getSexid()) ? "女" : "男");
        holder.setItemText(R.id.tv_name, item.getBrandno() + "");
        holder.setItemText(R.id.tv_name_1, "姓名:" + item.getDescription());
        holder.setItemText(R.id.tv_name_2, item.getJobstatus());
        holder.setItemText(R.id.btn_yuyue, item.getDestinestatus());
        holder.getItemView(R.id.iv_select).setSelected(item.isSelect());
        if ("在上点".equals(item.getJobstatus())) {
          holder.getItemView(R.id.ll_texh).setBackgroundResource(R.color.color_yellow_bg);
        } else {
          holder.getItemView(R.id.ll_texh).setBackground(null);
        }
        //        if ("True".equals(item.getIscalctime())) {
        //          holder.getItemView(R.id.tv_name_1).setVisibility(View.VISIBLE);
        //          holder.getItemView(R.id.tv_name_2).setVisibility(View.VISIBLE);
        //          holder.setItemText(R.id.tv_name_1, item.getButtonName());
        //          holder.setItemText(R.id.tv_name_2, item.getButtonSellName());
        //        } else {
        //          holder.getItemView(R.id.tv_name_1).setVisibility(View.GONE);
        //          holder.getItemView(R.id.tv_name_2).setVisibility(View.GONE);
        //        }
      }
    };
    rightAdapter.addOnItemClickListener(this);
    new RecyclerUtil(rightAdapter).set2View((RecyclerView) findViewById(R.id.recycler_right));

    HttpManager.SelectRelaxInvtType(this);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
      ((RecyclerView) findViewById(R.id.recycler_right))
          .setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
              super.onScrolled(recyclerView, dx, dy);
              LOG.e("SelectTechWindowActivity", "onScrolled.117:" + dy);
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
        List<TechListBean.ValueBean> lists = rightAdapter.getData();
        select.clear();
        for (TechListBean.ValueBean item : lists) {
          if (item.isSelect()) {
            select.add(item);
          }
        }
        refuseView();
      }
    });
    rightAdapter.setOnAttachedToBottomListener(new BaseAdapter.OnAttachedToBottomListener() {
      @Override
      public void onAttachedToTop() {
        LOG.e("SelectTechWindowActivity", "onAttachedToTop");
        leftAdapter.setPassData(0);
        leftAdapter.notifyDataSetChanged();
      }

      @Override
      public void onAttachedToBottom(int position) {
        LOG.e("SelectTechWindowActivity", "onAttachedToBottom.139:" + position);
      }
    });
    rightAdapter.setDisplay(new BaseAdapter.DisplayOption<TechListBean.ValueBean>() {
      @Override
      public boolean show(TechListBean.ValueBean bean, Object rule, int position) {
        return TextUtils.equals(bean.getBrandno(), (String) rule);
      }
    });
  }

  private void refuseView() {
    //    jishu.clear();
    //    int sum = 0;
    //    for (TechListBean.ValueBean item : select) {
    //      if (jishu.containsKey(item.getBrandnotype())) {
    //        int newNum = jishu.get(item.getBrandnotype()) + item.getShowNum();
    //        jishu.put(item.getTypeCode(), newNum);
    //      } else {
    //        jishu.put(item.getTypeCode(), item.getShowNum());
    //      }
    //      sum += item.getShowNum();
    //    }
    //    setString2View( R.id.tv_shop_car_num,sum + "");
    //    leftAdapter.notifyDataSetChanged();
  }

  List<TechListBean.ValueBean> select = new ArrayList<>();

  //滑动右侧列表到相应位置 todo 显示规则与 toShow 不对称
  private void toSlidingRight(DianTypeBean.TypeValueBean o) {
    List<TechListBean.ValueBean> right = rightAdapter.getData();
    int toSeePosition = 0;
    for (int i = 0; i < right.size(); i++) {
      if (TextUtils.equals(right.get(i).getBrandnotype(), o.getCode())) {
        toSeePosition = i;
        break;
      }
    }
    RecyclerView rv = findViewById(R.id.recycler_right);
    LinearLayoutManager llm = (LinearLayoutManager) rv.getLayoutManager();
    int first = llm.findFirstVisibleItemPosition();
    int last = llm.findLastVisibleItemPosition();
    int showSize = last - first + 1;//可见条数
    if (first > toSeePosition) {
      rv.scrollToPosition(toSeePosition);
    } else if (last < toSeePosition - showSize) {
      rv.scrollToPosition(toSeePosition + showSize);
    } else if (toSeePosition > first && toSeePosition < last) {
      rv.scrollToPosition(toSeePosition - showSize);
    }
  }

  //滑动左侧侧列表到相应位置 todo 显示规则与 toSlidingRight 不对称
  private void toShow(int firstType) {
    TechListBean.ValueBean dataItem = ((TechListBean.ValueBean) rightAdapter
        .getDataItem(firstType));
    if (dataItem == null || dataItem.getBrandnotype() == null) return;
    String typeCode = dataItem.getBrandnotype();
    int selectPosition = 0;
    List<DianTypeBean.TypeValueBean> left = leftAdapter.getData();
    for (int i = 0; i < left.size(); i++) {
      if (typeCode.equals(left.get(i).getCode())) {
        selectPosition = i;
      }
    }
    leftAdapter.setPassData(selectPosition);
    leftAdapter.notifyDataSetChanged();
  }

  private String getTypeName(String typeCode) {
    if (typeCode == null) return "";
    for (DianTypeBean.TypeValueBean item : (List<DianTypeBean.TypeValueBean>) leftAdapter
        .getData()) {
      if (typeCode.equals(item.getCode())) {
        return item.getDescription();
      }
    }
    return typeCode;
  }

  String searchBrandNo = "";

  @Override
  public void onItemClick(BaseViewHolder holder, Object o, View view, int position) {
    if (o instanceof DianTypeBean.TypeValueBean) {//点击了左侧列表
      leftAdapter.setPassData(position);
      leftAdapter.notifyDataSetChanged();
      toSlidingRight((DianTypeBean.TypeValueBean) o);
    } else if (o instanceof TechListBean.ValueBean) {
      TechListBean.ValueBean bean = (TechListBean.ValueBean) o;
      bean.setSelect(!bean.isSelect());
      if (!"".equals(bean.getExpendtimeid1())) {
        String con = "技师" + bean.getBrandno() + " 正在房间:" + bean
            .getFacilitynoname() + "上钟,预计还需要:" + bean.getExpendtimeid1() + "分钟下钟";
        DialogHelper.showRemind(SelectTechWindowActivity.this, con, null);
      }
      if (bean.isSelect()) {
        searchBrandNo = bean.getBrandno();
        HttpManager.CheckTechDestineStatus(SelectTechWindowActivity.this, bean.getBrandno());
      }
      //      if (view.getId() == R.id.btn_add) {
      //        bean.showSizeAdd();
      //      } else if (view.getId() == R.id.btn_cut) {
      //        bean.showSizeCut();
      //      } else if (view.getId() == R.id.tv_name_1) {
      //        toast("点钟" + bean.getName());
      //      } else if (view.getId() == R.id.tv_name_2) {
      //        toast("阿选中" + bean.getName());
      //      }
      LOG.e("SelectTechWindowActivity", "onItemClick.126:");
      rightAdapter.notifyDataSetChanged();
    }
  }

  @OnClick({R.id.bg, R.id.btn_commit, R.id.btn_search})
  public void onClick(View v) {
    if (v.getId() == R.id.bg) {
      finish();
    } else if (v.getId() == R.id.btn_commit) {
      if (select != null && select.size() > 0) {
        StringBuffer se = new StringBuffer();
        for (TechListBean.ValueBean item : select) {
          LOG.bean("onClick", item);
          se.append("," + item.getBrandno());
        }
        LOG.e("SelectTechWindowActivity", "onClick.308:" + se.toString());
        if (se.toString().length() > 0) {
          Intent intent = new Intent();
          intent.putExtra("res", se.toString().substring(1));
          setResult(Activity.RESULT_OK, intent);
          finish();
        } else {
          toast("未选择数据");
        }
      } else {
        LOG.e("SelectTechWindowActivity", "onClick.318:");
      }
    } else if (v.getId() == R.id.btn_search) {
      rightAdapter.display(getInput(R.id.et_search));
    }
  }

  public static String getSelectNumsFromIntent(Intent intent) {
    if (intent != null) {
      return intent.getStringExtra("res");
    }
    return "";
  }
}
