package com.yskrq.yjs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.yskrq.common.LoginActivity;
import com.yskrq.common.util.LOG;

import androidx.annotation.Nullable;

public class WelcomeActivity extends Activity {

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    LOG.e("WelcomeActivity", "onCreate.16:" + BaseApplication.isLogin);
    if (BaseApplication.isLogin) {
      Intent intent = new Intent(this, MainActivity.class);
      startActivity(intent);
      finish();
    } else {
      Intent intent = new Intent(this, LoginActivity.class);
      startActivity(intent);
      finish();
    }
  }
}
