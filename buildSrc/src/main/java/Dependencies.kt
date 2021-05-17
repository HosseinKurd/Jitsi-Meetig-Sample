import org.gradle.api.JavaVersion

object Configs {
    const val applicationId = "com.hosseinkurd.app.jitsimeetigsample"
    const val compileSdkVersion = 30
    const val buildToolsVersion = "30.0.3"
    const val minSdkVersion = 21
    const val targetSdkVersion = 30
    const val versionCode = 1
    const val versionName = "1.0.0"
    val javaVersion = JavaVersion.VERSION_1_8
}

object Versions {

    const val androidx_appcompat = "1.2.0"
    const val androidx_annotation = "1.2.0"
    const val androidx_material = "1.3.0"
    const val androidx_core = "1.3.2"
    const val androidx_loader_version = "1.1.0"
    const val androidx_recyclerview = "1.1.0"
    const val androidx_navigation = "2.0.0"
    const val androidx_constraintLayout = "2.0.4"
    const val androidx_fragment_version = "1.2.5"
    const val androidx_fragment_ktx_version = "1.3.3"
    const val androidx_drawerlayout_version = "1.1.1"
    const val androidx_viewpager_v2 = "1.0.0"
    const val androidx_swipe_refresh = "1.1.0"
    const val google_material_version = "1.3.0-alpha01"
    const val junit = "4.12"
    const val ext = "1.1.1"
    const val androidx_espresso = "3.2.0"
    const val androidx_testing = "1.1.1"
    const val gradleandroid = "4.0.0"
    const val kotlin = "1.3.20"
    const val kotlin_stdlib_version = "1.3.61"
    const val gradleversions = "0.28.0"
    const val jakewharton_butterknife = "10.2.1"
    const val jakewharton_butterknife_compiler = "10.2.1"
    const val navigation = "2.3.0"
    const val lifecycle_extensions_version = "2.2.0"
    const val lifecycle_viewmodel_version = "1.1.0"
    const val lifecycle_viewmodel_ktx_version = "2.3.1"
    const val lifecycle_livedata_version = "1.1.0"
    const val lifecycle_livedata_ktx_version = "2.3.1"
    const val spinKit_version = "1.4.0"
    const val lifecycle_runtime_version = "2.3.0-alpha06"
    const val magical_video_player_version = "1.0.15"
    const val viewpager_dots_indicator = "4.1.2"
    const val google_sign_in = "18.0.0"
    const val alerter_version = "5.1.2"
    const val google_service_version = "4.3.3"
    const val firebase_gradle = "2.2.0"
    const val firebase_analytics = "17.4.4"
    const val firebase_crashlytics = "17.1.1"
    const val firebase_messaging = "20.2.3"
    const val jetBrains_annotations_version = "16.0.1"
    const val meow_bottom_navigation_version = "1.0.0-alpha"
    const val glide_bumptech_version = "4.12.0"
    const val chatkit_version = "3.0.30"
    const val squareup_retrofit2_version = "2.9.0"
    const val room_db_version = "2.2.5"
    const val gson_converter_version = "2.5.0"
    const val mapbox_version = "9.3.0"
    const val mapbox_plugin_annotation_version = "0.8.0"
    const val mapbox_plugin_markerView_version = "0.4.0"
    const val rele_version = "1.3.2"
    const val range_seekBar_version = "1.3"
    const val seek_arc_version = "v1.1"
    const val persian_date_picker_version = "1.0.6"
    const val persian_date_converter_version = "0.8"
    const val mkLoader_version = "1.4.0"
    const val lawone_sdk_version = "1.2.290"
    const val easy_image_version = "3.2.0"

}

object Deps {

    const val androidx_appcompat = "androidx.appcompat:appcompat:${Versions.androidx_appcompat}"
    const val androidx_annotation = "androidx.annotation:annotation:${Versions.androidx_annotation}"
    const val androidx_material = "com.google.android.material:material:${Versions.androidx_material}"
    const val androidx_core = "androidx.core:core-ktx:${Versions.androidx_core}"
    const val androidx_loader = "androidx.loader:loader:${Versions.androidx_loader_version}"
    const val androidx_constraintlayout = "androidx.constraintlayout:constraintlayout:${Versions.androidx_constraintLayout}"
    const val androidx_navigation_fragment = "androidx.navigation:navigation-fragment-ktx:${Versions.androidx_navigation}"
    const val androidx_navigation_ui = "androidx.navigation:navigation-ui-ktx:${Versions.androidx_navigation}"
    const val androidx_fragment = "androidx.fragment:fragment:${Versions.androidx_fragment_version}"
    const val androidx_fragment_ktx = "androidx.fragment:fragment-ktx:${Versions.androidx_fragment_ktx_version}"
    const val androidx_recyclerview = "androidx.recyclerview:recyclerview:${Versions.androidx_recyclerview}"
    const val androidx_viewpager_v2 = "androidx.viewpager2:viewpager2:${Versions.androidx_viewpager_v2}"
    const val androidx_drawerlayout = "androidx.drawerlayout:drawerlayout:${Versions.androidx_drawerlayout_version}"
    const val androidx_swipe_refresh = "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.androidx_swipe_refresh}"
    const val testlib_junit = "junit:junit:${Versions.junit}"
    const val testlib_ext = "androidx.test.ext:junit:${Versions.ext}"
    const val testandroidx_rules = "androidx.test:rules:${Versions.androidx_testing}"
    const val testandroidx_runner = "androidx.test:runner:${Versions.androidx_testing}"
    const val chatkit = "com.github.HamidrezaAmz:ChatKitLight:${Versions.chatkit_version}"
    const val testandroidx_espressocore = "androidx.test.espresso:espresso-core:${Versions.androidx_espresso}"
    const val tools_gradleandroid = "com.android.tools.build:gradle:${Versions.gradleandroid}"
    const val tools_kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val tools_kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin_stdlib_version}"
    const val tools_gradleversions = "com.github.ben-manes:gradle-versions-plugin:${Versions.gradleversions}"
    const val jakewharton_butterknife = "com.jakewharton:butterknife:${Versions.jakewharton_butterknife}"
    const val jakewharton_butterknife_compiler = "com.jakewharton:butterknife-compiler:${Versions.jakewharton_butterknife_compiler}"
    const val nav_ui = "androidx.navigation:navigation-ui:${Versions.navigation}"
    const val alerter = "com.tapadoo.android:alerter:${Versions.alerter_version}"
    const val nav_fragment = "androidx.navigation:navigation-fragment:${Versions.navigation}"
    const val lifecycle_extensions = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycle_extensions_version}"
    const val lifecycle_viewmodel = "androidx.lifecycle:lifecycle-viewmodel:${Versions.lifecycle_viewmodel_version}"
    const val lifecycle_viewmodel_ktx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle_viewmodel_ktx_version}"
    const val lifecycle_livedata = "androidx.lifecycle:lifecycle-livedata:${Versions.lifecycle_livedata_version}"
    const val lifecycle_livedata_ktx = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle_livedata_ktx_version}"
    const val lifecycle_runtime = "androidx.lifecycle:lifecycle-runtime:${Versions.lifecycle_runtime_version}"
    const val magical_video_player = "com.github.HamidrezaAmz:MagicalExoPlayer:${Versions.magical_video_player_version}"
    const val viewpager_dots_indicator = "com.tbuonomo.andrui:viewpagerdotsindicator:${Versions.viewpager_dots_indicator}"
    const val google_sign_in = "com.google.android.gms:play-services-auth:${Versions.google_sign_in}"
    const val firebase_gradle = "com.google.firebase:firebase-crashlytics-gradle:${Versions.firebase_gradle}"
    const val firebase_analytics = "com.google.firebase:firebase-analytics:${Versions.firebase_analytics}"
    const val firebase_crashlytics = "com.google.firebase:firebase-crashlytics:${Versions.firebase_crashlytics}"
    const val firebase_messaging = "com.google.firebase:firebase-messaging:${Versions.firebase_messaging}"
    const val google_service = "com.google.gms:google-services:${Versions.google_service_version}"
    const val jetBrains_annotations = "org.jetbrains:annotations:${Versions.jetBrains_annotations_version}"
    const val spinKit = "com.github.ybq:Android-SpinKit:${Versions.spinKit_version}"
    const val meow_bottom_navigation = "com.github.HamidrezaAmz:MeowBottomNavigation:${Versions.meow_bottom_navigation_version}"
    const val glide_bumptech = "com.github.bumptech.glide:glide:${Versions.glide_bumptech_version}"
    const val glide_bumptech_compiler = "com.github.bumptech.glide:compiler:${Versions.glide_bumptech_version}"
    const val squareup_retrofit2 = "com.squareup.retrofit2:retrofit:${Versions.squareup_retrofit2_version}"
    const val persian_date_picker = "com.github.mohammadfrh:Persian-Date-Picker:${Versions.persian_date_picker_version}"
    const val room_db = "androidx.room:room-runtime:${Versions.room_db_version}"
    const val room_db_compiler = "androidx.room:room-compiler:${Versions.room_db_version}"
    const val room_db_annotation = "android.arch.persistence.room:compiler:${Versions.room_db_version}"
    const val gson_converter = "com.squareup.retrofit2:converter-gson:${Versions.gson_converter_version}"
    const val lawone_sdk = "com.sdk.lawone:android:${Versions.lawone_sdk_version}"
    const val mapbox_annotation = "com.mapbox.mapboxsdk:mapbox-android-plugin-annotation-v9:${Versions.mapbox_plugin_annotation_version}"
    const val mapbox_markerView = "com.mapbox.mapboxsdk:mapbox-android-plugin-markerview-v9:${Versions.mapbox_plugin_markerView_version}"
    const val rele = "com.github.HamidrezaAmz:Rele:${Versions.rele_version}"
    const val range_seekBar = "io.apptik.widget:multislider:${Versions.range_seekBar_version}"
    const val seek_arc = "com.github.Triggertrap:SeekArc:${Versions.seek_arc_version}"
    const val persian_date = "com.github.samanzamani.persiandate:PersianDate:${Versions.persian_date_converter_version}"
    const val mkLoader = "com.tuyenmonkey:mkloader:${Versions.mkLoader_version}"
    const val easy_image = "com.github.jkwiecien:EasyImage:${Versions.easy_image_version}"
}