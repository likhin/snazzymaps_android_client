package com.nikhilbawane.snazzymaps.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.util.TypedValue;

/**
 * Created by Nikhil on 16-07-2017.
 */

public class Util {

    public static int getNavigationBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            int height = resources.getDimensionPixelSize(resourceId);
            Log.i("Util", "NavigationBarHeight = " + height);
            return height;
        }
        return 0;
    }

    public static int getStatusBarHeight(Context context) {
        Resources resources = context.getResources();
        if (isStatusBarTranslucent(context)) {
            int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                int height = resources.getDimensionPixelSize(resourceId);
                Log.i("Util", "StatusBarHeight = " + height);
                return height;
            }
        }
        return 0;
    }

    public static int getActionBarHeight(Context context) {
        TypedValue tv = new TypedValue();
        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true))
        {
            int height = TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());
            Log.i("Util", "ActionBarHeight = " + height);
            return height;
        }
        return 48;
    }

    public static int dpToPx (int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int pxToDP (int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    public static boolean isStatusBarTranslucent(Context context) {
        TypedValue tv = new TypedValue();
        if (context.getTheme().resolveAttribute(android.R.attr.windowTranslucentStatus, tv, true))
        {
            int state = tv.data;
            Log.i("Util", "isStatusBarTranslucent = " + state);
            return state == -1;
        }
        return false;
    }
}
