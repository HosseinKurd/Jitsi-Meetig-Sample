package modularization.libraries.uicomponents;

import android.app.Activity;

import androidx.core.content.res.ResourcesCompat;

import com.bumptech.glide.load.HttpException;
import com.tapadoo.alerter.Alerter;

import java.io.IOException;
import java.util.Objects;

import modularization.libraries.uicomponents.exceptions.MagicalException;

public class MagicalAlerter {

    public static void show(Activity activity, String msg) {
        if (msg == null)
            msg = activity.getString(R.string.error);

        Alerter.create(activity)
                .setIcon(R.drawable.ic_logo_lawone_svg)
                .setText(msg)
                .setTitleTypeface(Objects.requireNonNull(ResourcesCompat.getFont(activity, R.font.app_font)))
                .setTextTypeface(Objects.requireNonNull(ResourcesCompat.getFont(activity, R.font.app_font)))
                .setBackgroundColorRes(R.color.colorPrimary)
                .showRightIcon(false)
                .setTextAppearance(R.style.AppTheme_AlerterTextView_Base)
                .show();
    }

    public static void show(Activity activity, String title, String msg) {
        if (title == null)
            title = activity.getString(R.string.error_title);

        if (msg == null)
            msg = activity.getString(R.string.error);

        Alerter.create(activity)
                .setIcon(R.drawable.ic_logo_lawone_svg)
                .setTitle(title)
                .setText(msg)
                .setTitleTypeface(Objects.requireNonNull(ResourcesCompat.getFont(activity, R.font.app_font)))
                .setTextTypeface(Objects.requireNonNull(ResourcesCompat.getFont(activity, R.font.app_font)))
                .setTextAppearance(R.style.AppTheme_AlerterTextView_Base)
                .setBackgroundColorRes(R.color.colorPrimary)
                .showRightIcon(false)
                .enableRightIconPulse(false)
                .show();
    }

    public static void show(Activity activity, MagicalException magicalException) {

        if (magicalException == null)
            magicalException = new MagicalException();

        if (magicalException.getErrorMessage() == null || magicalException.getErrorMessage().isEmpty())
            magicalException.setErrorMessage(activity.getString(R.string.title_please_provide_error));

        String errorCode = "";

        if (BuildConfig.DEBUG && magicalException.getErrorCode() != null && !magicalException.getErrorCode().isEmpty()) {

            errorCode = new StringBuilder()
                    .append(activity.getString(R.string.title_error_code))
                    .append(": ")
                    .append(magicalException.getErrorCode()).toString();
        }

        Alerter.create(activity)
                .setIcon(R.drawable.ic_logo_lawone_svg)
                .setTitle(magicalException.getErrorMessage())
                .setText(errorCode)
                .setTitleTypeface(Objects.requireNonNull(ResourcesCompat.getFont(activity, R.font.app_font)))
                .setTextTypeface(Objects.requireNonNull(ResourcesCompat.getFont(activity, R.font.app_font)))
                .setTextAppearance(R.style.AppTheme_AlerterTextView_Base)
                .setBackgroundColorRes(R.color.colorPrimary)
                .showRightIcon(false)
                .enableRightIconPulse(false)
                .show();
    }

    public static void show(Activity activity, Throwable t) {

        String errorMessage = "";

        if (t instanceof IOException) {
            // handle network error
            errorMessage = activity.getString(R.string.title_check_network_connection);
        } else if (t instanceof HttpException) {
            // handle HTTP error response code
            errorMessage = "HttpException accrued";
        } else {
            // handle other exceptions
            errorMessage = "onFailure: " + t.getCause();
        }

        Alerter.create(activity)
                .setIcon(R.drawable.ic_logo_lawone_svg)
                .setTitle(errorMessage)
                .setTitleTypeface(Objects.requireNonNull(ResourcesCompat.getFont(activity, R.font.app_font)))
                .setTextTypeface(Objects.requireNonNull(ResourcesCompat.getFont(activity, R.font.app_font)))
                .setTextAppearance(R.style.AppTheme_AlerterTextView_Base)
                .setBackgroundColorRes(R.color.colorPrimary)
                .showRightIcon(false)
                .enableRightIconPulse(false)
                .show();
    }

}
