package modularization.libraries.utils.helpers;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import saman.zamani.persiandate.PersianDate;
import saman.zamani.persiandate.PersianDateFormat;

public class DateHelper {

    public static String getHumanReadableTimeFromTimeStamp(long timestamp) {

        try {
            PersianDate persianDate = new PersianDate(timestamp);
            PersianDateFormat dateFormat = new PersianDateFormat("d/F/y");
            return dateFormat.format(persianDate);
        } catch (Exception e) {
            e.printStackTrace();
            return "undefined";
        }
    }

    @NotNull
    public static String milliSecToTimeFormat(long millis) {
        return String.format(Locale.US, "%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1));
    }
}
