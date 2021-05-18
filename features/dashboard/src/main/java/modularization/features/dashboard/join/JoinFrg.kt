package modularization.features.dashboard.join

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.hardware.camera2.*
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import modularization.features.dashboard.R
import modularization.libraries.uicomponents.baseClasses.BaseFragmentBinding
import modularization.libraries.utils.CameraPermissionHelper
import modularization.libraries.utils.Logger
import org.jitsi.meet.sdk.JitsiMeet
import org.jitsi.meet.sdk.JitsiMeetActivityDelegate
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions
import pl.aprilapps.easyphotopicker.*
import timber.log.Timber
import java.net.MalformedURLException
import java.net.URL
import java.util.*


class JoinFrg : BaseFragmentBinding<JoinViewDataBinding>(), View.OnClickListener {

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

    /**
     * Check Requested Permissions Result
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        JitsiMeetActivityDelegate.onRequestPermissionsResult(requestCode, permissions, grantResults)
        /*if (!CameraPermissionHelper.hasCameraPermission(requireActivity())) {
            showMessage("Camera permission is needed to run this application")
            if (!CameraPermissionHelper.shouldShowRequestPermissionRationale(requireActivity())) {
                // Permission denied with checking "Do not ask again".
                CameraPermissionHelper.launchPermissionSettings(requireActivity())
            } else {
                cameraPermissionAllowed()
            }
        }*/
    }

    private fun cameraPermissionAllowed() {

    }

    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_join
    }

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
            when (event.getType()) {
                BroadcastEvent.Type.CONFERENCE_JOINED -> Timber.i(
                    "Conference Joined with url%s",
                    event.getData().get("url")
                )
                BroadcastEvent.Type.PARTICIPANT_JOINED -> Timber.i(
                    "Participant joined%s",
                    event.getData().get("name")
                )
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(JoinViewModel::class.java)
        return inflater.inflate(layoutResourceId, container, false).apply {
            findViewById<View>(R.id.btn_submit).setOnClickListener(this@JoinFrg)
            viewModel.liveData.observe(viewLifecycleOwner, Observer {
                val item = it ?: return@Observer
            })
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!CameraPermissionHelper.hasCameraPermission(requireActivity())) {
                    CameraPermissionHelper.requestCameraPermission(requireActivity())
                    return this
                }
            }
            initJitsi()
        }
    }

    private fun initJitsi() {

        // Initialize default options for Jitsi Meet conferences.
        val serverURL: URL
        serverURL = try {
            // When using JaaS, replace "https://meet.jit.si" with the proper serverURL
            URL("https://meet.jit.si")
        } catch (e: MalformedURLException) {
            Logger.e(TAG, "MalformedURLException --> ${e.message}", e)
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
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(broadcastReceiver, intentFilter)
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.btn_submit) {
            viewModel.submit(requireContext())
        }
    }

}