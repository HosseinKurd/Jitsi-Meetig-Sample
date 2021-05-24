package modularization.features.dashboard.join

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.hardware.camera2.*
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import modularization.features.dashboard.R
import modularization.features.dashboard.configs.Meeting
import modularization.features.dashboard.interfaces.OnNavigate
import modularization.libraries.dataSource.models.FeatureAdapterItem
import modularization.libraries.dataSource.models.FeatureFlags
import modularization.libraries.uicomponents.MagicalEditText
import modularization.libraries.uicomponents.baseClasses.BaseFragmentBinding
import modularization.libraries.utils.Logger
import org.jitsi.meet.sdk.*
import pl.aprilapps.easyphotopicker.*
import java.net.MalformedURLException
import java.net.URL
import java.util.*


class JoinFrg : BaseFragmentBinding<JoinViewDataBinding>() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        var instance: JoinFrg? = null
            get() {
                if (field == null) {
                    field = JoinFrg()
                }
                return field
            }
    }

    /** ViewModel related to Fragment*/
    private lateinit var viewModel: JoinViewModel

    /** ViewModel related to Fragment*/
    private lateinit var edtMeeting: MagicalEditText

    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            onBroadcastReceived(intent)
        }
    }

    /**
     * Example for handling different JitsiMeetSDK events
     */
    private fun onBroadcastReceived(intent: Intent?) {
        if (intent != null) {
            val event = BroadcastEvent(intent)
            when (event.type) {
                BroadcastEvent.Type.CONFERENCE_JOINED -> Logger.w(
                    TAG, "Conference Joined with url : ${event.data.get("url")}"

                )
                BroadcastEvent.Type.PARTICIPANT_JOINED -> Logger.w(
                    TAG, "Participant joined : ${event.data.get("name")}"
                )
                else -> Logger.w(
                    TAG, "Undefined joined : ${event.data}"
                )
            }
        }
    }

    override fun onDestroy() {
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(broadcastReceiver)
        super.onDestroy()
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_join
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(JoinViewModel::class.java)
        return inflater.inflate(layoutResourceId, container, false).apply {
            Meeting.features = getFeatures()
            edtMeeting = findViewById(R.id.edt_meeting)
            findViewById<View>(R.id.btn_submit).setOnClickListener { onSubmitClicked() }
            findViewById<View>(R.id.btn_settings).setOnClickListener { onSettingsClicked() }
            viewModel.liveData.observe(viewLifecycleOwner, Observer {
                val item = it ?: return@Observer
            })
            initJitsi()
        }
    }

    private fun getFeatures(): MutableList<FeatureAdapterItem> {
        return mutableListOf<FeatureAdapterItem>().apply {
            add(
                FeatureAdapterItem(
                    FeatureFlags.addPeopleEnabled,
                    "Flag indicating if add-people functionality should be enabled.\nDefault: disabled (true)"
                )
            )
            add(
                FeatureAdapterItem(
                    FeatureFlags.audioFocusDisabled,
                    "Flag indicating if the SDK should not require the audio focus.\nUsed by apps that do not use Jitsi audio.\nDefault: disabled (false)"
                )
            )
            add(
                FeatureAdapterItem(
                    FeatureFlags.audioMuteEnabled,
                    "Flag indicating if the audio mute button should be displayed.\nDefault: enabled (true)."
                )
            )
            add(
                FeatureAdapterItem(
                    FeatureFlags.audioOnlyEnabled,
                    "Flag indicating that the Audio only button in the overflow menu is enabled.\nDefault: enabled (true)."
                )
            )
            add(
                FeatureAdapterItem(
                    FeatureFlags.calendarEnabled,
                    "Flag indicating that the Audio only button in the overflow menu is enabled.\nDefault: enabled (true)."
                )
            )
            add(
                FeatureAdapterItem(
                    FeatureFlags.callIntegrationEnabled,
                    "Flag indicating if call integration (CallKit on iOS, ConnectionService on Android)\nshould be enabled.\nDefault: enabled (true)."
                )
            )
            add(
                FeatureAdapterItem(
                    FeatureFlags.closeCaptionsEnabled,
                    "Flag indicating if close captions should be enabled.\nDefault: enabled (true)."
                )
            )
            add(
                FeatureAdapterItem(
                    FeatureFlags.conferenceTimerEnabled,
                    "Flag indicating if conference timer should be enabled.\nDefault: enabled (true)."
                )
            )
            add(
                FeatureAdapterItem(
                    FeatureFlags.chatEnabled,
                    "Flag indicating if chat should be enabled.\nDefault: enabled (true)."
                )
            )
            add(
                FeatureAdapterItem(
                    FeatureFlags.filmstripEnabled,
                    "Flag indicating if the filmstrip should be enabled.\nDefault: enabled (true)."
                )
            )
            add(
                FeatureAdapterItem(
                    FeatureFlags.fullscreenEnabled,
                    "Flag indicating if fullscreen (immersive) mode should be enabled.\nDefault: enabled (true)."
                )
            )
            add(
                FeatureAdapterItem(
                    FeatureFlags.helpEnabled,
                    "Flag indicating if the Help button should be enabled.\nDefault: enabled (true)."
                )
            )
            add(
                FeatureAdapterItem(
                    FeatureFlags.inviteEnabled,
                    "Flag indicating if invite functionality should be enabled.\nDefault: enabled (true)."
                )
            )
            add(
                FeatureAdapterItem(
                    FeatureFlags.kickOutEnabled,
                    "Flag indicating if kickout is enabled.\nDefault: enabled (true)."
                )
            )
            add(
                FeatureAdapterItem(
                    FeatureFlags.liveStreamingEnabled,
                    "Flag indicating if live-streaming should be enabled.\nDefault: enabled (true)."
                )
            )
            add(
                FeatureAdapterItem(
                    FeatureFlags.lobbyModeEnabled,
                    "Flag indicating if lobby mode button should be enabled.\nDefault: enabled (true)."
                )
            )
            add(
                FeatureAdapterItem(
                    FeatureFlags.meetingNameEnabled,
                    "Flag indicating if displaying the meeting name should be enabled.\nDefault: enabled (true)."
                )
            )
            add(
                FeatureAdapterItem(
                    FeatureFlags.meetingPasswordEnabled,
                    "* Flag indicating if the meeting password button should be enabled.\nNote that this flag just decides on the button, if a meeting has a password\nset, the password dialog will still show up.\nDefault: enabled (true)."
                )
            )
            add(
                FeatureAdapterItem(
                    FeatureFlags.notificationsEnabled,
                    "Flag indicating if the notifications should be enabled.\nDefault: enabled (true)."
                )
            )
            add(
                FeatureAdapterItem(
                    FeatureFlags.overflowMenuEnabled,
                    "Flag indicating if the audio overflow menu button should be displayed.\nDefault: enabled (true)."
                )
            )
            add(
                FeatureAdapterItem(
                    FeatureFlags.pipEnabled,
                    "Flag indicating if Picture-in-Picture should be enabled.\nDefault: enabled (true)."
                )
            )
            add(
                FeatureAdapterItem(
                    FeatureFlags.raiseHandEnabled,
                    "Flag indicating if raise hand feature should be enabled.\nDefault: enabled (true)."
                )
            )
            add(
                FeatureAdapterItem(
                    FeatureFlags.recordingEnabled,
                    "Flag indicating if recording should be enabled.\nDefault: enabled (true)."
                )
            )
            add(
                FeatureAdapterItem(
                    FeatureFlags.securityOptionsEnabled,
                    "Flag indicating if the security options button should be enabled.\nDefault: enabled (true)."
                )
            )
            add(
                FeatureAdapterItem(
                    FeatureFlags.serverUrlChangeEnabled,
                    "Flag indicating if server URL change is enabled.\nDefault: enabled (true)."
                )
            )
            add(
                FeatureAdapterItem(
                    FeatureFlags.tileViewEnabled,
                    "Flag indicating if tile view feature should be enabled.\nDefault: enabled (true)."
                )
            )
            add(
                FeatureAdapterItem(
                    FeatureFlags.toolboxAlwaysVisible,
                    "Flag indicating if the toolbox should be always be visible.\nDefault: enabled (false)."
                )
            )
            add(
                FeatureAdapterItem(
                    FeatureFlags.toolboxEnabled,
                    "Flag indicating if the toolbox should be enabled.\nDefault: enabled (true)."
                )
            )
            add(
                FeatureAdapterItem(
                    FeatureFlags.videoMuteEnabled,
                    "Flag indicating if the video mute button should be displayed.\nDefault: enabled (true)."
                )
            )
            add(
                FeatureAdapterItem(
                    FeatureFlags.videoShareEnabled,
                    "Flag indicating if the video share button should be enabled.\nDefault: enabled (true)."
                )
            )
            add(
                FeatureAdapterItem(
                    FeatureFlags.WelcomePageEnabled,
                    "Flag indicating if the welcome page should be enabled.\nDefault: enabled (false)."
                )
            )
        }
    }

    private fun initJitsi() {
        // Initialize default options for Jitsi Meet conferences.
        val serverURL: URL
        serverURL = try {
            // When using JaaS, replace "https://meet.jit.si" with the proper serverURL
            URL(getString(R.string.jitsi_server))
            // URL("https://meet.jit.si")
        } catch (e: MalformedURLException) {
            e.printStackTrace()
            throw RuntimeException("Invalid server URL!")
        }
        val defaultOptions = JitsiMeetConferenceOptions.Builder()
            .setServerURL(serverURL)
            // When using JaaS, set the obtained JWT here
            //.setToken("MyJWT")
            // Different features flags can be set
            //.setFeatureFlag("toolbox.enabled", false)
            //.setFeatureFlag("filmstrip.enabled", false)
            .setWelcomePageEnabled(false)
            .build()
        JitsiMeet.setDefaultConferenceOptions(defaultOptions)

        registerForBroadcastMessages()
    }

    fun onSubmitClicked() {
        Logger.w(TAG, "FeatureFlags : $Meeting")
        if (edtMeeting.text.toString().isEmpty()) {
            Toast.makeText(requireContext(), R.string.empty_meeting_name, Toast.LENGTH_LONG).show()
        } else {
            // Build options object for joining the conference. The SDK will merge the default
            // one we set earlier and this one when joining.
            val options = JitsiMeetConferenceOptions.Builder()
                .setRoom(edtMeeting.text.toString())
                // Settings for audio and video
                /*.setAudioOnly(Meeting.config.isAudioOnly)
                .setAudioMuted(Meeting.config.isAudioMuted)
                .setVideoMuted(Meeting.config.isVideoMuted)
                .setWelcomePageEnabled(Meeting.config.isWelcomePageEnabled)*/
            Meeting.features.forEach { value ->
                options.setFeatureFlag(value.featureFlag.key, value.featureFlag.value)
            }
            // Launch the new activity with the given options. The launch() method takes care
            // of creating the required Intent and passing the options.
            JitsiMeetActivity.launch(requireContext(), options.build())
        }
    }

    fun onSettingsClicked() {
        if (requireActivity() is OnNavigate)
            (requireActivity() as OnNavigate).onNavigated(2)
    }

    private fun registerForBroadcastMessages() {
        val intentFilter = IntentFilter()

        /* This registers for every possible event sent from JitsiMeetSDK
           If only some of the events are needed, the for loop can be replaced
           with individual statements:
           ex:  intentFilter.addAction(BroadcastEvent.Type.AUDIO_MUTED_CHANGED.action);
                intentFilter.addAction(BroadcastEvent.Type.CONFERENCE_TERMINATED.action);
                ... other events
         */
        for (type in BroadcastEvent.Type.values()) {
            intentFilter.addAction(type.action)
        }
        LocalBroadcastManager.getInstance(requireContext())
            .registerReceiver(broadcastReceiver, intentFilter)
    }

    // Example for sending actions to JitsiMeetSDK
    private fun hangUp() {
        val hangupBroadcastIntent: Intent = BroadcastIntentHelper.buildHangUpIntent()
        LocalBroadcastManager.getInstance(org.webrtc.ContextUtils.getApplicationContext())
            .sendBroadcast(hangupBroadcastIntent)
    }
}