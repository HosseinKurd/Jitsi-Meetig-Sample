package modularization.features.dashboard.configs

import modularization.libraries.dataSource.models.FeatureAdapterItem
import modularization.libraries.dataSource.models.MeetingConfig

object Meeting {

    // var featureFlags = FeatureFlags
    var features = mutableListOf<FeatureAdapterItem>()
   /* var config: MeetingConfig = MeetingConfig(
        isAudioOnly = true,
        isAudioMuted = true,
        isVideoMuted = true,
        isWelcomePageEnabled = true
    )*/

    override fun toString(): String {
        val stringBuilder = StringBuilder()
        features.forEach { value ->
            if (stringBuilder.isNotEmpty()){
                stringBuilder.append(" , ")
            }
            stringBuilder.append("{")
            stringBuilder.append(value.featureFlag.key)
            stringBuilder.append(":")
            stringBuilder.append(value.featureFlag.value)
            stringBuilder.append("}")
        }
        return stringBuilder.toString()
    }
}