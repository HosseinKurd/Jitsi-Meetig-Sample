package modularization.features.dashboard.meet

import android.annotation.SuppressLint
import com.facebook.react.modules.core.PermissionListener
import modularization.libraries.utils.Logger
import org.jitsi.meet.sdk.JitsiMeetActivityInterface
import org.jitsi.meet.sdk.JitsiMeetFragment

class MeetFrg : JitsiMeetFragment(), JitsiMeetActivityInterface {

    private val TAG = javaClass.simpleName + "_TAG"

    companion object {
        @SuppressLint("StaticFieldLeak")
        var instance: MeetFrg? = null
            get() {
                if (field == null) {
                    field = MeetFrg()
                }
                return field
            }
    }

    override fun onResume() {
        Logger.w(TAG, "onResume ...")
        super.onResume()
    }

    override fun checkPermission(p0: String?, p1: Int, p2: Int): Int {
        return 1
    }

    override fun checkSelfPermission(p0: String?): Int {
        return 1
    }

    override fun requestPermissions(p0: Array<out String>?, p1: Int, p2: PermissionListener?) {

    }
}