package modularization.features.dashboard.connecting.services
/*

import android.app.Activity
import com.calendarevents.CalendarEventsPackage
import com.corbt.keepawake.KCKeepAwakePackage
import com.facebook.react.ReactInstanceManager
import com.facebook.react.ReactPackage
import com.facebook.react.bridge.NativeModule
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.common.LifecycleState
import com.facebook.react.devsupport.DevInternalSettings
import com.facebook.react.jscexecutor.JSCExecutorFactory
import com.facebook.react.modules.core.DeviceEventManagerModule.RCTDeviceEventEmitter
import com.facebook.react.shell.MainReactPackage
import com.facebook.react.uimanager.ViewManager
import com.facebook.soloader.SoLoader
import com.horcrux.svg.SvgPackage
import com.kevinresol.react_native_default_preference.RNDefaultPreferencePackage
import com.learnium.RNDeviceInfo.RNDeviceInfo
import com.ocetnik.timer.BackgroundTimerPackage
import com.oney.WebRTCModule.RTCVideoViewManager
import com.oney.WebRTCModule.WebRTCModule
import com.reactnativecommunity.asyncstorage.AsyncStoragePackage
import com.reactnativecommunity.netinfo.NetInfoPackage
import com.reactnativecommunity.webview.RNCWebViewPackage
import com.rnimmersive.RNImmersivePackage
import com.zmxv.RNSound.RNSoundPackage
import org.devio.rn.splashscreen.SplashScreenModule
import org.jitsi.meet.sdk.*
import org.jitsi.meet.sdk.net.NAT64AddrInfoModule
import org.webrtc.SoftwareVideoDecoderFactory
import org.webrtc.SoftwareVideoEncoderFactory
import org.webrtc.audio.AudioDeviceModule
import org.webrtc.audio.JavaAudioDeviceModule
import java.util.*

class ReactInstanceManagerHolder {
    private var reactInstanceManager: ReactInstanceManager? = null

    fun ReactInstanceManagerHolder() {}

    private fun createNativeModules(reactContext: ReactApplicationContext): List<NativeModule?>? {
        val nativeModules: MutableList<NativeModule?> = ArrayList<NativeModule?>(
            Arrays.asList(
                AndroidSettingsModule(reactContext),
                AppInfoModule(reactContext),
                AudioModeModule(reactContext),
                DropboxModule(reactContext),
                ExternalAPIModule(reactContext),
                JavaScriptSandboxModule(reactContext),
                LocaleDetector(reactContext),
                LogBridgeModule(reactContext),
                SplashScreenModule(reactContext),
                PictureInPictureModule(reactContext),
                ProximityModule(reactContext),
                WiFiStatsModule(reactContext),
                NAT64AddrInfoModule(reactContext)
            )
        )
        if (AudioModeModule.useConnectionService()) {
            nativeModules.add(RNConnectionService(reactContext))
        }
        val options = WebRTCModule.Options()
        val adm: AudioDeviceModule =
            JavaAudioDeviceModule.builder(reactContext).createAudioDeviceModule()
        options.setAudioDeviceModule(adm)
        options.setVideoDecoderFactory(SoftwareVideoDecoderFactory())
        options.setVideoEncoderFactory(SoftwareVideoEncoderFactory())
        nativeModules.add(WebRTCModule(reactContext, options))
        return nativeModules
    }

    private fun createViewManagers(reactContext: ReactApplicationContext): List<ViewManager<*, *>?>? {
        return Arrays.asList(RTCVideoViewManager())
    }

    fun emitEvent(eventName: String?, data: Any?) {
        val reactInstanceManager = getReactInstanceManager()
        if (reactInstanceManager != null) {
            val reactContext = reactInstanceManager.currentReactContext
            if (reactContext != null) {
                (reactContext.getJSModule(RCTDeviceEventEmitter::class.java) as RCTDeviceEventEmitter).emit(
                    eventName!!, data
                )
            }
        }
    }

    fun <T : NativeModule?> getNativeModule(nativeModuleClass: Class<T>?): T? {
        val reactContext =
            if (reactInstanceManager != null) reactInstanceManager!!.currentReactContext else null
        return reactContext?.getNativeModule(nativeModuleClass)
    }

    fun getCurrentActivity(): Activity? {
        val reactContext =
            if (reactInstanceManager != null) reactInstanceManager!!.currentReactContext else null
        return reactContext?.currentActivity
    }

    fun getReactInstanceManager(): ReactInstanceManager? {
        return reactInstanceManager
    }

    fun initReactInstanceManager(activity: Activity) {
        if (reactInstanceManager == null) {
            SoLoader.init(activity, false)
            val packages: ArrayList<*> = ArrayList<Any?>(
                Arrays.asList(
                    CalendarEventsPackage(),
                    KCKeepAwakePackage(),
                    MainReactPackage(),
                    SvgPackage(),
                    RNDefaultPreferencePackage(),
                    RNDeviceInfo(),
                    BackgroundTimerPackage(),
                    AsyncStoragePackage(),
                    NetInfoPackage(),
                    RNCWebViewPackage(),
                    RNImmersivePackage(),
                    RNSoundPackage(),
                    object : ReactPackageAdapter() {
                        fun createNativeModules(reactContext: ReactApplicationContext): List<NativeModule> {
                            return ReactInstanceManagerHolder.createNativeModules(reactContext)
                        }

                        fun createViewManagers(reactContext: ReactApplicationContext): List<ViewManager<*, *>?>? {
                            return ReactInstanceManagerHolder.createViewManagers(reactContext)
                        }
                    })
            )
            try {
                val googlePackageClass =
                    Class.forName("co.apptailor.googlesignin.RNGoogleSigninPackage")
                val constructor = googlePackageClass.getConstructor()
                packages.add(constructor.newInstance() as ReactPackage)
            } catch (var4: Exception) {
            }
            val jsFactory = JSCExecutorFactory("", "")
            reactInstanceManager =
                ReactInstanceManager.builder().setApplication(activity.application)
                    .setCurrentActivity(activity).setBundleAssetName("index.android.bundle")
                    .setJSMainModulePath("index.android").setJavaScriptExecutorFactory(jsFactory)
                    .addPackages(packages).setUseDeveloperSupport(false)
                    .setInitialLifecycleState(LifecycleState.RESUMED).build()
            val devSettings =
                reactInstanceManager.getDevSupportManager().devSettings as DevInternalSettings
            if (devSettings != null) {
                devSettings.isBundleDeltasEnabled = false
            }
            JitsiMeetUncaughtExceptionHandler.register()
        }
    }

}*/
