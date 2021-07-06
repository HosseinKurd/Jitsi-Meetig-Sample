package ir.vasl.simulator.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.vasl.simulator.R
import ir.vasl.simulator.model.MessageModel
import ir.vasl.simulator.model.utils.PublicFunction
import ir.vasl.simulator.model.utils.PublicValue
import ir.vasl.simulator.view.adapter.MessagesAdapter
import modularization.libraries.dataSource.models.FeatureFlags
import org.jitsi.meet.sdk.*
import java.net.MalformedURLException
import java.net.URL

class SimulatorActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var adapter: MessagesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simulator)

        // init actionbar(toolbar)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.title = "Android Presenter"

        // init view items and references
        findViewById<View>(R.id.button_send).setOnClickListener(this)

        // init major items
        initMessageList()
        initJitsiSDK()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.simulator_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_video_call -> {
                startVideoCall()
                true
            }
            R.id.action_voice_call -> {
                startVoiceCall()
                true
            }
            else ->
                super.onOptionsItemSelected(item)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.button_send -> {
                addNewMessage()
            }
        }
    }

    private fun initMessageList() {
        val recyclerView = findViewById<View>(R.id.recyclerView) as RecyclerView
        val messages = MessageModel.getContactList(25)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.reverseLayout = false
        layoutManager.stackFromEnd = true
        recyclerView.layoutManager = layoutManager

        adapter = MessagesAdapter(messages)
        recyclerView.adapter = adapter
    }

    private fun initJitsiSDK() {
        // Initialize default options for Jitsi Meet conferences.
        val serverURL: URL = try {
            // When using JaaS, replace "https://meet.jit.si" with the proper serverURL
            URL(PublicValue.TEMPLATE_SERVER_URL)
            // URL("https://meet.jit.si")
        } catch (e: MalformedURLException) {
            e.printStackTrace()
            throw RuntimeException("Invalid server URL!")
        }
        val defaultOptions = JitsiMeetConferenceOptions.Builder()
            .setContext(this@SimulatorActivity)
            .setServerURL(serverURL)
            .setWelcomePageEnabled(false)
            .build()
        JitsiMeet.setDefaultConferenceOptions(defaultOptions)
    }

    private fun startVideoCall() {
        // Build options object for joining the conference. The SDK will merge the default
        // one we set earlier and this one when joining.
        val options = JitsiMeetConferenceOptions.Builder()
            .setContext(this@SimulatorActivity)
            .setRoom(PublicValue.TEMPLATE_ROOM_NAME)
            .setUserInfo(getJitsiMeetUserInfo())
            .setUsername(PublicValue.TEMPLATE_USERNAME)
            .setPassword(PublicValue.TEMPLATE_PASSWORD)
        // Settings options
        options.setFeatureFlag(FeatureFlags.chatEnabled.key, false)
        options.setFeatureFlag(FeatureFlags.overflowMenuEnabled.key, false)
        options.setFeatureFlag(FeatureFlags.WelcomePageEnabled.key, false)
        options.setFeatureFlag(FeatureFlags.tileViewEnabled.key, true)
        options.setFeatureFlag(FeatureFlags.pipEnabled.key, false)
        options.setFeatureFlag(FeatureFlags.notificationsEnabled.key, false)
        options.setFeatureFlag(FeatureFlags.meetingNameEnabled.key, false)
        options.setFeatureFlag(FeatureFlags.calendarEnabled.key, false)
        options.setFeatureFlag(FeatureFlags.inviteEnabled.key, false)
        options.setFeatureFlag(FeatureFlags.inviteEnabled.key, false)
        // Launch the new activity with the given options. The launch() method takes care
        // of creating the required Intent and passing the options.
        JitsiMeetActivity.launch(this@SimulatorActivity, options.build())
    }

    private fun startVoiceCall() {
        // Build options object for joining the conference. The SDK will merge the default
        // one we set earlier and this one when joining.
        val options = JitsiMeetConferenceOptions.Builder()
            .setContext(this@SimulatorActivity)
            .setRoom(PublicValue.TEMPLATE_ROOM_NAME)
            .setUserInfo(getJitsiMeetUserInfo())
            .setUsername(PublicValue.TEMPLATE_USERNAME)
            .setPassword(PublicValue.TEMPLATE_PASSWORD)
            .setWelcomePageEnabled(false)
            .setAudioOnly(true)
        // Settings options
        options.setFeatureFlag(FeatureFlags.chatEnabled.key, false)
        options.setFeatureFlag(FeatureFlags.overflowMenuEnabled.key, false)
        options.setFeatureFlag(FeatureFlags.WelcomePageEnabled.key, false)
        options.setFeatureFlag(FeatureFlags.tileViewEnabled.key, true)
        options.setFeatureFlag(FeatureFlags.pipEnabled.key, false)
        options.setFeatureFlag(FeatureFlags.notificationsEnabled.key, false)
        options.setFeatureFlag(FeatureFlags.meetingNameEnabled.key, false)
        options.setFeatureFlag(FeatureFlags.calendarEnabled.key, false)
        options.setFeatureFlag(FeatureFlags.inviteEnabled.key, false)
        // Launch the new activity with the given options. The launch() method takes care
        // of creating the required Intent and passing the options.
        JitsiMeetActivity.launch(applicationContext, options.build())
    }

    private fun getJitsiMeetUserInfo(): JitsiMeetUserInfo {
        return JitsiMeetUserInfo().apply {
            email = "h.kurd@vasl.ir"
            displayName = "Reza Amz"
            avatar = URL("https://media.isna.ir/content/1415692213741_555.jpg/3")
        }
    }

    private fun addNewMessage() {
        val input = findViewById<View>(R.id.editText_input) as EditText
        val recyclerView = findViewById<View>(R.id.recyclerView) as RecyclerView

        if (input.text.toString().trim().isEmpty())
            return

        val messageModel =
            MessageModel(
                PublicFunction.getMessageID(),
                "USERNAME",
                input.text.toString()
            )
        adapter.addNewMessage(messageModel)
        recyclerView.smoothScrollToPosition(adapter.itemCount)

        // clear input
        input.setText("")
    }

}