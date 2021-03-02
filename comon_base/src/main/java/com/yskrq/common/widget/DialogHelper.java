package com.yskrq.common.widget;


import android.animation.ValueAnimator;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.yskrq.common.R;
import com.yskrq.common.util.LOG;
import com.yskrq.common.util.ToastUtil;

import java.util.Calendar;

import androidx.appcompat.app.AlertDialog;
import androidx.core.widget.NestedScrollView;

public class DialogHelper {
  public interface OnDateSetListener {
    // month:0~11
    void onDateSet(DatePicker view, int year, int month, int dayOfMonth);
  }

  public static void showTimeDialog(Context context, final OnDateSetListener listener) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
      DatePickerDialog dialog = new DatePickerDialog(context);
      dialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
          if (listener != null) listener.onDateSet(view, year, month, dayOfMonth);
        }
      });
      dialog.show();
    } else {
      DatePickerDialog_less24 dialog = new DatePickerDialog_less24(context);
      dialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
          if (listener != null) listener.onDateSet(view, year, month, dayOfMonth);
        }
      });
      dialog.show();
    }
  }

  public static void showHourTimeDialog(Context context,
                                        final TimePickerDialog.OnTimeSetListener listener) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      TimePickerDialog dialog = new TimePickerDialog(context, listener, Calendar.getInstance()
                                                                                .get(Calendar.HOUR_OF_DAY), Calendar
          .getInstance().get(Calendar.MINUTE), true);
      dialog.show();
    } else {
      TimerPickerDialog_less21 dialog = new TimerPickerDialog_less21(context);
      dialog.setOnDateSetListener(new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
          if (listener != null) listener.onTimeSet(view, hourOfDay, minute);
        }
      });
      dialog.show();
    }
  }

  public interface DialogConfirmListener {

    void onSure();

    void onCancel();
  }


  public static void showRemind(Context context, String content,
                                final DialogConfirmListener listener) {
    final Dialog dialog = new AlertDialog.Builder(context).setTitle("提示")
                                                          .setMessage(Html.fromHtml(content))
                                                          .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(
                                                                DialogInterface dialog, int which) {
                                                              if (listener != null)
                                                                listener.onCancel();
                                                            }
                                                          })
                                                          .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(
                                                                DialogInterface dialog, int which) {
                                                              if (listener != null)
                                                                listener.onSure();
                                                            }
                                                          }).create();
    try {
      dialog.show();
    } catch (Exception e) {

    }
  }

  static class CanShow {
    boolean canDisMiss = false;

    public CanShow() {
    }

    public void setCanShow(boolean b) {
      this.canDisMiss = b;
    }

    public boolean canDis() {
      return canDisMiss;
    }
  }

  public static void showWebRemind(Context context, final DialogConfirmListener listener) {
    String url = "http://hotel16.yskvip.com:8180/h5";
    final LayoutInflater inflater = LayoutInflater.from(context);
    View customView = inflater.inflate(R.layout.dialog_web, null, false);
    NestedScrollView sv = customView.findViewById(R.id.sv_pan);
    final FrameLayout fl = customView.findViewById(R.id.fl_pan);
    initWebView(context, fl, url);
    final CanShow needSc = new CanShow();
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      needSc.setCanShow(false);
      sv.setOnScrollChangeListener(new View.OnScrollChangeListener() {
        @Override
        public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX,
                                   int oldScrollY) {
          boolean isOk = v.getHeight() + scrollY >= fl.getHeight() * .95F;
          if (!needSc.canDis()) needSc.setCanShow(isOk);
        }
      });
    } else {
      needSc.setCanShow(true);
    }
    final Dialog dialog = new AlertDialog.Builder(context).setView(customView)
                                                          .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(
                                                                DialogInterface dialog, int which) {
                                                              if (listener != null)
                                                                listener.onCancel();
                                                            }
                                                          })
                                                          .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(
                                                                DialogInterface dialog, int which) {
                                                              if (!needSc.canDis()) {
                                                                ToastUtil.show("请滑动阅读，阅读完毕后再操作");
                                                                return;
                                                              }
                                                              if (listener != null) {
                                                                listener.onSure();
                                                              }
                                                            }
                                                          }).create();
    try {
      dialog.show();
    } catch (Exception e) {

    }
  }

  private static void initWebView(Context context, final FrameLayout webContainer, String webUrl) {
    final WebView mMebView = new WebView(context);
    mMebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
    mMebView.getSettings().setDomStorageEnabled(true);// 解决对某些标签的不支持出现白屏
    mMebView.getSettings().setJavaScriptEnabled(true);
    mMebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
    mMebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
    mMebView.getSettings().setDatabaseEnabled(true);
    mMebView.getSettings().setAppCacheEnabled(true);
    mMebView.getSettings().setAllowFileAccess(true);
    mMebView.getSettings().setSavePassword(true);
    mMebView.getSettings().setSupportZoom(true);
    mMebView.getSettings().setBuiltInZoomControls(true);
    mMebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
    mMebView.getSettings().setUseWideViewPort(true);
    final ImageView iv_route = webContainer.findViewById(R.id.iv_route);
    ValueAnimator valueAnimator = new ValueAnimator();
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
        iv_route.setRotationX(.5F);
        iv_route.setRotationY(.5F);
        iv_route.setRotation(value);
      }
    });
    mMebView.setWebViewClient(new WebViewClient() {
      @Override
      public boolean shouldOverrideUrlLoading(WebView webView, String url) {
        LOG.e("DialogHelper", "shouldOverrideUrlLoading.200:");
        if (Build.VERSION.SDK_INT < 26) {
          webView.loadUrl(url);
          return true;
        }
        return false;
      }

      @Override
      public void onPageStarted(WebView webView, String url, Bitmap bitmap) {
        super.onPageStarted(webView, url, bitmap);
        LOG.e("DialogHelper", "onPageStarted.211:");
        LOG.e("WebActivity", "onPageStarted.url = " + url);
      }

      @Override
      public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        //        super.onReceivedSslError(view, handler, error);
        LOG.e("DialogHelper", "onReceivedSslError.218:");
        handler.proceed();
      }

      @Override
      public void onPageFinished(WebView webView, String url) {
        super.onPageFinished(webView, url);
        webView.getSettings().setBlockNetworkImage(false);
        webContainer.findViewById(R.id.ll_route).setVisibility(View.GONE);
        LOG.e("DialogHelper", "onPageFinished.226:");
        LOG.e("WebActivity", mMebView.canScrollVertically(10) + "  " + mMebView
            .canScrollVertically(-10) + "  onPageFinished.url = " + url);
      }
    });
    webContainer.addView(mMebView, 0);
    valueAnimator.start();
    if (!TextUtils.isEmpty(webUrl)) {
      mMebView.loadUrl(webUrl);
    }
  }

  public static void showBatteryRemind(Context context, final DialogConfirmListener listener) {
    final LayoutInflater inflater = LayoutInflater.from(context);
    View customView = inflater.inflate(R.layout.dialog_battery, null, false);
    NestedScrollView sv = customView.findViewById(R.id.sv_pan);
    final LinearLayout ll = customView.findViewById(R.id.ll_pan);
    final CanShow needSc = new CanShow();
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      needSc.setCanShow(false);
      sv.setOnScrollChangeListener(new View.OnScrollChangeListener() {
        @Override
        public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX,
                                   int oldScrollY) {
          boolean isOk = v.getHeight() + scrollY >= ll.getHeight() * .95F;
          if (!needSc.canDis()) needSc.setCanShow(isOk);
        }
      });
    } else {
      needSc.setCanShow(true);
    }
    final Dialog dialog = new AlertDialog.Builder(context).setView(customView)
                                                          .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(
                                                                DialogInterface dialog, int which) {
                                                              if (listener != null)
                                                                listener.onCancel();
                                                            }
                                                          })
                                                          .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(
                                                                DialogInterface dialog, int which) {
                                                              if (!needSc.canDis()) {
                                                                ToastUtil.show("请滑动阅读，阅读完毕后再操作");
                                                                return;
                                                              }
                                                              if (listener != null) {
                                                                listener.onSure();
                                                              }
                                                            }
                                                          }).create();
    try {
      dialog.show();
    } catch (Exception e) {

    }
  }

  public interface DialogCheckListener {

    void onSure(String select);

    void onCancel();
  }

  public static void showCheckBoxPan(Context context, final DialogCheckListener listener,
                                     String... content) {
    final View contentView = LayoutInflater.from(context).inflate(R.layout.dialog_checkbox, null);
    final TextView tvLeft = contentView.findViewById(R.id.ck_left);
    final TextView tvRight = contentView.findViewById(R.id.ck_right);
    final Dialog dialog = new AlertDialog.Builder(context).setView(contentView)
                                                          .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(
                                                                DialogInterface dialog, int which) {
                                                              listener.onCancel();
                                                            }
                                                          })
                                                          .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(
                                                                DialogInterface dialog, int which) {
                                                              String se = "";
                                                              if (tvLeft.isSelected()) {
                                                                se = tvLeft.getText().toString();
                                                              } else {
                                                                se = tvRight.getText().toString();
                                                              }
                                                              listener.onSure(se);
                                                            }
                                                          }).create();
    if (contentView.findViewById(R.id.tv_title) != null)
      ((TextView) contentView.findViewById(R.id.tv_title)).setText(content[0]);
    if (tvLeft != null) ((TextView) tvLeft).setText(content[1]);
    if (tvRight != null) ((TextView) tvRight).setText(content[2]);
    View.OnClickListener clickListener = new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        tvLeft.setSelected(v.getId() == R.id.ck_left);
        tvRight.setSelected(v.getId() == R.id.ck_right);
      }
    };
    tvLeft.setOnClickListener(clickListener);
    tvRight.setOnClickListener(clickListener);
    tvLeft.setSelected(true);
    dialog.show();
  }

}
