package modularization.libraries.dataSource.models

data class MeetingConfig(
    var isAudioOnly: Boolean,
    var isAudioMuted: Boolean,
    var isVideoMuted: Boolean,
    var isWelcomePageEnabled: Boolean
)