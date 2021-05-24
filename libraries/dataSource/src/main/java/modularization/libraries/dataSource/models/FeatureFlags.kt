package modularization.libraries.dataSource.models

object FeatureFlags {
    /**
     * Flag indicating if add-people functionality should be enabled.
     * Default: enabled (true).
     */
    val addPeopleEnabled = FeatureFlag("add-people.enabled", true)

    /**
     * Flag indicating if the SDK should not require the audio focus.
     * Used by apps that do not use Jitsi audio.
     * Default: disabled (false)
     */
    val audioFocusDisabled = FeatureFlag("audio-focus.disabled", false)

    /**
     * Flag indicating if the audio mute button should be displayed.
     * Default: enabled (true).
     */
    val audioMuteEnabled = FeatureFlag("audio-mute.enabled", true)

    /**
     * Flag indicating that the Audio only button in the overflow menu is enabled.
     * Default: enabled (true).
     */
    val audioOnlyEnabled = FeatureFlag("audio-only.enabled", true)

    /**
     * Flag indicating if calendar integration should be enabled.
     * Default: enabled (true) on Android, auto-detected on iOS.
     */
    val calendarEnabled = FeatureFlag("calendar.enabled", true)

    /**
     * Flag indicating if call integration (CallKit on iOS, ConnectionService on Android)
     * should be enabled.
     * Default: enabled (true).
     */
    val callIntegrationEnabled = FeatureFlag("call-integration.enabled", true)

    /**
     * Flag indicating if close captions should be enabled.
     * Default: enabled (true).
     */
    val closeCaptionsEnabled = FeatureFlag("close-captions.enabled", true)

    /**
     * Flag indicating if conference timer should be enabled.
     * Default: enabled (true).
     */
    val conferenceTimerEnabled = FeatureFlag("conference-timer.enabled", true)

    /**
     * Flag indicating if chat should be enabled.
     * Default: enabled (true).
     */
    val chatEnabled = FeatureFlag("chat.enabled", true)

    /**
     * Flag indicating if the filmstrip should be enabled.
     * Default: enabled (true).
     */
    val filmstripEnabled = FeatureFlag("filmstrip.enabled", true)

    /**
     * Flag indicating if fullscreen (immersive) mode should be enabled.
     * Default: enabled (true).
     */
    val fullscreenEnabled = FeatureFlag("fullscreen.enabled", true)

    /**
     * Flag indicating if the Help button should be enabled.
     * Default: enabled (true).
     */
    val helpEnabled = FeatureFlag("help.enabled", true)

    /**
     * Flag indicating if invite functionality should be enabled.
     * Default: enabled (true).
     */
    val inviteEnabled = FeatureFlag("invite.enabled", true)

    /**
     * Flag indicating if kickout is enabled.
     * Default: enabled (true).
     */
    val kickOutEnabled = FeatureFlag("kick-out.enabled", true)

    /**
     * Flag indicating if live-streaming should be enabled.
     * Default: auto-detected.
     */
    val liveStreamingEnabled = FeatureFlag("live-streaming.enabled", true)

    /**
     * Flag indicating if lobby mode button should be enabled.
     * Default: enabled.
     */
    val lobbyModeEnabled = FeatureFlag("lobby-mode.enabled", true)

    /**
     * Flag indicating if displaying the meeting name should be enabled.
     * Default: enabled (true).
     */
    val meetingNameEnabled = FeatureFlag("meeting-name.enabled", true)

    /**
     * Flag indicating if the meeting password button should be enabled.
     * Note that this flag just decides on the button, if a meeting has a password
     * set, the password dialog will still show up.
     * Default: enabled (true).
     */
    val meetingPasswordEnabled = FeatureFlag("meeting-password.enabled", true)

    /**
     * Flag indicating if the notifications should be enabled.
     * Default: enabled (true).
     */
    val notificationsEnabled = FeatureFlag("notifications.enabled", true)

    /**
     * Flag indicating if the audio overflow menu button should be displayed.
     * Default: enabled (true).
     */
    val overflowMenuEnabled = FeatureFlag("overflow-menu.enabled", true)

    /**
     * Flag indicating if Picture-in-Picture should be enabled.
     * Default: auto-detected.
     */
    val pipEnabled = FeatureFlag("pip.enabled", true)

    /**
     * Flag indicating if raise hand feature should be enabled.
     * Default: enabled.
     */
    val raiseHandEnabled = FeatureFlag("raise-hand.enabled", true)

    /**
     * Flag indicating if recording should be enabled.
     * Default: auto-detected.
     */
    val recordingEnabled = FeatureFlag("recording.enabled", true)

    /**
     * Flag indicating the local and (maximum) remote video resolution. Overrides
     * the server configuration.
     * Default: (unset).
     */
    val resolution = FeatureFlag("resolution", "unset")

    /**
     * Flag indicating if the security options button should be enabled.
     * Default: enabled (true)
     */
    val securityOptionsEnabled = FeatureFlag("security-options.enabled", true)

    /**
     * Flag indicating if server URL change is enabled.
     * Default: enabled (true)
     */
    val serverUrlChangeEnabled = FeatureFlag("server-url-change.enabled", true)

    /**
     * Flag indicating if tile view feature should be enabled.
     * Default: enabled.
     */
    val tileViewEnabled = FeatureFlag("tile-view.enabled", true)

    /**
     * Flag indicating if the toolbox should be always be visible
     * Default: disabled (false).
     */
    val toolboxAlwaysVisible = FeatureFlag("toolbox.alwaysVisible", false)

    /**
     * Flag indicating if the toolbox should be enabled
     * Default: enabled.
     */
    val toolboxEnabled = FeatureFlag("toolbox.enabled", true)

    /**
     * Flag indicating if the video mute button should be displayed.
     * Default: enabled (true).
     */
    val videoMuteEnabled = FeatureFlag("video-mute.enabled", true)

    /**
     * Flag indicating if the video share button should be enabled
     * Default: enabled (true).
     */
    val videoShareEnabled = FeatureFlag("video-share.enabled", true)

    /**
     * Flag indicating if the welcome page should be enabled.
     * Default: disabled (false).
     */
    val WelcomePageEnabled = FeatureFlag("welcomepage.enabled", false)

}