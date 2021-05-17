package modularization.libraries.utils

import android.util.Log

object Logger {

	private const val MAX_LOG_SIZE = 1000

	fun i(tag: String, msg: String) {
		Log.i(tag, msg)
		for (i in 0 until msg.length / MAX_LOG_SIZE) {
			val start = i * MAX_LOG_SIZE
			var end = (i + 1) * MAX_LOG_SIZE
			end = if (end > msg.length) msg.length else end
			Log.i(tag, msg.substring(start, end))
		}
	}

	fun d(tag: String, msg: String) {
		Log.d(tag, msg)
		for (i in 0 until msg.length / MAX_LOG_SIZE) {
			val start = i * MAX_LOG_SIZE
			var end = (i + 1) * MAX_LOG_SIZE
			end = if (end > msg.length) msg.length else end
			Log.d(tag, msg.substring(start, end))
		}
	}

	fun w(tag: String, msg: String) {
		Log.w(tag, msg)
		for (i in 0 until msg.length / MAX_LOG_SIZE) {
			val start = i * MAX_LOG_SIZE
			var end = (i + 1) * MAX_LOG_SIZE
			end = if (end > msg.length) msg.length else end
			Log.w(tag, msg.substring(start, end))
		}
	}

	fun e(tag: String, msg: String, throwable: Throwable) {
		Log.e(tag, msg)
		for (i in 0 until msg.length / MAX_LOG_SIZE) {
			val start = i * MAX_LOG_SIZE
			var end = (i + 1) * MAX_LOG_SIZE
			end = if (end > msg.length) msg.length else end
			Log.e(tag, msg.substring(start, end))
		}
		Log.e(tag, "throwable: ", throwable)
	}

	fun e(tag: String, msg: String) {
		Log.e(tag, msg)
		for (i in 0 until msg.length / MAX_LOG_SIZE) {
			val start = i * MAX_LOG_SIZE
			var end = (i + 1) * MAX_LOG_SIZE
			end = if (end > msg.length) msg.length else end
			Log.e(tag, msg.substring(start, end))
		}
	}
}