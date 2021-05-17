package modularization.features.dashboard.preview

import android.annotation.SuppressLint
import android.hardware.camera2.*
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import modularization.features.dashboard.R
import modularization.libraries.uicomponents.baseClasses.BaseFragmentBinding
import modularization.libraries.utils.CameraPermissionHelper
import org.jitsi.meet.sdk.JitsiMeetActivityDelegate
import pl.aprilapps.easyphotopicker.*
import java.util.*


class JoinFrg : BaseFragmentBinding<CameraViewDataBinding>(), View.OnClickListener {

    companion object {
        @SuppressLint("StaticFieldLeak")
        var instance: JoinFrg? = null
            get() {
                if (field == null) {
                    field = JoinFrg()
                }
                return field
            }
    }

    /** ViewModel related to Fragment*/
    private lateinit var viewModel: JoinViewModel

    /**
     * Check Requested Permissions Result
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        JitsiMeetActivityDelegate.onRequestPermissionsResult(requestCode, permissions, grantResults)
        /*if (!CameraPermissionHelper.hasCameraPermission(requireActivity())) {
            showMessage("Camera permission is needed to run this application")
            if (!CameraPermissionHelper.shouldShowRequestPermissionRationale(requireActivity())) {
                // Permission denied with checking "Do not ask again".
                CameraPermissionHelper.launchPermissionSettings(requireActivity())
            } else {
                cameraPermissionAllowed()
            }
        }*/
    }

    private fun cameraPermissionAllowed() {

    }

    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_join
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(JoinViewModel::class.java)
        return inflater.inflate(layoutResourceId, container, false).apply {
            findViewById<View>(R.id.btn_submit).setOnClickListener(this@JoinFrg)
            viewModel.liveData.observe(viewLifecycleOwner, Observer {
                val item = it ?: return@Observer
            })
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!CameraPermissionHelper.hasCameraPermission(requireActivity())) {
                    CameraPermissionHelper.requestCameraPermission(requireActivity())
                    return this
                }
            }
        }
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.btn_submit) {
            viewModel.submit(requireContext())
        }
    }

}