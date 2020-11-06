package com.yskrq.common;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.rq.rvlibrary.BaseAdapter;
import com.rq.rvlibrary.RecyclerUtil;
import com.yskrq.common.util.LOG;
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
    toast(data.getRespMsg());
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
    if (findViewById(R.id.base_btn_back) != null) {
      findViewById(R.id.base_btn_back).setVisibility(View.INVISIBLE);
    }
    if (findViewById(R.id.base_title) instanceof TextView) {
      ((TextView) findViewById(R.id.base_title)).setText(title);
    }
  }

  protected <T extends View> T findViewById(int vId) {
    return view.findViewById(vId);
  }

  @Nullable
  @Override
  public final View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                                 Bundle savedInstanceState) {
    view = inflater.inflate(layoutId(), container, false);
    return view;
  }

  protected void toast(String con) {
    Toast.makeText(getActivity(), con, Toast.LENGTH_LONG).show();
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
                if (findViewById(id) != null) {
                  findViewById(id).setOnClickListener((View.OnClickListener) this);
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

  protected void setTextView2View(String con, int vId) {
    if (findViewById(vId) instanceof TextView) {
      ((TextView) findViewById(vId)).setText(con);
    }
  }

  protected void setRecyclerView(int vId, BaseAdapter adapter) {
    setRecyclerView(vId, new RecyclerUtil(adapter));
  }

  protected void setRecyclerView(int vId, RecyclerUtil util) {
    util.context(getActivity());
    RecyclerView recyclerView = view.findViewById(vId);
    if (recyclerView == null) {
      LOG.e(this.getClass().getSimpleName(), "setRecyclerView.error:NULL");
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

  ProgressDialog progressDialog;

  @Override
  public void showLoading(String... str) {
    if (progressDialog == null) {
      progressDialog = new ProgressDialog(getActivity());
    }
    if (str.length > 0) {
      progressDialog.setMessage(str[0]);
    }
    progressDialog.show();
  }

  @Override
  public void dismissLoading() {
    if (progressDialog != null && progressDialog.isShowing()) progressDialog.dismiss();
  }

}
