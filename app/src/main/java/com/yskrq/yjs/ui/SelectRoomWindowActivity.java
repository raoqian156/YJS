package com.yskrq.yjs.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.rq.rvlibrary.BaseAdapter;
import com.rq.rvlibrary.BaseViewHolder;
import com.rq.rvlibrary.EasyViewHolder;
import com.rq.rvlibrary.OnItemClickListener;
import com.rq.rvlibrary.RecyclerUtil;
import com.yskrq.common.BaseActivity;
import com.yskrq.common.OnClick;
import com.yskrq.common.util.LOG;
import com.yskrq.net_library.HttpInnerListener;
import com.yskrq.yjs.R;
import com.yskrq.yjs.bean.RoomListBean;
import com.yskrq.yjs.net.HttpManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;


public class SelectRoomWindowActivity extends BaseActivity implements View.OnClickListener,
                                                                      OnItemClickListener {


  public interface RoomSelectListener extends Serializable {
    void onSelect(RoomListBean.FixingViewValueBean item);
  }

  private static RoomSelectListener mRoomSelectListener;

  public static void start(final Context activity, final RoomSelectListener listener) {
    final ProgressDialog dialog = new ProgressDialog(activity);
    dialog.setMessage("数据加载中...");
    dialog.show();
    mRoomSelectListener = listener;
    HttpManager.SelectDataByStatus(activity, new HttpInnerListener() {
      @Override
      public void onString(String json) {
        dialog.dismiss();
        RoomListBean bean = new Gson().fromJson(json, RoomListBean.class);
        if (bean != null && bean.isOk() && bean.getFixingView_value() != null && bean
            .getFixingView_value().size() > 0) {
          Intent intent = new Intent(activity, SelectRoomWindowActivity.class);
          intent.putExtra("data", bean.getFixingView_value());
          activity.startActivity(intent);
        } else {
          Toast.makeText(activity, "数据查询异常,请稍后重试", Toast.LENGTH_LONG).show();
        }
      }

      @Override
      public void onEmptyResponse() {
        Toast.makeText(activity, "数据查询异常,请稍后重试", Toast.LENGTH_LONG).show();
      }
    });
  }

  BaseAdapter mAdapter;

  @Override
  protected int layoutId() {
    return R.layout.act_pop_room_select;
  }

  Handler handler = new Handler(Looper.getMainLooper());

  @Override
  protected void initView() {
    findViewById(com.yskrq.common.R.id.tv_title).requestFocus(1000);
    mAdapter = new BaseAdapter(this, com.yskrq.common.R.layout.login_item_table_content, EasyViewHolder.class) {

      @Override
      public void onBindEasyHolder(BaseViewHolder holder, int position, Object o) {
        super.onBindEasyHolder(holder, position, o);
        RoomListBean.FixingViewValueBean bean = (RoomListBean.FixingViewValueBean) o;
        holder.setItemText(R.id.tv_name, bean.getDescription());
        try {
          int selectPosition = (int) mAdapter.getNullablePass(0);
          holder.itemView
              .setBackgroundResource(selectPosition == position ? R.color.room_gray : R.color.room_qian);
        } catch (Exception e) {

        }
      }
    };
    List<RoomListBean.FixingViewValueBean> beans = (ArrayList<RoomListBean.FixingViewValueBean>) getIntent()
        .getSerializableExtra("data");
    if (beans == null) {
      toast("数据获取异常,请稍后重试.");
      finish();
      return;
    }
    mAdapter.setPassData(-1);
    mAdapter.setData(beans);
    mAdapter.addOnItemClickListener(this);
    new RecyclerUtil(mAdapter).row(Math.min(beans.size(), 4))
                              .set2View((RecyclerView) findViewById(com.yskrq.common.R.id.recycler));
  }

  @Override
  public void onItemClick(BaseViewHolder holder, Object o, View view, int position) {
    try {
      int selectPosition = (int) mAdapter.getNullablePass(0);
      if (selectPosition == -1) {
        mAdapter.setPassData(position);
        mAdapter.notifyDataSetChanged();
        RoomListBean.FixingViewValueBean item = (RoomListBean.FixingViewValueBean) o;
        toast("是否选择房间【" + item.getDescription() + "】");
        handler.postDelayed(new Runnable() {
          @Override
          public void run() {
            findViewById(R.id.btn_sure).setVisibility(View.VISIBLE);
          }
        }, 2 * 1000);
      } else {
        mAdapter.setPassData(-1);
        mAdapter.notifyDataSetChanged();
        findViewById(R.id.btn_sure).setVisibility(View.GONE);
      }
    } catch (Exception e) {

    }
  }


  @OnClick({R.id.btn_cancel, R.id.btn_sure})
  public void onClick(View v) {
    if (v.getId() == R.id.btn_cancel) {
      finish();
    } else if (v.getId() == R.id.btn_sure) {
      int select = (int) mAdapter.getNullablePass(0);
      RoomListBean.FixingViewValueBean item = (RoomListBean.FixingViewValueBean) mAdapter
          .getDataItem(select);
      LOG.e("SelectRoomWindowActivity", "onClick.ChangeRoom:" + item.getFacilityNo());
      mRoomSelectListener.onSelect(item);
      finish();
    }
  }
}
