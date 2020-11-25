package com.yskrq.common.widget;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;

import com.yskrq.common.util.LOG;
import com.yskrq.common.util.ToastUtil;

import java.io.File;
import java.io.IOException;

import androidx.core.content.FileProvider;

import static android.os.Environment.DIRECTORY_DOWNLOADS;
import static android.os.Environment.getExternalStoragePublicDirectory;

public class DownloadUtils {
  //下载器
  private DownloadManager downloadManager;
  private Context mContext;
  //下载的ID
  private long downloadId;
  private String name;
  private String pathstr;
  private String url;

  public DownloadUtils(Context context, String url, String name) {
    this.mContext = context;
    this.name = name;
    this.url = url;
  }

  public void start() {
    downloadAPK(url, name);
  }

  //下载apk
  private void downloadAPK(String url, String name) {
    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
    //指定下载路径和下载文件名
    request.setDestinationInExternalPublicDir(DIRECTORY_DOWNLOADS, name);
    pathstr = getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS) + "/" + name;
    //获取下载管理器
    downloadManager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
    // 设置下载路径和文件名
    request.setDescription("云上客软件更新");
    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
    request.setMimeType("application/vnd.android.package-archive");
    // 设置为可被媒体扫描器找到
    request.allowScanningByMediaScanner();
    // 设置为可见和可管理
    request.setVisibleInDownloadsUi(true);
    //将下载任务加入下载队列，否则不会进行下载
    long refernece = downloadManager.enqueue(request);
    SharedPreferences sp = mContext.getSharedPreferences("download", Context.MODE_PRIVATE);
    SharedPreferences.Editor edit = sp.edit();
    edit.putLong("refernece", refernece);
    edit.apply();
    //        // 把当前下载的ID保存起来
    //注册广播接收者，监听下载状态
    mContext.registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
  }

  //广播监听下载的各个状态
  private BroadcastReceiver receiver = new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) {
      checkStatus();
    }
  };

  //检查下载状态
  private void checkStatus() {
    SharedPreferences sp = mContext.getSharedPreferences("download", Context.MODE_PRIVATE);
    downloadId = sp.getLong("refernece", 0);
    LOG.e("DownloadUtils", "checkStatus.83:" + downloadId);

    DownloadManager.Query query = new DownloadManager.Query();
    if (query == null) {
      LOG.e("DownloadUtils", "checkStatus.86:");
      return;
    }
    //通过下载的id查找
    query.setFilterById(downloadId);
    Cursor cursor = downloadManager.query(query);
    if (cursor.moveToFirst()) {
      int status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
      switch (status) {
        //下载暂停
        case DownloadManager.STATUS_PAUSED:
          break;
        //下载延迟
        case DownloadManager.STATUS_PENDING:
          break;
        //正在下载
        case DownloadManager.STATUS_RUNNING:
          break;
        //下载完成
        case DownloadManager.STATUS_SUCCESSFUL:
          //下载完成安装APK
          installAPK();
          cursor.close();
          break;
        //下载失败
        case DownloadManager.STATUS_FAILED:
          ToastUtil.show("下载失败");
          cursor.close();
          mContext.unregisterReceiver(receiver);
          break;
      }
    }
  }

  private void installAPK() {
    setPermission(pathstr);
    Intent intent = new Intent(Intent.ACTION_VIEW);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
      File file = (new File(pathstr));
      //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
      Uri apkUri = FileProvider.getUriForFile(mContext, "com.yskrq.yjs.fileProvider", file);
      //添加这一句表示对目标应用临时授权该Uri所代表的文件
      intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
      intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
    } else {
      File file = (new File(pathstr));
      intent.setDataAndType(Uri.parse("file://" + file
          .getAbsolutePath() + "/" + name), "application/vnd.android.package-archive");
    }
    mContext.startActivity(intent);
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
}
