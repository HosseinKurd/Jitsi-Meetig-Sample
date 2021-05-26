package modularization.features.dashboard.connecting

/*
import android.media.AudioManager
import com.facebook.react.bridge.*
import org.jitsi.meet.sdk.AudioDeviceHandlerConnectionService
import org.jitsi.meet.sdk.AudioDeviceHandlerGeneric
import org.jitsi.meet.sdk.AudioModeModule
import org.jitsi.meet.sdk.ReactInstanceManagerHolder
import org.jitsi.meet.sdk.log.JitsiMeetLogger
import java.util.*
import java.util.concurrent.ExecutorService

class AudioModeModule : ReactContextBaseJavaModule() {
    val NAME = "AudioMode"
    val DEFAULT = 0
    val AUDIO_CALL = 1
    val VIDEO_CALL = 2
    val TAG = "AudioMode"
    private var supportsConnectionService = false
    private var useConnectionService_ = false
    private var audioManager: AudioManager? = null
    private var audioDeviceHandler: AudioDeviceHandlerInterface? = null
    private var executor: ExecutorService? = null
    private var mode = -1
    val DEVICE_BLUETOOTH = "BLUETOOTH"
    val DEVICE_EARPIECE = "EARPIECE"
    val DEVICE_HEADPHONES = "HEADPHONES"
    val DEVICE_SPEAKER = "SPEAKER"
    private val DEVICE_CHANGE_EVENT = "org.jitsi.meet:features/audio-mode#devices-update"
    private var availableDevices: MutableSet<String?> = HashSet<Any?>()
    private var selectedDevice: String? = null
    private var userSelectedDevice: String? = null

    fun useConnectionService(): Boolean {
        return supportsConnectionService && useConnectionService_
    }

    fun AudioModeModule(reactContext: ReactApplicationContext) {
        super(reactContext)
        audioManager = reactContext.getSystemService("audio") as AudioManager
    }

    override fun getConstants(): Map<String?, Any?>? {
        val constants: MutableMap<String?, Any?> = HashMap<Any?, Any?>()
        constants["DEVICE_CHANGE_EVENT"] = "org.jitsi.meet:features/audio-mode#devices-update"
        constants["AUDIO_CALL"] = 1
        constants["DEFAULT"] = 0
        constants["VIDEO_CALL"] = 2
        return constants
    }

    private fun notifyDevicesChanged() {
        runInAudioThread(Runnable {
            val data = Arguments.createArray()
            val hasHeadphones = availableDevices.contains("HEADPHONES")
            val var3: Iterator<*> = availableDevices.iterator()
            while (true) {
                var device: String
                do {
                    if (!var3.hasNext()) {
                        ReactInstanceManagerHolder.emitEvent(
                            "org.jitsi.meet:features/audio-mode#devices-update",
                            data
                        )
                        JitsiMeetLogger.i("AudioMode Updating audio device list", *arrayOfNulls(0))
                        return@Runnable
                    }
                    device = var3.next() as String
                } while (hasHeadphones && device == "EARPIECE")
                val deviceInfo = Arguments.createMap()
                deviceInfo.putString("type", device)
                deviceInfo.putBoolean("selected", device == selectedDevice)
                data.pushMap(deviceInfo)
            }
        })
    }

    override fun getName(): String? {
        return "AudioMode"
    }

    override fun initialize() {
        this.runInAudioThread { this@AudioModeModule.setAudioDeviceHandler() }
    }

    private fun setAudioDeviceHandler() {
        if (this.audioDeviceHandler != null) {
            this.audioDeviceHandler!!.stop()
        }
        if (useConnectionService()) {
            this.audioDeviceHandler = AudioDeviceHandlerConnectionService(this.audioManager)
        } else {
            this.audioDeviceHandler = AudioDeviceHandlerGeneric(this.audioManager)
        }
        this.audioDeviceHandler!!.start(this)
    }

    fun runInAudioThread(runnable: Runnable?) {
        executor!!.execute(runnable)
    }

    @ReactMethod
    fun setAudioDevice(device: String) {
        this.runInAudioThread {
            if (!this@AudioModeModule.availableDevices.contains(device)) {
                JitsiMeetLogger.w(
                    "AudioMode Audio device not available: $device",
                    *arrayOfNulls(0)
                )
                this@AudioModeModule.userSelectedDevice = null
            } else {
                if (this@AudioModeModule.mode != -1) {
                    JitsiMeetLogger.i(
                        "AudioMode User selected device set to: $device",
                        *arrayOfNulls(0)
                    )
                    this@AudioModeModule.userSelectedDevice = device
                    this@AudioModeModule.updateAudioRoute(this@AudioModeModule.mode, false)
                }
            }
        }
    }

    @ReactMethod
    fun setMode(mode: Int, promise: Promise) {
        if (mode != 0 && mode != 1 && mode != 2) {
            promise.reject("setMode", "Invalid audio mode $mode")
        } else {
            val currentActivity = this.currentActivity
            if (currentActivity != null) {
                if (mode == 0) {
                    currentActivity.volumeControlStream = -2147483648
                } else {
                    currentActivity.volumeControlStream = 0
                }
            }
            this.runInAudioThread {
                var success: Boolean
                try {
                    success = this@AudioModeModule.updateAudioRoute(mode, false)
                } catch (var3: Throwable) {
                    success = false
                    JitsiMeetLogger.e(
                        var3,
                        "AudioMode Failed to update audio route for mode: $mode",
                        *arrayOfNulls(0)
                    )
                }
                if (success) {
                    this@AudioModeModule.mode = mode
                    promise.resolve(null as Any?)
                } else {
                    promise.reject("setMode", "Failed to set audio mode to $mode")
                }
            }
        }
    }

    @ReactMethod
    fun setUseConnectionService(use: Boolean) {
        this.runInAudioThread {
            AudioModeModule.useConnectionService_ = use
            this@AudioModeModule.setAudioDeviceHandler()
        }
    }

    private fun updateAudioRoute(mode: Int, force: Boolean): Boolean {
        JitsiMeetLogger.i("AudioMode Update audio route for mode: $mode", *arrayOfNulls(0))
        return if (!this.audioDeviceHandler!!.setMode(mode)) {
            false
        } else if (mode == 0) {
            this.selectedDevice = null
            this.userSelectedDevice = null
            this.notifyDevicesChanged()
            true
        } else {
            val bluetoothAvailable = this.availableDevices.contains("BLUETOOTH")
            val headsetAvailable = this.availableDevices.contains("HEADPHONES")
            var audioDevice: String
            audioDevice = if (bluetoothAvailable) {
                "BLUETOOTH"
            } else if (headsetAvailable) {
                "HEADPHONES"
            } else {
                "SPEAKER"
            }
            if (this.userSelectedDevice != null && this.availableDevices.contains(this.userSelectedDevice)) {
                audioDevice = this.userSelectedDevice!!
            }
            if (!force && this.selectedDevice != null && this.selectedDevice == audioDevice) {
                true
            } else {
                this.selectedDevice = audioDevice
                JitsiMeetLogger.i("AudioMode Selected audio device: $audioDevice", *arrayOfNulls(0))
                this.audioDeviceHandler!!.setAudioRoute(audioDevice)
                this.notifyDevicesChanged()
                true
            }
        }
    }

    fun getSelectedDevice(): String? {
        return this.selectedDevice
    }

    fun resetSelectedDevice() {
        this.selectedDevice = null
        this.userSelectedDevice = null
    }

    fun addDevice(device: String?) {
        this.availableDevices.add(device)
        this.resetSelectedDevice()
    }

    fun removeDevice(device: String?) {
        this.availableDevices.remove(device)
        this.resetSelectedDevice()
    }

    fun replaceDevices(devices: MutableSet<String?>) {
        this.availableDevices = devices
        this.resetSelectedDevice()
    }

    fun updateAudioRoute() {
        if (this.mode != -1) {
            this.updateAudioRoute(this.mode, false)
        }
    }

    fun resetAudioRoute() {
        if (this.mode != -1) {
            this.updateAudioRoute(this.mode, true)
        }
    }

    static
    {
        supportsConnectionService = VERSION.SDK_INT >= 26
        useConnectionService_ = supportsConnectionService
        executor = Executors.newSingleThreadExecutor()
    }

    internal interface AudioDeviceHandlerInterface {
        fun start(var1: AudioModeModule?)
        fun stop()
        fun setAudioRoute(var1: String?)
        fun setMode(var1: Int): Boolean
    }
}*/
