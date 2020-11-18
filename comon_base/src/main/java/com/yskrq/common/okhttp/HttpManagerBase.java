package com.yskrq.common.okhttp;

import android.content.Context;

import com.google.gson.Gson;
import com.yskrq.common.AppInfo;
import com.yskrq.common.R;
import com.yskrq.common.bean.LoginBean;
import com.yskrq.common.bean.LoginBean2;
import com.yskrq.common.bean.ProfitCenterBean;
import com.yskrq.common.bean.TecColorBean;
import com.yskrq.common.bean.UpdateBean;
import com.yskrq.common.util.AppUtils;
import com.yskrq.common.util.LOG;
import com.yskrq.common.util.MD5Util;
import com.yskrq.common.util.SPUtil;
import com.yskrq.net_library.BaseBean;
import com.yskrq.net_library.BaseView;
import com.yskrq.net_library.HttpInnerListener;
import com.yskrq.net_library.HttpProxy;
import com.yskrq.net_library.RequestType;

import java.util.HashMap;

import static com.yskrq.common.okhttp.Constants_base.TransCode.GET_TEC_COLOR_STATUS;
import static com.yskrq.common.okhttp.Constants_base.TransCode.LOGIN_Luo;
import static com.yskrq.common.okhttp.Constants_base.TransCode.SELECT_FOOT_Liu;
import static com.yskrq.common.okhttp.Constants_base.TransCode.SELECT_LOGIN_Liu;
import static com.yskrq.common.okhttp.Constants_base.TransCode.UPDATE_CHECK_Liu;
import static com.yskrq.common.okhttp.Constants_base.TransCode.getTechNo;
import static com.yskrq.net_library.HttpProxy.TAG_CHANGE_URL;


/**
 * 页面业务简单，暂时不用 MVP
 * 页面继承 BaseView 复写 onResponseSucceed 方法
 * 同一界面多个接口 根据 type 判断，Type 为 -->  Constants.TransCode.***
 */
public class HttpManagerBase {

  /**
   * @param user 账号
   * @param pass 密码
   *             登录成功 {@link BaseView#onResponseSucceed(RequestType, BaseBean)}
   *             数据已保存{@link AppInfo#saveLoginInfo(Context, LoginBean)}
   *             登录逻辑出错，需用户做对应操作
   *             {@link com.yskrq.net_library.BaseView#onResponseError(RequestType, BaseBean)} )}
   *             {@link RequestType#more(Object obj)}  >>>> String、UpdateBean、List<ProfitCenterValueBean>
   */
  public static void login(final BaseView view, String user, String pass) {
    HashMap<String, String> param = new HashMap<>();
    param.put("UserId", user);
    param.put("Password", MD5Util.getMD5String(pass + "ym"));
    HttpProxy.inner(new HttpInnerListener() {
      @Override
      public void onString(String json) {
        final LoginBean login = new Gson().fromJson(json, LoginBean.class);
        if (login == null) {
          view.onConnectError(new RequestType(LOGIN_Luo)
              .more(login == null ? "接口异常" : login.getRespMsg()));
          return;
        }
        if (!"0".equals(login.getRespCode())) {
          view.onResponseError(new RequestType(LOGIN_Luo), login);
          return;
        }
        SPUtil.saveString(view.getContext(), TAG_CHANGE_URL, login.getServerip());
        getUpdate(login, new HttpInnerListener() {
          @Override
          public void onString(String json) {
            UpdateBean bean = new Gson().fromJson(json, UpdateBean.class);
            if (bean == null || bean.getUpdate() == null || AppUtils.getVersion(view.getContext())
                                                                    .equals(bean.getUpdate()
                                                                                .getAPPVersion())) {
              toMain(view, login);

            } else {
              view.onResponseError(new RequestType(LOGIN_Luo).more(login), bean);
            }
          }

          @Override
          public void onEmptyResponse() {
            LOG.e("HttpManagerBase", "onEmptyResponse.82:");
            view.onConnectError(new RequestType(LOGIN_Luo));
          }
        }, view);
      }

      @Override
      public void onEmptyResponse() {
        LOG.e("HttpManagerBase", "onEmptyResponse.155:");
        view.onConnectError(new RequestType(LOGIN_Luo));
      }
    }, view, LOGIN_Luo, param);
  }


  public static void toMain(final BaseView view, final LoginBean login) {
    getLogin(login, new HttpInnerListener() {
      @Override
      public void onString(String json) {
        final LoginBean2 bean = new Gson().fromJson(json, LoginBean2.class);
        if ("0".equals(bean.getError())) {
          if (bean.getUser_value() != null && bean.getUser_value().size() > 0) {
            login.setShopsid(bean.getUser_value().get(0).getShopsID());
            login.setGroupid(bean.getUser_value().get(0).getGroupId());
            login.setUsername(bean.getUser_value().get(0).getShopsName());

            getTechNo(login, new HttpInnerListener() {
              @Override
              public void onString(String json) {
                LOG.bean("HttpManagerBase", json);
                BaseBean teach = new Gson().fromJson(json, BaseBean.class);
                teach.setAll(json);
                AppInfo.setTechNum(view.getContext(), (String) teach.get(String.class, "Value"));
                AppInfo.setTechType(view.getContext(), (String) teach.get(String.class, "Comment"));
                AppInfo.setTechSex(view.getContext(), (String) teach.get(String.class, "sex"));
                getFoot(login, bean, new HttpInnerListener() {
                  @Override
                  public void onString(String json) {
                    ProfitCenterBean bean = new Gson().fromJson(json, ProfitCenterBean.class);
                    if (bean.isOk()) {
                      AppInfo.saveLoginInfo(view.getContext(), login);
                      if (bean.getProfitCenter_value() != null && bean.getProfitCenter_value()
                                                                      .size() <= 1) {
                        AppInfo
                            .setProfitCenter(view.getContext(), bean.getProfitCenter_value().get(0)
                                                                    .getCode());
                        getColors(login, new HttpInnerListener() {
                          @Override
                          public void onString(String json) {
                            TecColorBean tecColorBean = new Gson()
                                .fromJson(json, TecColorBean.class);
                            if (tecColorBean != null && tecColorBean.isOk()) {
                              AppInfo.saveColor(view.getContext(), tecColorBean.getValue());
                              view.onResponseSucceed(new RequestType(LOGIN_Luo), login);
                            } else {
                              view.onResponseError(new RequestType(LOGIN_Luo), tecColorBean);
                            }
                          }

                          @Override
                          public void onEmptyResponse() {

                          }
                        }, view);
                      } else {
                        view.onResponseError(new RequestType(LOGIN_Luo), bean);
                      }
                    }
                  }

                  @Override
                  public void onEmptyResponse() {
                    LOG.e("HttpManagerBase", "onEmptyResponse.125:");
                    view.onConnectError(new RequestType(LOGIN_Luo));
                  }
                }, view);
              }

              @Override
              public void onEmptyResponse() {
                LOG.e("HttpManagerBase", "onEmptyResponse.133:");
                view.onConnectError(new RequestType(LOGIN_Luo));
              }
            }, view);
          } else {
            view.onResponseError(new RequestType(LOGIN_Luo).more("用户信息获取失败"), null);
          }
        } else {
          view.onResponseError(new RequestType(LOGIN_Luo).more(bean.getComment()), null);
        }
      }

      @Override
      public void onEmptyResponse() {
        LOG.e("HttpManagerBase", "onEmptyResponse.147:");
        view.onConnectError(new RequestType(LOGIN_Luo));
      }
    }, view);
  }

  public static void getTechNo(LoginBean bean, HttpInnerListener listener, BaseView view) {
    HashMap<String, String> param = new HashMap<>();
    param.put("APPToken", bean.getApptoken());
    param.put("ShopsId", bean.getShopsid());
    param.put("UserId", bean.getUserid());
    HttpProxy.inner(listener, view, getTechNo, param);
  }

  public static void getColors(LoginBean bean, HttpInnerListener listener, BaseView view) {
    HashMap<String, String> param = new HashMap<>();
    param.put("APPToken", bean.getApptoken());
    param.put("ShopsId", bean.getShopsid());
    param.put("UserId", bean.getUserid());
    HttpProxy.inner(listener, view, GET_TEC_COLOR_STATUS, param);
  }

  private static void getUpdate(LoginBean bean, HttpInnerListener listener, BaseView view) {
    HashMap<String, String> param = new HashMap<>();
    param.put("APPToken", bean.getApptoken());
    param.put("UserId", bean.getUserid());
    param.put("ShopsId", bean.getShopsid());
    param.put("appName", view.getContext().getResources().getString(R.string.app_name));
    HttpProxy.inner(listener, view, UPDATE_CHECK_Liu, param);
  }

  private static void getLogin(LoginBean bean, HttpInnerListener listener, BaseView view) {
    HashMap<String, String> param = new HashMap<>();
    param.put("APPToken", bean.getApptoken());
    param.put("UserId", bean.getUserid());
    HttpProxy.inner(listener, view, SELECT_LOGIN_Liu, param);
  }

  private static void getFoot(LoginBean login, LoginBean2 bean, HttpInnerListener listener,
                              BaseView view) {
    HashMap<String, String> param = new HashMap<>();
    param.put("ShopsId", bean.getUser_value().get(0).getShopsID());
    param.put("APPToken", login.getApptoken());
    param.put("UserId", login.getUserid());
    HttpProxy.inner(listener, view, SELECT_FOOT_Liu, param);
  }
}
