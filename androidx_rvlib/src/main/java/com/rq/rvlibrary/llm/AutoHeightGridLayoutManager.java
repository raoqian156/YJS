package com.rq.rvlibrary.llm;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 自适应最小高度LLM
 */
public class AutoHeightGridLayoutManager extends GridLayoutManager {
  int row = 1;

  public AutoHeightGridLayoutManager(Context context, int row) {
    super(context, row);
    this.row = row;
  }

  //  @Override
  //  public void onMeasure(@NonNull RecyclerView.Recycler recycler, @NonNull RecyclerView.State state,
  //                        int widthSpec, int heightSpec) {
  //    super.onMeasure(recycler, state, widthSpec, heightSpec);
  //    try {
  //
  //      View view = recycler.getViewForPosition(0);
  //      if (view != null) {
  //        measureChild(view, widthSpec, heightSpec);
  //        int measuredWidth = View.MeasureSpec.getSize(widthSpec);
  //        int child = getChildCount();
  //        int hang = child / row + (child % row == 0 ? 0 : 1);
  //        int measuredHeight = 2 * View.MeasureSpec.getSize(heightSpec);
  //        Log.e("AutoHeightGrid", hang+".onMeasure.measuredHeight:" + measuredHeight);
  //        setMeasuredDimension(measuredWidth, measuredHeight);
  //      }
  //    } catch (Exception e) {
  //
  //    }
  //  }

//  @Override
//  public boolean isAutoMeasureEnabled() {
//    return true;
//  }
//
//
//  private int[] mMeasuredDimension = new int[2];
//
//  @Override
//  public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec,
//                        int heightSpec) {
//    final int widthMode = View.MeasureSpec.getMode(widthSpec);
//    final int heightMode = View.MeasureSpec.getMode(heightSpec);
//    final int widthSize = View.MeasureSpec.getSize(widthSpec);
//    final int heightSize = View.MeasureSpec.getSize(heightSpec);
//    int width = 0;
//    int height = 0;
//    Log.d("state:", state.toString());
//    int lie = 0;
//
//    for (int i = 0; i < getItemCount(); i++) {
//      try {
//        measureScrapChild(recycler, i, widthSpec, View.MeasureSpec
//            .makeMeasureSpec(i, View.MeasureSpec.UNSPECIFIED), mMeasuredDimension);
//      } catch (IndexOutOfBoundsException e) {
//        e.printStackTrace();
//      }
//
//      if (getOrientation() == HORIZONTAL) {
//        width = width + mMeasuredDimension[0];
//        if (i == 0) {
//          height = mMeasuredDimension[1];
//        }
//      } else {
//        height = height + mMeasuredDimension[1];
//        if (i == 0) {
//          width = mMeasuredDimension[0];
//        }
//      }
//    }
//
//
//    //        Logger.d("ll width:"+width+";widthSize:"+widthSize+";widthSpec:"+widthSpec);
//    //        Logger.d("ll height:"+width+";heightSize:"+heightSize+";heightSpec:"+heightSpec);
//    //        Logger.d("ll widthMode:"+widthMode+";heightMode:"+heightMode);
//
//    switch (widthMode) {
//      case View.MeasureSpec.EXACTLY:
//        //                    width = widthSize;
//      case View.MeasureSpec.AT_MOST:
//      case View.MeasureSpec.UNSPECIFIED:
//    }
//
//    switch (heightMode) {
//      case View.MeasureSpec.EXACTLY:
//        height = heightSize;
//      case View.MeasureSpec.AT_MOST:
//      case View.MeasureSpec.UNSPECIFIED:
//    }
//    setMeasuredDimension(widthSpec, height);
//
//  }
//
//  private void measureScrapChild(RecyclerView.Recycler recycler, int position, int widthSpec,
//                                 int heightSpec, int[] measuredDimension) {
//    View view = recycler.getViewForPosition(position);
//
//    // For adding Item Decor Insets to view
//    //        super.measureChildWithMargins(view, 0, 0);
//
//    if (view != null) {
//      RecyclerView.LayoutParams p = (RecyclerView.LayoutParams) view.getLayoutParams();
//      int childHeightSpec = ViewGroup
//          .getChildMeasureSpec(heightSpec, getPaddingTop() + getPaddingBottom(), p.height);
//      view.measure(widthSpec, childHeightSpec);
//      measuredDimension[0] = view.getMeasuredWidth() + p.leftMargin + p.rightMargin;
//      measuredDimension[1] = view.getMeasuredHeight() + p.bottomMargin + p.topMargin;
//      recycler.recycleView(view);
//    }
//  }
//
//  @Override
//  public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
//    //        Logger.e("SyLinearLayoutManager state:" + state.toString());
//    super.onLayoutChildren(recycler, state);
//  }

}
