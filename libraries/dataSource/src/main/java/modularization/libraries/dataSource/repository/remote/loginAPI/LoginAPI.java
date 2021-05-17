package modularization.libraries.dataSource.repository.remote.loginAPI;

import android.util.Log;

import io.swagger.client.api.RegistrationControllerApi;
import io.swagger.client.model.ActivationDto;
import io.swagger.client.model.ActivationResponseDto;
import io.swagger.client.model.RegisterWithTypeDto;
import modularization.libraries.dataSource.globalEnums.ResultEnum;
import modularization.libraries.dataSource.models.NetworkResult;
import modularization.libraries.dataSource.repository.remote.baseClasses.BaseAPI;
import modularization.libraries.dataSource.viewModels.LoginViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginAPI extends BaseAPI {

    private static final String TAG = "LoginAPI";

    private static LoginAPI instance;

    public static LoginAPI getInstance() {
        if (instance == null)
            instance = new LoginAPI();
        return instance;
    }

    public void register(LoginViewModel loginViewModel) {
        loginViewModel.exposeNetworkLiveData().postValue(new NetworkResult(ResultEnum.Loading));

        // init DTO
        RegisterWithTypeDto registerWithTypeDto = new RegisterWithTypeDto();
        registerWithTypeDto.setMobile(loginViewModel.getPhoneLiveData().getValue());
        registerWithTypeDto.setRegistrationType(RegisterWithTypeDto.RegistrationTypeEnum.SMS_OTP);

        // call api
        getApiClient().createService(RegistrationControllerApi.class).register1(registerWithTypeDto).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                loginViewModel.exposeNetworkLiveData().postValue(new NetworkResult(ResultEnum.Success));

                if (loginViewModel.getLoginStateLiveData().getValue() == LoginViewModel.StateEnum.SIGNUP)
                    loginViewModel.getLoginStateLiveData().postValue(LoginViewModel.StateEnum.OTP);
                else if (loginViewModel.getLoginStateLiveData().getValue() == LoginViewModel.StateEnum.OTP)
                    loginViewModel.getShouldRefresh().postValue(true);

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                loginViewModel.exposeNetworkLiveData().postValue(new NetworkResult(ResultEnum.Failure));
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }

    public void activate(LoginViewModel loginViewModel) {
        loginViewModel.exposeNetworkLiveData().postValue(new NetworkResult(ResultEnum.Loading));

        ActivationDto activationDto = new ActivationDto();
        activationDto.setActivationCode(loginViewModel.getOtpLiveData().getValue());
        activationDto.setMobile(loginViewModel.getPhoneLiveData().getValue());
        activationDto.setRegType(ActivationDto.RegTypeEnum.SMS_OTP);

        // call api
        getApiClient().createService(RegistrationControllerApi.class).activateByCode(activationDto).enqueue(new Callback<ActivationResponseDto>() {
            @Override
            public void onResponse(Call<ActivationResponseDto> call, Response<ActivationResponseDto> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    loginViewModel.exposeNetworkLiveData().postValue(new NetworkResult(ResultEnum.Success));
                    loginViewModel.getLoginStateLiveData().postValue(LoginViewModel.StateEnum.DONE);
                    loginViewModel.saveMobileOtp();
                    return;
                }

                loginViewModel.exposeNetworkLiveData().postValue(new NetworkResult(ResultEnum.Failure));

            }

            @Override
            public void onFailure(Call<ActivationResponseDto> call, Throwable t) {
                loginViewModel.exposeNetworkLiveData().postValue(new NetworkResult(ResultEnum.Failure));
            }
        });
    }

}