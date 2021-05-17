package modularization.libraries.utils.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public class ScreenHelper {

    Context context;

    public ScreenHelper(Context context) {
        this.context = context;
    }

    public double getScreenSize() {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        double x = Math.pow(dm.widthPixels / dm.xdpi, 2);
        double y = Math.pow(dm.heightPixels / dm.ydpi, 2);
        return Math.sqrt(x + y);
    }

    public int getScreenWidth() {
        try {
            return context.getResources().getDisplayMetrics().widthPixels;
        } catch (Exception e) {
            new LogHelper(this.getClass()).e(e);
            return 0;
        }
    }

    public int getScreenHeight() {
        try {
            return context.getResources().getDisplayMetrics().heightPixels;
        } catch (Exception e) {
            new LogHelper(this.getClass()).e(e);
            return 0;
        }
    }

    public float getScreenHeightInDp() {
        final DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.heightPixels / displayMetrics.density;
    }

    public float convertPixelsToDp(float px) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(px / (displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT));

    }

    public int convertDpToPixelsInt(float dp) {
        Resources resources = context.getResources();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
    }

}
