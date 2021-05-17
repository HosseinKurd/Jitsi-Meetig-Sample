package modularization.libraries.dataSource.repository.remote.baseClasses;

import android.content.Context;

import com.google.gson.Gson;

import io.swagger.client.ApiClient;
import io.swagger.client.utils.PublicValues;
import modularization.libraries.dataSource.sharedPreference.PreferenceDataSource;
import okhttp3.ResponseBody;

abstract public class BaseAPI {

    static {
        System.loadLibrary("native-lib");
    }

    public static native String getClientId();

    public static native String getSecretId();


    private static ApiClient apiClientPublic = null; // api client with out username/password

    private static ApiClient apiClientPrivate = null; // api client with username/password


    private static ApiClient getPublicApiClient() {
        if (apiClientPublic == null) {
            apiClientPublic = new ApiClient.ApiClientBuilder(PublicValues.BASE_URL_STAGE)
                    .build();
        }
        return apiClientPublic;
    }

    private static ApiClient getPrivateApiClient(Context context) {

        if (apiClientPrivate == null) {

            // get username & password
            String username = PreferenceDataSource.readString(context, PreferenceDataSource.KEY_MOBILE, "");
            String password = PreferenceDataSource.readString(context, PreferenceDataSource.KEY_OTP, "");

            // init private api client instance
            apiClientPrivate = new ApiClient.ApiClientBuilder(PublicValues.BASE_URL_STAGE, PublicValues.BASE_URL_STAGE)
                    .setClientId(getClientId())
                    .setSecret(getSecretId())
                    .setUsername(username)
                    .setPassword(password)
                    .build();
        }

        return apiClientPrivate;
    }

    public ApiClient getApiClient() {
        return getApiClient(null);
    }

    public ApiClient getApiClient(Context context) {
        return (context != null) ? getPrivateApiClient(context) : getPublicApiClient();
    }

    public static void refreshApiClient() {
        apiClientPublic = null;
        apiClientPrivate = null;
    }

    public String getErrorMessage(ResponseBody errorBody) {

        try {
            Gson gson = new Gson();
            BaseErrorResponse baseErrorResponse = gson.fromJson(errorBody.charStream(), BaseErrorResponse.class);
            return baseErrorResponse.getMessage();
        } catch (Exception e) {
            return "No Error Message Found On Casting BaseErrorResponse";
        }
    }

    public String getErrorMessage(Throwable t) {

        // TODO: 4/27/21 need to work on error messages and handling

        String errorMessage = "";

        if (t.getCause() != null && t.getCause().getLocalizedMessage() != null)
            errorMessage = "onFailure: " + t.getCause().getLocalizedMessage();
        else
            errorMessage = "onFailure: " + t.getMessage();

        return errorMessage;
    }
}
