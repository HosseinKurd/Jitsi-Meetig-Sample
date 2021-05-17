package modularization.libraries.dataSource.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import modularization.libraries.dataSource.models.NetworkResult;

public class NetworkAndroidViewModel extends AndroidViewModel {

    private MutableLiveData<NetworkResult> liveData;

    public NetworkAndroidViewModel(@NonNull Application application) {
        super(application);
        this.liveData = new MutableLiveData<>();
    }

    public MutableLiveData<NetworkResult> exposeNetworkLiveData() {
        return liveData;
    }

}
