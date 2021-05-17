package modularization.features.dashboard.preview

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class JoinViewModel : ViewModel() {

    private val _liveData = MutableLiveData<String>()
    val liveData: LiveData<String> = _liveData

    fun setColorValue(colorValue: String) {
        _liveData.value = colorValue
    }

    fun submit(context: Context) {

    }
}