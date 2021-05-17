package modularization.libraries.dataSource.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import modularization.libraries.dataSource.models.NetworkResult;

public class NetworkViewModel extends ViewModel {

    private MutableLiveData<NetworkResult> liveData;

    public NetworkViewModel() {
        this.liveData = new MutableLiveData<>();
        liveData.observeForever(new Observer<NetworkResult>() {
            @Override
            public void onChanged(NetworkResult networkResult) {
                if (networkResult.getCode() == null)
                    return;
                switch (networkResult.getCode()) {
                    case "403":
                        //                        AndroidUtils.toast(networkResult.getMessage());
//                        Repository.getInstance().clearAllTablesFromDB();
//                        AndroidUtils.logOutApp(MyApplication.getmGoogleSignInClient(), MyApplication.getContext());
                        break;
                }
            }
        });
    }

    private void showFinishDialog(String message) {
       /* DialogAlert dialogAlert = new DialogAlert(MyApplication.getContext(), null);
        dialogAlert.setText(message);
        dialogAlert.setPositiveButton(R.string.ok);
        dialogAlert.setCancelable(false);
        dialogAlert.setCanceledOnTouchOutside(false);
        dialogAlert.setDialogCallback(new DialogCallback() {
            @Override
            public void onPositiveButtonClicked() {
                Repository.getInstance().clearAllTablesFromDB();
                AndroidUtils.logOutApp(MyApplication.getmGoogleSignInClient(), MyApplication.getContext());
            }
            @Override
            public void onNegativeButtonClicked() {
            }
        });
        if (!dialogAlert.isShowing()) {
                dialogAlert.show();
        }*/
    }

    public MutableLiveData<NetworkResult> exposeNetworkLiveData() {
        return liveData;
    }
}
