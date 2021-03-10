package com.yskrq.common.util;

import android.app.UiModeManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Point;
import android.location.LocationManager;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Looper;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.yskrq.common.BASE;
import com.yskrq.net_library.BuildConfig;

import static android.content.Context.UI_MODE_SERVICE;
import static android.content.Context.WINDOW_SERVICE;


public class AppUtils {


  public static boolean isDebug() {
    return BuildConfig.SHOW_LOG;
  }

  /**
   * 忽略电池优化  -->  OPPO无法完成
   *
   * @param context
   */
  public static void requestIgnoreBatteryOptimizations(Context context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      try {
        Intent intent = new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
        intent.setData(Uri.parse("package:" + context.getPackageName()));
        context.startActivity(intent);
      } catch (Exception e) {
        ToastUtil.show("当前手机无此功能");
      }
    }
  }


  public static String getWifiName(Context context) {
    if (context == null) return "getWifiName";
    WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    WifiInfo wifiInfo = wifiManager.getConnectionInfo();
    Log.d("wifiInfo", wifiInfo.toString());
    String wifiName = wifiInfo.getSSID();
    Log.d("SSID", wifiName);
    if (wifiName == null) return "";
    if (WifiManager.UNKNOWN_SSID.equals(wifiName)) {
      if (!isLocServiceEnable(context)) {
        Log.e("AppUtils", ">>>>>>>>>>getWifiName.error : due to the locate switch is close");
      }
    }
    return wifiName.replaceAll("\"", "");
  }

  public static boolean isLocServiceEnable(Context context) {
    LocationManager locationManager = (LocationManager) context
        .getSystemService(Context.LOCATION_SERVICE);
    boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    if (gps || network) {
      return true;
    }
    return false;
  }


  public static double showInches() {
    Point point = new Point();
    WindowManager windowManager = (WindowManager) BASE.getCxt().getSystemService(WINDOW_SERVICE);
    windowManager.getDefaultDisplay().getRealSize(point);
    DisplayMetrics dm = BASE.getCxt().getResources().getDisplayMetrics();
    double x = Math.pow(point.x / dm.xdpi, 2);
    double y = Math.pow(point.y / dm.ydpi, 2);
    double screenInches = Math.sqrt(x + y);
    LOG.e("AppUtils", "getScreenSizeOfDevice2:" + screenInches);
    return screenInches;
  }

  public static boolean isIgnoringBatteryOptimizations(Context context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      boolean isIgnoring = false;
      PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
      if (powerManager != null) {
        isIgnoring = powerManager.isIgnoringBatteryOptimizations(context.getPackageName());
      }
      return isIgnoring;
    } else {
      return true;
    }
  }

  public static boolean isTV(Context context) {
    UiModeManager uiModeManager = (UiModeManager) context.getSystemService(UI_MODE_SERVICE);
    if (uiModeManager.getCurrentModeType() == Configuration.UI_MODE_TYPE_TELEVISION) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * 初始化屏幕信息
   */
  public static int getScreenWidth(Context context) {
    //屏幕信息
    DisplayMetrics dm = new DisplayMetrics();
    WindowManager windowManager = (WindowManager) context.getSystemService(WINDOW_SERVICE);
    windowManager.getDefaultDisplay().getMetrics(dm);
    //        screenWidth = dm.widthPixels;
    //        screenHeight = dm.heightPixels;
    return dm.widthPixels;
  }


  public static void hideInputPan(Context context, EditText editText) {
    InputMethodManager manager = ((InputMethodManager) context
        .getSystemService(Context.INPUT_METHOD_SERVICE));
    if (manager != null) manager
        .hideSoftInputFromWindow(editText.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
  }

  /**
   * 是否是UI线程
   *
   * @return
   */
  public static boolean isUiThread() {
    return Looper.myLooper() == Looper.getMainLooper();
  }

  //    /**
  //     * 手机振动
  //     */
  //    @RequiresPermission(android.Manifest.permission.VIBRATE)
  //    public static void vibrate() {
  //        long milliseconds = 100;
  //        Vibrator vib = (Vibrator) BASE.getCxt().getSystemService(Service.VIBRATOR_SERVICE);
  //        vib.vibrate(milliseconds);
  //    }

  /**
   * 获取版本号
   *
   * @return 当前应用的版本号
   */
  public static String getVersion(Context context) {
    try {
      PackageManager manager = context.getPackageManager();
      PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
      return info.versionName;
    } catch (Exception e) {
      e.printStackTrace();
      return "";
    }
  }

  public static String getPhoneType() {
    return Build.MANUFACTURER + "-" + Build.MODEL;
  }

  public static boolean isOV() {//是否为OPPO、VIVO
    String type = Build.MANUFACTURER + "-" + Build.MODEL;
    LOG.e("AppUtils", "isOV.type:" + type);
    return type != null && (type.toLowerCase().contains("oppo") || type.toLowerCase()
                                                                       .contains("vivo"));
  }


  public static String getVersionName() {
    return getVersion(BASE.getCxt());
  }

  //
  //    /**
  //     * 检测是否安装支付宝
  //     *
  //     * @return
  //     */
  //    public static boolean checkAliPayInstalled() {
  //        Uri uri = Uri.parse("alipays://platformapi/startApp");
  //        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
  //        ComponentName componentName = intent.resolveActivity(BASE.getCxt().getPackageManager());
  //        return componentName != null;
  //    }
  //
  //    /**
  //     * 判断 用户是否安装微信客户端
  //     */
  //    public static boolean checkWxInstalled() {
  //        final PackageManager packageManager = BASE.getCxt().getPackageManager();// 获取packagemanager
  //        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
  //        if (pinfo != null) {
  //            for (int i = 0; i < pinfo.size(); i++) {
  //                String pn = pinfo.get(i).packageName;
  //                if (pn.equals("com.tencent.mm")) {
  //                    return true;
  //                }
  //            }
  //        }
  //        return false;
  //    }
  //
  //    /**
  //     * 把文本复制到粘贴板
  //     *
  //     * @param content
  //     */
  //    public static void clipContent(String content) {
  //        ClipboardManager cm = (ClipboardManager) BASE.getCxt()
  //                                                     .getSystemService(Context.CLIPBOARD_SERVICE);
  //        ClipData mClipData = ClipData.newPlainText("Label", content);
  //        if (cm != null) {
  //            cm.setPrimaryClip(mClipData);
  //        }
  //    }
  //
  //    /**
  //     * 保存视图显示内容到本地
  //     *
  //     * @param fileName 文件名 不需要带尾缀，使用 png 作为尾缀
  //     */
  //    public static void saveViewBitmap(View view, String fileName) {
  //        view.setDrawingCacheEnabled(true);//设置能否缓存图片信息（drawing cache）
  //        view.buildDrawingCache();//如果能够缓存图片，则创建图片缓存
  //
  //        final Bitmap mBitmap = view.getDrawingCache();//如果图片已经缓存，返回一个bitmap
  //        view.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
  //            @Override
  //            public void onViewAttachedToWindow(View v) {
  //
  //            }
  //
  //            @Override
  //            public void onViewDetachedFromWindow(View v) {
  //                mBitmap.recycle();
  //            }
  //        });
  //        try {
  //            String pathAll = BASE.getBaseDir() + "/" + fileName + ".png";
  //            ToastUtil.show(view.getContext().getResources()
  //                               .getString(R.string.toast_tip_bitmap_save) + pathAll);
  //            BitmapUtil.saveToLocal(mBitmap, fileName + ".png");
  //        } catch (IOException e) {
  //            ToastUtil.show(R.string.toast_save2local_error);
  //            e.printStackTrace();
  //        }
  //    }

}
