package modularization.features.dashboard.settings

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import modularization.features.dashboard.R
import modularization.libraries.uicomponents.baseClasses.BaseFragmentBinding

class SettingsFrg : BaseFragmentBinding<SettingsViewDataBinding>() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        var instance: SettingsFrg? = null
            get() {
                if (field == null) {
                    field = SettingsFrg()
                }
                return field
            }
    }

    /** ViewModel related to Fragment*/
    private lateinit var viewModel: SettingsViewModel

    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_settings
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)
        return inflater.inflate(layoutResourceId, container, false).apply {
            viewModel.liveData.observe(viewLifecycleOwner, Observer {
                val item = it ?: return@Observer
            })
        }
    }
}