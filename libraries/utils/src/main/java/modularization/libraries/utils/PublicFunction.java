package modularization.libraries.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.util.Locale;

public class PublicFunction {

    public static String toEnglishNumber(String input) {
        String[] persian = {"۰", "۱", "۲", "۳", "۴", "۵", "۶", "۷", "۸", "۹"};

        for (int j = 0; j < persian.length; j++)
            input = input.replace(persian[j], String.valueOf(j));

        return input;
    }

    public static String toPersianNumber(String input) {
        String[] english = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        String[] persian = {"۰", "۱", "۲", "۳", "۴", "۵", "۶", "۷", "۸", "۹"};

        for (int j = 0; j < persian.length; j++)
            input = input.replace(english[j], persian[j]);

        return input;
    }

    public static boolean StringIsEmptyOrNull(String string) {
        try {
            return string == null || string.length() == 0 || string.equals("null");
        } catch (Exception var2) {
            return false;
        }
    }

    public static int convertDpToPixels(float dp, Context context) {
        Resources resources = context.getResources();
        int pixel = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
        return pixel;
    }

    public static int convertPixelsToDp(float px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return (int) dp;
    }



    public static void showSoftwareKeyboard(Activity activity, boolean showKeyboard) {
        final InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);

        if (activity.getCurrentFocus() != null)
            inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), showKeyboard ? InputMethodManager.SHOW_FORCED : InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static void openBrowser(final Context context, String url) {

        String HTTPS = "https://";
        String HTTP = "http://";

        if (!url.startsWith(HTTP) && !url.startsWith(HTTPS)) {
            url = HTTP + url;
        }

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(Intent.createChooser(intent, "Choose browser"));// Choose browser is arbitrary :)

    }


    /**
     * open the direction apps which are installed on the device, so that the user could get a direction between gps location and selected marker
     * It is worked with GoogleMap & Waze Application
     */

    public static void openDirectionApps(Context context, double lat, double lng, String headLine, String appNotFound) {
        String uri = "";
        Intent intent;
        try {

            uri = String.format(Locale.ENGLISH, "geo:" + lat + "," + lng + "?q=" + lat + "," + lng + " (" + headLine + ")");
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            try {
                Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                context.startActivity(unrestrictedIntent);
            } catch (ActivityNotFoundException innerEx) {
                Toast.makeText(context, appNotFound, Toast.LENGTH_LONG).show();
            }
        }

    }

    public static void callPhoneNumber(Context context,String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
        context.startActivity(intent);
    }


    public static Bitmap getBitmapFromDrawable(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else {
            // width and height are equal for all assets since they are ovals.
            Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        }
    }
}
