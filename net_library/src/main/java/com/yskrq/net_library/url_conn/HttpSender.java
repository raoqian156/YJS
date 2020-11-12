package com.yskrq.net_library.url_conn;


import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;
import com.yskrq.net_library.BaseBean;
import com.yskrq.net_library.CrashHandler;
import com.yskrq.net_library.HttpInnerListener;
import com.yskrq.net_library.NET_PARAM_HELPER;
import com.yskrq.net_library.OnHttpListener;
import com.yskrq.net_library.RequestType;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class HttpSender {
  private static final String TAG = "HttpSender";

  private static Handler main = new Handler(Looper.getMainLooper());

  //访问网络获取数据
  public static void post(final OnHttpListener listener, final String url,
                          final Map<String, String> params, final Class<? extends BaseBean> clazz) {
    final String path = url;
    final String tag = url.substring(url.lastIndexOf("/") + 1);
    innerPost(path, params, new HttpInnerListener() {

      @Override
      public void onEmptyResponse() {
        listener.onConnectError(new RequestType(tag).with("-1", "请求异常"));
      }

      @Override
      public void onString(String con) {
        BaseBean base = new Gson().fromJson(con, clazz);
        if (base != null && base.isOk()) {
          base.setAll(con);
          LOG.e("HttpSender", "onString.46:");
          listener.onResponseSucceed(new RequestType(tag), base);
        } else if (base != null) {
          base.setAll(con);
          LOG.e("HttpSender", "onString.49:");
          listener.onResponseError(new RequestType(tag), base);
        } else {
          LOG.e("HttpSender", "onString.52:");
          listener.onConnectError(new RequestType(tag).with("-1", "请求异常"));
        }
      }
    });
  }

  public static void post(String path, HashMap<String, String> param,
                          final HttpInnerListener listener) {
    innerPost(path, param, new HttpInnerListener() {
      @Override
      public void onString(String con) {
        if (listener != null) listener.onString(con);
      }

      public void onEmptyResponse() {
        if (listener != null) listener.onEmptyResponse();
      }
    });
  }

  private static Set<String> waitRequest = new HashSet<>();

  //访问网络获取数据
  private static void innerPost(final String url, final Map<String, String> params,
                                final HttpInnerListener listener) {
    final String path = url;
    if (waitRequest.contains(path) && !"https://hotel17.yskvip.com:9092/RM_Others/wirtelog"
        .equals(path)) {
      LOG.e("HttpSender", "重复请求，被拦截:" + path);
      return;
    }
    waitRequest.add(path);
    final String tag = path.substring(path.lastIndexOf("/") + 1);


    LOG.e("BEAN." + tag + ".POST.url", "=============<" + url + ">=============");
    LOG.bean(tag + ".POST", params);

    new Thread(new Runnable() {
      @Override
      public void run() {
        String stringBuffer = getRequestData(NET_PARAM_HELPER.getParam(params), "UTF-8").toString();
        LOG.e("HttpSender", "93:" + path + "?" + stringBuffer);
        byte[] data = stringBuffer.getBytes();//获得请求体
        InputStream inptStream = null;
        OutputStream outputStream = null;
        HttpURLConnection httpURLConnection = null;
        try {
          URL url = new URL(path);
          httpURLConnection = (HttpURLConnection) url.openConnection();
          httpURLConnection.setConnectTimeout(10000);     //设置连接超时时间
          httpURLConnection.setDoInput(true);                  //打开输入流，以便从服务器获取数据
          httpURLConnection.setDoOutput(true);                 //打开输出流，以便向服务器提交数据
          httpURLConnection.setRequestMethod("POST");     //设置以Post方式提交数据
          httpURLConnection.setUseCaches(false);               //使用Post方式不能使用缓存

          //必须设置false，否则会自动redirect到重定向后的地址
          httpURLConnection.setInstanceFollowRedirects(false);
          //设置请求体的类型是文本类型
          httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
          //设置请求体的长度
          httpURLConnection.setRequestProperty("Content-Length", String.valueOf(data.length));
          //获得输出流，向服务器写入数据

          outputStream = httpURLConnection.getOutputStream();
          outputStream.write(data);
          //获得服务器的响应码
          int response = httpURLConnection.getResponseCode();
          if (response == HttpURLConnection.HTTP_OK) {
            inptStream = httpURLConnection.getInputStream();
            String resultData = null;      //存储处理结果
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] d = new byte[1024];
            int len = 0;
            try {
              while ((len = inptStream.read(d)) != -1) {
                byteArrayOutputStream.write(d, 0, len);
              }
            } catch (IOException e) {
              waitRequest.remove(path);
              main.post(new Runnable() {
                @Override
                public void run() {
                  if (listener != null) listener.onEmptyResponse();
                }
              });
              e.printStackTrace();
            }
            resultData = new String(byteArrayOutputStream.toByteArray());
            final String finalResultData = resultData;
            String tagFoot = "";
            if (listener != null) {
              tagFoot = "." + listener.getClass().getSimpleName();
            }
            LOG.bean(tag + tagFoot, resultData);
            CrashHandler.getInstance().saveNETResponseInfo(tag + ".HttpSender", params, resultData);
            waitRequest.remove(path);
            main.post(new Runnable() {
              @Override
              public void run() {
                if (listener != null) listener.onString(finalResultData);
              }
            });
          } else {
            waitRequest.remove(path);
            main.post(new Runnable() {
              @Override
              public void run() {
                if (listener != null) listener.onEmptyResponse();
              }
            });
          }
        } catch (final Exception e) {
          LOG.e("HttpSender", "run.174:" + e.getMessage());
          waitRequest.remove(path);
          e.printStackTrace();
          main.post(new Runnable() {
            @Override
            public void run() {
              if (listener != null) listener.onEmptyResponse();
            }
          });
        } finally {
          if (httpURLConnection != null) httpURLConnection.disconnect();
          if (inptStream != null) {
            try {
              inptStream.close();
            } catch (IOException e) {
              e.printStackTrace();
            }
          }
          if (outputStream != null) {
            try {
              outputStream.close();
            } catch (IOException e) {
              e.printStackTrace();
            }
          }
        }
      }
    }).start();
  }

  /**
   * 封装请求体信息
   *
   * @param params:请求体内容
   * @param encode:编码格式
   *
   * @return
   */
  public static StringBuffer getRequestData(Map<String, String> params, String encode) {
    if (params == null || params.size() == 0) return new StringBuffer();
    //存储封装好的请求体信息
    StringBuffer stringBuffer = new StringBuffer();
    try {
      for (Map.Entry<String, String> entry : params.entrySet()) {
        if (entry.getValue() == null) continue;
        stringBuffer.append(entry.getKey()).append("=")
                    .append(URLEncoder.encode(entry.getValue(), encode)).append("&");
      }
      //删除最后的一个"&"
      stringBuffer.deleteCharAt(stringBuffer.length() - 1);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return stringBuffer;
  }

  public static void upload(final String path, final File file, final Map<String, String> params,
                            final HttpInnerListener listener) {//, byte[] bytesData

    final String tag = path.substring(path.lastIndexOf("/") + 1);
    LOG.e("BEAN." + tag + ".POST.url", "=============<" + path + ">=============");
    LOG.bean(tag + ".POST", params);
    new Thread(new Runnable() {
      @Override
      public void run() {
        OutputStream out = null;
        HttpURLConnection conn = null;
        InputStream is = null;
        try {
          //          String stringBuffer = getRequestData(NET_PARAM_HELPER.getParam(params), "UTF-8")
          //              .toString();
          //          LOG.e("HttpSender", "stringBuffer:" + stringBuffer);
          //          byte[] bytesData = stringBuffer.getBytes();//获得请求体
          final String newLine = "\r\n"; // 换行符
          final String boundaryPrefix = "--"; //边界前缀
          // 定义数据分隔线
          final String boundary = String.format("=========%s", System.currentTimeMillis());
          URL url = new URL(path);
          conn = (HttpURLConnection) url.openConnection();
          conn.setRequestMethod("POST");
          // 发送POST请求必须设置如下两行
          conn.setDoOutput(true);
          conn.setDoInput(true);
          conn.setUseCaches(false);
          // 设置请求头参数
          conn.setRequestProperty("connection", "Keep-Alive");
          conn.setRequestProperty("Charsert", "UTF-8");
          conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

          out = new DataOutputStream(conn.getOutputStream());
          String keyValue = "Content-Disposition: form-data; name=\"%s\"\r\n\r\n%s\r\n";
          byte[] parameterLine = (boundaryPrefix + boundary + newLine).getBytes();
          //构建请求参数
          if (params != null && params.size() > 0) {
            for (Map.Entry<String, String> e : params.entrySet()) {
              byte[] keyValueBytes = String.format(keyValue, e.getKey(), e.getValue()).getBytes();
              out.write(parameterLine);
              out.write(keyValueBytes);
            }
          }

          StringBuilder sb = new StringBuilder();
          sb.append(boundaryPrefix);
          sb.append(boundary);
          sb.append(newLine);
          // 文件参数
          sb.append("Content-Disposition: form-data;name=\"file\";filename=\"" + "image.jpg" + "\"" + newLine);
          sb.append("Content-Type:application/octet-stream");
          // 参数头设置完以后需要两个换行，然后才是参数内容
          sb.append(newLine);
          sb.append(newLine);
          // 将参数头的数据写入到输出流中
          out.write(sb.toString().getBytes());

          is = new FileInputStream(file);
          byte[] buffer = new byte[1024];
          int len = 0;
          while ((len = is.read(buffer)) != -1) {
            out.write(buffer, 0, len);
          }

          //            InputStream inputStream = FormatTools.getInstance().Byte2InputStream(bytesData);
          //            // 数据输入流,用于读取文件数据
          //            DataInputStream in = new DataInputStream(inputStream);
          //            byte[] bufferOut = new byte[1024];
          //            int bytes = 0;
          //            // 每次读1KB数据,并且将文件数据写入到输出流中
          //            while ((bytes = in.read(bufferOut)) != -1) {
          //                out.write(bufferOut, 0, bytes);
          //            }
          //          out.write(bytesData);
          // 最后添加换行
          out.write(newLine.getBytes());
          //            in.close();
          // 定义最后数据分隔线，即--加上boundary再加上--。
          byte[] end_data = (newLine + boundaryPrefix + boundary + boundaryPrefix + newLine)
              .getBytes();
          // 写上结尾标识
          out.write(end_data);
          out.flush();
          out.close();

          // 定义BufferedReader输入流来读取URL的响应
          StringBuffer sbOutPut = new StringBuffer();
          BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
          String line = null;
          while ((line = reader.readLine()) != null) {
            sbOutPut.append(line);
          }
          final String res = sbOutPut.toString();

          LOG.bean(tag, res);
          main.post(new Runnable() {
            @Override
            public void run() {
              if (listener != null) listener.onString(res);
            }
          });

        } catch (Exception e) {
          LOG.e("HttpSender", "run.310:");
          System.out.println("发送POST请求出现异常！" + e);
          e.printStackTrace();
          main.post(new Runnable() {
            @Override
            public void run() {
              if (listener != null) listener.onEmptyResponse();
            }
          });
        } finally {
          if (is != null) {
            try {
              is.close();
            } catch (IOException e) {
              e.printStackTrace();
            }
          }
          if (out != null) {
            try {
              out.close();
            } catch (IOException e) {
              e.printStackTrace();
            }
          }
          if (conn != null) {
            conn.disconnect();
          }
        }
      }
    }).start();
  }
}
