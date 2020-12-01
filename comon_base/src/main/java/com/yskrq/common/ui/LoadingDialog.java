package com.yskrq.common.ui;

import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.yskrq.common.R;
import com.yskrq.common.util.LOG;

public class LoadingDialog extends Dialog {
  private static final int CHANGE_TITLE_WHAT = 1;
  private static final int CHNAGE_TITLE_DELAYMILLIS = 300;
  private static final int MAX_SUFFIX_NUMBER = 3;
  private static final char SUFFIX = '.';


  private ImageView iv_route;
  private TextView detail_tv;
  private TextView tv_point;


  private Handler handler = new Handler() {
    private int num = 0;


    public void handleMessage(android.os.Message msg) {
      if (msg.what == CHANGE_TITLE_WHAT) {
        StringBuilder builder = new StringBuilder();
        if (num >= MAX_SUFFIX_NUMBER) {
          num = 0;
        }
        num++;
        for (int i = 0; i < num; i++) {
          builder.append(SUFFIX);
        }
        tv_point.setText(builder.toString());
        if (isShowing()) {
          handler.sendEmptyMessageDelayed(CHANGE_TITLE_WHAT, CHNAGE_TITLE_DELAYMILLIS);
        } else {
          num = 0;
        }
      }
    }

    ;
  };


  public LoadingDialog(Context context) {
    super(context, R.style.LoadingDialog);
    init();
  }

  private void init() {
    setContentView(R.layout.common_dialog_loading_layout);
    iv_route = (ImageView) findViewById(R.id.iv_route);
    detail_tv = (TextView) findViewById(R.id.detail_tv);
    tv_point = (TextView) findViewById(R.id.tv_point);
    initAnim();
  }

  ValueAnimator valueAnimator;

  private void initAnim() {
    // mAnim = new RotateAnimation(360,Animation.RESTART,0.5f,0.5f);
    valueAnimator = new ValueAnimator();
    valueAnimator.setFloatValues(0F, 360);
    valueAnimator.setDuration(2000);
    valueAnimator.setInterpolator(null);
    valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
    valueAnimator.setRepeatMode(ValueAnimator.RESTART);
    valueAnimator.setTarget(iv_route);
    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override
      public void onAnimationUpdate(ValueAnimator animation) {
        float value = (float) animation.getAnimatedValue();
        LOG.e("LoadingDialog", "onAnimationUpdate.79:" + value);
        iv_route.setRotationX(.5F);
        iv_route.setRotationY(.5F);
        iv_route.setRotation(value);
      }
    });
    //    mAnim = new RotateAnimation(0F,Animation.RESTART, 0.5f, 0.5f);
    //    mAnim.setDuration(2000);
    //    mAnim.setRepeatCount(Animation.INFINITE);
    //    mAnim.setRepeatMode(Animation.RESTART);
    //    mAnim.setStartTime(Animation.START_ON_FIRST_FRAME);
  }


  @Override
  public void show() {//在要用到的地方调用这个方法
    valueAnimator.start();
    handler.sendEmptyMessage(CHANGE_TITLE_WHAT);
    super.show();
  }


  @Override
  public void dismiss() {
    valueAnimator.end();
    super.dismiss();
  }

  @Override
  public void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    valueAnimator.end();
  }

  @Override
  public void setTitle(CharSequence title) {
    if (TextUtils.isEmpty(title)) {
      detail_tv.setText("正在加载");
    } else {
      detail_tv.setText(title);
    }
  }


  @Override
  public void setTitle(int titleId) {
    setTitle(getContext().getString(titleId));
  }


  public static void dismissDialog(LoadingDialog loadingDialog) {
    if (null == loadingDialog) {
      return;
    }
    loadingDialog.dismiss();
  }
}
