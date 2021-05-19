package modularization.features.dashboard.settings

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import modularization.features.dashboard.R
import modularization.features.dashboard.configs.Meeting
import modularization.libraries.uicomponents.baseClasses.BaseFragmentBinding
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions

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
            findViewById<CheckBox>(R.id.chk_audio_only).setOnCheckedChangeListener { buttonView, isChecked ->
                    Meeting.config.isAudioOnly = isChecked
            }
            findViewById<CheckBox>(R.id.chk_audio_mute).setOnCheckedChangeListener { buttonView, isChecked ->
                    Meeting.config.isAudioMuted = isChecked
            }
            findViewById<CheckBox>(R.id.chk_video_mute).setOnCheckedChangeListener { buttonView, isChecked ->
                    Meeting.config.isVideoMuted = isChecked
            }
            findViewById<CheckBox>(R.id.chk_welcome_page).setOnCheckedChangeListener { buttonView, isChecked ->
                    Meeting.config.isWelcomePageEnabled = isChecked
            }
            viewModel.liveData.observe(viewLifecycleOwner, Observer {
                val item = it ?: return@Observer
            })
            val options = JitsiMeetConferenceOptions.Builder()

        }
    }
}