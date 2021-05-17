package modularization.libraries.utils.helpers;

import android.text.Editable;
import android.text.TextUtils;

import modularization.libraries.utils.PublicFunction;

public class ValidatorHelper {

    // todo: need to create class instance

    public static boolean validateMobile(String num, int validMobileNumberLength) {
        String english = PublicFunction.toEnglishNumber(num);
        return english.length() >= validMobileNumberLength
                && (num.startsWith("09") || num.startsWith("+98"));
    }

    public static boolean validateOtp(String num, int validOTPCodeLength) {
        String english = PublicFunction.toEnglishNumber(num);
        return english.length() >= validOTPCodeLength;
    }

    public static boolean validateEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public static boolean hasText(Editable s) {
        return s != null && s.toString().length() > 0;
    }

    public static boolean correctOtp(Editable otp, int validOTPCodeLength) {
        return otp != null && otp.toString().trim().length() == validOTPCodeLength;
    }
}