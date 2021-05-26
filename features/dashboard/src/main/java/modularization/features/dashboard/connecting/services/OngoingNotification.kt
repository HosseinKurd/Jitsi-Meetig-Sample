package modularization.features.dashboard.connecting.services
/*

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build.VERSION
import androidx.annotation.StringRes
import androidx.core.app.NotificationCompat
import org.jitsi.meet.sdk.JitsiMeetOngoingConferenceService
import org.jitsi.meet.sdk.log.JitsiMeetLogger
import java.util.*

class OngoingNotification {
    private val TAG = OngoingNotification::class.java.simpleName
    private val CHANNEL_ID = "JitsiNotificationChannel"
    private val CHANNEL_NAME = "Ongoing Conference Notifications"
    val NOTIFICATION_ID = Random().nextInt(99999) + 10000
    private var startingTime = 0L

    fun OngoingNotification() {}

    fun createOngoingConferenceNotificationChannel() {
        if (VERSION.SDK_INT >= 26) {
            val context: Context? = ReactInstanceManagerHolder.getCurrentActivity()
            if (context == null) {
                JitsiMeetLogger.w(
                    "$TAG Cannot create notification channel: no current context",
                    *arrayOfNulls(0)
                )
            } else {
                val notificationManager =
                    context.getSystemService("notification") as NotificationManager
                var channel = notificationManager.getNotificationChannel("JitsiNotificationChannel")
                if (channel == null) {
                    channel = NotificationChannel(
                        "JitsiNotificationChannel",
                        "Ongoing Conference Notifications",
                        3
                    )
                    channel.enableLights(false)
                    channel.enableVibration(false)
                    channel.setShowBadge(false)
                    notificationManager.createNotificationChannel(channel)
                }
            }
        }
    }

    fun buildOngoingConferenceNotification(isMuted: Boolean): Notification? {
        val context: Context? = ReactInstanceManagerHolder.getCurrentActivity()
        return if (context == null) {
            JitsiMeetLogger.w(
                "$TAG Cannot create notification: no current context",
                *arrayOfNulls(0)
            )
            null
        } else {
            val notificationIntent = Intent(context, context.javaClass)
            val pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0)
            val builder = NotificationCompat.Builder(context, "JitsiNotificationChannel")
            if (startingTime == 0L) {
                startingTime = System.currentTimeMillis()
            }
            builder.setCategory("call")
                .setContentTitle(context.getString(string.ongoing_notification_title))
                .setContentText(context.getString(string.ongoing_notification_text)).setPriority(0)
                .setContentIntent(pendingIntent).setOngoing(true).setWhen(startingTime)
                .setUsesChronometer(true).setAutoCancel(false).setVisibility(1)
                .setOnlyAlertOnce(true).setSmallIcon(
                    context.resources.getIdentifier(
                        "ic_notification",
                        "drawable",
                        context.packageName
                    )
                )
            val hangupAction = createAction(
                context,
                JitsiMeetOngoingConferenceService.Action.HANGUP,
                string.ongoing_notification_action_hang_up
            )
            val toggleAudioAction =
                if (isMuted) JitsiMeetOngoingConferenceService.Action.UNMUTE else JitsiMeetOngoingConferenceService.Action.MUTE
            val toggleAudioTitle: Int =
                if (isMuted) string.ongoing_notification_action_unmute else string.ongoing_notification_action_mute
            val audioAction = createAction(context, toggleAudioAction, toggleAudioTitle)
            builder.addAction(hangupAction)
            builder.addAction(audioAction)
            builder.build()
        }
    }

    fun resetStartingtime() {
        startingTime = 0L
    }

    private fun createAction(
        context: Context,
        action: JitsiMeetOngoingConferenceService.Action,
        @StringRes titleId: Int
    ): NotificationCompat.Action {
        val intent = Intent(context, JitsiMeetOngoingConferenceService::class.java)
        intent.action = action.getName()
        val pendingIntent = PendingIntent.getService(context, 0, intent, 134217728)
        val title = context.getString(titleId)
        return NotificationCompat.Action(0, title, pendingIntent)
    }
}*/
