package com.yskrq.common;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.smarx.notchlib.NotchScreenManager;
import com.yskrq.common.util.LOG;
import com.yskrq.net_library.BaseBean;
import com.yskrq.net_library.BaseView;
import com.yskrq.net_library.RequestType;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity implements
                                                             BaseView {//,LifecycleProvider<ActivityEvent>
  Handler main = new Handler(Looper.getMainLooper());

  @Override
  public <T extends BaseBean> void onResponseSucceed(@NonNull RequestType type, @NonNull T data) {

  }

  @Override
  public <T extends BaseBean> void onResponseError(@NonNull RequestType type, @NonNull T data) {
    dismissLoading();
    toast(data.getRespMsg());
  }

  @Override
  public void onConnectError(@NonNull RequestType type) {
    dismissLoading();
    toast("网络请求异常，请确认网络后重试");
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    NotchScreenManager.getInstance().setDisplayInNotch(this);
    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//设置屏幕为竖屏, 设置后会锁定方向
    LOG.e("BaseActivity", "====================" + this.getClass()
                                                       .getSimpleName() + "====================");
    //    lifecycleSubject.onNext(ActivityEvent.CREATE);
    getWindow()
        .setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);    // 去除标题栏
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    if (layoutId() > 0) setContentView(layoutId());
    //SCREEN_ORIENTATION_SENSOR_LANDSCAPE
    //        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    initOnClick();
    initView();
  }

  protected void initTitle(String title) {
    if (findViewById(R.id.base_btn_back) != null) {
      findViewById(R.id.base_btn_back).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          finish();
        }
      });
    }
    if (findViewById(R.id.base_title) instanceof TextView) {
      ((TextView) findViewById(R.id.base_title)).setText(title);
    }
  }

  protected abstract int layoutId();

  protected abstract void initView();

  private void initOnClick() {
    LOG.e("BaseActivity", "initOnClick.38:" + is(View.OnClickListener.class));
    if (is(View.OnClickListener.class)) {
      Method method = null;
      try {
        method = get(View.OnClickListener.class).getClass()
                                                .getDeclaredMethod("onClick", View.class);
      } catch (NoSuchMethodException e) {
        e.printStackTrace();
      }
      if (method == null) return;
      Annotation[] annotations = method.getAnnotations();
      if (annotations.length > 0) {
        for (Annotation annotation : annotations) {
          if (annotation instanceof OnClick) {
            OnClick inject = (OnClick) annotation;
            int[] value = inject.value();
            if (value.length > 0) {
              for (int id : value) {
                if (findViewById(id) != null) {
                  findViewById(id).setOnClickListener((View.OnClickListener) this);
                }
              }
            }
          }
        }
      }
    }
  }

  protected void setString2View(int vId, String con) {
    if (findViewById(vId) instanceof TextView) {
      ((TextView) findViewById(vId)).setText(con);
    }
  }

  public final void runOnUiThread(Runnable action, long delay) {
    new Handler(Looper.getMainLooper()).postDelayed(action, delay);
  }

  protected final void toast(final String toast) {
    main.post(new Runnable() {
      @Override
      public void run() {
        Toast.makeText(BaseActivity.this, toast, Toast.LENGTH_LONG).show();
      }
    });
  }

  protected final boolean is(Class<?> clazz) {
    boolean res = clazz.isAssignableFrom(this.getClass());
    return res;
  }

  public <T> T get(Class<T> type) {
    return type.cast(this);
  }

  protected String getInput(int id) {
    try {
      return ((TextView) findViewById(id)).getText().toString().trim();
    } catch (Exception e) {
      return "";
    }
  }


  @Override
  public void handleFailResponse(com.yskrq.net_library.BaseBean baseBean) {

  }

  ProgressDialog progressDialog;

  @Override
  public void showLoading(String... str) {
    if (progressDialog == null) {
      progressDialog = new ProgressDialog(this);
    }
    if (str.length > 0) {
      progressDialog.setMessage(str[0]);
    }
    progressDialog.show();
  }

  @Override
  public void dismissLoading() {
    try {
      if (progressDialog != null && progressDialog.isShowing()) progressDialog.dismiss();
    } catch (Exception e) {

    }
  }

  @Override
  public Context getContext() {
    return this;
  }
  //
  //  @Override
  //  public <T> LifecycleTransformer<T> bindToLifecycle() {
  //    return RxLifecycleAndroid.bindActivity(lifecycleSubject);
  //  }
  //
  //
  //  private final BehaviorSubject<ActivityEvent> lifecycleSubject = BehaviorSubject.create();
  //
  //  @Override
  //  @NonNull
  //  @CheckResult
  //  public final Observable<ActivityEvent> lifecycle() {
  //    return lifecycleSubject.hide();
  //  }
  //
  //  @Override
  //  @NonNull
  //  @CheckResult
  //  public final <T> LifecycleTransformer<T> bindUntilEvent(@NonNull ActivityEvent event) {
  //    return RxLifecycle.bindUntilEvent(lifecycleSubject, event);
  //  }
  //
  //  @Override
  //  @CallSuper
  //  protected void onStart() {
  //    super.onStart();
  //    lifecycleSubject.onNext(ActivityEvent.START);
  //  }
  //
  //  @Override
  //  @CallSuper
  //  protected void onResume() {
  //    super.onResume();
  //    lifecycleSubject.onNext(ActivityEvent.RESUME);
  //  }
  //
  //  @Override
  //  @CallSuper
  //  protected void onPause() {
  //    lifecycleSubject.onNext(ActivityEvent.PAUSE);
  //    super.onPause();
  //  }
  //
  //  @Override
  //  @CallSuper
  //  protected void onStop() {
  //    lifecycleSubject.onNext(ActivityEvent.STOP);
  //    super.onStop();
  //  }
  //
  //  @Override
  //  @CallSuper
  //  protected void onDestroy() {
  //    lifecycleSubject.onNext(ActivityEvent.DESTROY);
  //    super.onDestroy();
  //  }
}
