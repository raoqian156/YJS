package com.rq.rvlibrary.llm;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 自适应最小高度LLM
 */
public class AutoHeightLinearLayoutManager extends LinearLayoutManager {
  public AutoHeightLinearLayoutManager(Context context) {
    super(context);
  }

  @Override
  public void onMeasure(@NonNull RecyclerView.Recycler recycler, @NonNull RecyclerView.State state,
                        int widthSpec, int heightSpec) {
    super.onMeasure(recycler, state, widthSpec, heightSpec);
    try {

      View view = recycler.getViewForPosition(0);
      if (view != null) {
        measureChild(view, widthSpec, heightSpec);
        int measuredWidth = View.MeasureSpec.getSize(widthSpec);
        int measuredHeight = view.getMeasuredHeight();
        setMeasuredDimension(measuredWidth, measuredHeight);
      }
    } catch (Exception e) {

    }
  }

}
