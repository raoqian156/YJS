package com.yskrq.yjs.util;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;
import android.text.TextUtils;
import android.view.View;

import com.yskrq.common.BASE;
import com.yskrq.common.util.LOG;

import java.util.Map;

import androidx.annotation.ColorRes;
import androidx.core.content.ContextCompat;

public class BgHelper {

  public static String getLunColor(Map<String, String> cash) {//轮钟颜色 在用房间
    String color = "";
    if (cash != null) {
      color = cash.get("htmlBrandColor1");
    }
    if (TextUtils.isEmpty(color)) {
      color = "#00A0E8";
    }
    return color;
  }

  public static String getKongColor(Map<String, String> cash) {//空房 候钟
    String color = "";
    if (cash != null) {
      color = cash.get("htmlBrandColor3");
    }
    if (TextUtils.isEmpty(color)) {
      color = "#8CE1E7";
    }
    return color;
  }

  public static String getYuYueColor(Map<String, String> cash) {//预约
    String color = "";
    if (cash != null) {
      color = cash.get("htmlBrandColor7");
    }
    if (TextUtils.isEmpty(color)) {
      color = "#7BEF7B";
    }
    return color;
  }

  public static String getWeiXiuColor(Map<String, String> cash) {//维修房 技师休息
    String color = "";
    if (cash != null) {
      color = cash.get("htmlBrandColor10");
    }
    if (TextUtils.isEmpty(color)) {
      color = "#FFE000";
    }
    return color;
  }

  public static String getZangColor(Map<String, String> cash) {//脏房
    String color = "";
    if (cash != null) {
      color = cash.get("htmlBrandColordirty");
    }
    if (TextUtils.isEmpty(color)) {
      color = "#1C3754";
    }
    return color;
  }


  public static String getDianColor(Map<String, String> cash) {//技师点钟
    String color = "";
    if (cash != null) {
      color = cash.get("htmlBrandColor2");
    }
    if (TextUtils.isEmpty(color)) {
      color = "#E81F37";
    }
    return color;
  }

  public static String getRelaxColor(Map<String, String> cash) {//技师未上班
    String color = "";
    if (cash != null) {
      color = cash.get("htmlBrandColor11");
    }
    if (TextUtils.isEmpty(color)) {
      color = "#BCC3C4";
    }
    return color;
  }

  public interface PeopleDataGetter {
    String getIstatus();

    String getRelaxclocktype();

    String getIjobstatus();

    String getDestinestatus();

    String getLog();
  }

  public static <T extends PeopleDataGetter> void bindPeopleBg(Object colorCash, T item,
                                                               View view) {
    if (item == null || view == null) {
      LOG.e("BgHelper", "bindPeopleBg.15:");
      return;
    }
    Map<String, String> colors = null;
    try {
      colors = (Map<String, String>) colorCash;
    } catch (Exception e) {
      e.printStackTrace();
    }
    String color;

    if ("1".equals(item.getIstatus())) {//--------------------- 休息
      color = getWeiXiuColor(colors);
    } else {
      if ("2".equals(item.getIjobstatus())) {
        if ("0".equals(item.getRelaxclocktype())) {//-----------轮钟
          color = getLunColor(colors);//RelaxClockType=0
        } else {//----------------------------------------------点钟
          color = getDianColor(colors);//RelaxClockType=0
        }
      } else if ("3".equals(item.getIjobstatus())) {//----------未上班
        color = getRelaxColor(colors);
      } else if ("1".equals(item.getDestinestatus())) {//-------预约
        color = getYuYueColor(colors);
      } else {//-------------------------------------------------候钟
        color = getKongColor(colors);
      }
    }
    GradientDrawable drawable = new GradientDrawable();
    drawable.setCornerRadius(8);
    //    drawable.setStroke(2, Color.parseColor(Constant.APP_THEME_COLOR));
    //    drawable.setColor(Color.parseColor(Constant.APP_THEME_COLOR));
    drawable.setColor(Color.parseColor(color));
    view.setBackground(drawable);
  }

  public interface RoomDataGetter {

    String getResourceType();

    String getCalctype();

    String getCleanId();

    String getStatus();

    String getLog();

  }

  public static <T extends RoomDataGetter> void bindRoomBg(Object colorCash, T item, View view) {
    if (item == null || view == null) {
      LOG.e("RoomBgHelper", "bindBg.24:");
      return;
    }
    Map<String, String> colors = null;
    if (colorCash instanceof Map) {
      try {
        colors = (Map<String, String>) colorCash;
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    String color;
    if ("K".equals(item.getResourceType())) {//预约
      color = getYuYueColor(colors);
    } else if ("M".equals(item.getCalctype())) {//维修房
      color = getWeiXiuColor(colors);
    } else if ("1".equals(item.getCleanId())) {//脏房
      color = getZangColor(colors);
    } else if ("1".equals(item.getStatus())) {//空房
      color = getKongColor(colors);
    } else if ("0".equals(item.getStatus())) {//在用房间
      color = getLunColor(colors);
    } else {
      color = getLunColor(colors);
    }
    GradientDrawable drawable = new GradientDrawable();
    drawable.setCornerRadius(8);
    //    drawable.setStroke(2, Color.parseColor(Constant.APP_THEME_COLOR));
    //    drawable.setColor(Color.parseColor(Constant.APP_THEME_COLOR));
    drawable.setColor(Color.parseColor(color));
    view.setBackgroundDrawable(drawable);
  }


  public static Drawable getProduct(String color) {
    return getProductDrawable(Color.parseColor(color));
  }

  public static Drawable getProduct(@ColorRes int color) {
    int colorOut = ContextCompat.getColor(BASE.getCxt(), color);
    return getProductDrawable(colorOut);
  }

  private static Drawable getProductDrawable(int color) {
    String colorInner = "#FFFFFF";
    GradientDrawable drawable = new GradientDrawable();
    float f20 = dip2px(15);
    float[] radii = new float[]{f20, f20, f20, f20, f20, f20, f20, f20};
    //    top-left, top-right, bottom-right, bottom-left
    drawable.setCornerRadii(radii);
    drawable.setColor(color);
    if (TextUtils.isEmpty(colorInner)) {
      return drawable;
    }
    GradientDrawable drawableSmall = new GradientDrawable();
    drawableSmall.setCornerRadii(radii);
    //    drawableSmall.setPadding(dip2px(10), dip2px(10),4,4);
    drawableSmall.setColor(Color.parseColor(colorInner));
    StateListDrawable res = new StateListDrawable();
    float f10 = dip2px(10);
    InsetDrawable insetLayer2 = new InsetDrawable(drawableSmall, 1, (int) f10, 1, 1);
    res.addState(new int[]{}, new LayerDrawable(new Drawable[]{drawable, insetLayer2}));
    return res;
  }

  public static float dip2px(float dpValue) {
    final float scale = BASE.getCxt().getResources().getDisplayMetrics().density;
    return (dpValue * scale + 0.5f);
  }
}
