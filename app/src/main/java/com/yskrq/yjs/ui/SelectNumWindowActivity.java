package com.yskrq.yjs.ui;

import android.app.Activity;
import android.content.Intent;
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
import com.yskrq.net_library.BaseBean;
import com.yskrq.net_library.HttpInnerListener;
import com.yskrq.net_library.RequestType;
import com.yskrq.yjs.R;
import com.yskrq.yjs.net.HttpManager;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static com.yskrq.yjs.net.Constants.TransCode.OpenTheStage;


public class SelectNumWindowActivity extends BaseActivity implements View.OnClickListener,
                                                                     OnItemClickListener {

  public static void start(final Activity activity, final String facilityNo) {
    HttpManager.selectzhtddan(new HttpInnerListener() {
      @Override
      public void onString(String json) {
        BaseBean bean = new Gson().fromJson(json, BaseBean.class);
        if ("10".equals(bean.getRespCode())) {
          Intent intent = new Intent(activity, SelectNumWindowActivity.class);
          intent.putExtra("facilityNo", facilityNo);
          activity.startActivityForResult(intent, 0x01101);
        } else {
          Toast.makeText(activity, "当前房间已开台", Toast.LENGTH_SHORT).show();
        }
      }

      @Override
      public void onEmptyResponse() {

      }
    }, activity, facilityNo);

  }

  BaseAdapter mAdapter;

  @Override
  protected int layoutId() {
    return R.layout.act_pop_room_select;
  }

  String facilityNo;

  @Override
  protected void initView() {
    facilityNo = getIntent().getStringExtra("facilityNo");
    setString2View(R.id.tv_title, "请选择人数");
    mAdapter = new BaseAdapter(this, com.yskrq.common.R.layout.login_item_table_content, EasyViewHolder.class) {
      @Override
      public int getItemCount() {
        return 20;
      }

      @Override
      public void onBindEasyHolder(BaseViewHolder holder, int position, Object o) {
        super.onBindEasyHolder(holder, position, o);
        holder.setItemText(R.id.tv_name, (position + 1) + "人");
        holder.itemView.setBackgroundResource(R.drawable.conner_4dp_blue_btn);
      }
    };
    new RecyclerUtil(mAdapter).row(4)
                              .set2View((RecyclerView) findViewById(com.yskrq.common.R.id.recycler));
    mAdapter.addOnItemClickListener(this);
  }

  @Override
  public void onItemClick(BaseViewHolder holder, Object o, View view, int position) {
    int people = position + 1;
    HttpManager.OpenTheStage(this, facilityNo, people + "");
  }

  public static final int RESULT_QUIT = 0x001201;

  @Override
  public <T extends BaseBean> void onResponseSucceed(@NonNull RequestType type, @NonNull T data) {
    if (type.is(OpenTheStage)) {
      String account = data.get(String.class, "Account");
      setResult(RESULT_QUIT);
      CarErProjectActivity.start(this, account, facilityNo);
      finish();
    }
  }

  @OnClick({R.id.background, R.id.btn_cancel, R.id.btn_sure})
  public void onClick(View v) {
    if (v.getId() == R.id.background) {
      finish();
    } else if (v.getId() == R.id.btn_cancel) {
      finish();
    }
  }
}
