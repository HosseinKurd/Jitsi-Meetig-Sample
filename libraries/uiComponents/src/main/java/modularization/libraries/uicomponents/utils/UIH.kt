package modularization.libraries.uicomponents.utils

import android.content.Context

object UIH {
	fun getHeight(context: Context): Int {
		/*val screenDensity: Int =
			context.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK*/
		val metrics = context.resources.displayMetrics
		val wPX = metrics.widthPixels
		val hPX = metrics.heightPixels
		if (wPX < hPX) {
			return hPX
		} else if (wPX > hPX) {
			return wPX
		}
		return hPX
	}

	fun getWidth(context: Context): Int {
		/*val screenDensity: Int =
			context.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK*/
		val metrics = context.resources.displayMetrics
		val wPX = metrics.widthPixels
		val hPX = metrics.heightPixels
		if (wPX < hPX) {
			return wPX
		} else if (wPX > hPX) {
			return hPX
		}
		return wPX
	}
}