package com.ead.fuelpass.toast;

import android.content.Context;
import android.graphics.Color;

import io.github.muddz.styleabletoast.StyleableToast;

/**
 * Toast display
 */
public class Toasts {

    //Toast on success
    public static void success(Context context, String message)
    {
        new StyleableToast
                .Builder(context)
                .text(message)
                .textColor(Color.WHITE)
                .backgroundColor(Color.parseColor("#169E1C"))
                .show();
    }

    //Toast on error
    public static void error(Context context, String message)
    {
         new StyleableToast
                .Builder(context)
                .text(message)
                .textColor(Color.WHITE)
                .backgroundColor(Color.RED)
                .show();
    }

}
