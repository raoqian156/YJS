package com.yskrq.yjs.util;

import android.view.View;

import java.util.HashMap;

public class DoubleClickHelper {
  static HashMap<View, Long> click = new HashMap<>();

  public static boolean isDoubleClick(View view) {
    if (!click.containsKey(view)) {
      click.put(view, System.currentTimeMillis());
      return false;
    } else {
      long lastClickTime = click.get(view);
      boolean isDoubleClick = System.currentTimeMillis() - lastClickTime < 300;
      if (!isDoubleClick) {
        click.clear();
        click.put(view, System.currentTimeMillis());
      }
      return isDoubleClick;
    }
  }
}
