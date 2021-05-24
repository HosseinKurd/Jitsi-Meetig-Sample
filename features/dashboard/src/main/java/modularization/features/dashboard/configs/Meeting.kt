package modularization.features.dashboard.configs

import modularization.libraries.dataSource.models.MeetingConfig

object Meeting {
    // var featureFlags = FeatureFlags
    var config: MeetingConfig = MeetingConfig(
        isAudioOnly = true,
        isAudioMuted = true,
        isVideoMuted = true,
        isWelcomePageEnabled = true
    )

}