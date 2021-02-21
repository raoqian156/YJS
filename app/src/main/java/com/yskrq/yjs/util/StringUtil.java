package com.yskrq.yjs.util;

import android.text.TextUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class StringUtil {

  public static double getDouble(String s) {
    if (TextUtils.isEmpty(s)) return 0.00D;
    BigDecimal decimal = new BigDecimal(s);
    decimal.setScale(2, RoundingMode.HALF_UP);
    return decimal.doubleValue();
  }


  public static double doubleAdd(double value, String s) {
    if (TextUtils.isEmpty(s)) return value;
    BigDecimal decimal = new BigDecimal(s);
    decimal.setScale(2, RoundingMode.HALF_UP);
    return decimal.add(new BigDecimal(value + "")).setScale(2, RoundingMode.HALF_UP).doubleValue();
  }

  public static double doubleAdd(double value, double s) {
    BigDecimal decimal = new BigDecimal(s);
    decimal.setScale(2, RoundingMode.HALF_UP);
    return decimal.add(new BigDecimal(value + "")).setScale(2, RoundingMode.HALF_UP).doubleValue();
  }
}
