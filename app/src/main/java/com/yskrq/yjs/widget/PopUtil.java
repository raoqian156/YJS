package com.yskrq.yjs.widget;

import android.app.Activity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bigkoo.pickerview.configure.PickerOptions;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.contrarywind.interfaces.IPickerViewData;
import com.rq.rvlibrary.BaseAdapter;
import com.rq.rvlibrary.BaseViewHolder;
import com.rq.rvlibrary.EasyViewHolder;
import com.rq.rvlibrary.OnItemClickListener;
import com.rq.rvlibrary.RecyclerUtil;
import com.yskrq.common.util.ToastUtil;
import com.yskrq.yjs.R;
import com.yskrq.yjs.bean.CaiErProductBean;
import com.yskrq.yjs.bean.OrderListBean;
import com.yskrq.common.bean.RelaxListBean;
import com.yskrq.yjs.bean.RoomBean;
import com.yskrq.yjs.bean.RoomProjectListBean;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static com.bigkoo.pickerview.configure.PickerOptions.TYPE_PICKER_OPTIONS;
import static com.yskrq.yjs.widget.PopUtil.OnPhotoFromSelectListener.CANCEL;
import static com.yskrq.yjs.widget.PopUtil.OnPhotoFromSelectListener.SELECT_PHOTO;
import static com.yskrq.yjs.widget.PopUtil.OnPhotoFromSelectListener.SELECT_TAKE;


public class PopUtil {

  public interface OnPopWindowSelectListener<M> {
    void onItemClick(int position, M data, View clickFrom);
  }

  public static final int ACTION_CHANGE = 0x00123;
  public static final int ACTION_CLEAR = 0x00124;
  public static final int ACTION_COMMINT = 0x00125;

  public static <M> CustomPopWindow showCarPop(Activity view, final List<M> list,
                                               final OnPopWindowSelectListener<M> mClickListener) {
    View contentView = LayoutInflater.from(view).inflate(R.layout.pop_shop_car, null);
    //处理popWindow 显示内容
    final RecyclerView rv = contentView.findViewById(R.id.recycler);
    final CustomPopWindow popWindow = new CustomPopWindow.PopupWindowBuilder(view)
        .setView(contentView).sizeAs(null).create();
    contentView.findViewById(R.id.view).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        popWindow.dissmiss();
      }
    });
    popWindow.setParent(view.getWindow().getDecorView());
    final int height = (int) view.getResources().getDimension(R.dimen.padding_80px);
    int size = Math.min(3, list.size());
    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height * size);
    rv.setLayoutParams(lp);
    final BaseAdapter mAdapter = new BaseAdapter(view, R.layout.item_shop_car, EasyViewHolder.class) {
      @Override
      public void onBindEasyHolder(BaseViewHolder holder, int position, Object data) {
        if (data instanceof RoomProjectListBean.ValueBean) {
          RoomProjectListBean.ValueBean bean = (RoomProjectListBean.ValueBean) data;
          holder.setItemText(R.id.tv_name, bean.getName());
          holder.setItemText(R.id.tv_price, "￥" + bean.getPrice());
          StringBuffer show = new StringBuffer();
          if (!TextUtils.isEmpty(bean.getTechnician())) {
            show.append("、" + bean.getTechnician());
          }
          if (!TextUtils.isEmpty(bean.getTechnicianSell())) {
            show.append("、" + bean.getTechnicianSell());
          }
          if (!TextUtils.isEmpty(show.toString())) {
            holder.getItemView(R.id.btn_item_cut).setVisibility(View.GONE);
            holder.getItemView(R.id.btn_item_add).setVisibility(View.GONE);
            holder.setItemText(R.id.tv_num, show.toString().substring(1) + "号");
          } else {
            holder.getItemView(R.id.btn_item_cut).setVisibility(View.VISIBLE);
            holder.getItemView(R.id.btn_item_add).setVisibility(View.VISIBLE);
            holder.setItemText(R.id.tv_num, bean.getShowNum() + "");
          }
        } else if (data instanceof CaiErProductBean.ValueBean) {
          CaiErProductBean.ValueBean bean = (CaiErProductBean.ValueBean) data;
          holder.setItemText(R.id.tv_name, bean.getName());
          holder.setItemText(R.id.tv_price, "￥" + bean.getPrice());
          holder.getItemView(R.id.btn_item_cut).setVisibility(View.VISIBLE);
          holder.getItemView(R.id.btn_item_add).setVisibility(View.VISIBLE);
          holder.setItemText(R.id.tv_num, bean.getNum() + "");
        }
      }
    };
    mAdapter.addOnItemClickListener(new OnItemClickListener() {
      @Override
      public void onItemClick(BaseViewHolder holder, Object o, View view, int position) {
        if (o instanceof CaiErProductBean.ValueBean) {
          CaiErProductBean.ValueBean bean = (CaiErProductBean.ValueBean) o;
          if (view.getId() == R.id.btn_item_add) {
            bean.showSizeAdd();
          } else if (view.getId() == R.id.btn_item_cut) {
            bean.showSizeCut();
          }
          mAdapter.notifyDataSetChanged();
          mClickListener.onItemClick(ACTION_CHANGE, null, view);
          int size = Math.min(3, mAdapter.getData().size());
          if (size == 0) {
            mClickListener.onItemClick(ACTION_CLEAR, null, view);
            popWindow.dissmiss();
            return;
          }
          LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height * size);
          rv.setLayoutParams(lp);
          return;
        }
        RoomProjectListBean.ValueBean bean = (RoomProjectListBean.ValueBean) o;
        if (view.getId() == R.id.btn_item_add) {
          bean.showSizeAdd();
        } else if (view.getId() == R.id.btn_item_cut) {
          bean.showSizeCut();
        }
        mAdapter.notifyDataSetChanged();
        mClickListener.onItemClick(ACTION_CHANGE, null, view);
        int size = Math.min(3, mAdapter.getData().size());
        if (size == 0) {
          mClickListener.onItemClick(ACTION_CLEAR, null, view);
          popWindow.dissmiss();
          return;
        }
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height * size);
        rv.setLayoutParams(lp);
      }
    }, R.id.btn_item_cut, R.id.btn_item_add);
    //        mBrandPopGridAdapter.setSelectPosition(selectPosition);
    new RecyclerUtil(mAdapter).set2View(rv);
    mAdapter.setData(list);
    contentView.findViewById(R.id.btn_clear).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mClickListener.onItemClick(ACTION_CLEAR, null, null);
        popWindow.dissmiss();
      }
    });
    contentView.findViewById(R.id.btn_commit).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mClickListener.onItemClick(ACTION_COMMINT, null, null);
        popWindow.dissmiss();
      }
    });
    contentView.findViewById(R.id.btn_close).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        popWindow.dissmiss();
      }
    });
    //创建并显示popWindow
    popWindow.show();
    return popWindow;
  }


  public interface OnPhotoFromSelectListener {
    int SELECT_PHOTO = 1;
    int SELECT_TAKE = 2;
    int CANCEL = 0;

    boolean onSelect(int select);
  }

  public static CustomPopWindow getPhotoWindow(Activity activity,
                                               @NonNull final OnPhotoFromSelectListener listener) {
    View contentView = LayoutInflater.from(activity).inflate(R.layout.pop_photo, null);
    final CustomPopWindow popWindow = new CustomPopWindow.PopupWindowBuilder(activity)
        .setView(contentView).sizeAs(null).create();
    contentView.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (listener.onSelect(CANCEL)) {
          popWindow.dissmiss();
        }
      }
    });
    contentView.findViewById(R.id.tv_take).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (listener.onSelect(SELECT_TAKE)) {
          popWindow.dissmiss();
        }
      }
    });
    contentView.findViewById(R.id.tv_photo).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (listener.onSelect(SELECT_PHOTO)) {
          popWindow.dissmiss();
        }
      }
    });
    popWindow.showAtLocation(activity.getWindow()
                                     .getDecorView(), Gravity.BOTTOM, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    return popWindow;
  }


  //    public static void showAreaChoice(final BaseController controller, final OnChoiceListener listener) {
  //        AppUtils.hideInput(controller.getContextActivity());
  //        AddressSelectUtil.loadAddressList(controller.getContextActivity(), new AddressSelectUtil.AddressGetter() {
  //            @Override
  //            public void onFinish(final ArrayList<DBCityBean> options1Items, final ArrayList<ArrayList<DBCityBean>> options2Items, final ArrayList<ArrayList<ArrayList<DBCityBean>>> options3Items) {
  //                PickerOptions pickerOptions = new PickerOptions(TYPE_PICKER_OPTIONS);
  //                pickerOptions.context = controller.getContextActivity();
  //                pickerOptions.textContentConfirm = controller.getString(R.string.btn_sure);
  //                pickerOptions.textContentCancel = controller.getString(R.string.btn_cancel);
  //                pickerOptions.cancelable = true;
  //                pickerOptions.optionsSelectListener = new OnOptionsSelectListener() {
  //                    @Override
  //                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
  //                        //返回的分别是三个级别的选中位置
  //                        String provStr = options1Items.get(options1).getName();
  //                        String cityStr = options2Items.get(options1).get(options2).getName();
  //                        String areaStr = "";
  //                        String area0id = options1Items.get(options1).getId();
  //                        String area1id = options2Items.get(options1).get(options2).getId();
  //                        String area2id = "-1";
  //                        if (options3 >= 0) {
  //                            areaStr = options3Items.get(options1).get(options2).get(options3).getName();
  //                            area2id = options3Items.get(options1).get(options2).get(options3).getId();
  //                        }
  //                        listener.onChoice(provStr, cityStr, areaStr, area0id, area1id, area2id);
  //                    }
  //                };
  //                OptionsPickerView optionView = new OptionsPickerView(pickerOptions);
  //                optionView.setPicker(options1Items, options2Items, options3Items);
  //                optionView.show();
  //            }
  //        });
  //
  //    }
  //
  //    public interface OnChoiceListener {
  //
  //        void onChoice(String provStr, String cityStr, String areaStr, String area0id, String area1id, String area2id);
  //    }
  public interface OnChoiceListener<T> {
    void onChoice(View viewClick, T select, int position);
  }


  public static <T extends IPickerViewData> void showPopChoice(final Activity controller,
                                                               final List<T> data,
                                                               final View clickFrom,
                                                               final OnChoiceListener listener) {
    if(data==null){
      ToastUtil.show("数据异常，请重试");
      return;
    }
    PickerOptions pickerOptions = new PickerOptions(TYPE_PICKER_OPTIONS);
    pickerOptions.context = controller;
    pickerOptions.textContentConfirm = "确认";
    pickerOptions.textContentCancel = "取消";
    pickerOptions.cancelable = true;
    pickerOptions.optionsSelectListener = new OnOptionsSelectListener() {
      @Override
      public void onOptionsSelect(int options1, int options2, int options3, View v) {
        if (listener != null) listener.onChoice(clickFrom, data.get(options1), options1);
      }
    };
    OptionsPickerView optionView = new OptionsPickerView(pickerOptions);
    optionView.setPicker(data);
    optionView.show();

  }

  public interface OnPopClickListener<T> {
    void onPopClick(int position, T data, View clickFrom);
  }

  private static long lastDisTime = 0;//防止点击穿透重复唤醒

  public static void showCaoErRoom(Activity context, final View view,
                                   final RoomBean.FixingViewValueBean bean,
                                   final OnPopClickListener listener) {
    if (System.currentTimeMillis() - lastDisTime < 300) return;
    View contentView = LayoutInflater.from(context).inflate(R.layout.pop_cai, null);

    //    final CustomPopWindow popWindow = new CustomPopWindow.PopupWindowBuilder(context)
    //        .setView(R.layout.pop_tui).sizeAs(null).create();

    final PopupWindow mPopupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    mPopupWindow.setOutsideTouchable(true);
    //    );
    //    popWindow.setParent(click);
    //    popWindow.show();
    final Window mWindow = context.getWindow();
    WindowManager.LayoutParams params = mWindow.getAttributes();
    params.alpha = .5f;
    mWindow.addFlags(2);
    mWindow.setAttributes(params);
    TextView tv = contentView.findViewById(R.id.tv_name);
    if (tv != null) {
      tv.setText("当前操作：" + bean.getDescription());
      View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          if (v.getId() == R.id.btn_0) {
            mPopupWindow.dismiss();
            listener.onPopClick(0, bean, view);
          } else if (v.getId() == R.id.btn_1) {
            mPopupWindow.dismiss();
            listener.onPopClick(1, bean, view);
          }
        }
      };
      contentView.findViewById(R.id.btn_0).setOnClickListener(click);
      contentView.findViewById(R.id.btn_1).setOnClickListener(click);
    }

    mPopupWindow.showAtLocation(context.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
      @Override
      public void onDismiss() {
        lastDisTime = System.currentTimeMillis();
        WindowManager.LayoutParams params = mWindow.getAttributes();
        params.alpha = 1;
        mWindow.addFlags(2);
        mWindow.setAttributes(params);
      }
    });
  }


  public static void showTui(Activity context, final View view, final RelaxListBean.ValueBean bean,
                             final OnPopClickListener listener) {
    if (System.currentTimeMillis() - lastDisTime < 300) return;
    View contentView = LayoutInflater.from(context).inflate(R.layout.pop_tui, null);

    //    final CustomPopWindow popWindow = new CustomPopWindow.PopupWindowBuilder(context)
    //        .setView(R.layout.pop_tui).sizeAs(null).create();

    final PopupWindow mPopupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    mPopupWindow.setOutsideTouchable(true);
    //    );
    //    popWindow.setParent(click);
    //    popWindow.show();
    final Window mWindow = context.getWindow();
    WindowManager.LayoutParams params = mWindow.getAttributes();
    params.alpha = .5f;
    mWindow.addFlags(2);
    mWindow.setAttributes(params);
    TextView tv = contentView.findViewById(R.id.tv_name);
    if (tv != null) {
      tv.setText("当前操作：" + bean.getFacilityno());
      View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          if (v.getId() == R.id.btn_0) {
            mPopupWindow.dismiss();
            listener.onPopClick(0, bean, view);
          } else if (v.getId() == R.id.btn_1) {
            mPopupWindow.dismiss();
            listener.onPopClick(1, bean, view);
          }
        }
      };
      contentView.findViewById(R.id.btn_0).setOnClickListener(click);
      contentView.findViewById(R.id.btn_1).setOnClickListener(click);
    }

    mPopupWindow.showAtLocation(context.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
      @Override
      public void onDismiss() {
        lastDisTime = System.currentTimeMillis();
        WindowManager.LayoutParams params = mWindow.getAttributes();
        params.alpha = 1;
        mWindow.addFlags(2);
        mWindow.setAttributes(params);
      }
    });
  }

  public static void showTuiProject(Activity context, final View view,
                                    final OrderListBean.ValueBean bean,
                                    final OnPopClickListener listener, boolean isTech) {
    if (System.currentTimeMillis() - lastDisTime < 300) return;
    View contentView = LayoutInflater.from(context).inflate(R.layout.pop_tui_project, null);

    //    final CustomPopWindow popWindow = new CustomPopWindow.PopupWindowBuilder(context)
    //        .setView(R.layout.pop_tui).sizeAs(null).create();

    final PopupWindow mPopupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    mPopupWindow.setOutsideTouchable(true);
    if (!isTech) {
      ((TextView) contentView.findViewById(R.id.btn_0)).setText("退商品");
    }
    //    );
    //    popWindow.setParent(click);
    //    popWindow.show();
    final Window mWindow = context.getWindow();
    WindowManager.LayoutParams params = mWindow.getAttributes();
    params.alpha = .5f;
    mWindow.addFlags(2);
    mWindow.setAttributes(params);
    TextView tv = contentView.findViewById(R.id.tv_name);
    if (tv != null) {
      tv.setText(bean.getItemName());
      View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          if (v.getId() == R.id.btn_0) {
            mPopupWindow.dismiss();
            listener.onPopClick(0, bean, view);
          } else if (v.getId() == R.id.btn_1) {
            mPopupWindow.dismiss();
            listener.onPopClick(1, bean, view);
          } else if (v.getId() == R.id.btn_2) {
            mPopupWindow.dismiss();
            listener.onPopClick(2, bean, view);
          }
        }
      };
      contentView.findViewById(R.id.btn_0).setOnClickListener(click);
      contentView.findViewById(R.id.btn_1).setOnClickListener(click);
      contentView.findViewById(R.id.btn_2).setOnClickListener(click);
    }

    mPopupWindow.showAtLocation(context.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
      @Override
      public void onDismiss() {
        lastDisTime = System.currentTimeMillis();
        WindowManager.LayoutParams params = mWindow.getAttributes();
        params.alpha = 1;
        mWindow.addFlags(2);
        mWindow.setAttributes(params);
      }
    });
  }
}
