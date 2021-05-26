package modularization.features.dashboard.connecting
/*

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.FragmentActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.facebook.react.modules.core.PermissionListener
import modularization.features.dashboard.connecting.services.*
import modularization.features.dashboard.connecting.services.JitsiMeetOngoingConferenceService
import org.jitsi.meet.sdk.*
import org.jitsi.meet.sdk.log.JitsiMeetLogger
import java.util.*
import org.jitsi.meet.sdk.R.id
import org.jitsi.meet.sdk.R.layout


class ConnectingActivity : FragmentActivity() , JitsiMeetActivityInterface{
    companion object{
        private val ACTION_JITSI_MEET_CONFERENCE = "org.jitsi.meet.CONFERENCE"
        private val JITSI_MEET_CONFERENCE_OPTIONS = "JitsiMeetConferenceOptions"
        fun launch(context: Context, options: JitsiMeetConferenceOptions?) {
            val intent = Intent(context, ConnectingActivity::class.java)
            intent.action = ACTION_JITSI_MEET_CONFERENCE
            intent.putExtra(JITSI_MEET_CONFERENCE_OPTIONS, options)
            context.startActivity(intent)
        }

        fun launch(context: Context, url: String?) {
            val options = JitsiMeetConferenceOptions.Builder().setRoom(url).build()
            launch(context, options)
        }
    }
    private val TAG: String = javaClass.simpleName + "_TAG"
    private val broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            this@ConnectingActivity.onBroadcastReceived(intent)
        }
    }

    fun ConnectingActivity() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(layout.activity_jitsi_meet)
        registerForBroadcastMessages()
        if (!extraInitialize()) {
            initialize()
        }
    }

    override fun onDestroy() {
        leave()
        if (org.jitsi.meet.sdk.AudioModeModule.useConnectionService()) {
            ConnectionService.abortConnections()
        }
        JitsiMeetOngoingConferenceService.abort(this)
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver)
        super.onDestroy()
    }

    override fun finish() {
        leave()
        super.finish()
    }

    protected fun getJitsiView(): JitsiMeetView {
        val fragment =
            this.supportFragmentManager.findFragmentById(id.jitsiFragment) as JitsiMeetFragment?
        return fragment?.jitsiView!!
    }

    fun join(url: String?) {
        val options = JitsiMeetConferenceOptions.Builder().setRoom(url).build()
        this.join(options)
    }

    fun join(options: JitsiMeetConferenceOptions?) {
        val view = getJitsiView()
        if (view != null) {
            view.join(options)
        } else {
            JitsiMeetLogger.w("Cannot join, view is null", *arrayOfNulls(0))
        }
    }

    fun leave() {
        val view = getJitsiView()
        if (view != null) {
            view.leave()
        } else {
            JitsiMeetLogger.w("Cannot leave, view is null", *arrayOfNulls(0))
        }
    }

    private fun getConferenceOptions(intent: Intent): JitsiMeetConferenceOptions? {
        val action = intent.action
        if ("android.intent.action.VIEW" == action) {
            val uri = intent.data
            if (uri != null) {
                return JitsiMeetConferenceOptions.Builder().setRoom(uri.toString()).build()
            }
        } else if ("org.jitsi.meet.CONFERENCE" == action) {
            return intent.getParcelableExtra<Parcelable>("JitsiMeetConferenceOptions") as JitsiMeetConferenceOptions?
        }
        return null
    }

    protected fun extraInitialize(): Boolean {
        return false
    }

    protected fun initialize() {
        this.join(getConferenceOptions(this.intent))
    }

    protected fun onConferenceJoined(extraData: HashMap<String?, Any?>) {
        JitsiMeetLogger.i("Conference joined: $extraData", *arrayOfNulls(0))
        modularization.features.dashboard.connecting.services.JitsiMeetOngoingConferenceService.launch(this)
    }

    protected fun onConferenceTerminated(extraData: HashMap<String?, Any?>) {
        JitsiMeetLogger.i("Conference terminated: $extraData", *arrayOfNulls(0))
        finish()
    }

    protected fun onConferenceWillJoin(extraData: HashMap<String?, Any?>) {
        JitsiMeetLogger.i("Conference will join: $extraData", *arrayOfNulls(0))
    }

    protected fun onParticipantJoined(extraData: HashMap<String?, Any?>) {
        try {
            JitsiMeetLogger.i("Participant joined: ", *arrayOf<Any>(extraData))
        } catch (var3: Exception) {
            JitsiMeetLogger.w("Invalid participant joined extraData", *arrayOf<Any>(var3))
        }
    }

    protected fun onParticipantLeft(extraData: HashMap<String?, Any?>) {
        try {
            JitsiMeetLogger.i("Participant left: ", *arrayOf<Any>(extraData))
        } catch (var3: Exception) {
            JitsiMeetLogger.w("Invalid participant left extraData", *arrayOf<Any>(var3))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        JitsiMeetActivityDelegate.onActivityResult(this, requestCode, resultCode, data)
    }

    override fun onBackPressed() {
        JitsiMeetActivityDelegate.onBackPressed()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        var options: JitsiMeetConferenceOptions?
        if (getConferenceOptions(intent).also { options = it } != null) {
            this.join(options)
        } else {
            JitsiMeetActivityDelegate.onNewIntent(intent)
        }
    }

    override fun onUserLeaveHint() {
        val view = getJitsiView()
        view?.enterPictureInPicture()
    }

    override fun requestPermissions(
        permissions: Array<String?>?,
        requestCode: Int,
        listener: PermissionListener?
    ) {
        JitsiMeetActivityDelegate.requestPermissions(this, permissions, requestCode, listener)
    }

    fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>?,
        grantResults: IntArray?
    ) {
        JitsiMeetActivityDelegate.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun registerForBroadcastMessages() {
        val intentFilter = IntentFilter()
        intentFilter.addAction(BroadcastEvent.Type.CONFERENCE_JOINED.action)
        intentFilter.addAction(BroadcastEvent.Type.CONFERENCE_WILL_JOIN.action)
        intentFilter.addAction(BroadcastEvent.Type.CONFERENCE_TERMINATED.action)
        intentFilter.addAction(BroadcastEvent.Type.PARTICIPANT_JOINED.action)
        intentFilter.addAction(BroadcastEvent.Type.PARTICIPANT_LEFT.action)
        intentFilter.addAction(BroadcastEvent.Type.ENDPOINT_TEXT_MESSAGE_RECEIVED.action)
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, intentFilter)
    }

    private fun onBroadcastReceived(intent: Intent?) {
        if (intent != null) {
            val event = BroadcastEvent(intent)
            when (event.type) {
                BroadcastEvent.Type.CONFERENCE_JOINED -> onConferenceJoined(event.data)
                BroadcastEvent.Type.CONFERENCE_WILL_JOIN -> onConferenceWillJoin(event.data)
                BroadcastEvent.Type.CONFERENCE_TERMINATED -> onConferenceTerminated(event.data)
                BroadcastEvent.Type.PARTICIPANT_JOINED -> onParticipantJoined(event.data)
                BroadcastEvent.Type.PARTICIPANT_LEFT -> onParticipantLeft(event.data)
            }
        }
    }
}*/
