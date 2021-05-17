package modularization.features.dashboard.preview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import modularization.libraries.dataSource.models.ColorValue

class CameraViewModel : ViewModel() {

    private val _colorValue = MutableLiveData<ColorValue>()
    val colorValue: LiveData<ColorValue> = _colorValue

    fun setColorValue(colorValue: ColorValue) {
        _colorValue.value = colorValue
    }
}