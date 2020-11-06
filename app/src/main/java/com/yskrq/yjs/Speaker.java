package com.yskrq.yjs;

import android.content.Context;
import android.speech.tts.TextToSpeech;

import com.yskrq.common.util.LOG;
import com.yskrq.yjs.util.PhoneUtil;

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

  public static void speakOut(Context context, final String con, final OnSpeakListener listener) {
    textToSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
      @Override
      public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
          LOG.e("Speaker", "播报:" + con);
          textToSpeech.setLanguage(Locale.CHINESE);
          int s = textToSpeech.speak(con, TextToSpeech.QUEUE_FLUSH, null, "1");
          if (listener != null) {
            listener.onFinish(s);
          }
        }
      }
    });
  }
}
