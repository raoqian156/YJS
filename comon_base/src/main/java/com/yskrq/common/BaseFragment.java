package com.yskrq.common;

import android.os.Bundle;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.rq.rvlibrary.BaseAdapter;
import com.rq.rvlibrary.RecyclerUtil;
import com.yskrq.common.okhttp.HttpManagerBase;
import com.yskrq.common.ui.LoadingDialog;
import com.yskrq.common.util.LOG;
import com.yskrq.common.util.ToastUtil;
import com.yskrq.net_library.BaseBean;
import com.yskrq.net_library.BaseView;
import com.yskrq.net_library.RequestType;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

//import com.trello.rxlifecycle2.LifecycleProvider;
//import com.trello.rxlifecycle2.LifecycleTransformer;
//import com.trello.rxlifecycle2.RxLifecycle;
//import com.trello.rxlifecycle2.android.FragmentEvent;
//import com.trello.rxlifecycle2.android.RxLifecycleAndroid;
//import io.reactivex.Observable;
//import io.reactivex.subjects.BehaviorSubject;

public abstract class BaseFragment extends Fragment implements //LifecycleProvider<FragmentEvent>,
    BaseView {
  @Override
  public <T extends BaseBean> void onResponseSucceed(@NonNull RequestType type, @NonNull T data) {

  }

  @Override
  public <T extends BaseBean> void onResponseError(@NonNull RequestType type, @NonNull T data) {
    if (data != null) toast(data.getRespMsg());
  }

  @Override
  public void onConnectError(@NonNull RequestType type) {

  }

  View view;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    //    lifecycleSubject.onNext(FragmentEvent.CREATE);
  }

  protected void initTitle(String title) {
    if (findViewFromId(R.id.base_btn_back) != null) {
      findViewFromId(R.id.base_btn_back).setVisibility(View.INVISIBLE);
    }
    if (findViewFromId(R.id.base_title) instanceof TextView) {
      ((TextView) findViewFromId(R.id.base_title)).setText(title);
    }
  }

  public  <T extends View> T findViewById(int vId) {
    try {
      return view.findViewById(vId);
    }catch (Exception e){
      throw new NullPointerException();
    }
  }

  private <T extends View> T findViewFromId(int vId) {
    if (view == null) {
      onViewEmpty();
      return null;
    }
    return view.findViewById(vId);
  }

  protected void addTextListener(int tv_center, TextWatcher watcher) {
    if (findViewFromId(tv_center) instanceof EditText) {
      ((EditText) findViewFromId(tv_center)).addTextChangedListener(watcher);
    }
  }

  @Nullable
  @Override
  public final View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                                 Bundle savedInstanceState) {
    view = inflater.inflate(layoutId(), container, false);
    return view;
  }

  protected void toast(String con) {
    ToastUtil.show(con);
  }

  @Override
  public final void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    //    lifecycleSubject.onNext(FragmentEvent.CREATE_VIEW);
    initView();
    initOnClick();
  }

  private void initOnClick() {
    LOG.e(BaseFragment.this.getClass()
                           .getSimpleName(), "initOnClick.38:" + is(View.OnClickListener.class));
    if (is(View.OnClickListener.class)) {
      Method method = null;
      try {
        method = get(View.OnClickListener.class).getClass()
                                                .getDeclaredMethod("onClick", View.class);
      } catch (NoSuchMethodException e) {
        e.printStackTrace();
      }
      if (method == null) return;
      Annotation[] annotations = method.getAnnotations();
      if (annotations.length > 0) {
        for (Annotation annotation : annotations) {
          if (annotation instanceof OnClick) {
            OnClick inject = (OnClick) annotation;
            int[] value = inject.value();
            if (value.length > 0) {
              for (int id : value) {
                if (findViewFromId(id) != null) {
                  findViewFromId(id).setOnClickListener((View.OnClickListener) this);
                }
              }
            }
          }
        }
      }
    }
  }


  protected abstract int layoutId();

  protected abstract void initView();

  protected void setViewTag(int vId, Object tag) {
    View view = findViewFromId(vId);
    if (view == null) {
      return;
    }
    view.setTag(tag);
  }

  private void onViewEmpty() {
    toast("程序异常，请重新登陆");
    StringBuffer sb = new StringBuffer();
    sb.append("\nwhere:BaseFragment -> onViewEmpty");
    sb.append("\nwhere:getContext()==null -> " + (getContext() == null));
    sb.append("\nAppInfo.getTechNum:" + AppInfo.getTechNum());
    sb.append("\nAppInfo.getWorkDate:" + AppInfo.getWorkDate());
    HttpManagerBase.senError("VIEW_ERROR", sb.toString());
  }

  protected void setViewBackResource(int vId, int res) {
    View view = findViewFromId(vId);
    if (view == null) {
      return;
    }
    view.setBackgroundResource(res);
  }

  protected void setVisibility(int vid, int visible) {
    View view = findViewFromId(vid);
    if (view == null) {
      return;
    }
    view.setVisibility(visible);
  }

  protected void setTextView2View(int vId, String con) {
    if (findViewFromId(vId) instanceof TextView) {
      ((TextView) findViewFromId(vId)).setText(con);
    }
  }

  protected void setRecyclerView(int vId, BaseAdapter adapter) {
    setRecyclerView(vId, new RecyclerUtil(adapter));
  }

  protected void setRecyclerView(int vId, RecyclerUtil util) {
    util.context(getActivity());
    RecyclerView recyclerView = view.findViewById(vId);
    if (recyclerView == null) {
      return;
    }
    if (recyclerView.getLayoutManager() == null && util.context(getActivity()).build() != null) {
      recyclerView.setLayoutManager(util.build());
    }
    if (util.adapter() != null) {
      recyclerView.setAdapter(util.adapter());
    }
    if (util.getData() != null) {
      RecyclerView.Adapter adapter = recyclerView.getAdapter();
      if (adapter instanceof BaseAdapter) {
        if (util.getPage() == 1) {
          ((BaseAdapter) adapter).setData(util.getData());
        } else {
          ((BaseAdapter) adapter).addData(util.getData());
        }
      } else if (adapter == null) {
        adapter = util.adapter();
        recyclerView.setAdapter(adapter);
      }
    }
    if (util.getDecoration() != null) {
      recyclerView.addItemDecoration(util.getDecoration());
    }
  }

  protected final boolean is(Class<?> clazz) {
    boolean res = clazz.isAssignableFrom(this.getClass());
    return res;
  }

  public <T> T get(Class<T> type) {
    return type.cast(this);
  }


  //  private final BehaviorSubject<FragmentEvent> lifecycleSubject = BehaviorSubject.create();
  //
  //  @Override
  //  @NonNull
  //  @CheckResult
  //  public final Observable<FragmentEvent> lifecycle() {
  //    return lifecycleSubject.hide();
  //  }
  //
  //  @Override
  //  @NonNull
  //  @CheckResult
  //  public final <T> LifecycleTransformer<T> bindUntilEvent(@NonNull FragmentEvent event) {
  //    return RxLifecycle.bindUntilEvent(lifecycleSubject, event);
  //  }
  //
  //  @Override
  //  @NonNull
  //  @CheckResult
  //  public final <T> LifecycleTransformer<T> bindToLifecycle() {
  //    return RxLifecycleAndroid.bindFragment(lifecycleSubject);
  //  }
  //
  //  @Override
  //  public void onAttach(android.app.Activity activity) {
  //    super.onAttach(activity);
  //    lifecycleSubject.onNext(FragmentEvent.ATTACH);
  //  }
  //
  //  @Override
  //  public void onStart() {
  //    super.onStart();
  //    lifecycleSubject.onNext(FragmentEvent.START);
  //  }
  //
  //  @Override
  //  public void onResume() {
  //    super.onResume();
  //    lifecycleSubject.onNext(FragmentEvent.RESUME);
  //  }
  //
  //  @Override
  //  public void onPause() {
  //    lifecycleSubject.onNext(FragmentEvent.PAUSE);
  //    super.onPause();
  //  }
  //
  //  @Override
  //  public void onStop() {
  //    lifecycleSubject.onNext(FragmentEvent.STOP);
  //    super.onStop();
  //  }
  //
  //  @Override
  //  public void onDestroyView() {
  //    lifecycleSubject.onNext(FragmentEvent.DESTROY_VIEW);
  //    super.onDestroyView();
  //  }
  //
  //  @Override
  //  public void onDestroy() {
  //    lifecycleSubject.onNext(FragmentEvent.DESTROY);
  //    super.onDestroy();
  //  }
  //
  //  @Override
  //  public void onDetach() {
  //    lifecycleSubject.onNext(FragmentEvent.DETACH);
  //    super.onDetach();
  //  }

  @Override
  public void handleFailResponse(BaseBean baseBean) {

  }

  LoadingDialog progressDialog;

  @Override
  public void showLoading(String... str) {
    if (progressDialog == null) {
      progressDialog = new LoadingDialog(getActivity());
    }
    if (str.length > 0) {
      progressDialog.setTitle(str[0]);
    }
    progressDialog.show();
  }

  @Override
  public void dismissLoading() {
    if (progressDialog != null && progressDialog.isShowing()) progressDialog.dismiss();
  }

}
