package com.bmpak.anagramsolver.utils;

import android.os.Build;
import android.view.View;
import android.view.animation.AlphaAnimation;

/**
 * Created by charbgr on 10/1/14.
 */
public class ViewUtils {

    private ViewUtils(){}


    public static float DEFAULT_FULL_ALPHA = 1.0f;
    public static float DEFAULT_SEMI_ALPHA = 0.4f;

    public static void setAlpha(View view, float alpha, long durationMillis) {
        if (Build.VERSION.SDK_INT < 11) {
            final AlphaAnimation animation = new AlphaAnimation(alpha, alpha);
            animation.setDuration(durationMillis);
            animation.setFillAfter(true);
            view.startAnimation(animation);
        } else {
            view.setAlpha(alpha);
        }
    }
}
