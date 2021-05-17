package modularization.libraries.dataSource.models

import modularization.libraries.uicomponents.utils.Utility


data class ColorValue(val red: Int, val green: Int, val blue: Int, val rgb: Int) {

    class Builder(val red: Int, val green: Int, val blue: Int) {

        fun build(): ColorValue {
            return ColorValue(
                red = red, green = green, blue = blue, rgb = Utility.getIntFromColor(
                    red,
                    green,
                    blue
                )
            )
        }

    }
}