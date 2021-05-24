package modularization.libraries.dataSource.models

class FeatureAdapterItem(
    var featureFlag: FeatureFlag<Boolean>,
    var description: String
) {

    override fun toString(): String {
        val stringBuilder = StringBuilder()
        stringBuilder.append("{")
        stringBuilder.append(featureFlag.key)
        stringBuilder.append(":")
        stringBuilder.append(featureFlag.value)
        stringBuilder.append("}")
        return stringBuilder.toString()
    }
}