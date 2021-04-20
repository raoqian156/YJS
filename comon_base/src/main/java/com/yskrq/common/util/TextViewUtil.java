package com.yskrq.common.util;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.TextView;

import com.yskrq.common.BASE;


public class TextViewUtil {
  /**
   * @param tv           控件
   * @param con          所有内容
   * @param light        有颜色的内容
   * @param colorPrimary 颜色ID
   */
  public static void setText(TextView tv, String con, String light, int colorPrimary,
                             float colorSizeRate) {
    if (TextUtils.isEmpty(con) || TextUtils.isEmpty(light) || tv == null) {
      LOG.utilLog("传入参数异常");
      LOG.e("TextViewUtil", " tv = " + tv + "con = " + con + " \nlight = " + light);
      return;
    }
    SpannableString spannableString = new SpannableString(con);
    ForegroundColorSpan colorSpan = new ForegroundColorSpan(tv.getContext().getResources()
                                                              .getColor(colorPrimary));
    int start = con.indexOf(light);
    int end = start + light.length();
    RelativeSizeSpan sizeSpan = new RelativeSizeSpan(colorSizeRate);
    spannableString.setSpan(sizeSpan, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
    spannableString.setSpan(colorSpan, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
    tv.setText(spannableString);
  }

  public static void setText(TextView tv, int conRes, String light, int colorPrimary,
                             float colorSizeRate) {
    if (conRes <= 0 || TextUtils.isEmpty(light) || tv == null) {
      LOG.utilLog("传入参数异常");
      return;
    }
    String realContent = String.format(BASE.getCxt().getResources().getString(conRes), light);
    setText(tv, realContent, light, colorPrimary, colorSizeRate);
  }

  /**
   * 文字变大
   */
  public static void setTextBigger(TextView tv, String con, String light, int colorSizeRate) {
    if (TextUtils.isEmpty(con) || TextUtils.isEmpty(light) || tv == null) {
      LOG.utilLog("传入参数异常");
      LOG.e("TextViewUtil", " tv = " + tv + "con = " + con + " \nlight = " + light);
      return;
    }
    SpannableString spannableString = new SpannableString(con);
    int start = con.indexOf(light);
    int end = start + light.length();
    start = Math.max(0, start);
    RelativeSizeSpan sizeSpan = new RelativeSizeSpan(colorSizeRate);
    spannableString.setSpan(sizeSpan, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
    tv.setText(spannableString);
  }

  public static CharSequence getTextBigger(String con, String light, float colorSizeRate) {
    if (TextUtils.isEmpty(con) || TextUtils.isEmpty(light)) {
      LOG.utilLog("传入参数异常");
      LOG.e("TextViewUtil", "con = " + con + " \nlight = " + light);
      return "";
    }
    SpannableString spannableString = new SpannableString(con);
    int start = con.indexOf(light);
    int end = start + light.length();
    start = Math.max(0, start);
    RelativeSizeSpan sizeSpan = new RelativeSizeSpan(colorSizeRate);
    spannableString.setSpan(sizeSpan, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
    return spannableString;
  }

  /**
   * 文字变大
   */
  public static CharSequence getTextColor(String con, String light, int color) {
    if (TextUtils.isEmpty(con) || TextUtils.isEmpty(light)) {
      LOG.utilLog("传入参数异常");
      LOG.e("TextViewUtil", " tv = " + con + " \nlight = " + light);
      return "";
    }
    SpannableString spannableString = new SpannableString(con);
    ForegroundColorSpan colorSpan = new ForegroundColorSpan(BASE.getCxt().getResources()
                                                                .getColor(color));
    int start = con.indexOf(light);
    int end = start + light.length();
    spannableString.setSpan(colorSpan, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
    return spannableString;
  }

  public static CharSequence getTextColorSize(String con, String light, int color,
                                              float colorSizeRate) {
    if (TextUtils.isEmpty(con) || TextUtils.isEmpty(light)) {
      LOG.utilLog("传入参数异常");
      LOG.e("TextViewUtil", " tv = " + con + " \nlight = " + light);
      return "";
    }
    SpannableString spannableString = new SpannableString(con);
    ForegroundColorSpan colorSpan = new ForegroundColorSpan(BASE.getCxt().getResources()
                                                                .getColor(color));
    int start = con.indexOf(light);
    int end = start + light.length();
    spannableString.setSpan(colorSpan, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
    RelativeSizeSpan sizeSpan = new RelativeSizeSpan(colorSizeRate);
    spannableString.setSpan(sizeSpan, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
    return spannableString;
  }

  public static void setTextBigger(TextView tv, int conRes, String light, float colorSizeRate) {
    if (conRes <= 0 || TextUtils.isEmpty(light) || tv == null) {
      LOG.utilLog("传入参数异常");
      return;
    }
    String realContent = String.format(BASE.getCxt().getResources().getString(conRes), light);
    setTextBigger(tv, realContent, light, colorSizeRate);
  }

  /**
   * 文字变大
   */
  public static void setTextBigger(TextView tv, String con, String light, float colorSizeRate) {
    if (TextUtils.isEmpty(con) || TextUtils.isEmpty(light) || tv == null) {
      LOG.utilLog("传入参数异常");
      LOG.e("TextViewUtil", " tv = " + tv + "con = " + con + " \nlight = " + light);
      return;
    }
    SpannableString spannableString = new SpannableString(con);
    int start = con.indexOf(light);
    int end = start + light.length();
    start = Math.max(0, start);
    RelativeSizeSpan sizeSpan = new RelativeSizeSpan(colorSizeRate);
    spannableString.setSpan(sizeSpan, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
    tv.setText(spannableString);
  }

  /**
   * @param con           全部内容
   * @param light         变化内容
   * @param colorSizeRate 变化部分相对基础尺寸比例
   * @param colorClick    变化部分颜色
   * @param listener      变化部分点击事件
   */
  public static void setColorAndClick(TextView tv, String con, String light, float colorSizeRate,
                                      int colorClick, final View.OnClickListener listener) {
    if (tv == null || TextUtils.isEmpty(con) || TextUtils.isEmpty(light)) {
      return;
    }
    int start = Math.max(0, con.indexOf(light));
    int end = Math.min(start + light.length(), con.length());
    SpannableString spannableString = new SpannableString(con);
    if (colorSizeRate > 0) {
      RelativeSizeSpan sizeSpan = new RelativeSizeSpan(colorSizeRate);
      spannableString.setSpan(sizeSpan, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
    }
    if (listener != null) {
      spannableString
          .setSpan(new ClickSpan(listener), start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
      tv.setMovementMethod(LinkMovementMethod.getInstance());
    }
    if (colorClick > 0) {
      ForegroundColorSpan colorSpan = new ForegroundColorSpan(tv.getResources()
                                                                .getColor(colorClick));
      spannableString.setSpan(colorSpan, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
    }
    tv.setText(spannableString);
  }

  public interface OnTextInnerClick{

    void onClick(View v, String clickCon);
    void onCancel();
    void onSure();
  }


  /**
   * @param con           全部内容
   * @param click1        变化内容
   * @param colorSizeRate 变化部分相对基础尺寸比例
   * @param colorClick    变化部分颜色
   * @param listener      变化部分点击事件
   */
  public static CharSequence getColorAndClick(TextView tv, String con, float colorSizeRate,
                                              int colorClick, final OnTextInnerClick listener,
                                              String... click) {
    if (tv == null || TextUtils.isEmpty(con)) {
      return null;
    }
    SpannableString spannableString = new SpannableString(con);
    if (click != null && click.length > 0) {
      for (String clickItem : click) {
        int start = Math.max(0, con.indexOf(clickItem));
        int end = Math.min(start + clickItem.length(), con.length());
        if (colorSizeRate > 0) {
          RelativeSizeSpan sizeSpan = new RelativeSizeSpan(colorSizeRate);
          spannableString.setSpan(sizeSpan, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        if (listener != null) {
          spannableString
              .setSpan(new ClickSpan(listener, clickItem), start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
          tv.setMovementMethod(LinkMovementMethod.getInstance());
        }
        if (colorClick > 0) {
          ForegroundColorSpan colorSpan = new ForegroundColorSpan(tv.getResources()
                                                                    .getColor(colorClick));
          spannableString.setSpan(colorSpan, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }
      }
    }

    return spannableString;
  }

  private static class ClickSpan extends ClickableSpan {
    private View.OnClickListener mOnClickListener;
    private String clickCon;

    ClickSpan(View.OnClickListener clickListener) {
      this.mOnClickListener = clickListener;
    }

    ClickSpan(final OnTextInnerClick clickListener, String con) {
      this.mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          clickListener.onClick(v,clickCon);
        }
      };
      this.clickCon = con;
    }

    @Override
    public void onClick(final View widget) {
      LOG.e("TextViewUtil", "onClick.225:" + clickCon);
      mOnClickListener.onClick(widget);
      widget.postDelayed(new Runnable() {
        @Override
        public void run() {
          LOG.e("TextViewUtil", "callOnClick.251:" + clickCon);
          widget.callOnClick();
        }
      }, 300);
    }

    @Override
    public void updateDrawState(TextPaint tp) {
      tp.setColor(tp.linkColor);
      tp.setUnderlineText(false);
    }
  }
}
