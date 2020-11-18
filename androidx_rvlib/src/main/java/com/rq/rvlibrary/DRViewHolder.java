package com.rq.rvlibrary;

import android.view.View;

import com.rq.rvlibrary.llm.AutoHeightLinearLayoutManager;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public abstract class DRViewHolder<Par extends DRViewHolder.DRParent, Chi> extends
                                                                           BaseViewHolder<Par> {

  public interface DRParent {
    boolean isOpen();
  }

  BaseAdapter innerAdapter;

  public DRViewHolder(View itemView) {
    super(itemView);
    innerAdapter = new BaseAdapter<Chi, EasyViewHolder>(itemView
        .getContext(), getChildLayoutId(), EasyViewHolder.class, this) {
      @Override
      public void onBindEasyHolder(EasyViewHolder holder, int position, Chi bean) {
        super.onBindEasyHolder(holder, position, bean);
        fillChildData(holder, position, bean);
      }
    };
    new RecyclerUtil(innerAdapter).setLayoutManager(getLayoutManager())
                                  .set2View((RecyclerView) itemView
                                      .findViewById(getChildRecyclerId()));
  }


  protected RecyclerView.LayoutManager getLayoutManager() {
    return new AutoHeightLinearLayoutManager(getContext());
  }

  @Override
  public final void fillData(int position, Par parentBean) {
    super.fillData(position, parentBean);
    List item = getChildData(parentBean, position);
    boolean showChild=item != null && item.size() > 0 && parentBean.isOpen();
    fillParentData(this, position, parentBean,showChild);
    if (!showChild) {
      LOG.e("DRViewHolder", "fillData.46:");
      getItemView(getChildRecyclerId()).setVisibility(View.GONE);
      return;
    }
    LOG.e("DRViewHolder", "fillData.49:" + getChildData(parentBean, position).size());
    getItemView(getChildRecyclerId()).setVisibility(View.VISIBLE);
    innerAdapter.setData(BaseAdapter.deepCopy(getChildData(parentBean, position)));
  }

  protected abstract void fillChildData(EasyViewHolder holder, int position, Chi childBean);

  protected abstract void fillParentData(BaseViewHolder holder, int position, Par parentBean,
                                         boolean showChild);

  protected abstract List getChildData(Par parentBean, int position);

  protected abstract int getChildLayoutId();

  protected abstract int getChildRecyclerId();

}
