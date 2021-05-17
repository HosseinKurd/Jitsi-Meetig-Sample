package modularization.features.dashboard.preview

import android.annotation.SuppressLint
import android.hardware.camera2.*
import android.os.Build
import android.os.Bundle
import android.util.Size
import android.util.SparseIntArray
import android.view.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import modularization.features.dashboard.R
import modularization.libraries.uicomponents.baseClasses.BaseFragmentBinding
import modularization.libraries.utils.CameraPermissionHelper
import modularization.libraries.utils.CompareSizeByArea
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

        val ORIENTATIONS: SparseIntArray = SparseIntArray().apply {
            append(Surface.ROTATION_0, 0)
            append(Surface.ROTATION_90, 90)
            append(Surface.ROTATION_180, 180)
            append(Surface.ROTATION_270, 270)
        }

        fun sensorToDeviceOrientation(
            cameraCharacteristics: CameraCharacteristics,
            deviceOrientation: Int
        ): Int {
            val sensorOrientation = cameraCharacteristics[CameraCharacteristics.SENSOR_ORIENTATION]
            return (sensorOrientation!! + ORIENTATIONS[deviceOrientation] + 360) % 360
        }

        fun chooseOptimizeSize(
            choices: Array<Size>, width: Int, height: Int
        ): Size {
            val bigEnough = mutableListOf<Size>()
            for (option: Size in choices) {
                if (option.height == option.width * width / height &&
                    option.width >= width &&
                    option.height >= height
                ) {
                    bigEnough.add(option)
                }
            }
            if (bigEnough.size > 0) {
                return Collections.min(bigEnough, CompareSizeByArea())
            }
            return choices[0]
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
        if (!CameraPermissionHelper.hasCameraPermission(requireActivity())) {
            showMessage("Camera permission is needed to run this application")
            if (!CameraPermissionHelper.shouldShowRequestPermissionRationale(requireActivity())) {
                // Permission denied with checking "Do not ask again".
                CameraPermissionHelper.launchPermissionSettings(requireActivity())
            } else {
                cameraPermissionAllowed()
            }
        }
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
        return inflater.inflate(R.layout.fragment_join, container, false).apply {
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