package com.yskrq.yjs;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.speech.tts.TextToSpeech;

import com.yskrq.common.util.LOG;
import com.yskrq.yjs.util.PhoneUtil;

import java.util.HashMap;
import java.util.Locale;

public class Speaker {

  private static TextToSpeech textToSpeech;

  public interface OnSpeakListener {
    void onFinish(int status);
  }

  public static void speakOut(Context context, final String con) {
    PhoneUtil.openVoice(context);
    speakOut(context, con, null);
  }

  private volatile static HashMap<String, Long> readTime = new HashMap<>();

  public static void speakOut(Context context, final String con, final OnSpeakListener listener) {
    speakOutTwice(context, con, false, listener);
  }

  static Handler mainHandler = new Handler(Looper.getMainLooper());

  public static void speakOutTwice(Context context, final String con, final boolean isTwice,
                                   final OnSpeakListener listener) {
    textToSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
      @Override
      public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
          if (readTime.containsKey(con)) {
            try {

              long lastReadTime = readTime.get(con);
              if (System.currentTimeMillis() - lastReadTime < 30 * 1000) {
                LOG.e("Speaker", lastReadTime + ".stop:" + con);
                return;
              }
            } catch (Exception e) {

            }
          }
          LOG.e("Speaker", "播报:" + con);
          textToSpeech.setLanguage(Locale.CHINESE);
          mainHandler.post(new Runnable() {
            @Override
            public void run() {
              int s = textToSpeech.speak(con, TextToSpeech.QUEUE_FLUSH, null, "1");
              LOG.e("Speaker", "run.53:" + s);
              if (s == TextToSpeech.SUCCESS) {
                readTime.put(con, System.currentTimeMillis());
              }
              if (listener != null) {
                listener.onFinish(s);
              }
            }
          });
          if (isTwice) {
            mainHandler.postDelayed(new Runnable() {
              @Override
              public void run() {
                LOG.e("Speaker", "播报2:" + con);
                textToSpeech.speak(con, TextToSpeech.QUEUE_FLUSH, null, "2");
              }
            }, 10_000);
          }
        }
      }
    });
  }
}
