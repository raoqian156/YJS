package com.yskrq.yjs.util;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.request.target.CustomViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.yskrq.yjs.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class GlideTagIdHelper extends CustomViewTarget {
    public GlideTagIdHelper(@NonNull View view) {
        super(view);
        useTagId(R.id.tag_key);
    }

    @Override
    protected void onResourceCleared(@Nullable Drawable placeholder) {
        ((ImageView) view).setImageDrawable(placeholder);
    }

    @Override
    public void onLoadFailed(@Nullable Drawable errorDrawable) {
        ((ImageView) view).setImageDrawable(errorDrawable);
    }

    @Override
    public void onResourceReady(@NonNull Object resource, @Nullable Transition transition) {
        if (view instanceof ImageView) {
            if (resource instanceof Bitmap) {
                ((ImageView) view).setImageBitmap((Bitmap) resource);
            } else if (resource instanceof Drawable) {
                ((ImageView) view).setImageDrawable((Drawable) resource);
            } else if (resource instanceof Integer) {
                ((ImageView) view).setImageResource((Integer) resource);
            }
        }
    }
}
