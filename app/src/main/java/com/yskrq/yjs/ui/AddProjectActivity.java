package com.yskrq.yjs.ui;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.rq.rvlibrary.BaseAdapter;
import com.rq.rvlibrary.BaseViewHolder;
import com.rq.rvlibrary.EasyViewHolder;
import com.rq.rvlibrary.OnItemClickListener;
import com.rq.rvlibrary.RecyclerUtil;
import com.yskrq.common.BaseActivity;
import com.yskrq.common.OnClick;
import com.yskrq.net_library.BaseBean;
import com.yskrq.net_library.RequestType;
import com.yskrq.yjs.R;
import com.yskrq.yjs.bean.HavedRoomBean;
import com.yskrq.yjs.bean.RoomProjectBean;
import com.yskrq.yjs.net.HttpManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static com.yskrq.yjs.net.Constants.TransCode.GetFixingOnUse;
import static com.yskrq.yjs.net.Constants.TransCode.GetServiceItem;
import static com.yskrq.yjs.net.Constants.TransCode.InsertService;

public class AddProjectActivity extends BaseActivity implements OnItemClickListener,
                                                                View.OnClickListener {

  public static void start(Context context) {
    Intent intent = new Intent(context, AddProjectActivity.class);
    context.startActivity(intent);
  }

  @Override
  protected int layoutId() {
    return R.layout.act_add_project;
  }

  BaseAdapter topAdapter, bottomAdapter;

  @Override
  protected void initView() {
    initTitle("呼叫服务");
    topAdapter = new BaseAdapter(this, R.layout.item_add_pro_top, EasyViewHolder.class) {
      @Override
      public void onBindEasyHolder(BaseViewHolder holder, int position, Object data) {
        super.onBindEasyHolder(holder, position, data);
        HavedRoomBean.ValueBean bean = (HavedRoomBean.ValueBean) data;
        TextView tv = (TextView) holder.itemView;
        tv.setText(bean.getDescription());
        try {
          int select = (int) holder.getPassNullable(0);
          tv.setSelected(position == select);
        } catch (Exception e) {

        }
      }
    };
    topAdapter.setPassData(0);
    bottomAdapter = new BaseAdapter(this, R.layout.item_add_pro_bottom, EasyViewHolder.class) {
      @Override
      public void onBindEasyHolder(BaseViewHolder holder, int position, Object data) {
        super.onBindEasyHolder(holder, position, data);
        RoomProjectBean.ValueBean bean = (RoomProjectBean.ValueBean) data;
        TextView tv = (TextView) holder.itemView;
        tv.setText(bean.getName());
        try {
          Set<Integer> select = (Set<Integer>) holder.getPassNullable(0);
          tv.setSelected(select.contains(position));
        } catch (Exception e) {

        }
      }
    };
    new RecyclerUtil(topAdapter).row(4).set2View((RecyclerView) findViewById(R.id.recycler_top));
    new RecyclerUtil(bottomAdapter).set2View((RecyclerView) findViewById(R.id.recycler_bottom));
    topAdapter.addOnItemClickListener(this);
    bottomAdapter.addOnItemClickListener(this);
    HttpManager.GetFixingOnUse(this);
    HttpManager.GetServiceItem(this);
  }

  @Override
  public <T extends BaseBean> void onResponseSucceed(@NonNull RequestType type, @NonNull T data) {
    super.onResponseSucceed(type, data);
    if (type.is(GetFixingOnUse)) {
      HavedRoomBean bean = (HavedRoomBean) data;
      topAdapter.setData(bean.getValue());
    } else if (type.is(GetServiceItem)) {
      RoomProjectBean bean = (RoomProjectBean) data;
      bottomAdapter.setData(bean.getValue());
    } else if (type.is(InsertService)) {
      toast(data.getRespMsg());
      finish();
    }
  }

  @Override
  public void onItemClick(BaseViewHolder holder, Object o, View view, int position) {
    if (((View) view.getParent()).getId() == R.id.recycler_top) {
      topAdapter.setPassData(position);
      topAdapter.notifyDataSetChanged();
    } else if (((View) view.getParent()).getId() == R.id.recycler_bottom) {
      Set<Integer> select;
      try {
        select = (Set<Integer>) bottomAdapter.getNullablePass(0);
        if (select == null) {
          select = new HashSet<>();
        }
      } catch (Exception e) {
        select = new HashSet<>();
      }
      if (select.contains(position)) {
        select.remove(position);
      } else {
        select.add(position);
      }
      bottomAdapter.setPassData(select);
      bottomAdapter.notifyDataSetChanged();
    }
  }

  @OnClick({R.id.btn_send})
  public void onClick(View v) {
    if (v.getId() == R.id.btn_send) {
      List<RoomProjectBean.ValueBean> selPro = new ArrayList<>();
      HavedRoomBean.ValueBean selectRoom = null;
      try {
        selectRoom = (HavedRoomBean.ValueBean) topAdapter
            .getDataItem((Integer) topAdapter.getNullablePass(0));
        Set<Integer> select = (Set<Integer>) bottomAdapter.getNullablePass(0);
        for (Integer integer : select) {
          selPro.add((RoomProjectBean.ValueBean) bottomAdapter.getDataItem(integer));
        }
      } catch (Exception e) {

      }
      if (selectRoom == null || selPro.size() == 0) {
        toast("请选择发送内容");
        return;
      }
      HttpManager.InsertService(this, selectRoom.getAccount(), selectRoom.getFacilityno(), selPro);
    }
  }
}
