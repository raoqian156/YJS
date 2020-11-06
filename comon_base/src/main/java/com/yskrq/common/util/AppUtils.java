package com.yskrq.common.util;

import android.app.UiModeManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.yskrq.net_library.BuildConfig;

import static android.content.Context.UI_MODE_SERVICE;


public class AppUtils {


    public static boolean isDebug() {
        return BuildConfig.SHOW_LOG;
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
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);
        //        screenWidth = dm.widthPixels;
        //        screenHeight = dm.heightPixels;
        return dm.widthPixels;
    }


    public static void hideInputPan(Context context, EditText editText) {
        InputMethodManager manager = ((InputMethodManager) context
            .getSystemService(Context.INPUT_METHOD_SERVICE));
        if (manager != null) manager.hideSoftInputFromWindow(editText
            .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
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
