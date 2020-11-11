package com.yskrq.yjs.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.rq.rvlibrary.BaseAdapter;
import com.rq.rvlibrary.BaseViewHolder;
import com.rq.rvlibrary.EasyViewHolder;
import com.rq.rvlibrary.OnItemClickListener;
import com.rq.rvlibrary.RecyclerUtil;
import com.yskrq.common.AppInfo;
import com.yskrq.common.BaseFragment;
import com.yskrq.common.OnClick;
import com.yskrq.common.util.LOG;
import com.yskrq.common.util.SPUtil;
import com.yskrq.common.widget.DialogHelper;
import com.yskrq.net_library.BaseBean;
import com.yskrq.net_library.HttpInnerListener;
import com.yskrq.net_library.RequestType;
import com.yskrq.yjs.MainActivity;
import com.yskrq.yjs.R;
import com.yskrq.yjs.RunningHelper;
import com.yskrq.yjs.Speaker;
import com.yskrq.yjs.bean.CaiErListBean;
import com.yskrq.yjs.bean.ListParamBean;
import com.yskrq.yjs.bean.RelaxListBean;
import com.yskrq.yjs.bean.RoomListBean;
import com.yskrq.yjs.keep.KeepAliveService;
import com.yskrq.yjs.net.HttpManager;
import com.yskrq.yjs.util.LocationUtil;
import com.yskrq.yjs.util.PhoneUtil;
import com.yskrq.yjs.util.SpeakManager;
import com.yskrq.yjs.widget.PopUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import ecomm.lib_comm.permission.PermissionUtil;

import static com.yskrq.common.okhttp.Constants_base.TransCode.GET_SALE_DATE;
import static com.yskrq.yjs.net.Constants.TransCode.BrandnoOut;
import static com.yskrq.yjs.net.Constants.TransCode.CancelTec;
import static com.yskrq.yjs.net.Constants.TransCode.ChangeRoom;
import static com.yskrq.yjs.net.Constants.TransCode.GetHotelCode;
import static com.yskrq.yjs.net.Constants.TransCode.GetHotelCodePos;
import static com.yskrq.yjs.net.Constants.TransCode.GetRelaxServerList;
import static com.yskrq.yjs.net.Constants.TransCode.RelaxAddClock;
import static com.yskrq.yjs.net.Constants.TransCode.SelectServerlistView;
import static com.yskrq.yjs.net.Constants.TransCode.Techoffwork;
import static com.yskrq.yjs.net.Constants.TransCode.Techonwork;
import static com.yskrq.yjs.net.Constants.TransCode.UpdateStatueYes;
import static com.yskrq.yjs.net.Constants.TransCode.brandnoIn;

public class HomeFragment extends BaseFragment implements OnItemClickListener, View.OnClickListener,
                                                          RunningHelper.OnSecondTickListener,
                                                          KeepAliveService.RefuseListener {
  @Override
  protected int layoutId() {
    return R.layout.fra_home;
  }

  BaseAdapter clickAdapter;
  BaseAdapter newTaskAdapter;
  BaseAdapter caierAdapter;
  boolean canCaiEr = false;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    KeepAliveService.addRefuseListener(this);
    RunningHelper.getInstance().register(this);
    canCaiEr = "1".equals(AppInfo.getTechType(getContext()));
    LOG.e("HomeFragment", "onCreate.canCaiEr:" + canCaiEr);

    HttpManager.refuseSaleDate(this);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    KeepAliveService.removeRefuseListener(this);
  }

  @Override
  protected void initView() {
    initTitle("云技师");
    clickAdapter = new BaseAdapter(getContext(), R.layout.item_main_btn, EasyViewHolder.class) {
      @Override
      public void onBindEasyHolder(BaseViewHolder holder, int position, Object bean) {
        holder.setItemText(R.id.text, (CharSequence) bean);
        ImageView iv = (ImageView) holder.getItemView(R.id.image);
        if ("个人业绩".equals(bean)) {
          iv.setImageResource(R.mipmap.ic_home_tab_2);
        } else if ("我的预约".equals(bean)) {
          iv.setImageResource(R.mipmap.ic_home_tab_3);
        } else if ("加项目".equals(bean)) {
          iv.setImageResource(R.mipmap.ic_home_tab_4);
        } else if ("呼叫服务".equals(bean)) {
          iv.setImageResource(R.mipmap.ic_home_tab_5);
        } else if ("包厢点单".equals(bean)) {
          iv.setImageResource(R.mipmap.ic_home_tab_1);
        }
      }
    };
    newTaskAdapter = new BaseAdapter(getContext(), R.layout.item_main_new_task, HomeZuItemViewHolder.class);
    new RecyclerUtil(clickAdapter).row(4).set2View((RecyclerView) findViewById(R.id.recycler));
    new RecyclerUtil(newTaskAdapter).set2View((RecyclerView) findViewById(R.id.recycler_new_task));
    clickAdapter.addOnItemClickListener(this);
    newTaskAdapter.addOnItemClickListener(this);
    setTextView2View("技师号:" + AppInfo.getTechNum(getContext()), R.id.tv_num);
    List<String> btn = new ArrayList<>();
    btn.add("包厢点单");//晚一点
    btn.add("个人业绩");
    btn.add("我的预约");
    btn.add("加项目");
    btn.add("呼叫服务");//晚一点
    //采耳子页面        //晚一点
    clickAdapter.setData(btn);
    if (canCaiEr) {
      findViewById(R.id.ll_caier).setVisibility(View.VISIBLE);
      caierAdapter = new BaseAdapter(getContext(), R.layout.item_caier, EasyViewHolder.class) {
        @Override
        public void onBindEasyHolder(BaseViewHolder holder, int position, Object o) {
          super.onBindEasyHolder(holder, position, o);
          if (o == null) return;
          CaiErListBean.FixServiceItemListViewValueBean bean = (CaiErListBean.FixServiceItemListViewValueBean) o;
          holder.setItemText(R.id.tv_room, bean.getFacilityno());
          holder.setItemText(R.id.tv_project, bean.getName());
          holder.setItemText(R.id.tv_time, bean.getExpendtime());
        }
      };
      caierAdapter.addOnItemClickListener(this, R.id.btn_commit);
      new RecyclerUtil(caierAdapter).set2View((RecyclerView) findViewById(R.id.recycler_caier));
    }
  }

  boolean openRunning = false;
  RelaxListBean.ValueBean first;


  private static final String TAG_SAVE_OPEN_ALL = "is.open.all";
  private static final String TAG_SAVE_RUSH = "is.open.rush";

  @Override
  public <T extends BaseBean> void onResponseSucceed(@NonNull RequestType type, @NonNull T data) {
    super.onResponseSucceed(type, data);
    if (type.is(GET_SALE_DATE)) {
      HttpManager.getOpenAll(this);
      HttpManager.getRush(this);
      loadData();
    } else if (type.is(GetHotelCode)) {
      LOG.e("MainActivity", "onResponseSucceed.56:");
      ListParamBean bean = (ListParamBean) data;
      if (bean != null && bean.getValue() != null && bean.getValue().size() > 0) {
        String isOpenAll = bean.getValue().get(0).getData();
        SPUtil.saveString(getContext(), TAG_SAVE_OPEN_ALL, isOpenAll);
        HttpManager.getRush(this);
        loadData();
        LOG.e("MainActivity", "onResponseSucceed.isOpenAll:" + isOpenAll);
      }
    } else if (type.is(GetHotelCodePos)) {
      ListParamBean bean = (ListParamBean) data;
      if (bean != null && bean.getValue() != null && bean.getValue().size() > 0) {
        String rush = bean.getValue().get(0).getData();
        SPUtil.saveString(getContext(), TAG_SAVE_RUSH, rush);
        //        KeepManager.startAliveRun(this);
      }
    } else if (type.is(GetRelaxServerList)) {
      if (isRefuse) {
        isRefuse = false;
        toast("刷新成功");
        lastRefuseTime = 0;
      }
      RelaxListBean bean = (RelaxListBean) data;
      refuseList(bean);
    } else if (type.is(CancelTec)) {
      loadData();
      LOG.e("HomeFragment", "refuseList.177:");
    } else if (type.is(Techonwork) || type.is(Techoffwork) || type.is(RelaxAddClock) || type
        .is(ChangeRoom)) {
      toast(data.getRespMsg());
      dismissLoading();
      loadData();
    } else if (type.is(brandnoIn)) {
      loadData();
      Speaker.speakOut(getContext(), "上钟打卡成功");
      PhoneUtil.closeVoice(getContext());
    } else if (type.is(BrandnoOut)) {
      Speaker.speakOut(getContext(), "下钟打卡成功");
      AppInfo.saveRunningTargetTime(getContext(), 0);
      loadData();
    } else if (type.is(SelectServerlistView)) {
      CaiErListBean bean = (CaiErListBean) data;
      if (bean != null && bean.getValue() != null && bean.getValue().size() > 0) {
        if (skitTimes > 0) {
          skitTimes--;
        } else {
          Speaker.speakOut(getContext(), "有新的呼叫服务");
        }
        findViewById(R.id.line_caier).setVisibility(View.VISIBLE);
        caierAdapter.setHeadHolder(0, null, EasyViewHolder.class, R.layout.item_caier_head, null);
        caierAdapter.setData(bean.getValue());
      } else {
        findViewById(R.id.line_caier).setVisibility(View.GONE);
        caierAdapter.clearHeadView();
        caierAdapter.setData(null);
      }
    } else if (type.is(UpdateStatueYes)) {
      skitTimes++;
      if (canCaiEr) HttpManager.SelectServerlistView(this);
    }
  }

  int skitTimes = 0;


  private void refuseList(RelaxListBean bean) {
    Context context = getContext();
    if (bean == null || bean.getValue() == null || bean.getValue().size() <= 0) {
      findViewById(R.id.ll_new_task).setVisibility(View.GONE);
      openRunning = false;
      resetTimePan();
      first = null;
      AppInfo.setWaitType(context, 0);
      AppInfo.setWait(context, "");
      AppInfo.saveRunningTargetTime(context, 0);
      return;
    }
    first = bean.getValue().get(0);
    int tag = first.getShowStatus();
    AppInfo.setWaitType(context, tag);
    if (tag == 2) {
      AppInfo.setWait(context, first.getExpendtime());
      try {
        int secondLeft = Integer.parseInt(first.getSid());
        LOG.e("KeepAliveService", "notify.first.getSid():" + first.getSid());
        AppInfo.saveRunningTargetTime(context, secondLeft * 1000);
      } catch (Exception e) {
      }
    } else if (tag == 1) {
      AppInfo.saveRunningTargetTime(context, 0);
    } else {
      AppInfo.setWait(context, "");
    }

    openRunning = true;
    findViewById(R.id.ll_new_task).setVisibility(View.VISIBLE);
    first = bean.getValue().get(0);
    int readTag = SpeakManager.isRead(getContext(), first);

    //GroupId：9000未安排 9001 已打卡  9002待打卡 9003已下钟
    needRunning(tag);
    newTaskAdapter.setData(bean.getValue());
  }

  @Override
  public <T extends BaseBean> void onResponseError(@NonNull RequestType type, @NonNull T data) {
    if (type.is(GetRelaxServerList)) {
      return;
    }
    super.onResponseError(type, data);
    if (type.is(Techonwork) || type.is(Techoffwork)) {
      dismissLoading();
    }
  }

  @Override
  public void onConnectError(@NonNull RequestType type) {
    super.onConnectError(type);
    if (type.is(Techonwork) || type.is(Techoffwork)) {
      dismissLoading();
    }
  }

  private void loadData(String... hoteDate) {
    HttpManager.GetRelaxServerList(this, hoteDate);
    if (canCaiEr) HttpManager.SelectServerlistView(this, hoteDate);
  }

  private void needRunning(int tag) {
    try {
      int secondLeft = Integer.parseInt(first.getSid());
      if (tag == 1) {
        LOG.e("HomeFragment", "notify.first.getSid():" + first.getSid());
        AppInfo.saveRunningTargetTime(getContext(), secondLeft * 1000);
      }
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("下钟\nHH:mm:ss");
      simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
      findViewById(R.id.tv_center).setTag(tag);
      LOG.e("HomeFragment", "needRunning.251:");
      openRunning = true;
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void resetTimePan() {
    ((TextView) findViewById(R.id.tv_center)).setText("---\n00:00:00");
    PhoneUtil.openVoice(getContext());
    findViewById(R.id.tv_center).setTag(0);
    findViewById(R.id.tv_center).setBackgroundResource(R.drawable.circle_bg_blue);
  }

  @Override
  public void onItemClick(BaseViewHolder holder, Object o, View view, int position) {
    if (view
        .getId() == R.id.btn_commit && o instanceof CaiErListBean.FixServiceItemListViewValueBean) {
      //采耳点击了确定
      CaiErListBean.FixServiceItemListViewValueBean bean = (CaiErListBean.FixServiceItemListViewValueBean) o;
      HttpManager
          .UpdateStatueYes(this, bean.getAccount(), bean.getFacilityno(), bean.getSeqnum(), bean
              .getSid());

    } else if (o instanceof String) {
      if ("个人业绩".equals(o)) {
        PersonalAchievementActivity.start(getContext());
      } else if ("我的预约".equals(o)) {
        MyOrderActivity.start(getContext());
      } else if ("加项目".equals(o)) {
        if (first == null) {
          toast("未发现上点的项目，暂不可加项目");
          return;
        }
        AddProjectWindowActivity.start(getActivity(), first.getIndexnumber(), first.getAccount());
      } else if ("呼叫服务".equals(o)) {
        HttpManager.GetRelaxTechJobStatus(new HttpInnerListener() {
          @Override
          public void onString(String json) {
            BaseBean bean=new Gson().fromJson(json,BaseBean.class);
            if(bean!=null&&bean.isOk()){
              AddProjectActivity.start(getActivity());
            }
          }

          @Override
          public void onEmptyResponse() {

          }
        },getContext());
      } else if ("包厢点单".equals(o)) {
        if (first == null) {
          toast("未发现上点的项目，暂不可加项目");
          return;
        }
        RoomProjectActivity.start(getActivity(), first.getAccount(), first.getFacilityno());
      } else {

        toast("点击了" + o);
      }
    } else if (o instanceof RelaxListBean.ValueBean) {
      final RelaxListBean.ValueBean itemFrom = (RelaxListBean.ValueBean) o;
      PopUtil.showTui(getActivity(), view, itemFrom, new PopUtil.OnPopClickListener() {
        @Override
        public void onPopClick(int position, Object data, View clickFrom) {
          if (position == 0) {//退技师
            HttpManager.CancelTec(HomeFragment.this, itemFrom.getAccount());
          } else if (position == 1) {//整单换房
            SelectRoomWindowActivity
                .start(getContext(), new SelectRoomWindowActivity.RoomSelectListener() {
                  @Override
                  public void onSelect(RoomListBean.FixingViewValueBean item) {
                    LOG.e("HomeFragment", "onSelect.ChangeRoom:" + item.getFacilityNo());
                    HttpManager.ChangeRoom(HomeFragment.this, itemFrom.getFacilityno(), item
                        .getFacilityNo(), itemFrom.getAccount());
                  }
                });
          }
        }
      });
    }
  }

  @OnClick({R.id.btn_to_tai, R.id.btn_modify, R.id.btn_sign, R.id.btn_add, R.id.tv_center, R.id.btn_refuse})
  //, R.id.tv_test_1, R.id.tv_test_2
  public void onClick(View v) {
    if (v.getId() == R.id.btn_to_tai) {
      RoomListActivity.start(getContext());
    } else if (v.getId() == R.id.btn_modify) {//更改项目
      ModifyProjectWindowActivity.start(getActivity(), first);
    } else if (v.getId() == R.id.btn_sign) {//考勤打卡
      toSign();
    } else if (v.getId() == R.id.btn_add) {//加钟
      if (first == null) {
        toast("未发现上点的项目，暂不可加加钟");
        return;
      }
      showZhongDialog();
    } else if (v.getId() == R.id.tv_center && v.getTag() instanceof Integer) {
      int tag = (int) v.getTag();// 0-不能打卡  1-代打卡   2-已打卡 3-已下钟
      sing(tag, first);
    } else if (v.getId() == R.id.btn_refuse) {//刷新
      isRefuse = true;
      lastRefuseTime = 0;
      LOG.e("HomeFragment", "refuseList.327:");
      loadData();
    }
  }

  private void sing(final int tag, final RelaxListBean.ValueBean first) {//上下钟打卡
    if (first == null) return;
    DialogHelper
        .showRemind(getContext(), tag == 1 ? "是否上钟?" : "是否下钟?", new DialogHelper.DialogConfirmListener() {
          @Override
          public void onSure() {
            if (tag == 1) {
              HttpManager.brandnoIn(HomeFragment.this, first.getSeqnum(), first.getAccount());
            } else {
              HttpManager.BrandnoOut(HomeFragment.this, first.getSeqnum(), first.getAccount());
            }
          }

          @Override
          public void onCancel() {

          }
        });
  }

  @Override
  public void onResume() {
    super.onResume();
    LOG.e("HomeFragment", "onResume.399:");
    loadData();
  }

  private void toSign() {
    PermissionUtil.openLocate(getActivity(), new PermissionUtil.OnPermissionListener() {
      @Override
      public void onPermissionOk() {
        DialogHelper.showCheckBoxPan(getContext(), new DialogHelper.DialogCheckListener() {
          @Override
          public void onSure(String s) {
            signNet(s);
          }

          @Override
          public void onCancel() {

          }
        }, "打卡", "上班", "下班");

      }
    });
  }

  private void signNet(final String btnStr) {
    showLoading();
    LocationUtil
        .requestLocation(getContext(), LocationUtil.Mode.AUTO, new LocationUtil.OnResponseListener() {
          @Override
          public void onSuccessResponse(double latitude, double longitude) {
            showBanDialog(latitude, longitude, btnStr);
            LocationUtil.stopLocation();
          }

          @Override
          public void onErrorResponse(String provider, int status) {
            dismissLoading();
          }
        });

  }

  private void showZhongDialog() {//加钟
    LOG.e("HomeFragment", "showZhongDialog.322:");
    final String indexnumber = first.getIndexnumber();
    final String account = first.getAccount();
    DialogHelper.showCheckBoxPan(getContext(), new DialogHelper.DialogCheckListener() {
      @Override
      public void onSure(String s) {
        if ("全钟".equals(s)) {
          HttpManager.RelaxAddClock(HomeFragment.this, true, indexnumber, account);
        } else if ("半钟".equals(s)) {
          HttpManager.RelaxAddClock(HomeFragment.this, false, indexnumber, account);
        }
      }

      @Override
      public void onCancel() {

      }
    }, "加钟", "全钟", "半钟");
  }

  private void showBanDialog(double latitude, double longitude, String btnStr) {//上下班
    if (!MainActivity.isShowToUser) {
      return;
    }
    if (!AppInfo.canSign(getContext(), latitude, longitude)) {
      toast("请确认当前WIFI和地点能够进行打卡操作");
      String wifiName = SPUtil.getString(getContext(), "Sign.wifi");
      String lat = SPUtil.getString(getContext(), "Sign.lat");
      String lon = SPUtil.getString(getContext(), "Sign.lon");
      HttpManager
          .senError(getContext(), "wifiName<" + wifiName + ">  lat<" + lat + ">  lon<" + lon + ">", null);
      dismissLoading();
      return;
    }
    if ("上班".equals(btnStr)) {
      HttpManager.Techonwork(HomeFragment.this);
    } else if ("下班".equals(btnStr)) {
      HttpManager.Techoffwork(HomeFragment.this);
    }
  }

  boolean isRefuse = false;

  @Override
  public void on15Second() {
    //    if (KeepLive.stop) refuseList();
  }

  long lastRefuseTime = 0;

  @Override
  public void onSecond() {
    LOG.e("HomeFragment", "onSecond.467:");
    if (!MainActivity.isShowToUser || getContext() == null) {
      return;
    }
    if (System.currentTimeMillis() - lastRefuseTime < 900) {
      return;
    }
    lastRefuseTime = System.currentTimeMillis();
    if (!openRunning) return;
    TextView textView = findViewById(R.id.tv_center);
    if (AppInfo.getWaitType(getContext()) == 0) return;
    int tag = AppInfo.getWaitType(getContext());
    if (tag == 0) {
      openRunning = false;
      resetTimePan();
    } else if (tag == 1) {
      textView.setBackgroundResource(R.drawable.circle_bg_blue);
      textView.setText("上钟\n" + "00:00:00");
    } else if (tag == 2) {
      long timeLeft = AppInfo.getRunningLeftTime(getContext());
      SimpleDateFormat simpleDateFormat;
      simpleDateFormat = new SimpleDateFormat("下钟\nHH:mm:ss");
      if (timeLeft < 0) {
        timeLeft = -timeLeft;
        textView.setBackgroundResource(R.drawable.circle_bg_red);
      } else {
        textView.setBackgroundResource(R.drawable.circle_bg_green);
      }
      simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
      textView.setText(simpleDateFormat.format(timeLeft));
    } else {
      resetTimePan();
    }
  }
  //    if (openRunning && time > 0 && textView != null) {
  //      time += 1000;
  //      if (AppInfo.getWaitType(getContext()) == 1) {
  //        textView.setBackgroundResource(R.drawable.circle_bg_red);
  //        textView.setText("已等待\n" + AppInfo.getWait(getContext()) + " 分钟");
  //      } else {
  //        textView.setBackgroundResource(R.drawable.circle_bg_green);
  //        textView.setText(simpleDateFormat.format(time));
  //      }
  //    } else if (openRunning && textView != null) {
  //      time -= 1000;
  //      textView.setBackgroundResource(R.drawable.circle_bg_red);
  //      textView.setText(simpleDateFormat.format(-time));
  //    }

  @Override
  public void onHour() {

  }

  @Override
  public void onDetach() {
    super.onDetach();
    HttpManager.senDetachError(getContext(), "Fragment.dismiss.onDetach", null);
  }

  @Override
  public void onRefuse(RelaxListBean bean) {
    if (canCaiEr) HttpManager.SelectServerlistView(this);
    refuseList(bean);
  }
}
