package modularization.libraries.utils

import android.util.Size
import kotlin.math.sign

class CompareSizeByArea : Comparator<Size> {
    override fun compare(o1: Size?, o2: Size?): Int {
        return (o1!!.width.toLong() * o2!!.height.toLong() / o2.width.toLong() * o1.height.toLong()).sign
    }
}