package modularization.features.dashboard.preview

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.SurfaceTexture
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.GradientDrawable
import android.hardware.camera2.*
import android.hardware.camera2.params.StreamConfigurationMap
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.util.Size
import android.util.SparseIntArray
import android.view.*
import android.widget.ImageView
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.vasl.magicalcolorpicker.utils.addLinearSnapHelper
import com.vasl.magicalcolorpicker.utils.copyToClipboard
import modularization.features.dashboard.R
import modularization.libraries.dataSource.models.ColorValue
import modularization.libraries.uicomponents.baseClasses.BaseFragmentBinding
import modularization.libraries.uicomponents.baseClasses.RcvBaseAdapter
import modularization.libraries.uicomponents.utils.UIH
import modularization.libraries.uicomponents.utils.Utility
import modularization.libraries.utils.CameraPermissionHelper
import modularization.libraries.utils.CompareSizeByArea
import modularization.libraries.utils.Logger
import org.slf4j.helpers.Util
import pl.aprilapps.easyphotopicker.*
import java.util.*


class CameraFrg : BaseFragmentBinding<CameraViewDataBinding>(), View.OnTouchListener,
    TextureView.SurfaceTextureListener,
    View.OnClickListener {

    companion object {
        @SuppressLint("StaticFieldLeak")
        var instance: CameraFrg? = null
            get() {
                if (field == null) {
                    field = CameraFrg()
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
    private lateinit var viewModel: CameraViewModel

    /** Recent Picked [ColorValue]*/
    private var colorValue: ColorValue? = null

    /** Toolbar*/
    private var toolbar: Toolbar? = null

    /** RecyclerView to show Selected Colors*/
    private var recyclerView: RecyclerView? = null

    /** ImageView to pick Selected Colors*/
    private var imgPick: ImageView? = null

    /** ToggleButton to toggle between camera and gallery*/
    private var toggleButton: ToggleButton? = null

    /** ImageView to preview selected image from gallery*/
    private var imgPreview: ImageView? = null

    /** ImageView to mark center/selected coordination of camera preview*/
    private var imgCenter: ImageView? = null

    /** View for displaying the camera preview.  */
    private var mPreview: TextureView? = null

    /** Size of Camera's Preview*/
    private var mPreviewSzie: Size? = null

    /** To build CaptureRequest for camera preview*/
    private var mCaptureRequestBuilder: CaptureRequest.Builder? = null

    /** Background Handler Thread*/
    private var mBackgroundHandlerThread: HandlerThread? = null

    /** Background Handler*/
    private var mBackgroundHandler: Handler? = null

    /** The specific camera device that we're using.  */
    private var mCameraDevice: CameraDevice? = null

    /** The specific camera device ID that we're using.  */
    private var mCameraId: String? = null

    /**
     * Calledbacks invoked upon state changes in our `CameraDevice`.
     *
     *These are run on
     * `mBackgroundThread`.
     */
    private val mCameraStateCallback: CameraDevice.StateCallback =
        object : CameraDevice.StateCallback() {
            override fun onOpened(camera: CameraDevice) {
                Logger.i(TAG, "Successfully opened camera")
                mCameraDevice = camera
                startPreview()
            }

            override fun onDisconnected(camera: CameraDevice) {
                Logger.e(TAG, "Camera was disconnected")
                camera.close()
                mCameraDevice = null
            }

            override fun onError(camera: CameraDevice, error: Int) {
                Logger.e(TAG, "State error on device '" + camera.id + "': code " + error)
                mCameraDevice = null
            }
        }

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
                connectCamera()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        beginPreview()
    }

    override fun onPause() {
        stopPreview()
        super.onPause()
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_camera
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(CameraViewModel::class.java)
        return inflater.inflate(R.layout.fragment_camera, container, false).apply {
            toolbar = findViewById(R.id.toolbar)
            (activity as AppCompatActivity).setSupportActionBar(toolbar)
            (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
            (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
            // toolbar!!.title = getString(R.string.app_name)
            // toolbar!!.setTitleTextColor(Utility.getColor(context, R.color.white))
            imgCenter = findViewById(R.id.img_pointer)
            imgPreview = findViewById(R.id.img_preview)
            imgPreview!!.setOnTouchListener(this@CameraFrg)
            toggleButton = findViewById(R.id.img_toggle)
            mPreview = findViewById(R.id.textureView)
            mPreview!!.apply {
                setOnTouchListener(this@CameraFrg)
                surfaceTextureListener = this@CameraFrg
            }
            recyclerView = findViewById(R.id.recyclerView)
            recyclerView?.apply {
                setHasFixedSize(true)
                addLinearSnapHelper()
                adapter = ColorAdapter(requireContext(), mutableListOf()).also {
                    it.onItemClickListener =
                        object : RcvBaseAdapter.OnItemClickListener<ColorValue> {
                            override fun onClicked(actionId: Int, position: Int, item: ColorValue) {
                                Logger.w(TAG, "position : $position , item : $item")
                                if (actionId == ColorAdapter.PRESSED) {
                                    copyColorToClipboard(item)
                                } else if (actionId == ColorAdapter.LONG_PRESSED) {
                                    Logger.w(TAG, "Long Pressed , item 1 : $item")
                                    recyclerView?.adapter?.apply {
                                        if (this is ColorAdapter) {
                                            this.remove(position = position)
                                            val hexColor = java.lang.String.format(
                                                "#%06X",
                                                0xFFFFFF and item.rgb
                                            )
                                            showMessage("Color removed : $hexColor")
                                        }
                                    }
                                }
                            }

                        }
                }
            }
            findViewById<ImageView>(R.id.img_share).setOnClickListener(this@CameraFrg)
            findViewById<ImageView>(R.id.img_copy).setOnClickListener(this@CameraFrg)
            imgPick = findViewById(R.id.img_pick)
            imgPick?.setOnClickListener(this@CameraFrg)
            viewModel.colorValue.observe(viewLifecycleOwner, Observer {
                val item = it ?: return@Observer
                colorValue = item
                val strokeWidth = 15
                val strokeColor = Utility.getColor(context, R.color.teal)
                val fillColor = Color.parseColor(
                    java.lang.String.format(
                        "#%06X",
                        0xFFFFFF and colorValue!!.rgb
                    )
                )
                val gradientDrawable = GradientDrawable()
                gradientDrawable.setColor(fillColor)
                gradientDrawable.shape = GradientDrawable.OVAL
                gradientDrawable.setStroke(strokeWidth, strokeColor)
                imgPick!!.setImageDrawable(gradientDrawable)
                /*imgPick!!.setColorFilter(Color.parseColor(
                    java.lang.String.format(
                        "#%06X",
                        0xFFFFFF and colorValue!!.rgb
                    )
                ), android.graphics.PorterDuff.Mode.SRC_IN)*/
                /*toolbar!!.setBackgroundColor(
                    Color.parseColor(
                        java.lang.String.format(
                            "#%06X",
                            0xFFFFFF and colorValue!!.rgb
                        )
                    )
                )*/
            })
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getImageFromGallery()
    }

    private val easyImage: EasyImage
        get() = EasyImage.Builder(requireContext())
            .setChooserType(ChooserType.CAMERA_AND_GALLERY)
            .setCopyImagesToPublicGalleryFolder(false)
            .allowMultiple(false)
            .build()

    private fun getImageFromGallery() {
        toggleButton?.setOnCheckedChangeListener { buttonView, isChecked ->
            imgPreview!!.isVisible = isChecked
            mPreview!!.isVisible = !isChecked
            // imgCenter!!.isVisible = !isChecked
            val constraintParams = imgCenter!!.layoutParams as ConstraintLayout.LayoutParams
            constraintParams.topToTop = 0
            constraintParams.bottomToBottom = 0
            constraintParams.startToStart = 0
            constraintParams.endToEnd = 0
            /*imgCenter!!.apply {
                val constraintLayout: ConstraintLayout = findViewById(R.id.preview_container)
                x = constraintLayout.x + (constraintLayout.width / 2)
                y = constraintLayout.y + (constraintLayout.height / 2)
            }*/
            imgCenter!!.layoutParams = constraintParams
            if (isChecked) {
                // stopPreview()
                toggleButton!!.setBackgroundResource(R.drawable.vtr_camera)
                easyImage.openGallery(this@CameraFrg)
                /*registerForActivityResult(
                    ActivityResultContracts.StartActivityForResult(),
                    ActivityResultCallback<ActivityResult> { result ->
                        if (result.resultCode == Activity.RESULT_OK) {
                            //Image Uri will not be null for RESULT_OK
                            val fileUri = result.data?.data
                            // imgProfile.setImageURI(fileUri)
                        } else if (result.resultCode == Activity.RESULT_CANCELED) {
                            Logger.e(TAG, "registerForActivityResult --> Operation Canceled!")
                            imgToggle!!.toggle()
                        }
                    })*/
            } else {
                // beginPreview()
                toggleButton!!.setBackgroundResource(R.drawable.vtr_gallery)
            }
        }
    }

    private fun beginPreview() {
        startBackgroundThread()
        if (mPreview!!.isAvailable) {
            setupCamera(mPreview!!.width, mPreview!!.height)
            connectCamera()
        } else {
            mPreview!!.surfaceTextureListener = this@CameraFrg
        }
    }

    private fun stopPreview() {
        closeCamera()
        stopBackgroundThread()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        easyImage.handleActivityResult(
            requestCode,
            resultCode,
            data,
            requireActivity(),
            object : DefaultCallback() {
                override fun onMediaFilesPicked(
                    imageFiles: Array<MediaFile>,
                    source: MediaSource
                ) {
                    Logger.w(
                        TAG,
                        "registerForActivityResult --> onMediaFilesPicked ${imageFiles[0].file.absolutePath}"
                    )
                    Utility.glide(imageFiles[0].file.absolutePath, imgPreview!!)
                }

                override fun onImagePickerError(error: Throwable, source: MediaSource) {
                    //Some error handling
                    error.printStackTrace()
                    Logger.e(
                        TAG,
                        "registerForActivityResult --> onImagePickerError ${error.message}",
                        error
                    )
                    toggleButton!!.toggle()
                }

                override fun onCanceled(source: MediaSource) {
                    //Not necessary to remove any files manually anymore
                    Logger.e(TAG, "registerForActivityResult --> onCanceled")
                    toggleButton!!.toggle()
                }
            })
    }

    private fun onTouched(event: MotionEvent?) {
        imgCenter!!.layoutParams
        imgCenter!!.apply {
            x = event!!.x + imgPreview!!.x - (width / 2)
            y = event.y + imgPreview!!.y - (height / 2)
        }
        Logger.w(
            TAG,
            "onTouched --> event x : ${event!!.x} , event y : ${event.y} , UIH W : ${
                UIH.getWidth(requireContext())
            } , UIH H : ${UIH.getHeight(requireContext())} "
        )
        onTouched(
            event.x.toInt() /*- imgPreview!!.x.toInt()*/,
            event.y.toInt() /*- imgPreview!!.y.toInt()*/
        )
    }

    private fun onTouched(x: Int, y: Int) {
        val bitmap: Bitmap = if (mPreview!!.isVisible) {
            mPreview!!.bitmap!!
        } else {
            (imgPreview!!.drawable as BitmapDrawable).bitmap
        }
        val view: View = if (mPreview!!.isVisible) {
            mPreview!!
        } else {
            imgPreview!!
        }
        /*Logger.w(TAG, "Pixel : $x x $y")*/
        if ((x !in 0..bitmap.width - 1 || y !in 0..bitmap.height - 1)
        // || (x !in (view.x + 1)..(view.x + view.width - 1) || x !in view.y + 1..(view.y + view.height - 1))
        ) {
            Logger.e(
                TAG,
                "IllegalArgumentException: x must be in 0..bitmap.width() y must be in 0..bitmap.height()"
            )
            return
        }
        val pixel: Int = bitmap.getPixel(
            x,
            y
        )
        //then do what you want with the pixel data, e.g
        val red: Int = Color.red(pixel)
        val blue: Int = Color.blue(pixel)
        val green: Int = Color.green(pixel)
        /*val hex = java.lang.String.format(
            "#%02x%02x%02x",
            red,
            green,
            blue
        )
        Logger.w(
            TAG, "onTouched --> red : $red , blue : $blue , green : $green , ${
                Utility.getIntFromColor(
                    red,
                    green,
                    blue
                )
            } , hex : $hex"
        )*/
        viewModel.setColorValue(
            ColorValue.Builder(
                red = red, green = green, blue = blue
            ).build()
        )


        /*val rectShape = RoundRectShape(
            floatArrayOf(
                10f,
                10f,
                10f,
                10f,
                10f,
                10f,
                10f,
                10f
            ), null, null
        )

        val layer1 = ShapeDrawable(rectShape)
        layer1.paint.color = Utility.getIntFromColor(
            red,
            green,
            blue)
        layer1.paint.style = Paint.Style.FILL
        layer1.paint.isAntiAlias = true
        layer1.paint.flags = Paint.ANTI_ALIAS_FLAG

        val layer2 = ShapeDrawable(rectShape)
        layer2.paint.color = Utility.getIntFromColorWithAlpha(
            red,
            green,
            blue)
        layer2.paint.style = Paint.Style.STROKE
        layer2.paint.isAntiAlias = true
        layer2.paint.flags = Paint.ANTI_ALIAS_FLAG

        val layers = arrayOf<Drawable>(layer1, layer2)
        val layerDrawable = LayerDrawable(layers)
        imgPick?.background = layerDrawable*/
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        if (v?.id == R.id.img_preview) {
            onTouched(event = event)
            return true
        }
        return false
    }

    override fun onSurfaceTextureAvailable(surface: SurfaceTexture, width: Int, height: Int) {
        Logger.i(TAG, "onSurfaceTextureAvailable ...")
        setupCamera(width, height)
        connectCamera()
    }

    override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture, width: Int, height: Int) {
        Logger.i(TAG, "onSurfaceTextureSizeChanged ...")
    }

    override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean {
        Logger.i(TAG, "onSurfaceTextureDestroyed ...")
        return true
    }

    override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {
        // Logger.i(TAG, "onSurfaceTextureUpdated ...")
        if (mPreview!!.isVisible) {
            onTouched(
                (mPreview!!.width / 2),
                (mPreview!!.height / 2)
                /*(imgCenter!!.x + (imgCenter!!.width / 2)).toInt(),
                (imgCenter!!.y + (imgCenter!!.height / 2)).toInt()*/
            )
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.img_share -> {
                val item = colorValue ?: return
                share(item)
            }
            R.id.img_copy -> {
                val item = colorValue ?: return
                copyColorToClipboard(item)
            }
            R.id.img_pick -> {
                if (recyclerView?.adapter != null) {
                    recyclerView?.adapter?.apply {
                        if (this is ColorAdapter) {
                            this.addItemToFirst(
                                colorValue
                            )
                            notifyDataSetChanged()
                        }
                    }
                }
            }
        }
    }

    private fun copyColorToClipboard(item : ColorValue) {
        val hexColor = java.lang.String.format(
            "#%06X",
            0xFFFFFF and item.rgb
        )
        hexColor.copyToClipboard(requireContext(), "Color")
        showMessage("Color $hexColor Copied to clipboard")
    }

    private fun share(item : ColorValue) {
        val hex = java.lang.String.format(
            "#%02x%02x%02x",
            item.red,
            item.green,
            item.blue
        )
        val intent = Intent(Intent.ACTION_SEND)
        intent.setType("text/plain")
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share_subject))
        intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_text, hex))
        startActivity(Intent.createChooser(intent, getString(R.string.share_using)));
    }

    private fun closeCamera() {
        if (mCameraDevice != null) {
            mCameraDevice?.close()
            mCameraDevice = null
        }
    }

    private fun setupCamera(width: Int, height: Int) {
        val mCameraManager =
            requireActivity().getSystemService(Context.CAMERA_SERVICE) as CameraManager?
        for (cameraId: String in mCameraManager!!.cameraIdList) {
            try {
                val cameraCharacteristics = mCameraManager.getCameraCharacteristics(
                    cameraId
                )
                if (cameraCharacteristics.get(CameraCharacteristics.LENS_FACING) ==
                    CameraCharacteristics.LENS_FACING_BACK
                ) {
                    Logger.i(TAG, "setupCamera --> Found a back-facing camera")
                    val streamConfigurationMap: StreamConfigurationMap? =
                        cameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)
                    val deviceOrientation: Int =
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                            requireActivity().display?.rotation!!
                        } else {
                            requireActivity().windowManager.defaultDisplay.rotation
                        }
                    val totalOrientation: Int =
                        sensorToDeviceOrientation(cameraCharacteristics, deviceOrientation)
                    val swoapRotaion: Boolean = totalOrientation == 90 || totalOrientation == 270

                    val rotationWidth = if (swoapRotaion) {
                        height
                    } else {
                        width
                    }
                    val rotationHeight = if (swoapRotaion) {
                        width
                    } else {
                        height
                    }
                    mPreviewSzie =
                        chooseOptimizeSize(
                            streamConfigurationMap!!.getOutputSizes(SurfaceTexture::class.java),
                            rotationWidth,
                            rotationHeight
                        )
                    mCameraId = cameraId
                    return
                }
            } catch (ex: CameraAccessException) {
                Logger.e(
                    TAG,
                    "setupCamera --> Failed to create a capture session , ${ex.message}",
                    ex
                )
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun connectCamera() {
        val mCameraManager =
            requireContext().getSystemService(Context.CAMERA_SERVICE) as CameraManager?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!CameraPermissionHelper.hasCameraPermission(requireActivity())) {
                CameraPermissionHelper.requestCameraPermission(requireActivity())
                return
            }
        }
        mCameraManager?.openCamera(mCameraId!!, mCameraStateCallback, mBackgroundHandler)
    }

    private fun startPreview() {
        val mSurfaceTexture: SurfaceTexture = mPreview!!.surfaceTexture!!
        mSurfaceTexture.setDefaultBufferSize(mPreview!!.width, mPreview!!.height)
        val previewSurface = Surface(mSurfaceTexture)
        try {
            mCaptureRequestBuilder =
                mCameraDevice!!.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
            mCaptureRequestBuilder!!.addTarget(previewSurface)
            // val sessionConfiguration:SessionConfiguration=SessionConfiguration()
            mCameraDevice!!.createCaptureSession(
                Arrays.asList(previewSurface).toMutableList(),
                object :
                    CameraCaptureSession.StateCallback() {

                    override fun onConfigured(session: CameraCaptureSession) {
                        Logger.w(TAG, "onConfigured ...")
                        try {
                            session.setRepeatingRequest(
                                mCaptureRequestBuilder!!.build(), null, mBackgroundHandler
                            )
                        } catch (ex: CameraAccessException) {
                            Logger.e(
                                TAG,
                                "startPreview --> Failed to create a capture session , ${ex.message}",
                                ex
                            )
                        }
                    }

                    override fun onConfigureFailed(session: CameraCaptureSession) {
                        Logger.w(TAG, "onConfigureFailed ...")
                    }

                },
                null
            )
        } catch (ex: CameraAccessException) {
            Logger.e(
                TAG,
                "startPreview --> Failed to create a capture session , ${ex.message}",
                ex
            )
        }
    }

    private fun startBackgroundThread() {
        mBackgroundHandlerThread = HandlerThread("TextreView_HandlerThread")
        mBackgroundHandlerThread?.start()
        mBackgroundHandler = Handler(mBackgroundHandlerThread?.looper!!)

    }

    private fun stopBackgroundThread() {
        mBackgroundHandlerThread?.quitSafely()
        try {
            mBackgroundHandlerThread?.join()
            mBackgroundHandlerThread = null
            mBackgroundHandler = null
        } catch (ex: InterruptedException) {
            Logger.e(
                TAG,
                "stopBackgroundThread --> Failed to stop mBackgroundHandlerThread , ${ex.message}",
                ex
            )
        }
    }
}