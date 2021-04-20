package com.yskrq.yjs.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.rq.rvlibrary.BaseAdapter;
import com.rq.rvlibrary.BaseViewHolder;
import com.rq.rvlibrary.EasyViewHolder;
import com.rq.rvlibrary.OnItemClickListener;
import com.rq.rvlibrary.RecyclerUtil;
import com.tencent.bugly.crashreport.CrashReport;
import com.yskrq.common.AppInfo;
import com.yskrq.common.BASE;
import com.yskrq.common.BaseFragment;
import com.yskrq.common.OnClick;
import com.yskrq.common.bean.RelaxListBean;
import com.yskrq.common.bean.TecColorBean;
import com.yskrq.common.okhttp.HttpManagerBase;
import com.yskrq.common.util.AppUtils;
import com.yskrq.common.util.LOG;
import com.yskrq.common.util.SPUtil;
import com.yskrq.common.util.ToastUtil;
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
import com.yskrq.yjs.bean.RoomListBean;
import com.yskrq.yjs.jpush.JpushHelper;
import com.yskrq.yjs.keep.KeepAliveService;
import com.yskrq.yjs.keep.KeepManager;
import com.yskrq.yjs.net.HttpManager;
import com.yskrq.yjs.util.LocationUtil;
import com.yskrq.yjs.util.PhoneUtil;
import com.yskrq.yjs.util.SoundUtils;
import com.yskrq.yjs.util.TechStatusManager;
import com.yskrq.yjs.util.status.NetWorkMonitor;
import com.yskrq.yjs.util.status.NetWorkMonitorManager;
import com.yskrq.yjs.util.status.NetWorkState;
import com.yskrq.yjs.widget.PopUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
                                                          KeepAliveService.RefuseListener,
                                                          JpushHelper.OnPushListener,
                                                          TechStatusManager.OnTechStatusListener,
                                                          TechStatusManager.OnDataChangeListener {
  @Override
  protected int layoutId() {
    return R.layout.fra_home;
  }

  BaseAdapter clickAdapter;
  BaseAdapter newTaskAdapter;
  BaseAdapter caierAdapter;
  boolean canCaiEr = false;
  SoundUtils mSoundUtils;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    try {
      CrashReport.setUserId(AppInfo.getUserid());
    } catch (Exception e) {

    }
    mSoundUtils = new SoundUtils(getActivity(), SoundUtils.RING_SOUND);
    mSoundUtils.putSound(0, R.raw.fiveremind);

    KeepAliveService.addRefuseListener(this);
    RunningHelper.getInstance().register(this);
    NetWorkMonitorManager.getInstance().register(this);
    canCaiEr = "1".equals(AppInfo.getTechType(getContext()));
    LOG.e("HomeFragment", "onCreate.canCaiEr:" + canCaiEr);
    HttpManager.refuseSaleDate(this);
    AppInfo.getColors(getContext());
    JpushHelper.addOnPushListener(this);
    TechStatusManager.getInstance().addOnTechStatusListener(this);
    TechStatusManager.getInstance().addDataChangeListener(this);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    KeepAliveService.removeRefuseListener(this);
    JpushHelper.removeOnPushListener(this);
    RunningHelper.getInstance().remove(this);
    NetWorkMonitorManager.getInstance().unregister(this);
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
    setRecyclerView(R.id.recycler, new RecyclerUtil(clickAdapter).row(4));
    setRecyclerView(R.id.recycler_new_task, newTaskAdapter);
    clickAdapter.addOnItemClickListener(this);
    newTaskAdapter.addOnItemClickListener(this);
    try {
      String color1 = null, color2 = null;
      for (TecColorBean.ValueBean color : AppInfo.getColors(getContext())) {
        if ("htmlBrandColor1".equals(color.getEntry())) {
          color1 = color.getData();
        } else if ("htmlBrandColor2".equals(color.getEntry())) {
          color2 = color.getData();
        }
      }
      newTaskAdapter.setPassData(color1, color2);
    } catch (Exception e) {

    }

    setTextView2View(R.id.tv_num, "技师号:" + AppInfo.getTechNum());
    List<String> btn = new ArrayList<>();
    btn.add("包厢点单");//晚一点
    btn.add("个人业绩");
    btn.add("我的预约");
    btn.add("加项目");
    btn.add("呼叫服务");//晚一点
    //采耳子页面        //晚一点
    clickAdapter.setData(btn);
    if (canCaiEr) {
      setVisibility(R.id.ll_caier, View.VISIBLE);
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
      setRecyclerView(R.id.recycler_caier, caierAdapter);
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
      }
    } else if (type.is(GetRelaxServerList)) {
      if (isClickRefuse) {
        isClickRefuse = false;
        toast("刷新成功");
        lastRefuseTime = 0;
      }
      RelaxListBean bean = (RelaxListBean) data;
      if (bean != null) {
        TechStatusManager.getInstance().refuseFromService(bean);
      }
      //      TechStatusManager.getInstance().refuseFromService(bean);
      if (bean == null || bean.getValue() == null || bean.getValue().size() == 0) {
        if (KeepAliveService.READ_WAY == 1) KeepManager.startAliveRun();
      }
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
      if (AppInfo.needScreenKeep()) {
        KeepOnActivity.start(getContext());
      }
    } else if (type.is(BrandnoOut)) {
      Speaker.speakOut(getContext(), "下钟打卡成功");
      TechStatusManager.getInstance().brandOut();
      loadData();
    } else if (type.is(SelectServerlistView)) {
      CaiErListBean bean = (CaiErListBean) data;
      if (bean != null && bean.getValue() != null && bean.getValue().size() > 0) {
        if (skitTimes > 0) {
          skitTimes--;
        } else {
          Speaker.speakOut(getContext(), "有新的呼叫服务");
        }
        setVisibility(R.id.line_caier, View.VISIBLE);
        caierAdapter.setHeadHolder(0, null, EasyViewHolder.class, R.layout.item_caier_head, null);
        caierAdapter.setData(bean.getValue());
      } else {
        setVisibility(R.id.line_caier, View.GONE);
        caierAdapter.clearHeadView();
        caierAdapter.setData(null);
      }
    } else if (type.is(UpdateStatueYes)) {
      skitTimes++;
      if (canCaiEr) HttpManager.SelectServerlistView(this);
    }
  }

  int skitTimes = 0;
  private int mStatus;

  @Override
  public void onStatus(RelaxListBean.ValueBean first, int status, long runningTargetTime,
                       String notifyShowTime) {
    this.first = first;
    openRunning = status > 0;
    if (mStatus != 1 && status == 1) {
      mStatus = 1;
      loadData();
    }
    setViewTag(R.id.tv_center, status);
    if (status <= 0) resetTimePan();
    LOG.e("HomeFragment", "onStatus.status:" + status);
    LOG.e("HomeFragment", "onStatus.runningTargetTime:" + runningTargetTime);
    LOG.e("HomeFragment", "onStatus.notifyShowTime:" + notifyShowTime);
  }

  public void refuseList(RelaxListBean bean) {
    if (bean != null && newTaskAdapter != null) newTaskAdapter.setData(bean.getValue());
    setVisibility(R.id.ll_new_task, bean == null ? View.GONE : View.VISIBLE);
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

  @NetWorkMonitor
  public void onNetChange(NetWorkState state) {
    if (state == NetWorkState.NONE) {
      setVisibility(R.id.tv_net_error, View.VISIBLE);
    } else {
      loadData();
      setVisibility(R.id.tv_net_error, View.GONE);
    }
  }

  private void loadData(String... hoteDate) {
    LOG.e("HomeFragment", "loadData.291:" + BASE.getUseFrom());
    LOG.e("HomeFragment", "loadData.GetRelaxServerList:");
    HttpManager.GetRelaxServerList(this, hoteDate);
    if (canCaiEr) HttpManager.SelectServerlistView(this, hoteDate);
  }

  private void resetTimePan() {
    setTextView2View(R.id.tv_center, "---\n00:00:00");
    setViewTag(R.id.tv_center, 0);
    setViewBackResource(R.id.tv_center, R.drawable.circle_bg_blue);
    if (KeepAliveService.READ_WAY == 1) KeepManager.stopAliveRun();
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
        if (first == null || first.getShowStatus() != 2) {
          toast("未发现已上钟的项目，暂不可加项目");
          return;
        }
        AddProjectWindowActivity.start(getActivity(), first.getIndexnumber(), first.getAccount());
      } else if ("呼叫服务".equals(o)) {
        HttpManager.GetRelaxTechJobStatus(new HttpInnerListener() {
          @Override
          public void onString(String json) {
            BaseBean bean = new Gson().fromJson(json, BaseBean.class);
            if (bean != null && bean.isOk()) {
              AddProjectActivity.start(getActivity());
            } else if (bean != null) {
              toast(bean.getRespMsg());
            } else {
              toast(json);
            }
          }

          @Override
          public void onEmptyResponse() {
            toast("网络数据异常，请稍后重试");
          }
        }, getContext());
      } else if ("包厢点单".equals(o)) {
        if (first == null || first.getShowStatus() != 2) {
          toast("未发现已上钟的项目，暂不可点单");
          return;
        }
        RoomProjectActivity.start(getActivity(), first.getAccount(), first.getFacilityno());
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
  public void onClick(View v) {
    if (v.getId() == R.id.btn_to_tai) {
      RoomListActivity.start(getContext());
    } else if (v.getId() == R.id.btn_modify) {//更改项目
      GaiProjectWindowActivity.start(getActivity(), first);
    } else if (v.getId() == R.id.btn_sign) {//考勤打卡
      toSign();
    } else if (v.getId() == R.id.btn_add) {//加钟
      if (first == null || first.getShowStatus() != 2) {
        toast("未发现已上钟的项目，暂不可加钟");
        return;
      }
      showZhongDialog();
    } else if (v.getId() == R.id.tv_center) {
      //      mSoundUtils.playSound(0, SoundUtils.SINGLE_PLAY);
      if (v.getTag() instanceof Integer) {
        int tag = (int) v.getTag();// 0-不能打卡  1-代打卡   2-已打卡 3-已下钟
        LOG.e("HomeFragment", "onClick.433:" + tag);
        if (tag != 1 && AppInfo.needScreenKeep() && !TechStatusManager.getInstance().willClose()) {
          KeepOnActivity.start(getContext());
          return;
        }
        sing(tag, first);
      } else {
        LOG.e("HomeFragment", "onClick.446:");
      }
    } else if (v.getId() == R.id.btn_refuse) {//刷新
      isClickRefuse = true;
      lastRefuseTime = 0;
      LOG.e("HomeFragment", "refuseList.327:");
      if (canCaiEr) HttpManager.SelectServerlistView(this);
      loadData();
    }
  }

  private void sing(final int tag, final RelaxListBean.ValueBean first) {//上下钟打卡
    if (first == null) {
      LOG.e("HomeFragment", "sing.454:");
      return;
    }
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

  boolean isShow = false;

  public boolean isShow() {
    return isShow;
  }

  @Override
  public void onPause() {
    super.onPause();
    this.isShow = false;
    LOG.e("HomeFragment", "onPause.onRefuse:" + isShow());
    checkRunning(false);
  }

  @Override
  public void onResume() {
    super.onResume();
    this.isShow = true;
    LOG.e("HomeFragment", "onResume.onRefuse:" + isShow());
    loadData();
    checkRunning(true);
  }

  private void checkRunning(boolean show) {
    if (!show) {
      if (KeepAliveService.READ_WAY == 1) KeepManager.startAliveRun();
    } else if (!openRunning) {
      if (KeepAliveService.READ_WAY == 1) KeepManager.stopAliveRun();
    }
  }

  //打卡检查
  private void toSign() {
    if (AppInfo.needLocation(getContext()) || AppInfo.needWifi()) {
      PermissionUtil.openLocate(getActivity(), new PermissionUtil.OnPermissionListener() {
        @Override
        public void onPermissionOk() {
          LOG.e("HomeFragment", "onPermissionOk.529:");
          if (AppUtils.isLocServiceEnable(getContext())) {
            showSignDialog();
          } else {
            ToastUtil.show("请打开定位后签到");
          }
        }
      });
    } else {
      LOG.e("HomeFragment", "toSign.534:");
      showSignDialog();
    }
  }

  //展示打卡弹窗
  private void showSignDialog() {
    DialogHelper.showCheckBoxPan(getContext(), new DialogHelper.DialogCheckListener() {
      @Override
      public void onSure(final String s) {
        if (!AppInfo.needLocation(getContext())) {
          uploadSignInfo(0, 0, s);
        } else {
          signWhitLocation(s);
        }
      }

      @Override
      public void onCancel() {
      }
    }, "打卡", "上班", "下班");
  }

  private void signWhitLocation(final String btnStr) {
    showLoading();
    LocationUtil
        .requestLocation(getContext(), LocationUtil.Mode.AUTO, new LocationUtil.OnResponseListener() {
          @Override
          public void onSuccessResponse(double latitude, double longitude) {
            uploadSignInfo(latitude, longitude, btnStr);
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

  private void uploadSignInfo(double latitude, double longitude, String btnStr) {//上下班
    if (!MainActivity.isShowToUser) {
      return;
    }
    int signStatus = AppInfo.canSign(getContext(), latitude, longitude);
    if (signStatus == 1) {
      toast("请确认当前WIFI后进行打卡操作");
      return;
    } else if (signStatus == 2) {
      toast("当前位置不在打卡范围内");
      return;
    }
    if ("上班".equals(btnStr)) {
      HttpManager.Techonwork(HomeFragment.this);
    } else if ("下班".equals(btnStr)) {
      HttpManager.Techoffwork(HomeFragment.this);
    }
  }

  boolean isClickRefuse = false;

  @Override
  public void on15Second() {
  }

  long lastRefuseTime = 0;

  @Override
  public void onSecond() {
    if (!MainActivity.isShowToUser || getContext() == null) {
      return;
    }
    if (System.currentTimeMillis() - lastRefuseTime < 900) {
      return;
    }
    lastRefuseTime = System.currentTimeMillis();
    if (!openRunning) {
      return;
    }
    int tag = TechStatusManager.getInstance().getWaitType();
    if (tag == 0) {
      resetTimePan();
      return;
    }
    setViewTag(R.id.tv_center, tag);
    if (tag == 0) {
      openRunning = false;
      resetTimePan();
    } else if (tag == 1) {
      setViewBackResource(R.id.tv_center, R.drawable.circle_bg_blue);
      setTextView2View(R.id.tv_center, "上钟\n" + "00:00:00");
    } else if (tag == 2) {
      if (KeepAliveService.READ_WAY == 1) KeepManager.stopAliveRun();
      long timeLeft = TechStatusManager.getInstance().getRunningLeftTime();
      SimpleDateFormat simpleDateFormat;
      simpleDateFormat = new SimpleDateFormat("下钟\nHH:mm:ss");
      if (timeLeft < 0) {
        timeLeft = -timeLeft;
        setViewBackResource(R.id.tv_center, R.drawable.circle_bg_red);
      } else {
        setViewBackResource(R.id.tv_center, R.drawable.circle_bg_green);
      }
      simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
      String str = simpleDateFormat.format(timeLeft);
      setTextView2View(R.id.tv_center, str);
      checkShowStr(str);
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
  public void onRefuse(RelaxListBean bean) {
    LOG.e("HomeFragment", "onRefuse.654:" + isShow());
    if (!isShow() || !openRunning) {
      LOG.e("HomeFragment", "onRefuse.655:" + isShow());
      if (canCaiEr) HttpManager.SelectServerlistView(this);
      refuseList(bean);
    }
  }

  @Override
  public void onConnected(boolean connected) {
    HttpManagerBase
        .senError("极光" + AppInfo.getTechNum(), AppInfo.getTechNum() + ".连接状态：" + connected);
  }

  @Override
  public void onPush(RelaxListBean.ValueBean bean) {
    loadData();
  }

  private void checkShowStr(String s) {
    try {
      int tag = (int) findViewById(R.id.tv_center).getTag();
      LOG.d("HomeFragment", tag + ".afterTextChanged:" + s);
      boolean refuse = s.endsWith("30") || s.endsWith("00");
      if (refuse && tag > 0) {
        loadData();
      } else {
        LOG.e("HomeFragment", "checkShowStr.744:");
      }
    } catch (Exception e) {

    }
  }
}
