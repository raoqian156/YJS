package com.yskrq.yjs.ui;

import android.view.View;

import com.rq.rvlibrary.BaseViewHolder;
import com.rq.rvlibrary.DRViewHolder;
import com.rq.rvlibrary.EasyViewHolder;
import com.yskrq.yjs.R;
import com.yskrq.yjs.bean.StaticsListBean;

import java.util.List;

public class DRStatiscHolder extends
                             DRViewHolder<StaticsListBean.ValueBean, StaticsListBean.ValueBean.ChildrenBean> {
  public DRStatiscHolder(View itemView) {
    super(itemView);
  }


  @Override
  protected int getChildLayoutId() {
    return R.layout.item_statis_inner;
  }

  @Override
  protected void fillChildData(EasyViewHolder holder, int position,
                               StaticsListBean.ValueBean.ChildrenBean childBean) {
    holder.setItemText(R.id.tv_name, childBean.getItem_name());
    holder.setItemText(R.id.tv_room_num, childBean.getFacilityNo());
    holder.setItemText(R.id.tv_num, childBean.getItem_count_qty() + "");
    holder.setItemText(R.id.tv_all, childBean.getItem_price_sum() +"");
    holder.setItemText(R.id.tv_time, childBean.getWaitBeginTime());
  }

  @Override
  public int inflateLayoutId() {
    return R.layout.item_statis_out;
  }

  @Override
  protected void fillParentData(BaseViewHolder holder, int position,
                                StaticsListBean.ValueBean parentBean, boolean showChild) {
    holder.setItemText(R.id.tv_name, parentBean.getItem_name());
    holder.getItemView(R.id.btn_expand).setSelected(parentBean.isOpen());
    holder.setItemText(R.id.tv_num, parentBean.getNum());
    holder.setItemText(R.id.tv_all, parentBean.getAll());
    holder.getItemView(R.id.ll_head).setVisibility(showChild ? View.VISIBLE : View.GONE);
  }

  @Override
  protected List getChildData(StaticsListBean.ValueBean parentBean, int position) {
    return parentBean.getChildren();
  }

  @Override
  protected int getChildRecyclerId() {
    return R.id.recycler;
  }
}
