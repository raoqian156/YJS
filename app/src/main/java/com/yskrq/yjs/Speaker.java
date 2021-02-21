package com.yskrq.yjs;

import android.content.Context;
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

  public static void speakOutTwice(Context context, final String con, final boolean isTwice,
                                   final OnSpeakListener listener) {
    textToSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
      @Override
      public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
          if (readTime.containsKey(con)) {
            long lastReadTime = readTime.get(con);
            if (System.currentTimeMillis() - lastReadTime < 30 * 1000) {
              return;
            } else {
              readTime.put(con, System.currentTimeMillis());
            }
          } else {
            readTime.put(con, System.currentTimeMillis());
          }
          LOG.e("Speaker", "播报:" + con);
          textToSpeech.setLanguage(Locale.CHINESE);
          int s = textToSpeech.speak(con, TextToSpeech.QUEUE_FLUSH, null, "1");
          if (listener != null) {
            listener.onFinish(s);
          }
          if (isTwice) {
            new Thread(new Runnable() {
              @Override
              public void run() {
                try {
                  Thread.sleep(10_000);
                } catch (Exception e) {
                  textToSpeech.speak(con, TextToSpeech.QUEUE_FLUSH, null, "2");
                }

              }
            }).start();
          }
        }
      }
    });
  }
}
