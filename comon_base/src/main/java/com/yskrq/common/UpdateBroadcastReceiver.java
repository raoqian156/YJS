package com.yskrq.common;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import com.yskrq.common.util.FileUtils;
import com.yskrq.common.util.LOG;
import com.yskrq.common.util.SPUtil;

import java.io.File;
import java.io.IOException;

import androidx.core.content.FileProvider;

public class UpdateBroadcastReceiver extends BroadcastReceiver {


  private void installApp(Context context, String pathstr) {
    setPermission(pathstr);
    Intent intent = new Intent(Intent.ACTION_VIEW);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
      File file = (new File(pathstr));
      //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
      String auth = context.getPackageName() + ".fileProvider";
      LOG.e("UpdateBroadcastReceiver", "installApp.39:" + auth);
      Uri apkUri = FileProvider.getUriForFile(context, auth, file);
      //添加这一句表示对目标应用临时授权该Uri所代表的文件
      intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
      intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
    } else {
      File file = (new File(pathstr));
      intent.setDataAndType(Uri
          .parse(file.getAbsolutePath()), "application/vnd.android.package-archive");
    }
    context.startActivity(intent);
  }

  //修改文件权限
  private void setPermission(String absolutePath) {
    String command = "chmod " + "777" + " " + absolutePath;
    Runtime runtime = Runtime.getRuntime();
    try {
      runtime.exec(command);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @SuppressLint("NewApi")
  public void onReceive(Context context, Intent intent) {
    long mDwonloadID = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
    long refernece = SPUtil.getLong(context, "refernece");
    LOG.e("UpdateBroadcastReceiver", refernece + ".onReceive:" + mDwonloadID);
    if (refernece == mDwonloadID) {
      DownloadManager dManager = (DownloadManager) context
          .getSystemService(Context.DOWNLOAD_SERVICE);
      Uri downloadFileUri = dManager.getUriForDownloadedFile(mDwonloadID);
      try {
        installApp(context, FileUtils.getPath(context, downloadFileUri));
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
