package com.yskrq.yjs.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.yskrq.common.BASE;
import com.yskrq.common.bean.RelaxListBean;
import com.yskrq.common.util.ToastUtil;
import com.yskrq.net_library.BaseBean;
import com.yskrq.net_library.BaseView;
import com.yskrq.net_library.RequestType;
import com.yskrq.yjs.R;
import com.yskrq.yjs.RunningHelper;
import com.yskrq.yjs.Speaker;
import com.yskrq.yjs.keep.KeepAliveService;
import com.yskrq.yjs.keep.KeepManager;
import com.yskrq.yjs.net.HttpManager;
import com.yskrq.yjs.util.PhoneUtil;
import com.yskrq.yjs.util.TechStatusManager;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static com.yskrq.yjs.net.Constants.TransCode.BrandnoOut;

public class KeepOnActivity extends Activity implements RunningHelper.OnSecondTickListener,
                                                        KeepAliveService.RefuseListener,
                                                        TechStatusManager.OnDataChangeListener,
                                                        BaseView {
  public static void start(Context context) {
    Intent intent = new Intent(context, KeepOnActivity.class);
    context.startActivity(intent);
  }

  TextView tvCenter;
  private static boolean isShowToUser = false;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    setContentView(R.layout.act_keep_on);
    getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    super.onCreate(savedInstanceState);
    tvCenter = findViewById(R.id.tv_center);
    RunningHelper.getInstance().register(this);
    KeepAliveService.addRefuseListener(this);
    TechStatusManager.getInstance().addDataChangeListener(this);
    //for new api versions.
    int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
    getWindow().getDecorView().setSystemUiVisibility(uiOptions);
    findViewById(R.id.rl_parent).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });
    findViewById(R.id.tv_center).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        RelaxListBean.ValueBean first = TechStatusManager.getInstance().getValueBean();
        HttpManager.BrandnoOut(KeepOnActivity.this, first.getSeqnum(), first.getAccount());
      }
    });
  }

  @Override
  protected void onResume() {
    super.onResume();
    isShowToUser = true;
  }

  @Override
  protected void onPause() {
    super.onPause();
    isShowToUser = false;
  }

  public static boolean isShowToUser() {
    return KeepOnActivity.isShowToUser;
  }

  @Override
  protected void onDestroy() {
    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    KeepAliveService.removeRefuseListener(this);
    TechStatusManager.getInstance().removeDataChangeListener(this);
    super.onDestroy();
  }

  @Override
  public void on15Second() {

  }

  long lastRefuseTime = 0;

  @Override
  public void onSecond() {
    if (System.currentTimeMillis() - lastRefuseTime < 900) {
      return;
    }
    lastRefuseTime = System.currentTimeMillis();
    if (TechStatusManager.getInstance().getWaitType() == 0) {
      return;
    }
    int tag = TechStatusManager.getInstance().getWaitType();
    tvCenter.setTag(tag);
    String btnStr = null;
    int btnBgRes = 0;
    if (tag == 0) {
      resetTimePan();
    } else if (tag == 1) {
      btnBgRes = R.drawable.circle_bg_blue;
      btnStr = "上钟\n" + "00:00:00";
    } else if (tag == 2) {
      if (KeepAliveService.READ_WAY == 1) KeepManager.stopAliveRun();
      long timeLeft = TechStatusManager.getInstance().getRunningLeftTime();
      SimpleDateFormat simpleDateFormat;
      simpleDateFormat = new SimpleDateFormat("下钟\nHH:mm:ss");

      if (timeLeft < 0) {
        timeLeft = -timeLeft;
        btnBgRes = R.drawable.circle_bg_red;
      } else {
        btnBgRes = R.drawable.circle_bg_green;
      }
      simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
      btnStr = simpleDateFormat.format(timeLeft);
    } else {
      resetTimePan();
    }
    if (btnStr != null) {
      tvCenter.setBackgroundResource(btnBgRes);
      tvCenter.setText(btnStr);
    }
  }

  @Override
  public void onHour() {

  }

  @Override
  public void onRefuse(RelaxListBean bean) {
    refuseList(bean);
  }


  public void refuseList(RelaxListBean bean) {
  }

  private void resetTimePan() {
    tvCenter.setText("---\n00:00:00");
    PhoneUtil.openVoice(this);
    tvCenter.setTag(R.id.tv_center, 0);
    tvCenter.setBackgroundResource(R.drawable.circle_bg_blue);
    if (KeepAliveService.READ_WAY == 1) KeepManager.stopAliveRun();
  }

  @Override
  public void handleFailResponse(BaseBean baseBean) {

  }

  @Override
  public void showLoading(String... show) {

  }

  @Override
  public void dismissLoading() {

  }

  @Override
  public Context getContext() {
    return this;
  }

  @Override
  public <T extends BaseBean> void onResponseSucceed(@NonNull RequestType type, @NonNull T data) {
    if (type.is(BrandnoOut)) {
      try {
        Speaker.speakOut(BASE.getCxt(), "下钟打卡成功");
      } catch (Exception e) {

      }
      ToastUtil.show("下钟打卡成功");
      TechStatusManager.getInstance().brandOut();
      finish();
    }
  }

  @Override
  public <T extends BaseBean> void onResponseError(@NonNull RequestType type, @NonNull T data) {
    ToastUtil.show(data.getRespMsg());
  }

  @Override
  public void onConnectError(@NonNull RequestType type) {

  }
}
