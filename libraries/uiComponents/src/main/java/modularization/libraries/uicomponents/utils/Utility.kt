package modularization.libraries.uicomponents.utils

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.widget.ImageView
import androidx.annotation.ColorRes
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

object Utility {

    /**
     * a function to getColor from colorResource
     * @return colorResInt type
     * */
    fun getColor(context: Context, @ColorRes resId: Int): Int = when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> context.resources.getColor(
            resId,
            null
        )
        else -> context.resources.getColor(resId)
    }

    fun getIntFromColor(_red: Int, _green: Int, _blue: Int): Int {
        /*val red = _red shl 16 and 0x00FF0000
        val green = _green shl 8 and 0x0000FF00
        val blue = _blue and 0x000000FF
        return -0x1000000 or red or green or blue*/
        return Color.rgb(_red, _green, _blue)
    }

    fun getIntFromColorWithAlpha(_red: Int, _green: Int, _blue: Int): Int {
        return Color.argb(114, _red, _green, _blue)
    }

    fun glide(path: String, v: ImageView) {

        Glide.with(v.context) //7
            .load(path)
            .apply(RequestOptions())
            .into(v)
    }

    fun decodeYUV420SP2(rgb: IntArray, yuv420sp: ByteArray, width: Int, height: Int) {
        val frameSize = width * height
        var j = 0
        var yp = 0
        while (j < height) {
            var uvp = frameSize + (j shr 1) * width
            var u = 0
            var v = 0
            var i = 0
            while (i < width) {
                var y = (0xff and yuv420sp[yp].toInt()) - 16
                if (y < 0) {
                    y = 0
                }
                if (i and 1 == 0) {
                    v = (0xff and yuv420sp[uvp++].toInt()) - 128
                    u = (0xff and yuv420sp[uvp++].toInt()) - 128
                }
                val y1192 = 1192 * y
                var r = y1192 + 1634 * v
                var g = y1192 - 833 * v - 400 * u
                var b = y1192 + 2066 * u
                if (r < 0) r = 0 else if (r > 262143) r = 262143
                if (g < 0) g = 0 else if (g > 262143) g = 262143
                if (b < 0) b = 0 else if (b > 262143) b = 262143
                rgb[yp] =
                    -0x1000000 or (r shl 6 and 0xff0000) or (g shr 2 and 0xff00) or (b shr 10 and 0xff)
                i++
                yp++
            }
            j++
        }
    }

    @Throws(NullPointerException::class, IllegalArgumentException::class)
    fun decodeYUV(out: IntArray?, fg: ByteArray?, width: Int, height: Int) {
        val sz = width * height
        if (out == null) throw NullPointerException("buffer out is null")
        if (out.size < sz) throw IllegalArgumentException(
            "buffer out size " + out.size
                    + " < minimum " + sz
        )
        if (fg == null) throw NullPointerException("buffer 'fg' is null")
        if (fg.size < sz) throw IllegalArgumentException(
            ("buffer fg size " + fg.size
                    + " < minimum " + (sz * 3 / 2))
        )
        var i: Int
        var j: Int
        var Y: Int
        var Cr = 0
        var Cb = 0
        j = 0
        while (j < height) {
            var pixPtr = j * width
            val jDiv2 = j shr 1
            i = 0
            while (i < width) {
                Y = fg[pixPtr].toInt()
                if (Y < 0) Y += 255
                if ((i and 0x1) != 1) {
                    val cOff = sz + (jDiv2 * width) + ((i shr 1) * 2)
                    Cb = fg[cOff].toInt()
                    if (Cb < 0) Cb += 127 else Cb -= 128
                    Cr = fg[cOff + 1].toInt()
                    if (Cr < 0) Cr += 127 else Cr -= 128
                }
                var R = Y + Cr + (Cr shr 2) + (Cr shr 3) + (Cr shr 5)
                if (R < 0) R = 0 else if (R > 255) R = 255
                var G = ((Y - (Cb shr 2)) + (Cb shr 4) + (Cb shr 5) - (Cr shr 1)
                        ) + (Cr shr 3) + (Cr shr 4) + (Cr shr 5)
                if (G < 0) G = 0 else if (G > 255) G = 255
                var B = Y + Cb + (Cb shr 1) + (Cb shr 2) + (Cb shr 6)
                if (B < 0) B = 0 else if (B > 255) B = 255
                out[pixPtr++] = -0x1000000 + (B shl 16) + (G shl 8) + R
                i++
            }
            j++
        }
    }
}