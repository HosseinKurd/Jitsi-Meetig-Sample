package modularization.libraries.dataSource.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import modularization.libraries.dataSource.repository.RepositoryManagerRemote;
import modularization.libraries.dataSource.sharedPreference.PreferenceDataSource;
import modularization.libraries.utils.helpers.ValidatorHelper;

public class LoginViewModel extends NetworkAndroidViewModel {

    public enum StateEnum {GOOGLE, SIGNUP, OTP, DONE}

    private MutableLiveData<StateEnum> loginState = new MutableLiveData<>(StateEnum.SIGNUP);
    private MutableLiveData<String> phoneLiveData = new MutableLiveData<>("");
    private MutableLiveData<String> otpLiveData = new MutableLiveData<>("");
    private MutableLiveData<Boolean> hasValidPhone = new MutableLiveData<>(false);
    private MutableLiveData<Boolean> hasValidOtp = new MutableLiveData<>(false);
    private MutableLiveData<Boolean> hasAcceptedTerms = new MutableLiveData<>(false);
    private MutableLiveData<Boolean> shouldRefresh = new MutableLiveData<>(false);

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<String> getPhoneLiveData() {
        return phoneLiveData;
    }

    public MutableLiveData<String> getOtpLiveData() {
        return otpLiveData;
    }

    public MutableLiveData<Boolean> getPhoneValidationLiveData() {
        return hasValidPhone;
    }

    public MutableLiveData<Boolean> getOtpValidationLiveData() {
        return hasValidOtp;
    }

    public MutableLiveData<StateEnum> getLoginStateLiveData() {
        return loginState;
    }

    public MutableLiveData<Boolean> getShouldRefresh() {
        return shouldRefresh;
    }

    public MutableLiveData<Boolean> getHasAcceptedTerms() {
        return hasAcceptedTerms;
    }

    public void validateMobile(int validMobileLength) {

        if (!ValidatorHelper.validateMobile(phoneLiveData.getValue(), validMobileLength))
            getPhoneValidationLiveData().postValue(false);
        else {
            getPhoneValidationLiveData().postValue(true);
        }
    }

    public void validateOtp(int validMobileLength) {
        if (!ValidatorHelper.validateOtp(otpLiveData.getValue(), validMobileLength))
            getOtpValidationLiveData().postValue(false);
        else {
            getOtpValidationLiveData().postValue(true);
        }
    }

    public void callRegisterApi() {
        RepositoryManagerRemote.newInstance().callRegister(this);
    }

    public void callValidateApi() {
        RepositoryManagerRemote.newInstance().callActivate(this);
    }

    public void callResendOTPApi() {
        RepositoryManagerRemote.newInstance().callRegister(this);
    }

    public void saveMobileOtp() {
        PreferenceDataSource.writeString(getApplication(), PreferenceDataSource.KEY_MOBILE, getPhoneLiveData().getValue());
        PreferenceDataSource.writeString(getApplication(), PreferenceDataSource.KEY_OTP, getOtpLiveData().getValue());
    }

}
