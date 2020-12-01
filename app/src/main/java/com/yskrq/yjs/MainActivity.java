package com.yskrq.yjs;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.yskrq.common.AppInfo;
import com.yskrq.common.BaseActivity;
import com.yskrq.common.LoginActivity;
import com.yskrq.common.okhttp.HttpManagerBase;
import com.yskrq.common.util.AppUtils;
import com.yskrq.common.util.LOG;
import com.yskrq.common.util.ToastUtil;
import com.yskrq.common.widget.DialogHelper;
import com.yskrq.yjs.keep.KeepManager;
import com.yskrq.yjs.net.HttpManager;
import com.yskrq.yjs.ui.HomeFragment;
import com.yskrq.yjs.ui.MineFragment;
import com.yskrq.yjs.util.PhoneUtil;
import com.yskrq.yjs.widget.FragmentSaveTabHost;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static com.yskrq.yjs.ui.ModifyPassActivity.RESULT_LOGIN_OUT;


public class MainActivity extends BaseActivity implements TabHost.OnTabChangeListener {

  private String texts[];
  private int imageButton[] = {R.drawable.selector_home_pic_1, R.drawable.selector_home_pic_2,};
  private Class fragmentArray[] = {HomeFragment.class,//首页
      MineFragment.class,//我的
  };

  @SuppressLint("InvalidWakeLockTag")
  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    KeepManager.startAliveRun(this);
    HttpManager.getWifiName(this);
    if (PhoneUtil.needPermission(getContext())) {
      AppInfo.setVoiceType(this, 1);
    }
    delete();
    BaseApplication.isLogin = true;
    LOG.e("MainActivity", "onCreate.49:");

    if (!AppUtils.isIgnoringBatteryOptimizations(this)) {
      DialogHelper
          .showRemind(this, "为了保持数据的实时性，需要忽略电池优化，是否前去开启？", new DialogHelper.DialogConfirmListener() {
            @Override
            public void onSure() {
              AppUtils.requestIgnoreBatteryOptimizations(MainActivity.this);
            }

            @Override
            public void onCancel() {

            }
          });
    } else {
      if (AppInfo.isDebugUser(this)) ToastUtil.show("无需开启");
    }
    PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
    if (powerManager != null) {
      mWakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "WakeLock");
    }
    HttpManager.sendLoginInfo(this);
  }

  PowerManager.WakeLock mWakeLock;


  @Override
  protected void onResume() {
    super.onResume();
    isShowToUser = true;
    if (getSupportFragmentManager() != null && getSupportFragmentManager().getFragments() != null) {
      for (Fragment fragment : getSupportFragmentManager().getFragments()) {
        fragment.onResume();
      }
    }
    if (mWakeLock != null) {
      mWakeLock.acquire();
    }
  }


  @Override
  protected void onDestroy() {
    super.onDestroy();
    LOG.e("MainActivity", "onDestroy.54:");
    BaseApplication.isLogin = false;
    if (mWakeLock != null) {
      mWakeLock.release();
    }
  }

  private boolean isInstallApp(String packageName) {
    try {
      PackageManager mPackageManager = getPackageManager();
      if (mPackageManager == null) return false;
      mPackageManager.getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
      return true;
    } catch (PackageManager.NameNotFoundException e) {
      return false;
    }
  }

  String packagename = "uni.UNI3550698";

  private void delete() {
    if (isInstallApp(packagename)) {
      DialogHelper.showRemind(this, "检测到老版本应用，是否卸载？", new DialogHelper.DialogConfirmListener() {
        @Override
        public void onSure() {
          Intent deleteIntent = new Intent();
          deleteIntent.setAction(Intent.ACTION_DELETE);
          deleteIntent.setData(Uri.parse("package:" + packagename));
          startActivityForResult(deleteIntent, 0);
        }

        @Override
        public void onCancel() {

        }
      });
    }

  }

  @Override
  protected int layoutId() {
    texts = new String[]{"云技师", "我的"};
    //    HttpManager.getPersonal(MainController.this);
    return R.layout.activity_main;
  }

  @Override
  protected void initView() {
    FragmentSaveTabHost tabhost = findViewById(android.R.id.tabhost);
    tabhost.setup(this, getSupportFragmentManager(), R.id.main_content);
    for (int i = 0; i < texts.length; i++) {
      TabHost.TabSpec spec = tabhost.newTabSpec(texts[i]).setIndicator(getItemView(i));
      tabhost.addTab(spec, fragmentArray[i], null);//R.layout.con_tab_content
    }
    tabhost.getTabWidget().setDividerDrawable(null);
    tabhost.setOnTabChangedListener(this);
    HttpManagerBase.senError(AppInfo.getTechNum(this), "============登录 >> " + AppInfo
        .getUserid(this) + " ============");
  }

  public static volatile boolean isShowToUser = false;

  @Override
  protected void onPause() {
    super.onPause();
    isShowToUser = false;
  }


  private View getItemView(int i) {
    //取得布局实例
    View view = View.inflate(this, R.layout.con_tab_content, null);

    //取得布局对象
    ImageView imageView = view.findViewById(R.id.image);
    TextView textView = view.findViewById(R.id.text);
    //设置图标
    imageView.setImageResource(imageButton[i]);
    //设置标题
    textView.setText(texts[i]);
    return view;
  }

  @Override
  public void onTabChanged(String tabId) {

  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == RESULT_LOGIN_OUT) {
      KeepManager.stopAliveRun(getContext());
      AppInfo.loginOut(getContext());
      Intent intent = new Intent(this, LoginActivity.class);
      startActivity(intent);
      finish();
    }
  }

  long quitTime;

  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event) {
    LOG.e("MainActivity", "onKeyDown.159:");
    if (keyCode == KeyEvent.KEYCODE_BACK) {
      if (System.currentTimeMillis() - quitTime > 2000) {
        quitTime = System.currentTimeMillis();
        toast("再点一次退出当前应用");
        return true;
      }
      LOG.e("MainActivity", "onKeyDown.160:");
    }
    return super.onKeyDown(keyCode, event);
  }

  @Override
  public void onBackPressed() {
    LOG.e("MainActivity", "onBackPressed.173:");
    if (System.currentTimeMillis() - quitTime > 2000) {
      LOG.e("MainActivity", "onBackPressed.173:");
      quitTime = System.currentTimeMillis();
      toast("再点一次退出当前应用");
      return;
    }
    super.onBackPressed();
  }
}