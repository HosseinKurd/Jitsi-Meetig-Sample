package modularization.features.dashboard.connecting.services
/*

import com.facebook.react.bridge.ReadableMap
import java.util.*
import java.util.Collections.synchronizedSet

class OngoingConferenceTracker {
    private val instance = OngoingConferenceTracker()
    private val CONFERENCE_WILL_JOIN = "CONFERENCE_WILL_JOIN"
    private val CONFERENCE_TERMINATED = "CONFERENCE_TERMINATED"
    private val listeners: MutableSet<Any?>? =
        synchronizedSet(
            HashSet<Any?>()
        )
    private var currentConference: String? = null

    fun OngoingConferenceTracker() {}

    fun getInstance() {
        return instance
    }

    @Synchronized
    fun getCurrentConference(): String? {
        return currentConference
    }

    @Synchronized
    fun onExternalAPIEvent(name: String, data: ReadableMap) {
        if (data.hasKey("url")) {
            val url = data.getString("url")
            if (url != null) {
                var var5: Byte = -1
                when (name.hashCode()) {
                    -940468954 -> if (name == "CONFERENCE_TERMINATED") {
                        var5 = 1
                    }
                    895709716 -> if (name == "CONFERENCE_WILL_JOIN") {
                        var5 = 0
                    }
                }
                when (var5) {
                    0.toByte() -> {
                        currentConference = url
                        updateListeners()
                    }
                    1.toByte() -> if (url == currentConference) {
                        currentConference = null
                        updateListeners()
                    }
                }
            }
        }
    }

    fun addListener(listener: OngoingConferenceListener?) {
        listeners?.add(listener)
    }

    fun removeListener(listener: OngoingConferenceListener?) {
        listeners?.remove(listener)
    }

    private fun updateListeners() {
        synchronized(listeners!!) {
            val var2: Iterator<*> = listeners.iterator()
            while (var2.hasNext()) {
                val listener = var2.next() as OngoingConferenceListener
                listener.onCurrentConferenceChanged(currentConference)
            }
        }
    }

    interface OngoingConferenceListener {
        fun onCurrentConferenceChanged(var1: String?)
    }
}*/
