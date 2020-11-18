package com.rq.rvlibrary;

import android.content.Context;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static com.rq.rvlibrary.BaseViewHolder.TAG_POSITION;

/**
 * Created by raoqian on 2018/9/21
 */

public class BaseAdapter<DATA, VH extends BaseViewHolder> extends
                                                          RecyclerView.Adapter<VH> implements
                                                                                   AdapterHolderControllerHelper {
  protected SparseArray<Class<? extends BaseViewHolder>> multipleHolder;
  Context mContext;
  Class<VH> mHolder;
  private int itemId;
  private Object mObject;
  private List<DATA> showData = new ArrayList<>();
  private SparseArray<Class<? extends BaseViewHolder>> headType = new SparseArray<>();//顶部视图处理器类型
  private SparseArray<Object> headViewData = new SparseArray<>();//顶部视图数据
  private SparseIntArray headViewResId = new SparseIntArray();
  private SparseArray<Class<? extends BaseViewHolder>> footType = new SparseArray<>();//底部视图处理器类型
  private SparseArray<Object> footViewData = new SparseArray<>();//底部视图数据
  private SparseIntArray footViewResId = new SparseIntArray();
  private ActionPasser mActionPasser;
  private HashMap<Class, OnClickMaker> clicks = new HashMap<>();
  private OnAttachedToBottomListener mOnAttachedToBottomListener;
  private Map<String, Object> contentCash = new HashMap<>();

  private void setContext(Context context) {
    this.mContext = context;
  }

  public BaseAdapter(Context context, @LayoutRes int itemLayoutId, Class<VH> baseViewHolderClass) {
    this(context, itemLayoutId, baseViewHolderClass, null);
  }

  /**
   * 单布局类型  只要一种子View setData 数据条数大于 0  才会显示
   *
   * @param itemLayoutId 对 BrandHolder 的描述，必须与 BaseViewHolder 使用保持一致
   */
  public BaseAdapter(Context context, @LayoutRes int itemLayoutId, Class<VH> baseViewHolderClass,
                     Object obj) {
    if (context == null || baseViewHolderClass == null || itemLayoutId == 0) {
      throw new AdapterUseException("BaseAdapter.使用三参数构造函数 值不能为空");
    }
    this.mContext = context;
    this.mHolder = baseViewHolderClass;
    this.itemId = itemLayoutId;
    this.mObject = obj;
  }


  /**
   * 不建议使用，每创建见一个ViewHolder会动用两次反射{@link BaseAdapter#getViewHolderByClass(Class, int, ViewGroup, int)}，
   * 布局ID通过重写{@link BaseViewHolder#inflateLayoutId()}指定
   */
  @Deprecated
  public BaseAdapter(Context context, Class<VH> baseViewHolderClass, Object obj) {
    if (context == null || baseViewHolderClass == null) {
      throw new AdapterUseException("BaseAdapter.使用三参数构造函数 值不能为空");
    }
    setContext(context);
    this.mHolder = baseViewHolderClass;
    this.mObject = obj;
  }

  /**
   * 无序布局   子视图有多种类型
   * 不固定位置
   *
   * @param maps key  对 BrandHolder 的描述，value key布局对应的 BaseViewHolder.class，必须与 BaseViewHolder 使用保持一致
   */
  public BaseAdapter(Context context, @NonNull SparseArray<Class<? extends BaseViewHolder>> maps,
                     Object... innerClassContext) {
    this.mContext = context;
    this.multipleHolder = maps;
    if (innerClassContext != null && innerClassContext.length > 0) {
      this.mObject = innerClassContext[0];
    }
    getMultipleHolderType(null, 0);//进行检测
  }

  /**
   * 追加型布局
   */
  public BaseAdapter(Context context) {
    this.mContext = context;
  }

  public void changeItemView(int viewLayout, boolean refuse) {
    this.itemId = viewLayout;
    if (refuse) {
      notifyDataSetChanged();
    }
  }

  boolean showEmpty = false;//展示空视图
  private final static int TYPE_EMPTY = 100865;
  Object[] passData;


  public void setPassData(Object... passData) {
    if (this.passData != null && passData != null && passData.length <= this.passData.length) {
      for (int i = 0; i < passData.length; i++) {
        this.passData[i] = passData[i];
      }
      return;
    }
    this.passData = passData;
  }

  public void setPassData2Index(int position, Object data) {
    if (passData != null && position >= passData.length) {
      Object[] newPass = new Object[position + 1];
      for (int i = 0; i < passData.length; i++) {
        newPass[i] = passData[i];
      }
      newPass[position] = data;
      this.passData = newPass;
      return;
    }
    if (passData == null) {
      passData = new Object[position + 1];
      this.passData[position] = data;
      return;
    }
    this.passData[position] = data;
  }

  public Object getNullablePass(int position) {
    if (passData != null && passData.length > position) {
      return passData[position];
    }
    return null;
  }

  /**
   * 直接刷新视图，数据为空将置空列表
   *
   * @param dataList 填充数据
   */
  public void setData(List dataList) {
    if (this.showRule != null) {
      LOG.e("BaseAdapter", "setData.167:" + showRule + "<");
      display(this.showRule, dataList);
      return;
    }
    this.showEmpty = emptyLayout() != 0 && (dataList == null || dataList.size() == 0);
    this.showData.clear();
    if (dataList != null) {
      this.showData = dataList;
    }
    if (dataList != null) {
      LOG.e("BaseAdapter", "setData.171:" + dataList.size());
    } else {
      LOG.e("BaseAdapter", "setData.171:null");
    }
    LOG.e("BaseAdapter", "setData.172:" + this.showData.size());
    this.notifyDataSetChanged();
  }

  private boolean removeRule() {
    if (originData.size() > showData.size()) {
      LOG.e("BaseAdapter", "removeRule.originData:" + originData.size());
      LOG.e("BaseAdapter", "removeRule.showData:" + showData.size());
      List dataList = new ArrayList();
      dataList.addAll(deepCopy(originData));
      this.showEmpty = emptyLayout() != 0 && (dataList == null || dataList.size() == 0);
      this.showData.clear();
      this.showData = dataList;
      LOG.e("BaseAdapter", "removeRule.171:" + dataList.size());
      LOG.e("BaseAdapter", "removeRule.172:" + this.showData.size());
      this.notifyDataSetChanged();
      originData.clear();
      return true;
    }
    return false;
  }

  public void bindData(List dataList) {
    this.showData = dataList;
    this.notifyDataSetChanged();
  }

  public List getData() {
    return showData;
  }

  @Override
  public void notifyDataChange() {
    notifyDataSetChanged();
  }

  protected int emptyLayout() {
    return R.layout.item_empty;
  }

  public void addData(List dataList) {
    if (dataList != null) {
      this.showData.addAll(dataList);
      this.showEmpty = this.showData == null || this.showData.size() == 0;
      this.notifyDataSetChanged();
    }
  }

  public void addData(int position, List dataList) {
    if (dataList != null && position <= this.showData.size()) {
      this.showData.addAll(position, dataList);
      this.showEmpty = this.showData == null || this.showData.size() == 0;
      this.notifyDataSetChanged();
    }
  }

  public void addData(DATA dataList) {
    if (dataList != null) {
      this.showData.add(dataList);
      this.showEmpty = this.showData == null || this.showData.size() == 0;
      this.notifyDataSetChanged();
    }
  }

  public void addData(int position, DATA dataList) {
    if (dataList != null && position <= this.showData.size()) {
      this.showData.add(position, dataList);
      this.showEmpty = this.showData == null || this.showData.size() == 0;
      this.notifyDataSetChanged();
    }
  }

  public DATA getDataItem(int position) {
    if (position >= 0 && position < showData.size()) {
      return showData.get(position);
    }
    return null;
  }

  @Override
  public int getItemViewType(int position) {
    int headSize = headViewData.size();
    int footSize = footViewData.size();
    int dataSize = showData.size();

    if (multipleHolder != null) {
      return getMultipleHolderType(getDataItem(position), position);
    }
    if (isShowEmpty() && showData.size() == 0) return TYPE_EMPTY;
    boolean isHead = position < headViewData.size();
    if (isHead) {//头部数据
      return -headSize + position;
    }
    boolean isFoot = (position >= headViewData.size() + showData.size());
    if (isFoot) { //尾部数据
      int footPosition = position - headSize - dataSize;
      return Integer.MIN_VALUE + footPosition;
    }
    return position;
  }

  /**
   * @param dataItem 数据内容
   * @param position 数据位置
   *
   * @return 返回布局Id
   */
  protected int getMultipleHolderType(DATA dataItem, int position) {
    throw new AdapterUseException(" 多类型布局使用错误，必须复写 getMultipleHolderType() 方法,并且不调用父类方法  ");
  }

  @Override
  public VH onCreateViewHolder(ViewGroup parent, int viewType) {
    if (viewType == TYPE_EMPTY) {
      LOG.e("BaseAdapter", "onCreateViewHolder.271:");
      View v = LayoutInflater.from(mContext).inflate(emptyLayout(), parent, false);
      return (VH) new EmptyViewHolder(v);
    }
    VH viewHolder;
    if (multipleHolder != null) {
      Class clazz = multipleHolder.get(viewType);
      if (clazz == null) {
        throw new AdapterUseException(" 多类型布局使用错误，multipleHolder key 为 R.layout.id   value 为 Holder.class  ");
      }
      LOG.e("BaseAdapter", "onCreateViewHolder.280:");
      viewHolder = getViewHolderByClass(clazz, viewType, parent, viewType);
    } else if (viewType < 0) {
      if (viewType >= -headViewData.size()) {
        //       viewType= - headViewData.size() + position
        int realPosition = headViewData.size() + viewType;
        final Class<?> holderRoot = headType.get(realPosition);
        LOG.e("BaseAdapter", "onCreateViewHolder.viewType: " + viewType + "  " + holderRoot);
        viewHolder = getViewHolderByClass(holderRoot, headViewResId
            .get(realPosition), parent, realPosition);
      } else {
        //        viewType=Integer.MIN_VALUE + position;
        int footPosition = viewType - Integer.MIN_VALUE;
        LOG.e("BaseAdapter", "onCreateViewHolder:footPosition = " + footPosition);
        if (footViewResId.size() > footPosition) {
          viewHolder = getViewHolderByClass(footType.get(footPosition), footViewResId
              .get(footPosition), parent, footPosition);
        } else {
          viewHolder = getViewHolderByClass(EasyViewHolder.class, itemId, parent, viewType);
        }
      }

    } else {
      LOG.e("BaseAdapter", "onCreateViewHolder.300:");
      viewHolder = getViewHolderByClass(mHolder, this.itemId, parent, viewType);
    }
    if (viewHolder != null) {
      if (passData != null) {
        viewHolder.setPassData(passData);
      }
      OnClickMaker itemOnClickMaker = clicks.get(viewHolder.getClass());
      if (itemOnClickMaker != null) {
        viewHolder.setClickInfo(itemOnClickMaker);
      }
      viewHolder.setContext(mContext);
      if (viewHolder.itemView.getLayoutParams() != null) {
        viewHolder.itemView
            .setLayoutParams(viewHolder.getLMLayoutParams(viewHolder.itemView.getLayoutParams()));
      }
    }
    return viewHolder;
  }

  private Object getMore() {
    return mObject;
  }

  public static void setCheckLayout(boolean check) {
    checkLayout = check;
  }

  private static boolean checkLayout = true;

  /**
   * 通过反射获取Holder实例
   */
  protected VH getViewHolderByClass(@NonNull Class<?> holderRoot, @LayoutRes int resId,
                                    ViewGroup parent, int viewType) {
    String error = "";
    String holdName = "";
    if (holderRoot == null) {
      View itemView = LayoutInflater.from(mContext).inflate(resId, parent, false);
      return (VH) new EmptyViewHolder(itemView);
    }
    holdName = holderRoot.getSimpleName();
    if (EmptyViewHolder.class.getSimpleName().equals(holdName)) {
      View itemView = LayoutInflater.from(mContext).inflate(resId, parent, false);
      return (VH) new EmptyViewHolder(itemView);
    }

    try {
      Constructor<?>[] ctors = holderRoot.getDeclaredConstructors();
      if (ctors != null && ctors.length > 0) {
        View itemView = LayoutInflater.from(mContext).inflate(resId, parent, false);
        VH holder;
        try {
          holder = (VH) ctors[0].newInstance(itemView);
        } catch (IllegalArgumentException e) {
          if (getMore() != null) {
            holder = (VH) ctors[0].newInstance(getMore(), itemView);
          } else {
            throw e;
          }
        }
        if (holder == null) {
          throw new AdapterUseException(holderRoot
              .getSimpleName() + " 获取到了一个空  Holder -_-||  --> " + viewType);
        }
        if (checkLayout && holder
            .inflateLayoutId() != resId && !(holder instanceof EasyViewHolder)) {
          throw new AdapterUseException(holderRoot
              .getSimpleName() + " 布局使用错误,请重写viewHolder.inflateLayoutId   --> " + viewType + "\n-------------该方法为了维护而写 可使用 BaseAdapter.setCheckLayout() 关闭该检查");
        }
        if (mActionPasser != null) {
          holder.setPasser(mActionPasser);
        }
        if (holder instanceof ViewDataGetter) {
          mViewDataGetters.add((ViewDataGetter) holder);
        }
        if (holder instanceof AdapterHolderController) {
          ((AdapterHolderController) holder).setDataPasser(this);
        }
        return holder;
      }
    } catch (InstantiationException e) {
      error = "InstantiationException:" + e.getMessage();
    } catch (IllegalAccessException e) {
      error = "IllegalAccessException:" + e.getMessage();
    } catch (Exception e) {
      error = "Exception:" + e.getMessage();
    }
    if (error != null && error.contains("Wrong number of arguments")) {
      error = error + "\n【【【" + holderRoot
          .getSimpleName() + ".调用类内部类ViewHolder 调用四参数构造方法 或者 重写 getMore() 内容 】】】";
    }
    String errName = holderRoot == null ? "ERROR" : holderRoot.getName();
    throw new AdapterUseException(errName + " 初始化异常:" + error);
  }

  List<ViewDataGetter> mViewDataGetters = new ArrayList<>();

  public List<Object> getItemInput() {
    List<Object> res = new ArrayList<>();
    if (mViewDataGetters.size() != 0) {
      for (ViewDataGetter item : mViewDataGetters) {
        res.add(item.getViewData());
      }
    }
    return res;

  }


  @Override
  public void onBindViewHolder(VH holder, int position) {
    if (passData != null) {
      holder.setPassData(passData);
    }
    holder.itemView.setTag(TAG_POSITION, position - headViewData.size());
    if (holder instanceof OnContentKeeper) {
      LOG.e("onBindViewHolder", "addView");
      LOG.e("BaseAdapter", "onBindViewHolder: " + holder.getPosition());
      useCash(holder);
      return;
    }
    if (holder instanceof EasyViewHolder) {
      onBindEasyHolder(holder, position - headViewData.size(), getDataItem(position - headViewData
          .size()));
    }
    if (multipleHolder != null) {
      holder.fillObject(getDataItem(position));
    } else if (position < headViewData.size()) {
      holder.setObject(headViewData.get(position));
      holder.fillObject(headViewData.get(position));
    } else if (position >= headViewData.size() + showData.size() && headViewData.size() + showData
        .size() > 0) {
      holder.setObject(footViewData.get(position - headViewData.size() - showData.size()));
      holder.fillObject(footViewData.get(position - headViewData.size() - showData.size()));
    } else {
      holder.setData(getDataItem(position - headViewData.size()));
      holder.fillData(position - headViewData.size(), getDataItem(position - headViewData.size()));
    }
  }

  public void onBindEasyHolder(VH holder, int position, DATA data) {
  }

  public static boolean NEED_EMPTY_SHOW = false;

  public boolean isShowEmpty() {
    return showEmpty && NEED_EMPTY_SHOW;
  }

  /**
   * 实际数据数量 不包含 HeadView 和 FootView 数量
   */
  public int getDataSize() {
    return showData.size();
  }

  /**
   * @see #getDataSize()
   */
  @Override
  @Deprecated
  public int getItemCount() {
    if (isShowEmpty()) return 1;
    if (showData == null || headViewData == null || footViewData == null) return 0;
    return showData.size() + headViewData.size() + footViewData.size();
  }

  /**
   * 由于 SparseArray 的特性，add***Holder只能从前面往后面追加，要增加指定顺序需更换为HasMap
   * 需要进行替换，使用以下方法
   *
   * @see #setFootHolder(int, Object, Class, int, Object)
   * @see #setHeadHolder(int, Object, Class, int, Object)
   */
  public void addFootHolder(Object object, Class<? extends BaseViewHolder> footHolder,
                            @LayoutRes int resId, Object more) {
    int oldSize = footType.size();
    footType.put(footType.size(), footHolder);
    footViewData.put(oldSize, object);
    footViewResId.put(oldSize, resId);
    this.mObject = more;
  }

  public void addFootHolder(Class<? extends BaseViewHolder> footHolder, @LayoutRes int resId,
                            Object more) {
    addFootHolder(null, footHolder, resId, more);
  }

  public void addHeadHolder(Class<? extends BaseViewHolder> headHolder, @LayoutRes int resId,
                            Object more) {
    addHeadHolder(null, headHolder, resId, more);
  }

  public void addHeadHolder(Object object, Class<? extends BaseViewHolder> headHolder,
                            @LayoutRes int resId, Object more) {
    int oldSize = headType.size();
    headType.put(headType.size(), headHolder);
    headViewData.put(oldSize, object);
    headViewResId.put(oldSize, resId);
    this.mObject = more;
    this.notifyDataSetChanged();
  }

  /**
   * @see #addFootHolder(Object, Class, int, Object)
   */
  public void setFootHolder(int position, Object object, Class<? extends BaseViewHolder> footHolder,
                            @LayoutRes int resId, Object more) {
    if (footType.size() < position) {
      addHeadHolder(object, footHolder, resId, more);
      return;
    }
    int oldSize = footType.size();
    footType.put(footType.size(), footHolder);
    footViewData.put(oldSize, object);
    footViewResId.put(oldSize, resId);
    this.mObject = more;
  }

  /**
   * @param position   头序号，与数据序号无关
   * @param object     数据
   * @param headHolder 文件
   * @param resId      布局
   */
  public void setHeadHolder(int position, Object object, Class<? extends BaseViewHolder> headHolder,
                            @LayoutRes int resId, Object more) {
    if (headType.size() < position) {
      addHeadHolder(object, headHolder, resId, more);
      return;
    }
    headType.delete(position);
    headType.put(position, headHolder);
    headViewData.delete(position);
    headViewData.put(position, object);
    headViewResId.delete(position);
    headViewResId.put(position, resId);
    this.mObject = more;
    notifyItemChanged(position);
  }

  public void clearHeadView() {
    headType.clear();
    headViewData.clear();
    headViewResId.clear();
    notifyDataSetChanged();
  }

  public void clearFootView() {
    footType.clear();
    footViewData.clear();
    footViewResId.clear();
    notifyDataSetChanged();
  }

  /**
   * 常见交互可通过 addOnItemClickListener 方法设置
   *
   * @see #addOnItemClickListener(OnItemClickListener, int...)
   */
  @Deprecated
  public void setActionPasser(ActionPasser passer) {
    this.mActionPasser = passer;
  }

  @Override
  public void onViewDetachedFromWindow(@NonNull VH holder) {
    if (holder instanceof OnContentKeeper) {
      setCash(holder);
    }
  }

  @Override
  public void onViewAttachedToWindow(@NonNull VH holder) {
    if (holder instanceof OnContentKeeper) {
      useCash(holder);
    }
    if (holder.getAdapterPosition() == getItemCount() - 1) {
      onAttachedToBottom(holder.getAdapterPosition());
    } else if (holder.getAdapterPosition() == 0) {
      if (mOnAttachedToBottomListener != null) {
        mOnAttachedToBottomListener.onAttachedToTop();
      }
    }
  }

  public DATA removeItem(int deletePosition) {
    if (deletePosition >= 0 && showData.size() > 0 && showData.size() > deletePosition) {
      DATA res = showData.remove(deletePosition);
      notifyDataSetChanged();
      return res;
    }
    return null;
  }

  public void removeItem(Object item) {
    if (item != null && showData.contains(item)) {
      showData.remove(item);
      notifyDataSetChanged();
    }
  }

  public void addOnItemClickListener(OnItemClickListener clickListener, int... ids) {
    this.addOnItemClickListener(mHolder, clickListener, ids);
  }

  // id = 0   或者不设置 id 则将点击事件绑定到最外层 ItemView
  public <T extends BaseViewHolder> void addOnItemClickListener(Class<T> clazz,
                                                                OnItemClickListener clickListener,
                                                                int... ids) {
    if (clazz != null && clickListener != null) {
      clicks.put(clazz, new OnClickMaker(clickListener, ids));
    }
  }

  public void setOnAttachedToBottomListener(OnAttachedToBottomListener l) {
    this.mOnAttachedToBottomListener = l;
  }

  protected void onAttachedToBottom(int position) {
    if (mOnAttachedToBottomListener != null) {
      mOnAttachedToBottomListener.onAttachedToBottom(position);
    }
  }

  public interface DisplayOption<DATA> {
    /**
     * @param bean     需要判断的数据
     * @param rule     提供的判断条件
     * @param position
     *
     * @return 是否显示该数据
     */
    boolean show(DATA bean, Object rule, int position);
  }

  private DisplayOption mDisplayOption;
  private Object showRule;

  public void setDisplay(DisplayOption mDisplayOption) {
    this.mDisplayOption = mDisplayOption;
  }

  List<DATA> originData = new ArrayList<>();

  boolean display(Object rule, List data) {
    this.originData = data;
    return display(rule);
  }

  /**
   * you must used {@link #setDisplay(DisplayOption)} before display
   *
   * @return 是否刷新视图
   */
  public boolean display(Object rule) {
    final int showSize = showData.size();
    final int originSize = originData.size();
    if (mDisplayOption == null) {
      LOG.e("BaseAdapter", "display error:you must used setDisplay before display");
      return false;
    }
    showRule = rule;
    if (rule == null) {
      return removeRule();
    }
    if (showSize > originSize) {
      originData.clear();
      originData.addAll(showData);
    }
    boolean hasChange = false;
    List<DATA> newShow = new ArrayList<>();
    for (int position = 0; position < originData.size(); position++) {
      if (mDisplayOption.show(originData.get(position), rule, position)) {
        newShow.add(originData.get(position));
        hasChange = true;
      }
    }
    this.showEmpty = emptyLayout() != 0 && (newShow.size() == 0);
    this.showData.clear();
    this.showData = newShow;
    LOG.e("BaseAdapter", "setData.669:" + newShow.size());
    LOG.e("BaseAdapter", "setData.670:" + this.showData.size());
    this.notifyDataSetChanged();
    return hasChange;
  }

  public static <E> List<E> deepCopy(List<E> src) {
    try {
      ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
      ObjectOutputStream out = new ObjectOutputStream(byteOut);
      out.writeObject(src);

      ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
      ObjectInputStream in = new ObjectInputStream(byteIn);
      @SuppressWarnings("unchecked") List<E> dest = (List<E>) in.readObject();
      return dest;
    } catch (Exception e) {
      if (e instanceof NotSerializableException) {
        throw new RuntimeException("数据需要序列化>>" + e.getMessage());
      }
      return new ArrayList<>();
    }
  }

  /**
   * ##########################################################################
   * RecyclerView 快速滑动时 EditText 等视图内容会随着视图复用而造成数据多余
   * Holder.class 实现 OnContentKeeper 即可解决此问题
   * ##########################################################################
   */

  public void setCash(VH holder) {//存储数据
    if (((OnContentKeeper) holder).getSaveViewId() == null || ((OnContentKeeper) holder)
        .getSaveViewId().length == 0) {
      return;
    }
    for (int saveContentId : ((OnContentKeeper) holder).getSaveViewId()) {
      Object save = ((OnContentKeeper) holder).getSave(saveContentId);
      if (save != null) {
        contentCash.put(holder.getPosition() + "" + saveContentId, save);
        LOG.e("BaseAdapter", holder.getPosition() + " setCash: " + save);
      }
    }
  }

  public void useCash(VH holder) {//使用存储数据填充视图
    if (((OnContentKeeper) holder).getSaveViewId() == null || ((OnContentKeeper) holder)
        .getSaveViewId().length == 0) {
      return;
    }
    for (int saveContentId : ((OnContentKeeper) holder).getSaveViewId()) {
      Object value = contentCash.get(holder.getPosition() + "" + saveContentId);
      ((OnContentKeeper) holder).onRelease(value, saveContentId);
    }
  }

  public interface OnAttachedToBottomListener {
    void onAttachedToTop();

    void onAttachedToBottom(int position);
  }
}
