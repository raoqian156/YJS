package com.yskrq.yjs.net;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.yskrq.common.AppInfo;
import com.yskrq.common.util.AppUtils;
import com.yskrq.common.util.LOG;
import com.yskrq.common.util.MD5Util;
import com.yskrq.common.util.SPUtil;
import com.yskrq.common.util.UUID;
import com.yskrq.net_library.BaseBean;
import com.yskrq.net_library.BaseView;
import com.yskrq.net_library.HttpInnerListener;
import com.yskrq.net_library.HttpProxy;
import com.yskrq.net_library.RequestType;
import com.yskrq.net_library.url_conn.HttpSender;
import com.yskrq.yjs.bean.CaiErListBean;
import com.yskrq.yjs.bean.CaiErProductBean;
import com.yskrq.yjs.bean.DianTypeBean;
import com.yskrq.yjs.bean.HavedRoomBean;
import com.yskrq.yjs.bean.KaiDanBean;
import com.yskrq.yjs.bean.ListParamBean;
import com.yskrq.yjs.bean.MoneyListBean;
import com.yskrq.yjs.bean.OrderBean;
import com.yskrq.yjs.bean.OrderListBean;
import com.yskrq.yjs.bean.PhotosBean;
import com.yskrq.yjs.bean.RelaxListBean;
import com.yskrq.yjs.bean.RoomBean;
import com.yskrq.yjs.bean.RoomLeftBean;
import com.yskrq.yjs.bean.RoomProjectBean;
import com.yskrq.yjs.bean.RoomProjectListBean;
import com.yskrq.yjs.bean.RoomProjectTypeBean;
import com.yskrq.yjs.bean.ShouKaBean;
import com.yskrq.yjs.bean.StaticsListBean;
import com.yskrq.yjs.bean.TechListBean;
import com.yskrq.yjs.bean.TechProjectBean;
import com.yskrq.yjs.bean.YuYueBean;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import static com.yskrq.common.okhttp.Constants_base.TransCode.GET_SALE_DATE;
import static com.yskrq.net_library.HttpProxy.TAG_CHANGE_URL;
import static com.yskrq.yjs.net.Constants.TransCode.AddBillitem;
import static com.yskrq.yjs.net.Constants.TransCode.AddBillitemBigRelax;
import static com.yskrq.yjs.net.Constants.TransCode.BillingSaled;
import static com.yskrq.yjs.net.Constants.TransCode.BrandnoOut;
import static com.yskrq.yjs.net.Constants.TransCode.CancelTec;
import static com.yskrq.yjs.net.Constants.TransCode.CardSaled;
import static com.yskrq.yjs.net.Constants.TransCode.ChangeRoom;
import static com.yskrq.yjs.net.Constants.TransCode.CheckTechDestineStatus;
import static com.yskrq.yjs.net.Constants.TransCode.DelItem;
import static com.yskrq.yjs.net.Constants.TransCode.GetData;
import static com.yskrq.yjs.net.Constants.TransCode.GetFixingOnUse;
import static com.yskrq.yjs.net.Constants.TransCode.GetHotelCode;
import static com.yskrq.yjs.net.Constants.TransCode.GetHotelCodePos;
import static com.yskrq.yjs.net.Constants.TransCode.GetRelaxCdTechFac;
import static com.yskrq.yjs.net.Constants.TransCode.GetRelaxReservation;
import static com.yskrq.yjs.net.Constants.TransCode.GetRelaxServerList;
import static com.yskrq.yjs.net.Constants.TransCode.GetRelaxTechJobStatus;
import static com.yskrq.yjs.net.Constants.TransCode.GetServiceItem;
import static com.yskrq.yjs.net.Constants.TransCode.InsertService;
import static com.yskrq.yjs.net.Constants.TransCode.OpenTheStage;
import static com.yskrq.yjs.net.Constants.TransCode.RelaxAddClock;
import static com.yskrq.yjs.net.Constants.TransCode.RelaxTechChangeItem;
import static com.yskrq.yjs.net.Constants.TransCode.SelectCountNum;
import static com.yskrq.yjs.net.Constants.TransCode.SelectDataByStatus;
import static com.yskrq.yjs.net.Constants.TransCode.SelectRelaxInvtType;
import static com.yskrq.yjs.net.Constants.TransCode.SelectRestaurantType;
import static com.yskrq.yjs.net.Constants.TransCode.SelectRestaurantView;
import static com.yskrq.yjs.net.Constants.TransCode.SelectServerlistView;
import static com.yskrq.yjs.net.Constants.TransCode.SelectType;
import static com.yskrq.yjs.net.Constants.TransCode.TechAddPro;
import static com.yskrq.yjs.net.Constants.TransCode.Techoffwork;
import static com.yskrq.yjs.net.Constants.TransCode.Techonwork;
import static com.yskrq.yjs.net.Constants.TransCode.TimeCardSaled;
import static com.yskrq.yjs.net.Constants.TransCode.UpdatePwd;
import static com.yskrq.yjs.net.Constants.TransCode.UpdatePwd2;
import static com.yskrq.yjs.net.Constants.TransCode.UpdateRelaxCountDown;
import static com.yskrq.yjs.net.Constants.TransCode.UpdateRelaxStatus;
import static com.yskrq.yjs.net.Constants.TransCode.UpdateSoundStatue;
import static com.yskrq.yjs.net.Constants.TransCode.UpdateStatueYes;
import static com.yskrq.yjs.net.Constants.TransCode.UploadPic;
import static com.yskrq.yjs.net.Constants.TransCode.brandnoIn;
import static com.yskrq.yjs.net.Constants.TransCode.checkTechNo;
import static com.yskrq.yjs.net.Constants.TransCode.checkrights;
import static com.yskrq.yjs.net.Constants.TransCode.deletePic;
import static com.yskrq.yjs.net.Constants.TransCode.getIscalctime;
import static com.yskrq.yjs.net.Constants.TransCode.getPaidMoney;
import static com.yskrq.yjs.net.Constants.TransCode.getTecCoverPhotos;
import static com.yskrq.yjs.net.Constants.TransCode.getTecPhotos;
import static com.yskrq.yjs.net.Constants.TransCode.getWorkingTech;
import static com.yskrq.yjs.net.Constants.TransCode.selectddan;
import static com.yskrq.yjs.net.Constants.TransCode.selectgoods;
import static com.yskrq.yjs.net.Constants.TransCode.selectgoodsbigrelax;
import static com.yskrq.yjs.net.Constants.TransCode.selectzhtddan;

/**
 * 页面业务简单，暂时不用 MVP
 * 页面继承 BaseView 复写 onResponseSucceed 方法
 * 同一界面多个接口 根据 type 判断，Type 为 -->  Constants.TransCode.***
 */
public class HttpManager {

  public static String REAL_URL;

  public static String getURL(Context context) {
    if (REAL_URL != null) return REAL_URL;
    return SPUtil.getString(context, TAG_CHANGE_URL);
  }

  public static final int PAGE_SIZE = 20;

  public static HashMap<String, String> getParam(Context context) {
    //        <param name="computername"></param>平板自定义名称
    HashMap<String, String> param = new HashMap<>();
    param.put("groupid", AppInfo.getGroupId(context));
    param.put("brandno", AppInfo.getTechNum(context));
    String saveName = AppInfo.getPhoneName(context);
    try {
      param.put("computermac", UUID.getDeviceId(context));
      if (TextUtils.isEmpty(saveName)) {
        param.put("computername", UUID.getDeviceId(context));
      } else {
        param.put("computername", saveName);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    param.put("shopsid", AppInfo.getShopsid(context));
    param.put("opid", AppInfo.getUserid(context));
    param.put("profitcenter", AppInfo.getProfitCenter(context));
    param.put("APPToken", AppInfo.getToken(context));
    param.put("UserId", AppInfo.getUserid(context));
    param.put("hoteldate", AppInfo.getWorkDate(context));
    param.put("computerip", AppUtils.getPhoneType());
    param.put("sip", AppUtils.getPhoneType());
    return param;
  }

  public static void justRefuseSaleDate(final Context context) {
    HashMap<String, String> param = getParam(context);
    HttpProxy.inner(new HttpInnerListener() {
      @Override
      public void onString(String json) {
        BaseBean dateBean = new Gson().fromJson(json, BaseBean.class);
        senError(context, json, "REFUSE", null);
        if (dateBean != null && "0".equals(dateBean.getRespCode())) {
          AppInfo.setWorkDate(context, dateBean.getRespMsg());
        }
      }

      @Override
      public void onEmptyResponse() {
      }
    }, context, GET_SALE_DATE, param);
  }

  public static void refuseSaleDate(final BaseView view) {
    HashMap<String, String> param = getParam(view.getContext());
    HttpProxy.inner(new HttpInnerListener() {
      @Override
      public void onString(String json) {
        BaseBean dateBean = new Gson().fromJson(json, BaseBean.class);
        if (dateBean != null && "0".equals(dateBean.getRespCode())) {
          AppInfo.setWorkDate(view.getContext(), dateBean.getRespMsg());
          view.onResponseSucceed(new RequestType(GET_SALE_DATE), dateBean);
        } else if (dateBean != null) {
          view.onResponseError(new RequestType(GET_SALE_DATE), dateBean);
        } else {
          view.onConnectError(new RequestType(GET_SALE_DATE));
        }
      }

      @Override
      public void onEmptyResponse() {
        view.onConnectError(new RequestType(GET_SALE_DATE));
      }
    }, view.getContext(), GET_SALE_DATE, param);
  }

  public static void getServerStatus(final Context context, final HttpInnerListener listener) {
    final HashMap<String, String> maps = getParam(context);
    final String tag = GetRelaxServerList.substring(GetRelaxServerList.lastIndexOf("/") + 1);
    HttpProxy.inner(listener, context, GetRelaxServerList, maps);
  }

  /**
   * 更新新任务提示
   */
  public static void sendNewTaskSuccess(Context context, String account, String seqnum,
                                        String facilityno) {

    final HashMap<String, String> map = getParam(context);
    map.put("account", account);
    map.put("seqnum", seqnum);
    map.put("facilityno", facilityno);
    HttpProxy.inner(null, context, UpdateRelaxStatus, map);
  }

  public static void hasSend(Context context, String account, String seqnum, String facilityno,
                             final int type) {
    final HashMap<String, String> map = getParam(context);
    map.put("account", account);
    map.put("seqnum", seqnum);
    map.put("facilityno", facilityno);
    map.put("sType", type + "");
    HttpProxy.inner(null, context, UpdateRelaxCountDown, map);
  }


  public static void senError(Context context, String msg, HttpInnerListener listener) {
    senError(context, msg, null, listener);
  }

  public static void senError(Context context, String msg, String tag, HttpInnerListener listener) {
    LOG.e("HttpManager", "sendErrorMsg.msg:" + msg);
    final String url = "https://hotel17.yskvip.com:9092/RM_Others/wirtelog";
    final HashMap<String, String> map = getParam(context);
    map.put("computername", "");
    String techNum = AppInfo.getTechNum(context);
    if (tag == null) {
      map.put("hoteldate", "NEW_TEST_" + techNum + "_" + new SimpleDateFormat(" yyyyMMdd")
          .format(System.currentTimeMillis()));
    } else {
      map.put("hoteldate", tag + "_" + techNum + "_" + new SimpleDateFormat(" yyyyMMdd")
          .format(System.currentTimeMillis()));
    }
    map.put("sremark", msg);
    HttpSender.post(url, map, listener);
  }

  public static void senDetachError(Context context, String msg, HttpInnerListener listener) {
    LOG.e("HttpManager", "sendErrorMsg.msg:" + msg);
    final String url = "https://hotel17.yskvip.com:9092/RM_Others/wirtelog";
    final HashMap<String, String> map = getParam(context);
    map.put("computername", "");
    map.put("hoteldate", "Detach_" + new SimpleDateFormat("yyyyMMdd")
        .format(System.currentTimeMillis()));
    map.put("sremark", msg);
    HttpSender.post(url, map, listener);
  }

  public static void getOpenAll(final BaseView view) {
    HashMap<String, String> param = getParam(view.getContext());
    param.put("section", "RelaxGuest");
    param.put("entry", "bUseCloudTech");
    HttpProxy.bean(view, GetHotelCode, param, ListParamBean.class);
  }

  public static void getRush(final BaseView view) {
    HashMap<String, String> param = getParam(view.getContext());
    param.put("section", "RelaxGuest");
    param.put("entry", "Rush");
    HttpProxy.inner(new HttpInnerListener() {
      @Override
      public void onString(String json) {
        ListParamBean bean = new Gson().fromJson(json, ListParamBean.class);
        if (bean == null || bean.getValue() == null || bean.getValue().size() == 0) return;
        AppInfo.setCuiZHongMinutes(view.getContext(), bean.getValue().get(0).getData());
      }

      @Override
      public void onEmptyResponse() {

      }
    }, view.getContext(), GetHotelCodePos, param);
  }

  public static void getYun(final HttpInnerListener view, Context context) {
    HashMap<String, String> param = getParam(context);
    param.put("section", "RelaxGuest");
    param.put("entry", "bUseCloudTech");
    HttpProxy.inner(new HttpInnerListener() {
      @Override
      public void onString(String json) {
        BaseBean bean = new Gson().fromJson(json, BaseBean.class);
      }

      @Override
      public void onEmptyResponse() {

      }
    }, context, GetHotelCodePos, param);
  }

  public static void SelectRelaxInvtType(BaseView view) {
    HttpProxy
        .bean(view, SelectRelaxInvtType, getParam(view.getContext()), RoomProjectTypeBean.class);
  }

  public static void selectgoods(BaseView view) {
    HttpProxy.bean(view, selectgoods, getParam(view.getContext()), RoomProjectListBean.class);
  }

  public static void GetRelaxServerList(BaseView view, String... hote) {
    HashMap<String, String> param = getParam(view.getContext());
    if (hote.length > 0) {
      param.put("hoteldate", hote[0]);
    }
    HttpProxy.bean(view, GetRelaxServerList, param, RelaxListBean.class);
  }

  public static void GetRelaxServerList(Context context, HttpInnerListener innerListener) {
    HttpProxy.inner(innerListener, context, GetRelaxServerList, getParam(context));
  }

  public static void CancelTec(BaseView view, String account) {
    HashMap<String, String> param = getParam(view.getContext());
    param.put("account", account);
    param.put("tecid", AppInfo.getTechNum(view.getContext()));
    HttpProxy.bean(view, CancelTec, param, BaseBean.class);
  }

  public static void DelItem(BaseView view, String account, String indexNum, String seqnum,
                             String tecid) {
    HashMap<String, String> param = getParam(view.getContext());
    param.put("account", account);
    param.put("tecid", tecid);
    param.put("indexNum", indexNum);
    param.put("seqnum", seqnum);
    HttpProxy.bean(view, DelItem, param, BaseBean.class);
  }

  public static void UpdateStatueYes(BaseView view, String account, String facilityno,
                                     String seqnum, String sid) {
    HashMap<String, String> param = getParam(view.getContext());
    param.put("account", account);
    param.put("facilityno", facilityno);
    param.put("seqnum", seqnum);
    param.put("sid", sid);
    HttpProxy.bean(view, UpdateStatueYes, param, BaseBean.class);
  }

  public static void SelectDataByStatus(Context view, HttpInnerListener listener) {
    HashMap<String, String> param = getParam(view);
    param.put("Type", "空台");
    HttpProxy.inner(listener, view, SelectDataByStatus, param);
  }
  //
  //  public static void getCommonParam(final BaseView view) {
  //    HashMap<String, String> param = getParam(view.getContext());
  //    //        section	是	string	业态参数
  //    //        entry	是	string	参数
  //    param.put("section", "RelaxGuest");
  //    param.put("entry", "bShowFactCount");
  //    HttpProxy.bean(view, GET_COMMON_PARAM, param, ParamBean.class);
  //  }


  public static void ChangeRoom(BaseView view, String oldFacilityNo, String facilityNo,
                                String account) {
    HashMap<String, String> param = getParam(view.getContext());
    param.put("oldFacilityNo", oldFacilityNo);
    param.put("facilityNo", facilityNo);
    param.put("account", account);
    HttpProxy.bean(view, ChangeRoom, param, BaseBean.class);
  }

  public static void getIscalctime(BaseView view) {
    HashMap<String, String> param = getParam(view.getContext());
    param.put("sbrandno", AppInfo.getTechNum(view.getContext()));
    HttpProxy.bean(view, getIscalctime, param, TechProjectBean.class);
  }

  //isCover（1封面图 -1轮播图）
  public static void UploadPic(final BaseView view, final String path, boolean isFen) {
    final HashMap<String, String> param = getParam(view.getContext());
    view.showLoading("上传中...");
    param.put("techNo", AppInfo.getTechNum(view.getContext()));
    param.put("isCover", isFen ? "1" : "-1");
    saveBitmap(path, new OnSaveSuccessListener() {
      @Override
      public void onSuccess(String absolutePath) {
        HttpProxy.upload(view, UploadPic, "9092", absolutePath, param, BaseBean.class);
      }
    });
  }

  public static void selectddan(BaseView view, String account) {
    HashMap<String, String> param = getParam(view.getContext());
    param.put("zhh", account);
    HttpProxy.bean(view, selectddan, param, OrderListBean.class);
  }

  public static void getPaidMoney(BaseView view, String account) {
    HashMap<String, String> param = getParam(view.getContext());
    param.put("account", account);
    HttpProxy.bean(view, getPaidMoney, param, MoneyListBean.class);
  }

  public static void SelectRestaurantType(BaseView view) {
    HashMap<String, String> param = getParam(view.getContext());
    HttpProxy.bean(view, SelectRestaurantType, param, RoomLeftBean.class);
  }

  public static void SelectCountNum(final BaseView view) {
    final HashMap<String, String> param = getParam(view.getContext());
    HttpProxy.inner(new HttpInnerListener() {
      @Override
      public void onString(String json) {
        BaseBean bean = new Gson().fromJson(json, BaseBean.class);
        final String allNum = bean.getRespMsg();
        param.put("Type", "开台");
        HttpProxy.inner(new HttpInnerListener() {
          @Override
          public void onString(String json) {
            BaseBean bean = new Gson().fromJson(json, BaseBean.class);
            final String userNum = bean.getRespMsg();
            param.put("Type", "空台");
            HttpProxy.inner(new HttpInnerListener() {
              @Override
              public void onString(String json) {
                BaseBean bean = new Gson().fromJson(json, BaseBean.class);
                final String emptyNum = bean.getRespMsg();
                param.put("Type", "脏房");
                HttpProxy.inner(new HttpInnerListener() {
                  @Override
                  public void onString(String json) {
                    BaseBean bean = new Gson().fromJson(json, BaseBean.class);
                    final String dutyNum = bean.getRespMsg();
                    param.put("Type", "锁台");
                    HttpProxy.inner(new HttpInnerListener() {
                      @Override
                      public void onString(String json) {
                        BaseBean bean = new Gson().fromJson(json, BaseBean.class);
                        String yudingNum = bean.getRespMsg();
                        String[] datas = new String[5];
                        datas[0] = allNum;
                        datas[1] = userNum;
                        datas[2] = emptyNum;
                        datas[3] = dutyNum;
                        datas[4] = yudingNum;
                        view.onResponseSucceed(new RequestType(SelectCountNum).more(datas), bean);
                      }

                      @Override
                      public void onEmptyResponse() {

                      }
                    }, view.getContext(), SelectCountNum, param);
                  }

                  @Override
                  public void onEmptyResponse() {
                  }
                }, view.getContext(), SelectCountNum, param);
              }

              @Override
              public void onEmptyResponse() {

              }
            }, view.getContext(), SelectCountNum, param);
          }

          @Override
          public void onEmptyResponse() {

          }
        }, view.getContext(), SelectCountNum, param);
      }

      @Override
      public void onEmptyResponse() {

      }
    }, view.getContext(), SelectCountNum, param);
  }

  public static void SelectRestaurantView(BaseView view) {
    HashMap<String, String> param = getParam(view.getContext());
    HttpProxy.bean(view, SelectRestaurantView, param, RoomBean.class);
  }

  public interface OnSaveSuccessListener {

    void onSuccess(String absolutePath);
  }

  public static void saveBitmap(String path, OnSaveSuccessListener onSaveSuccessListener) {
    //      ★★★★★★★★★★★★★★重点★★★★★★★★★★★★★
      /*  //★如果不压缩直接从path获取bitmap，这个bitmap会很大，下面在压缩文件到100kb时，会循环很多次，
        // ★而且会因为迟迟达不到100k，options一直在递减为负数，直接报错
        //★ 即使原图不是太大，options不会递减为负数，也会循环多次，UI会卡顿，所以不推荐不经过压缩，直接获取到bitmap
        Bitmap bitmap=BitmapFactory.decodeFile(path);*/
    //      ★★★★★★★★★★★★★★重点★★★★★★★★★★★★★
    //        Bitmap bitmap = decodeSampledBitmapFromPath(path, 640, 640);
    Bitmap bitmap = BitmapFactory.decodeFile(path);
    ByteArrayOutputStream baos = new ByteArrayOutputStream();

    /* options表示 如果不压缩是100，表示压缩率为0。如果是70，就表示压缩率是70，表示压缩30%; */
    int options = 100;
    if (bitmap == null) {
      LOG.e("HttpManager", "saveBitmap.301:图片上传失败");
      return;
    }
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

    while (baos.toByteArray().length / 1024 > 300) {
      // 循环判断如果压缩后图片是否大于500kb继续压缩
      baos.reset();
      options -= 10;
      if (options < 11) {//为了防止图片大小一直达不到200kb，options一直在递减，当options<0时，下面的方法会报错
        // 也就是说即使达不到200kb，也就压缩到10了
        bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
        break;
      }
      // 这里压缩options%，把压缩后的数据存放到baos中
      bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
    }
    String mDir = Environment.getExternalStorageDirectory() + "/yjs";
    File dir = new File(mDir);
    if (!dir.exists()) {
      dir.mkdirs();//文件不存在，则创建文件
    }
    File file = new File(mDir, "upload.jpg");
    FileOutputStream out = null;
    try {
      out = new FileOutputStream(file);
      out.write(baos.toByteArray());
      out.flush();
      onSaveSuccessListener.onSuccess(file.getAbsolutePath());
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if (out != null) {
          out.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }


  public static void TechAddPro(BaseView view, String relaxaccount, String itemno, String itemcount,
                                String indexnumber) {
    HashMap<String, String> param = getParam(view.getContext());
    param.put("sbrandno", AppInfo.getTechNum(view.getContext()));
    param.put("relaxaccount", relaxaccount);
    param.put("itemno", itemno);
    param.put("itemcount", itemcount);
    param.put("indexnumber", indexnumber);
    HttpProxy.bean(view, TechAddPro, param, BaseBean.class);
  }

  public static void Techonwork(BaseView view) {
    HashMap<String, String> param = getParam(view.getContext());
    param.put("opidname", AppInfo.getName(view.getContext()));
    HttpProxy.bean(view, Techonwork, param, BaseBean.class);
  }

  public static void Techoffwork(BaseView view) {
    HashMap<String, String> param = getParam(view.getContext());
    param.put("opidname", AppInfo.getName(view.getContext()));
    HttpProxy.bean(view, Techoffwork, param, BaseBean.class);
  }

  public static void RelaxAddClock(BaseView view, boolean isAll, String index, String account) {
    HashMap<String, String> param = getParam(view.getContext());
    param.put("iaddclock", isAll ? "1" : "0");
    param.put("iaddclockhalf", !isAll ? "1" : "0");
    param.put("indexnum", index);
    param.put("account", account);
    HttpProxy.bean(view, RelaxAddClock, param, BaseBean.class);
  }

  public static void GetData(BaseView view, String type, String start, String end) {
    HashMap<String, String> param = getParam(view.getContext());
    param.put("type", type);
    param.put("begingDate", start);
    param.put("endDate", end);
    param.put("Technician", AppInfo.getTechNum(view.getContext()));

    //    https://hotel16.yskvip.com:9092/TPC_AddItem/GetData?
    //    computerip=&Technician=YSK226691888&endDate=2020-11-04&
    //    groupid=J00908&computername=6D3D836DF5AE27533EDD8A517FD0DD520981127A&
    //    opid=YSK226691888&begingDate=2020-10-1&type=3&shopsid=226691&
    //    APPToken=A93E4B12652DCF29E41DA492CF872206&
    //    computermac=6D3D836DF5AE27533EDD8A517FD0DD520981127A&
    //    profitcenter=000202&UserId=YSK226691888&
    //    sip=6D3D836DF5AE27533EDD8A517FD0DD520981127A&brandno=888&hoteldate=2020-11-04

    //    https://hotel16.yskvip.com:9092/TPC_AddItem/GetData?
    //    // UserId=YSK226691888&APPToken=531EA45D2A13F09457DF0574A9A5A192&
    //    // shopsid=226691&type=1&
    //    // begingDate=2020-11-1&endDate=2020-11-4&groupid=J00908&
    //    // Technician=888&profitcenter=000202
    HttpProxy.bean(view, GetData, param, StaticsListBean.class);
  }

  public static void CardSaled(BaseView view, boolean isToday) {
    HashMap<String, String> param = getParam(view.getContext());
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    if (isToday) {
      param.put("HotelDate", sdf.format(System.currentTimeMillis()));
    } else {
      Calendar calendar = Calendar.getInstance();
      calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 1);
      param.put("HotelDate", sdf.format(calendar.getTimeInMillis()));
    }
    param.put("ShopsID", AppInfo.getShopsid(view.getContext()));
    param.put("Technician", AppInfo.getUserid(view.getContext()));
    HttpProxy.bean(view, CardSaled, "9095", param, ShouKaBean.class);
  }

  public static void TimeCardSaled(BaseView view, String from, String today) {
    HashMap<String, String> param = getParam(view.getContext());
    param.put("BeginDate", from);
    param.put("ShopsID", AppInfo.getShopsid(view.getContext()));
    param.put("EndDate", today);
    param.put("Technician", AppInfo.getUserid(view.getContext()));
    HttpProxy.bean(view, TimeCardSaled, "9095", param, ShouKaBean.class);
  }

  public static void BillingSaled(BaseView view, String... p) {
    HashMap<String, String> param = getParam(view.getContext());
    if (p.length == 1) {
      param.put("HotelDate", p[0]);
    } else {
      param.put("BeginDate", p[0]);
      param.put("EndDate", p[1]);
      param.put("hoteldate", "");

    }
    param.put("ShopsID", AppInfo.getShopsid(view.getContext()));
    param.put("Technician", AppInfo.getUserid(view.getContext()));
    HttpProxy.bean(view, BillingSaled, "9095", param, KaiDanBean.class);
  }

  public static void GetRelaxReservation(BaseView view, boolean isCancel, String key, String... p) {
    HashMap<String, String> param = getParam(view.getContext());
    param.put("stype", isCancel ? "1" : "0");

    if (!TextUtils.isEmpty(key)) {
      param.put("skey", key);
    }
    if (p.length == 2) {
      param.put("begindate", p[0]);
      param.put("enddate", p[1]);
    } else {
      param.put("begindate", AppInfo.getWorkDate(view.getContext()));
      param.put("enddate", AppInfo.getWorkDate(view.getContext()));
    }
    HttpProxy.bean(view, GetRelaxReservation, param, OrderBean.class);
  }

  public static void GetFixingOnUse(BaseView view) {
    HttpProxy.bean(view, GetFixingOnUse, getParam(view.getContext()), HavedRoomBean.class);
  }

  public static void GetServiceItem(BaseView view) {
    HttpProxy.bean(view, GetServiceItem, getParam(view.getContext()), RoomProjectBean.class);
  }

  public static void InsertService(BaseView view, String account, String room,
                                   List<RoomProjectBean.ValueBean> selPro) {
    HashMap<String, String> param = getParam(view.getContext());
    param.put("account", account);
    param.put("facilityno", room);
    param.put("xhl", new Gson().toJson(selPro));
    HttpProxy.bean(view, InsertService, param, BaseBean.class);
  }

  public static void brandnoIn(BaseView view, String seqnum, String account) {
    HashMap<String, String> param = getParam(view.getContext());
    param.put("seqnum", seqnum);
    param.put("account", account);
    HttpProxy.bean(view, brandnoIn, param, BaseBean.class);
  }

  public static void BrandnoOut(BaseView view, String seqnum, String account) {
    HashMap<String, String> param = getParam(view.getContext());
    param.put("seqnum", seqnum);
    param.put("account", account);
    HttpProxy.bean(view, BrandnoOut, param, BaseBean.class);
  }

  public static void GetRelaxTechJobStatus(HttpInnerListener listener, Context context) {
    HttpProxy.inner(listener, context, GetRelaxTechJobStatus, getParam(context));
  }

  public static void selectgoodsbigrelax(BaseView view) {
    HttpProxy.bean(view, selectgoodsbigrelax, getParam(view.getContext()), CaiErProductBean.class);
  }

  public static void RelaxTechChangeItem(BaseView view, OrderListBean.ValueBean project,
                                         TechProjectBean.ValueBean item) {
    HashMap<String, String> param = getParam(view.getContext());
    param.put("account", project.getAccount());
    param.put("seqnum", project.getSeqNum() + "");
    param.put("itemprice", item.getPrice() + "");
    param.put("indexnum", project.getIndexNumber() + "");
    param.put("itemno", item.getItemNo());
    param.put("itemname", item.getName());
    param.put("unit", item.getUnit());
    HttpProxy.bean(view, RelaxTechChangeItem, param, BaseBean.class);
  }


  public static void RelaxTechChangeItem(BaseView view, RelaxListBean.ValueBean project,
                                         TechProjectBean.ValueBean item) {
    HashMap<String, String> param = getParam(view.getContext());
    param.put("account", project.getAccount());
    param.put("seqnum", project.getSeqnum());
    param.put("itemprice", item.getPrice() + "");
    param.put("indexnum", project.getIndexnumber());
    param.put("itemno", item.getItemNo());
    param.put("itemname", item.getName());
    param.put("unit", item.getUnit());
    HttpProxy.bean(view, RelaxTechChangeItem, param, BaseBean.class);
  }

  public static void checkTechNo(BaseView view) {
    HashMap<String, String> param = getParam(view.getContext());
    param.put("techNo", AppInfo.getTechNum(view.getContext()));
    HttpProxy.bean(view, checkTechNo, param, BaseBean.class);
  }

  public static void UpdatePwd2(BaseView view, String pass) {
    HashMap<String, String> param = getParam(view.getContext());
    param.put("pwd", pass);
    HttpProxy.bean(view, UpdatePwd2, param, BaseBean.class);
  }

  public static void UpdatePwd(BaseView view, String pass) {
    HashMap<String, String> param = getParam(view.getContext());
    param.put("pwd", MD5Util.getMD5String(pass + "ym"));
    HttpProxy.bean(view, UpdatePwd, param, BaseBean.class);
  }

  public static void getTecCoverPhotos(BaseView view) {
    HashMap<String, String> param = getParam(view.getContext());
    param.put("tecid", AppInfo.getTechNum(view.getContext()));
    HttpProxy.bean(view, getTecCoverPhotos, param, BaseBean.class);
  }

  public static void getTecPhotos(BaseView view) {
    HashMap<String, String> param = getParam(view.getContext());
    param.put("tecid", AppInfo.getTechNum(view.getContext()));
    param.put("stype", "0");
    HttpProxy.bean(view, getTecPhotos, param, PhotosBean.class);
  }

  /**
   * @param index 照片序号
   */
  public static void deletePic(BaseView view, int index, boolean isFen) {
    HashMap<String, String> param = getParam(view.getContext());
    param.put("tecid", AppInfo.getTechNum(view.getContext()));
    param.put("index", (index + 1) + "");
    param.put("stype", isFen ? "2" : "0");//罗: stype=1 是咨客屏的轮播图,stype=0 是技师移动端的轮播图 2 封面图

    HttpProxy.bean(view, deletePic, param, BaseBean.class);
  }

  public static void getWifiName(final Context context) {
    HashMap<String, String> param = getParam(context);
    param.put("section", "RelaxGuest");
    param.put("entry", "clockin_wifi");
    HttpProxy.inner(new HttpInnerListener() {
      @Override
      public void onString(String json) {
        ListParamBean bean = new Gson().fromJson(json, ListParamBean.class);
        if (bean == null || bean.getValue() == null || bean.getValue().size() == 0) return;
        AppInfo.setClockWifi(context, bean.getValue().get(0).getData());
      }

      @Override
      public void onEmptyResponse() {

      }
    }, context, GetHotelCodePos, param);
    HashMap<String, String> param2 = getParam(context);
    param2.put("section", "RelaxGuest");
    param2.put("entry", "clockin_latitude");
    HttpProxy.inner(new HttpInnerListener() {
      @Override
      public void onString(String json) {
        ListParamBean bean = new Gson().fromJson(json, ListParamBean.class);
        if (bean == null || bean.getValue() == null || bean.getValue().size() == 0) return;
        AppInfo.setSignLat(context, bean.getValue().get(0).getData());
      }

      @Override
      public void onEmptyResponse() {

      }
    }, context, GetHotelCodePos, param2);
    HashMap<String, String> param3 = getParam(context);
    param3.put("section", "RelaxGuest");
    param3.put("entry", "clockin_longitude");
    HttpProxy.inner(new HttpInnerListener() {
      @Override
      public void onString(String json) {
        ListParamBean bean = new Gson().fromJson(json, ListParamBean.class);
        if (bean.getValue() == null || bean.getValue().size() == 0) return;
        AppInfo.setSignLon(context, bean.getValue().get(0).getData());
      }

      @Override
      public void onEmptyResponse() {

      }
    }, context, GetHotelCodePos, param3);
  }

  public static void SelectType(BaseView view) {
    HashMap<String, String> param = getParam(view.getContext());
    HttpProxy.bean(view, SelectType, param, DianTypeBean.class);
  }

  public static void getWorkingTech(BaseView view, String key, String itemno) {
    HashMap<String, String> param = getParam(view.getContext());
    param.put("key", key);
    param.put("itemno", itemno);
    HttpProxy.bean(view, getWorkingTech, param, TechListBean.class);
  }

  public static void CheckTechDestineStatus(BaseView view, String brandno) {
    HashMap<String, String> param = getParam(view.getContext());
    param.put("brandno", brandno);
    HttpProxy.bean(view, CheckTechDestineStatus, param, BaseBean.class);
  }

  public static void GetRelaxCdTechFac(BaseView view, String brandno) {
    HashMap<String, String> param = getParam(view.getContext());
    param.put("TechNo", brandno);
    HttpProxy.bean(view, GetRelaxCdTechFac, param, YuYueBean.class);
  }

  public static void selectzhtddan(HttpInnerListener view, Context context, String facilityno) {
    HashMap<String, String> param = getParam(context);
    param.put("facilityno", facilityno);
    HttpProxy.inner(view, context, selectzhtddan, param);
  }

  //  public static void SelectDataTai(HttpInnerListener view, Context context, String facilityno) {
  //    HashMap<String, String> param = getParam(context);
  //    param.put("facilityno", facilityno);
  //    HttpProxy.inner(view, context, SelectDataTai, param);
  //  }

  public static void OpenTheStage(BaseView view, String facilityno, String Covers) {
    HashMap<String, String> param = getParam(view.getContext());
    param.put("facilityno", facilityno);
    param.put("Covers", Covers);
    HttpProxy.bean(view, OpenTheStage, param, BaseBean.class);
  }

  public static void checkrights(BaseView view) {
    //    1、关单权限：functionname=POSBillClose 、 description=单据关单
    //    2、清洁房、脏房设置权限：functionname=POSFacilityNoSetSale 、 description=设施设置为可用
    //    3、录商品权限：functionname=POSBillItemNoAdd 、 description=输商品
    //    4、单据结帐权限：functionname=POSBillFolioCredit 、 description=单据结帐
    //    5、新建预约(修改)权限：functionname=RelaxNewDestine 、 description=新建预约
    //    6、取消预约权限：functionname=RelaxCancelDestine 、 description=取消预约
    //    7、退商品(项目)权限：functionname=POSBillItemNoDecrease 、 description=退商品
    //    8、开单(房)权限：functionname=POSBillNew 、 description=开新单
    //    9、换房(台)权限：functionname=POSBillFacilityNoChange 、 description=设施转台
    //    10、关单权限:functionname=POSBillClose、description=单据关单
    //    11、收银应收挂账权限:functionname=POSCreditConfer、description=收银应收挂账
    //    12、项目修改权限:functionname=POSBillItemUpBrandNo、description=项目修改
    //    13、设施上钟编辑权限:functionname=RelaxClockEdit、description=设施上钟编辑
    //    14、修改技师上钟时间权限:functionname=RelaxClockEditTime、description=修改技师上钟时间
    //    15、加单权限:functionname=POSBillItemNoAdd、description=加菜
    //    16、退项目 functionname=POSBillItemNoDecreaseBrandNo、description=退项目


    //    moduleid: '2002', functionname: 'POSBillNew', description: '开新单', accesstype: '0'
    HashMap<String, String> param = getParam(view.getContext());
    param.put("moduleid", "2002");
    param.put("functionname", "POSBillItemNoDecreaseBrandNo");
    param.put("description", "退项目");
    param.put("accesstype", "0");
    HttpProxy.bean(view, checkrights, param, YuYueBean.class);
  }

  private interface PermissionListener {
    void permissionOK();

    void permissionDefine(String reson);
  }

  public static void checkrights(Context context, final PermissionListener listener,
                                 String... paramString) {
    //    1、关单权限：functionname=POSBillClose 、 description=单据关单
    //    2、清洁房、脏房设置权限：functionname=POSFacilityNoSetSale 、 description=设施设置为可用
    //    3、录商品权限：functionname=POSBillItemNoAdd 、 description=输商品
    //    4、单据结帐权限：functionname=POSBillFolioCredit 、 description=单据结帐
    //    5、新建预约(修改)权限：functionname=RelaxNewDestine 、 description=新建预约
    //    6、取消预约权限：functionname=RelaxCancelDestine 、 description=取消预约
    //    7、退商品(项目)权限：functionname=POSBillItemNoDecrease 、 description=退商品
    //    8、开单(房)权限：functionname=POSBillNew 、 description=开新单
    //    9、换房(台)权限：functionname=POSBillFacilityNoChange 、 description=设施转台
    //    10、关单权限:functionname=POSBillClose、description=单据关单
    //    11、收银应收挂账权限:functionname=POSCreditConfer、description=收银应收挂账
    //    12、项目修改权限:functionname=POSBillItemUpBrandNo、description=项目修改
    //    13、设施上钟编辑权限:functionname=RelaxClockEdit、description=设施上钟编辑
    //    14、修改技师上钟时间权限:functionname=RelaxClockEditTime、description=修改技师上钟时间
    //    15、加单权限:functionname=POSBillItemNoAdd、description=加菜
    //    16、退项目 functionname=POSBillItemNoDecreaseBrandNo、description=退项目


    //    moduleid: '2002', functionname: 'POSBillNew', description: '开新单', accesstype: '0'
    HashMap<String, String> param = getParam(context);
    param.put("moduleid", "2002");
    param.put("accesstype", "0");
    try {
      param.put("functionname", paramString[0]);
      param.put("description", paramString[1]);
    } catch (Exception e) {
      listener.permissionDefine("未进行权限检查");
    }
    HttpProxy.inner(new HttpInnerListener() {
      @Override
      public void onString(String json) {
        BaseBean bean = new Gson().fromJson(json, BaseBean.class);
        if (bean != null && bean.isOk()) {
          listener.permissionOK();
        } else if (bean != null) {
          listener.permissionDefine(bean.getRespMsg());
        } else {
          listener.permissionDefine("请求异常");
        }
      }

      @Override
      public void onEmptyResponse() {
        listener.permissionDefine("请求异常");
      }
    }, context, checkrights, param);
  }

  public static void AddBillitem(final BaseView view, final String account,
                                 final List<RoomProjectListBean.ValueBean> sel,
                                 final String facilityno) {
    final HashMap<String, String> param = getParam(view.getContext());
    param.put("account", account);
    param.put("FacilityNo", facilityno);
    List<RoomProjectListBean.ValueBean.TransBean> data = RoomProjectListBean.ValueBean
        .getUploadXhl(sel, view.getContext(), account);
    String json = new Gson().toJson(data);
    param.put("xhl", json);
    param.put("RoundKind", "");
    checkrights(view.getContext(), new PermissionListener() {
      @Override
      public void permissionOK() {
        LOG.e("HttpManager", "checkrights.745:");
        HttpProxy.bean(view, AddBillitem, param, BaseBean.class);
      }

      @Override
      public void permissionDefine(String reson) {
        LOG.e("HttpManager", "checkrights.746:" + reson);
        view.onResponseError(new RequestType(AddBillitem), new BaseBean("-1", reson));
      }
    }, "POSBillItemNoAdd", "输商品");
  }

  public static void AddBillitemBigRelax(final BaseView view, final String account,
                                         final List<CaiErProductBean.ValueBean> sel,
                                         final String facilityno) {
    final HashMap<String, String> param = getParam(view.getContext());
    param.put("account", account);
    param.put("facilityno", facilityno);
    List<CaiErProductBean.ValueBean.TransBean> data = CaiErProductBean.ValueBean
        .getUploadXhl(sel, view.getContext(), account);
    String json = new Gson().toJson(data);
    param.put("xhl", json);
    param.put("relaxgroupid", "");
    HttpManager.GetRelaxTechJobStatus(new HttpInnerListener() {
      @Override
      public void onString(String json) {
        BaseBean bean = new Gson().fromJson(json, BaseBean.class);
        if (bean != null && bean.isOk()) {
          checkrights(view.getContext(), new PermissionListener() {
            @Override
            public void permissionOK() {
              LOG.e("HttpManager", "checkrights.745:");
              HttpProxy.bean(view, AddBillitemBigRelax, param, BaseBean.class);
            }

            @Override
            public void permissionDefine(String reson) {
              LOG.e("HttpManager", "checkrights.746:" + reson);
              view.onResponseError(new RequestType(AddBillitemBigRelax), new BaseBean("-1", reson));
            }
          }, "POSBillSend", "下单");
        } else {
          view.onResponseError(new RequestType(AddBillitemBigRelax), bean);
        }
      }

      @Override
      public void onEmptyResponse() {
        view.onResponseError(new RequestType(AddBillitemBigRelax), new BaseBean("-1", "请求异常"));

      }
    }, view.getContext());

  }


  public static void SelectServerlistView(BaseView view, String... hote) {
    HashMap<String, String> param = getParam(view.getContext());
    param.put("stype", "0");
    if (hote.length > 0) {
      param.put("hoteldate", hote[0]);
    }
    HttpProxy.bean(view, SelectServerlistView, param, CaiErListBean.class);
  }

  /// <summary>
  /// 更新钟房播报的状态并记录日志
  /// <param name="indexnumber"></param>唯一码
  /// <param name="facilityno"></param>房间号
  /// <param name="itemno"></param>项目号
  /// <param name="brandno"></param>技师号
  /// <param name="remark"></param>播报内容
  public static void updateSoundStatus(final BaseView view, final String sid, final String account,
                                       final String indexnumber, final String facilityno,
                                       final String itemno, final String brandno,
                                       final String remark) {
    HashMap<String, String> param = getParam(view.getContext());
    param.put("hoteldate", AppInfo.getSaleDate(view.getContext()));
    param.put("sid", sid);//列表序号
    param.put("account", account);
    param.put("indexnumber", indexnumber);
    param.put("facilityno", facilityno);
    param.put("itemno", itemno);
    param.put("brandno", brandno);
    param.put("remark", remark);
    param.put("type", "0");
    HttpProxy.bean(view, UpdateSoundStatue, param, BaseBean.class);
  }

}