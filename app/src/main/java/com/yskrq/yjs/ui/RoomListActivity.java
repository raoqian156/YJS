package com.yskrq.yjs.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.rq.rvlibrary.BaseAdapter;
import com.rq.rvlibrary.BaseViewHolder;
import com.rq.rvlibrary.EasyViewHolder;
import com.rq.rvlibrary.OnItemClickListener;
import com.rq.rvlibrary.RecyclerUtil;
import com.yskrq.common.AppInfo;
import com.yskrq.common.BaseActivity;
import com.yskrq.common.OnClick;
import com.yskrq.common.bean.TecColorBean;
import com.yskrq.common.util.LOG;
import com.yskrq.net_library.BaseBean;
import com.yskrq.net_library.HttpInnerListener;
import com.yskrq.net_library.RequestType;
import com.yskrq.yjs.R;
import com.yskrq.yjs.bean.RoomBean;
import com.yskrq.yjs.bean.RoomLeftBean;
import com.yskrq.yjs.net.HttpManager;
import com.yskrq.yjs.util.DoubleClickHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import static com.yskrq.yjs.net.Constants.TransCode.SelectCountNum;
import static com.yskrq.yjs.net.Constants.TransCode.SelectRestaurantType;
import static com.yskrq.yjs.net.Constants.TransCode.SelectRestaurantView;
import static com.yskrq.yjs.ui.SelectNumWindowActivity.RESULT_QUIT;

public class RoomListActivity extends BaseActivity implements View.OnClickListener,
                                                              OnItemClickListener {
  public static void start(Context context) {
    Intent intent = new Intent(context, RoomListActivity.class);
    context.startActivity(intent);
  }

  @Override
  protected int layoutId() {
    return R.layout.act_room_list;
  }

  @Override
  protected void initView() {
    initTitle("房态");
    recyclerLeft = findViewById(R.id.recycler_left);
    leftAdapter = new BaseAdapter(this, R.layout.item_room_left, EasyViewHolder.class) {
      @Override
      public void onBindEasyHolder(BaseViewHolder holder, int position, Object data) {
        super.onBindEasyHolder(holder, position, data);
        RoomLeftBean.FixingTypeValueBean bean = (RoomLeftBean.FixingTypeValueBean) data;
        TextView tv = (TextView) holder.getItemView(R.id.tv_name);
        LOG.e("RoomProjectActivity", position + ":" + bean.getDescription());
        tv.setText(bean.getDescription());
        holder.getItemView(R.id.tv_num).setVisibility(View.GONE);
        try {
          int select = (int) holder.getPassNullable(0);
          holder.itemView.setSelected(position == select);
          tv.setSelected(position == select);
        } catch (Exception e) {

        }
      }
    };
    new RecyclerUtil(leftAdapter).set2View(recyclerLeft);
    rightAdapter = new BaseAdapter(this, R.layout.item_room, EasyViewHolder.class) {
      @Override
      public void onBindEasyHolder(BaseViewHolder holder, int position, Object o) {
        super.onBindEasyHolder(holder, position, o);
        RoomBean.FixingViewValueBean bean = (RoomBean.FixingViewValueBean) o;

        GradientDrawable gd = new GradientDrawable();//创建drawable
        gd.setColor(Color.parseColor(bean.getColorCode(getNullablePass(0))));
        gd.setCornerRadius(10);
        holder.itemView.setBackground(gd);
        holder.getItemView(R.id.iv_has)
              .setVisibility("0".equals(bean.getStatus()) ? View.VISIBLE : View.GONE);
        holder.setItemText(R.id.tv_name, bean.getDescription());
        holder.setItemText(R.id.tv_left_name, bean.getMostPerson());
      }
    };
    List<TecColorBean.ValueBean> color = AppInfo.getColors(getContext());
    Map<String, String> colors = new HashMap<>();
    for (TecColorBean.ValueBean valueBean : color) {
      colors.put(valueBean.getEntry(), valueBean.getData());
    }
    rightAdapter.setPassData(colors);
    new RecyclerUtil(rightAdapter).row(4)
                                  .set2View((RecyclerView) findViewById(R.id.recycler_right));

    HttpManager.SelectRestaurantType(this);
    HttpManager.SelectCountNum(this);
    HttpManager.SelectRestaurantView(this);
    rightAdapter.setDisplay(new BaseAdapter.DisplayOption() {
      @Override
      public boolean show(Object o, Object r, int position) {
        RoomBean.FixingViewValueBean bean = (RoomBean.FixingViewValueBean) o;
        if (r instanceof String) {
          String type = (String) r;
          return TextUtils.equals(type, bean.getTypeCode());
        }
        int rule = (int) r;
        if (rule == TYPE_USE) {
          return "0".equals(bean.getStatus());
        } else if (rule == TYPE_Empty) {
          return "1".equals(bean.getStatus());
        } else if (rule == TYPE_DUTY) {
          return "1".equals(bean.getCleanId());
        } else if (rule == TYPE_YUDING) {
          return "K".equals(bean.getResourceType());
        }
        return true;
      }
    });
    rightAdapter.addOnItemClickListener(this);
    leftAdapter.addOnItemClickListener(this);
  }

  private final static int TYPE_USE = 1;
  private final static int TYPE_Empty = 2;
  private final static int TYPE_DUTY = 3;
  private final static int TYPE_YUDING = 4;

  BaseAdapter leftAdapter, rightAdapter;
  RecyclerView recyclerLeft;

  @Override
  public <T extends BaseBean> void onResponseSucceed(@NonNull RequestType type, @NonNull T data) {
    super.onResponseSucceed(type, data);
    if (type.is(SelectRestaurantType)) {
      RoomLeftBean bean = (RoomLeftBean) data;
      leftAdapter.setData(bean.getFixingType_value());
    } else if (type.is(SelectRestaurantView)) {
      RoomBean bean = (RoomBean) data;
      rightAdapter.setData(bean.getFixingView_value());
    } else if (type.is(SelectCountNum)) {
      String[] datas = (String[]) type.getMore();
      setString2View(R.id.tv_shop_car_num, datas[0]);
      setString2View(R.id.tv_shop_car_num1, datas[1]);
      setString2View(R.id.tv_shop_car_num2, datas[2]);
      setString2View(R.id.tv_shop_car_num3, datas[3]);
      setString2View(R.id.tv_shop_car_num4, datas[4]);
    }
  }

  @OnClick({R.id.btn_refuse, R.id.btn_back, R.id.btn_empty, R.id.btn_all, R.id.btn_use, R.id.btn_duty, R.id.btn_duty4})
  public void onClick(View v) {
    if (v.getId() == R.id.btn_refuse) {
      HttpManager.SelectRestaurantType(this);
      HttpManager.SelectCountNum(this);
      HttpManager.SelectRestaurantView(this);
      return;
    }
    if (v.getId() == R.id.btn_back) {
      finish();
      return;
    }
    leftAdapter.setPassData(-1);
    leftAdapter.notifyDataSetChanged();
    recyclerLeft.setVisibility(v.getId() == R.id.btn_all ? View.VISIBLE : View.GONE);
    if (v.getId() == R.id.btn_empty) {
      rightAdapter.display(TYPE_Empty);
    } else if (v.getId() == R.id.btn_all) {
      rightAdapter.display(null);
      HttpManager.SelectRestaurantType(this);
      HttpManager.SelectCountNum(this);
      HttpManager.SelectRestaurantView(this);
    } else if (v.getId() == R.id.btn_use) {
      rightAdapter.display(TYPE_USE);
    } else if (v.getId() == R.id.btn_duty) {
      rightAdapter.display(TYPE_DUTY);
    } else if (v.getId() == R.id.btn_duty4) {
      rightAdapter.display(TYPE_YUDING);
    }
  }


  @Override
  public void onItemClick(BaseViewHolder holder, Object o, View view, int position) {
    if (o instanceof RoomLeftBean.FixingTypeValueBean) {
      RoomLeftBean.FixingTypeValueBean bean = (RoomLeftBean.FixingTypeValueBean) o;
      leftAdapter.setPassData(position);
      leftAdapter.notifyDataSetChanged();
      rightAdapter.display(bean.getTypeCode());
    } else {
      if (!DoubleClickHelper.isDoubleClick(view)) {
        return;
      }
      final RoomBean.FixingViewValueBean bean = (RoomBean.FixingViewValueBean) o;
      boolean isUsed = "0".equals(bean.getStatus());
      if (isUsed) {
        HttpManager.selectzhtddan(new HttpInnerListener() {
          @Override
          public void onString(String json) {
            BaseBean b = new Gson().fromJson(json, BaseBean.class);
            if ("0".equals(b.getRespCode())) {
              String account = BaseBean.getStrInJson("Account", json);
              OrderDetailActivity.startCanAdd(RoomListActivity.this, account, bean.getFacilityNo());
            } else {
              toast("当前房间无数据");
            }
          }

          @Override
          public void onEmptyResponse() {

          }
        }, RoomListActivity.this, bean.getFacilityNo());
      } else {
        SelectNumWindowActivity.start(RoomListActivity.this, bean.getFacilityNo());
      }
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
