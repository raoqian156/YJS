package com.yskrq.yjs.ui;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import com.rq.rvlibrary.BaseViewHolder;
import com.yskrq.yjs.R;
import com.yskrq.yjs.bean.RelaxListBean;

public class HomeZuItemViewHolder extends BaseViewHolder {

  public HomeZuItemViewHolder(View itemView) {
    super(itemView);
  }

  String color1, color2;

  @Override
  public int inflateLayoutId() {
    return R.layout.item_main_new_task;
  }

  @Override
  public void fillData(int position, Object data) {
    super.fillData(position, data);
    RelaxListBean.ValueBean bean = (RelaxListBean.ValueBean) data;

    try {
      if (TextUtils.isEmpty(color1)) {
        color1 = (String) getPassNullable(0);
        color2 = (String) getPassNullable(1);
      }
      GradientDrawable drawable = new GradientDrawable();
      drawable.setShape(GradientDrawable.OVAL);
      drawable.setStroke(1, Color.BLACK);
      if ("0".equals(bean.getRelaxclocktype())) {
        drawable.setColor(Color.parseColor(color1));
      } else {
        drawable.setColor(Color.parseColor(color2));
      }
      getItemView(R.id.tv_type).setBackground(drawable);

    } catch (Exception e) {

    }


    setItemText(R.id.tv_type, bean.getRelaxclockname());
    String content = "包厢:" + bean.getFacilityno() + "\t项目:" + bean.getItemname();
    String status = "";
    String color = "#000000";
    //        9000 未安排（#8799a3） 9001 已打卡（#84c225） 9002 待打卡（#b0ccf0）9003 下钟  (#FB0002)
    StringBuffer bottom = new StringBuffer("下单:" + bean.getOccurtime());
    int tag = bean.getShowStatus();

    if (tag == 0) {
      status = "等待中";
      color = "#8799a3";
    } else if (tag == 1) {
      status = "待打卡";
      color = "#b0ccf0";
      bottom.append("\t等待:" + bean.getExpendtime() + "分钟");
    } else if (tag == 2) {
      status = "已打卡";
      color = "#84c225";
    } else if (tag == 3) {
      status = "下钟";
      color = "#FB0002";
    }
    content = content + "\t" + status;
    SpannableString spannableString = new SpannableString(content);
    spannableString
        .setSpan(new ForegroundColorSpan(Color.parseColor(color)), content.indexOf(status), content
            .indexOf(status) + status.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
    setItemText(R.id.tv_top, spannableString);
    setItemText(R.id.tv_bottom, bottom.toString());
  }
}
