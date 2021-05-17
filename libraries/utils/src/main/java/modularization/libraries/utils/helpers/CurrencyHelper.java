package modularization.libraries.utils.helpers;

import java.text.DecimalFormat;
import java.util.Locale;

public class CurrencyHelper {
    
    public static String convert(int price, String currency) {
        return String.format(new Locale("en"), "%,d %S", price, currency);
    }

    public static String convert(long price, String currency) {
        return String.format(new Locale("en"), "%,d %S", price, currency);
    }

    public static String convert(float price, String currency) {
        return String.format(new Locale("en"), "%,f %S", price, currency);
    }

    public static String convert(float price) {
        return String.format(new Locale("en"), "%,f ", price);
    }

    public static String convert(long price) {
        return String.format(new Locale("en"), "%,d ", price);
    }

    public static String removeCurrencyFormat(String text, String currency) {
        String string = text.replaceAll("[-+.^:,،٬]", "");
        string = string.replaceAll(currency, "");
        string = string.trim();
        return string;
    }

    public static String splitPriceByDigits(Double value) {
        if (value == null) return "";

        DecimalFormat df = new DecimalFormat("#,###");
        df.setMaximumFractionDigits(0);

        return df.format(value);
    }

    public static String splitPriceByDigits(Long value) {
        if (value == null) return "";

        DecimalFormat df = new DecimalFormat("#,###");
        df.setMaximumFractionDigits(0);

        return df.format(value);
    }
}